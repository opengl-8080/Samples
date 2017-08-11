package sample.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path source = Paths.get("src/main/java/sample/javaparser/Main.java");
        
        try {
            CompilationUnit unit = JavaParser.parse(source);
            
            unit.accept(new MyVoidVisitor(), "ARG!!");
            
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}