package sample.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path source = Paths.get("src/main/java/sample/javaparser/ParseCommentSample.java");
        
        try {
            CompilationUnit unit = JavaParser.parse(source);

            System.out.println("unit orphan comment > " + unit.getOrphanComments());

            unit.accept(new VoidVisitorAdapter<Void>() {
                @Override
                public void visit(ClassOrInterfaceDeclaration n, Void arg) {
                    System.out.println("<<ClassOrInterfaceDeclaration>>");
                    System.out.println("comment > " + n.getComment());
                    System.out.println("orphan comment > " + n.getOrphanComments());
                    super.visit(n, arg);
                }

                @Override
                public void visit(FieldDeclaration n, Void arg) {
                    System.out.println("<<FieldDeclaration>>");
                    System.out.println("comment > " + n.getComment());
                    super.visit(n, arg);
                }
            }, null);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
