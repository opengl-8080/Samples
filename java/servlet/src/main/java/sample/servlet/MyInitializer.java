package sample.servlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

@HandlesTypes({MyInterface.class, ConcreatClass.class, MyAnnotation.class})
public class MyInitializer implements ServletContainerInitializer {
    
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext ctx) throws ServletException {
        System.out.println("*****************************************************************");
        System.out.println("[MyInitializer] set=" + set);
        System.out.println("*****************************************************************");
    }
}
