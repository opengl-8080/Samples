package sample.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path source = Paths.get("src/main/java/sample/javaparser/ParseCommentSample.java");
        
        try {
            CompilationUnit unit = JavaParser.parse(source);
            
            unit.accept(new VoidVisitorAdapter<Void>() {
                @Override
                public void visit(LineComment n, Void arg) {
                    System.out.println(n);
                    super.visit(n, arg);
                }
            }, null);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
