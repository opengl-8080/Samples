package since9;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.validator.Java9Validator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        String srcDir = System.getenv("JDK9_SRC_DIR");
        
        ParserConfiguration configuration = new ParserConfiguration();
        configuration.setValidator(new Java9Validator());
        JavaParser.setStaticConfiguration(configuration);
        
        List<Callable<Set<Class<?>>>> tasks = new ArrayList<>();

        File[] moduleDirs = new File(srcDir).listFiles();
        if (moduleDirs == null) {
            throw new IllegalStateException("module directories is null.");
        }
        for (File moduleDir : moduleDirs) {
            String moduleName = moduleDir.getName();
            
            Callable<Set<Class<?>>> task = () -> {
                System.out.println("[" + Thread.currentThread().getName() + "] " + moduleName + " begin.");
                Set<Class<?>> classes = new HashSet<>();
                
                Files.walkFileTree(moduleDir.toPath(), new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (this.isNotJavaFile(file)) {
                            return FileVisitResult.CONTINUE;
                        }

                        try {
                            CompilationUnit cu = JavaParser.parse(file);
                            cu.accept(new VoidVisitorAdapter<Void>() {
                                @Override
                                public void visit(JavadocComment n, Void arg) {
                                    if (this.isSince9(n)) {
                                        classes.add(this.getCommentedNodeClass(n));
                                    }
                                    super.visit(n, arg);
                                }
                                
                                private boolean isSince9(JavadocComment javadocComment) {
                                    Javadoc javadoc = javadocComment.parse();
                                    return javadoc.getBlockTags().stream()
                                            .anyMatch(tag -> tag.getTagName().equals("since") && tag.getContent().toText().equals("9"));
                                }
                                
                                private Class<?> getCommentedNodeClass(JavadocComment javadocComment) {
                                    return javadocComment.getCommentedNode()
                                            .map(Object::getClass)
                                            .orElseThrow(() -> new IllegalStateException("commented node not found"));
                                }
                            }, null);
                        } catch (Exception e) {
                            System.out.println("e=" + e + ", message=" + e.getMessage() + ", file=" + file);
                        }

                        return super.visitFile(file, attrs);
                    }
                    
                    private boolean isNotJavaFile(Path file) {
                        String name = file.getFileName().toString();
                        return !name.endsWith(".java")
                                || name.equals("module-info.java")
                                || name.equals("package-info.java");
                    }
                });

                System.out.println("[" + Thread.currentThread().getName() + "] " + moduleName + " end.");
                return classes;
            };

            tasks.add(task);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        Set<Class<?>> commentedNodeClasses = new HashSet<>();
        List<Future<Set<Class<?>>>> futures = executorService.invokeAll(tasks);
        for (Future<Set<Class<?>>> future : futures) {
            commentedNodeClasses.addAll(future.get());
        }
        
        commentedNodeClasses.forEach(System.out::println);

        executorService.shutdown();
    }
}
