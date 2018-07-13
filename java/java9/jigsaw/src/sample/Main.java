package sample;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Set;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        ModuleLayer bootLayer = ModuleLayer.boot();

        // foo をルート・モジュールとしたモジュールグラフ(Configuration)を作成
        ModuleFinder moduleFinder = ModuleFinder.of(Paths.get("foo/classes"));
        ModuleFinder emptyFinder = ModuleFinder.of();
        Set<String> roots = Set.of("foo");

        Configuration bootConfiguration = bootLayer.configuration();
        Configuration newConfiguration = Configuration.resolve(moduleFinder, List.of(), emptyFinder, roots);

        // 新しい ModuleLayer を作成
        ModuleLayer newLayer = ModuleLayer.defineModulesWithOneLoader(newConfiguration, List.of(), ClassLoader.getSystemClassLoader()).layer();

        // foo モジュールから Foo クラスをロードして hello() メソッドを実行
        ClassLoader fooClassLoader = newLayer.findLoader("foo");
        Class<?> fooClass = fooClassLoader.loadClass("foo.Foo");
        Object fooInstance = fooClass.getConstructor().newInstance();
        Method helloMethod = fooClass.getMethod("hello");
        helloMethod.invoke(fooInstance);
    }
}