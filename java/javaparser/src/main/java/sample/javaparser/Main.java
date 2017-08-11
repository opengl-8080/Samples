package sample.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path source = Paths.get("src/main/java/sample/javaparser/ToStringSample.java");
        
        try {
            CompilationUnit unit = JavaParser.parse(source);
            System.out.println(unit);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
