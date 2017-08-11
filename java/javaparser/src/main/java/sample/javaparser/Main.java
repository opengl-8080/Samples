package sample.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.UnparsableStmt;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path source = Paths.get("src/main/java/sample/javaparser/TargetClass.java");
        
        try {
            CompilationUnit unit = JavaParser.parse(source);
            
            unit.accept(new VoidVisitorAdapter<Void>() {
                
                @Override
                public void visit(UnknownType n, Void arg) {
                    System.out.println("****************************************");
                    System.out.println(n.getParentNode().get().getChildNodes());
                    super.visit(n, arg);
                }
            }, null);

        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
