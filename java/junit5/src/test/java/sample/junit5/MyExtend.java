package sample.junit5;

import org.junit.gen5.api.extension.BeforeEachExtensionPoint;
import org.junit.gen5.api.extension.TestExtensionContext;

public class MyExtend implements BeforeEachExtensionPoint {

    @Override
    public void beforeEach(TestExtensionContext context) throws Exception {
        System.out.println("MyExtend.beforeEach()");
    }
}