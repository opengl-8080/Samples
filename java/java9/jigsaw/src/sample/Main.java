package sample;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        ModuleLayer bootLayer = ModuleLayer.boot();

        // foo をルートモジュールとしたモジュールグラフ(Configuration)を作成
        ModuleFinder moduleFinder = ModuleFinder.of(Paths.get("foo/classes"));
        ModuleFinder emptyFinder = ModuleFinder.of();
        Set<String> roots = Set.of("foo");

        Configuration bootConfiguration = bootLayer.configuration();
        Configuration newConfiguration = bootConfiguration.resolve(moduleFinder, emptyFinder, roots);

        // 新しい ModuleLayer を作成
        ModuleLayer newLayer = bootLayer.defineModulesWithOneLoader(newConfiguration, ClassLoader.getSystemClassLoader());

        // foo モジュールから Foo クラスをロードして hello() メソッドを実行
        ClassLoader fooClassLoader = newLayer.findLoader("foo");
        Class<?> fooClass = fooClassLoader.loadClass("foo.Foo");
        Object fooInstance = fooClass.getConstructor().newInstance();
        Method helloMethod = fooClass.getMethod("hello");
        helloMethod.invoke(fooInstance);
    }
}