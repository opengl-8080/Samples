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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main().execute();
    }
    
    private final Path tmpDirPath;
    private final Path srcDirPath;
    private final Path classDirPath;

    public Main() {
        String a = "";
        switch (a) {case "hoge":System.out.println(); break;default:System.out.println();}
        
        try {
            this.tmpDirPath = this.createTempDirectory();
            this.srcDirPath = this.createSrcDirectory();
            this.classDirPath = this.createClassDirectory();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    private Path createTempDirectory() throws IOException {
        Path path = Files.createTempDirectory(null);
        this.deleteOnExit(path);
        return path;
    }

    private Path createSrcDirectory() throws IOException {
        Path path = this.tmpDirPath.resolve("src");
        Files.createDirectories(path);
        this.deleteOnExit(path);
        return path;
    }

    private Path createClassDirectory() throws IOException {
        Path path = this.tmpDirPath.resolve("classes");
        Files.createDirectories(path);
        this.deleteOnExit(path);
        return path;
    }


    public void execute() throws IOException {
        this.copySourceCode();
        this.compile();
        this.run();
    }

    private void run() {
        try {
            URL url = this.classDirPath.toUri().toURL();
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{url}, Main.class.getClassLoader());
            Class<?> clazz = Class.forName("TestMain", true, classLoader);
            Method mainMethod = clazz.getMethod("main", String[].class);
            Object args = new String[0];
            mainMethod.invoke(null, args);
        } catch (MalformedURLException e) {
            throw new UncheckedIOException(e);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void compile() {
        try {
            List<String> sourceFileList = Files.walk(this.srcDirPath)
                                            .filter(path -> Files.isRegularFile(path))
                                            .map(path -> path.toAbsolutePath().toString())
                                            .collect(Collectors.toList());
            
            List<String> args = new ArrayList<>();
            args.add("-d");
            args.add(this.classDirPath.toString());
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
    
    private void copySourceCode() throws IOException {
        JarFile jarFile = this.findThisJarFile();

        this.forEachCompileTarget(jarFile, jarEntry -> {
            String entryName = jarEntry.getName();
            Path outputPath = this.tmpDirPath.resolve(Paths.get(entryName));

            Files.createDirectories(outputPath.getParent());
            this.deleteOnExit(outputPath.getParent());

            try (InputStream entryInputStream = Main.class.getResourceAsStream(entryName)) {
                Files.copy(entryInputStream, outputPath);
                this.deleteOnExit(outputPath);
            }
        });
    }
    
    private JarFile findThisJarFile() throws IOException {
        URL location = Main.class.getProtectionDomain().getCodeSource().getLocation();
        return new JarFile(location.getFile());
    }
    
    private void forEachCompileTarget(JarFile jarFile, JarEntryConsumer consumer) {
        jarFile.stream()
                .filter(this::isCompileTarget)
                .forEach(jarEntry -> {
                    try {
                        consumer.consume(jarEntry);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
    }
    
    private boolean isCompileTarget(JarEntry entry) {
        String name = entry.getName();
        return !entry.isDirectory()
                && name.startsWith("src/");
    }
    
    private void deleteOnExit(Path path) {
//        path.toFile().deleteOnExit();
    }
    
    interface JarEntryConsumer {
        void consume(JarEntry jarEntry) throws IOException;
    }
}
