package sample.junit5;

import java.lang.reflect.Parameter;

import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.MethodInvocationContext;
import org.junit.gen5.api.extension.MethodParameterResolver;
import org.junit.gen5.api.extension.ParameterResolutionException;

public class MyExtend implements MethodParameterResolver {

    @Override
    public boolean supports(Parameter parameter, MethodInvocationContext methodInvocationContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        System.out.println("supports()");
        return String.class.equals(parameter.getType());
    }

    @Override
    public Object resolve(Parameter parameter, MethodInvocationContext methodInvocationContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        System.out.println("resolve()");
        return "hoge";
    }
}