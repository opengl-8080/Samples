package sample.junit5;

import org.junit.gen5.api.extension.InstancePostProcessor;
import org.junit.gen5.api.extension.TestExtensionContext;

public class MyExtend implements InstancePostProcessor {

    @Override
    public void postProcessTestInstance(TestExtensionContext context) throws Exception {
        Object testInstance = context.getTestInstance();
        System.out.println(testInstance.getClass());
    }
}
