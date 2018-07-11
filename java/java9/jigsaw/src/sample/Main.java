package sample;

public class Main {
    public static void main(String[] args) {
        ModuleLayer bootLayer = ModuleLayer.boot();

        ClassLoader javaBaseModuleClassLoader = bootLayer.findLoader("java.base");
        System.out.println("java.base module ClassLoader => " + javaBaseModuleClassLoader);

        ClassLoader javaSqlModuleClassLoader = bootLayer.findLoader("java.compiler");
        System.out.println("java.sql module ClassLoader => " + javaSqlModuleClassLoader.getName());

        ClassLoader sampleModuleClassLoader = bootLayer.findLoader("sample");
        System.out.println("sample module ClassLoader => " + sampleModuleClassLoader.getName());
    }
}