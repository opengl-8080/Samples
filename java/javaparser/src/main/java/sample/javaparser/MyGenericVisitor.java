package sample.javaparser;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;

public class MyGenericVisitor extends GenericVisitorAdapter<Integer, String> {
    
    @Override
    public Integer visit(VariableDeclarator n, String arg) {
        System.out.println(arg);
        System.out.println(n);
        super.visit(n, arg);
        return -1;
    }
}
