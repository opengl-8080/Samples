package provider;

import api.MyInterface;

public class MyProvider implements MyInterface {

    @Override
    public void method() {
        System.out.println("Hello World");
    }
}