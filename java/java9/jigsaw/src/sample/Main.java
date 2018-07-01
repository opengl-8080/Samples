package sample;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;

public class Main {
    public static void main(String[] args) {
        ModuleFinder
            .ofSystem()
            .findAll()
            .stream()
            .map(ModuleReference::descriptor)
            .map(ModuleDescriptor::name)
            .sorted()
            .forEach(System.out::println);
    }
}