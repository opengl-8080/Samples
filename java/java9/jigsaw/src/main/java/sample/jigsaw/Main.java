package sample.jigsaw;

//import java.sql.Connection;

import org.apache.commons.lang3.RandomStringUtils;

import java.lang.module.ModuleFinder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    
    public static void main(String[] args) {
        String text = RandomStringUtils.random(10, "1234567890");
        System.out.println(text);
    }
    
    private static void printModule() {
        System.out.println("[Boot]");
//        ModuleLayer
//                .boot()
//                .modules()
//                .stream()
//                .map(Module::getName)
//                .sorted()
//                .forEach(System.out::println);
//
        Map<String, List<Module>> map = new HashMap<>();
        ModuleLayer
                .boot()
                .modules()
                .forEach(module -> {
                    ClassLoader classLoader = module.getClassLoader();
                    String name = classLoader == null ? "no classloader" : classLoader.getName();
                    List<Module> list = map.computeIfAbsent(name, key -> new ArrayList<>());
                    list.add(module);
                });

        map.keySet().forEach(key -> {
            System.out.println("<<" + key + ">>");
            map.get(key).stream().map(Module::getName).sorted().forEach(System.out::println);
        });

        System.out.println("*******************************");
        System.out.println("[System]");
        ModuleFinder
                .ofSystem()
                .findAll()
                .stream()
                .map(ref -> ref.descriptor().name())
                .sorted()
                .forEach(System.out::println);
    }
}
