package foo;

import bar.Bar;
import automatic.Automatic;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("==foo.Main==");
        System.out.println("bar = " + new Bar());
        Automatic automatic = new Automatic();
        System.out.println("automatic = " + automatic);
        automatic.method();
    }
}