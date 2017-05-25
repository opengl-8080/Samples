import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().execute(args);
    }

    private final Path tmpDirPath;
    private final Path srcDirPath;
    private final Path classDirPath;

    private Main() {
        try {
            this.tmpDirPath = this.createTempDirectory();
            this.srcDirPath = this.createSrcDirectory();
            this.classDirPath = this.createClassDirectory();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Files.walkFileTree(this.tmpDirPath, new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            try {
                                Files.delete(file);
                            } catch (IOException e) {
                                System.err.println("failed to delete file (" + file + ") : " + e.getMessage());
                                // continue to delete...
                            }
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            try {
                                Files.delete(dir);
                            } catch (IOException e) {
                                System.err.println("failed to delete directory (" + dir + ") : " + e.getMessage());
                                // continue to delete...
                            }
                            return FileVisitResult.CONTINUE;
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Path createTempDirectory() throws IOException {
        return Files.createTempDirectory(null);
    }

    private Path createSrcDirectory() throws IOException {
        Path path = this.tmpDirPath.resolve("src");
        Files.createDirectories(path);
        return path;
    }

    private Path createClassDirectory() throws IOException {
        Path path = this.tmpDirPath.resolve("classes");
        Files.createDirectories(path);
        return path;
    }


    public void execute(String[] commandLineArgs) throws IOException {
        this.copyResourceTarget();
        this.compile();
        this.copyNotCompileResource();
        this.initManifestFile();
        this.run(commandLineArgs);
    }

    private void initManifestFile() {
        try {
            Path metaInfDirPath = this.classDirPath.resolve("META-INF");
            Files.createDirectories(metaInfDirPath);
            Path manifest = metaInfDirPath.resolve("MANIFEST.MF");
            Files.write(manifest, "Start-Class: test.TestMain\n".getBytes("UTF-8"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void run(String[] commandLineArgs) {
        try {
            URL url = this.classDirPath.toUri().toURL();
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{url}, Main.class.getClassLoader());
            Class<?> clazz = Class.forName("org.springframework.boot.loader.JarLauncher", true, classLoader);
            Method mainMethod = clazz.getMethod("main", String[].class);
            mainMethod.invoke(null, (Object)commandLineArgs);
        } catch (MalformedURLException e) {
            throw new UncheckedIOException(e);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void compile() {
        try {
            List<String> sourceFileList = Files.walk(this.srcDirPath)
                    .filter(path -> Files.isRegularFile(path)
                            && path.getFileName().toString().endsWith(".java"))
                    .map(path -> path.toAbsolutePath().toString())
                    .collect(Collectors.toList());

            List<String> args = new ArrayList<>();
            args.add("-Xlint:none");
            args.add("-d");
            args.add(this.classDirPath.toString());
            args.add("-cp");
            args.add(this.srcDirPath.toString());
            args.add("-encoding");
            args.add("UTF-8");
            args.addAll(sourceFileList);

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            int returnCode = compiler.run(null, null, null, args.toArray(new String[args.size()]));

            if (returnCode != 0) {
                throw new RuntimeException("javac is not successful. (" + returnCode + ")");
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private void copyNotCompileResource() throws IOException {
        Files.walk(this.srcDirPath)
                .filter(path -> Files.isRegularFile(path) && !path.getFileName().toString().endsWith(".java"))
                .forEach(file -> {
                    Path outPath = this.classDirPath.resolve(this.srcDirPath.relativize(file));
                    try {
                        Files.createDirectories(outPath.getParent());
                        Files.copy(file, outPath);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
    }

    private void copyResourceTarget() throws IOException {
        JarFile jarFile = this.findThisJarFile();

        this.forEachResourceTarget(jarFile, jarEntry -> {
            String entryName = jarEntry.getName();
            Path outputPath = this.tmpDirPath.resolve(Paths.get(entryName));

            Files.createDirectories(outputPath.getParent());

            try (InputStream entryInputStream = Main.class.getResourceAsStream(entryName)) {
                Files.copy(entryInputStream, outputPath);
            }
        });
    }

    private JarFile findThisJarFile() throws IOException {
        URL location = Main.class.getProtectionDomain().getCodeSource().getLocation();
        return new JarFile(location.getFile());
    }

    private void forEachResourceTarget(JarFile jarFile, JarEntryConsumer consumer) {
        jarFile.stream()
                .filter(this::isResourceTarget)
                .forEach(jarEntry -> {
                    try {
                        consumer.consume(jarEntry);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
    }

    private boolean isResourceTarget(JarEntry entry) {
        String name = entry.getName();
        return !entry.isDirectory()
                && name.startsWith("src/");
    }

    interface JarEntryConsumer {
        void consume(JarEntry jarEntry) throws IOException;
    }
}
