package sample.jigsaw;

//import java.sql.Connection;

public class Main {
    
//    private Connection con;
    
    public static void main(String[] args) {
        System.out.println("Hello");
        ModuleLayer
                .boot()
                .modules()
                .stream()
                .map(Module::getName)
                .sorted()
                .forEach(System.out::println);

        System.out.println("[platform class loader]");
        ClassLoader platformClassLoader = ClassLoader.getPlatformClassLoader();
        platformClassLoader.getUnnamedModule().getPackages().stream().sorted().forEach(System.out::println);

        System.out.println("[system class loader]");
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        systemClassLoader.getUnnamedModule().getPackages().stream().sorted().forEach(System.out::println);
    }
}
