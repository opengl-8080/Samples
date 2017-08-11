package sample.javaparser;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MyVoidVisitor extends VoidVisitorAdapter<String> {

    @Override
    public void visit(VariableDeclarator n, String arg) {
        System.out.println("n > " + n);
        System.out.println("arg > " + arg);
        super.visit(n, arg);
    }
}
