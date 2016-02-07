package sample.junit5;

import org.junit.gen5.api.extension.ExceptionHandlerExtensionPoint;
import org.junit.gen5.api.extension.TestExtensionContext;

public class MyExtend implements ExceptionHandlerExtensionPoint {

    @Override
    public void handleException(TestExtensionContext context, Throwable throwable) throws Throwable {
        System.out.println("handleException() class=" + throwable.getClass() + ", message=" + throwable.getMessage());
    }
}