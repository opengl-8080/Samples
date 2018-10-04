package bar;

import java.net.URL;

public class Bar {
    public void checkFindResource(Class<?> clazz, String resource) {
        URL url = clazz.getResource(resource);
        System.out.println("@" + this.getClass().getSimpleName() + "[" + clazz.getSimpleName() + ".class.getResource(\"" + resource + "\") > " + url);
    }
}
