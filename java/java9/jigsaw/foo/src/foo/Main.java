package foo;

import bar.Bar;
import bar.internal.InternalBar;

public class Main {
    public static void main(String[] args) {
        Bar bar = new Bar();
        InternalBar ib = new InternalBar();
        Module unnamedModule = Thread.currentThread().getContextClassLoader().getUnnamedModule();
        unnamedModule.getPackages().forEach(packageName -> {
            boolean result = unnamedModule.isOpen(packageName);
            System.out.println(packageName + " is " + (result ? "opened." : "closed."));
        });
    }
}