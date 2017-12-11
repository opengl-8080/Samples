https://blog.gradle.org/java-9-support-update

# What Gradle supports as of version 4.2.1
> As of Gradle 4.2.1, building and running Java applications using major distributions of JDK 9 such as Oracle JDK9, OpenJDK9 and Azul JDK9 is fully supported.

Gradle 4.2.1 で、メジャーな JDK9 のディストリビューション(Oracle JDK9, OpenJDK9, Azul JDK9)を使って Java アプリケーションをビルド・実行することは完全にサポートされています.

> Further, cross-compilation (built by JDK9 but runs on JDK8) is supported.

さらに、クロスコンパイル（JDK9 でビルドしたものを JDK8 で動かす）もサポートされています.

> Some builds will break when upgrading to Java 9, regardless of build tool used.

なかには、ビルドツールを使っていることに関係なく Java 9 にアップグレードすることでビルドもあります.

> The Java team have made good and necessary changes to the JDK to facilitate better software architecture and security, but this has meant removing access to some APIs.

Java チームはソフトウェアのアーキテクチャとセキュリティをよくするために必要な良い変更を JDK に加えました.
しかし、この変更によりいくつかの API にはアクセスできなくなりました.

> Even if your project is ready, some tools and Gradle plugins have not yet been updated to work with Java 9.

もしあなたのプロジェクトが準備中なら、いくつかのツールと Gradle プラグインはまだ Java9 で動くためにアップデートされていません.

> There is no convenience methods for consuming and assembling Multi-Release JARs, but you can take a look at this MRJAR-gradle example if you desire to use them.

複数のリリース Jar を収集し消費する便利な手段はありません.
しかし、 MRJAR-gradle の例を見ることであなたが望むものが得られるでしょう.

# Java Modules aka Jigsaw Support
> If you’re not yet familiar with the Java 9 Platform Module System, also known as Project Jigsaw, you should read Project Jigsaw: Module System Quick-Start Guide.

> The motivation and terminology are well explained in The State of the Module System.

> A module is defined as “a named, self-describing collection of code and data” whereby packages are treated as code boundaries, and are explicitly exported and required.

> Non-exported packages are not visible to module consumers, and further 2 modules cannot export the same packages, nor can they have the same internal packages.

> This means that packages cannot be “split” or duplicated between multiple modules, or compilation will fail.

> Here is a guide that shows how to use Java modules with Gradle today.

> It walks you through the steps necessary to tell Gradle to use the modulepath and not classpath when compiling Java sources and patch modules for testing purposes.

> A bottom-up approach (convert libraries with no dependencies first) is recommended if you wish to incrementally convert to Java 9 modules.

> After all, modules are consumable as regular JARs.

> Be mindful of automatic modules when “legacy” JARs are added to the modulepath.

# Achieving encapsulation with the Java Library Plugin
> One of the 2 major goals of the Java 9 module system is to provide better software architecture through strong encapsulation.

> Gradle 3.4 introduced the Java Library Plugin that enforces strong encapsulation for libraries by separating api dependencies (those meant to be exposed to consumers) from implementation dependencies whose internals are not leaked to consumers.

> This, of course, does not eliminate use of Java classpaths as is stated as another goal of Java modules.

> You can learn about the motivation and usage of the Java Library Plugin in this post.

> It’s worth noting that the Java Library Plugin is useful for projects using Java 7 and above — you do not need to migrate to Java 9 to have some stronger encapsulation.

> Here’s what this means in practice, given this example library:

```groovy
apply plugin: 'java-library'

name = 'mylibrary'
group = 'com.mycompany'

dependencies {
   api project(':model')
   implementation 'com.google.guava:guava:18.0'
}
```

> Let’s presume we have an application that uses mylibrary.

```java
public class MyApplication {
    
    public static void main(String... args) {
        // This does not compile using 'java-library' plugin
        Set<String> strings = com.google.common.collect.ImmutableSet.of("Hello", "Goodbye");
        
        // This compiles and runs
        Foo foo = com.mycompany.model.internal.Foo();
        
        // This also compiles and runs
        Class clazz = MyApplication.class.getClassLoader().loadClass("com.mycompany.model.internal.Foo");
        Foo foo = (Foo) clazz.getConstructor().newInstance();
    }
}
```

> You can see that you get some of the benefits by adopting the Gradle’s Java Library plugin.

> If you are migrate to Java modules, you can use this rough mapping:

- implementation dependency => requires module declaration
- api dependency => requires transitive module declaration
- runtimeOnly dependency => requires static module declaration

# Next Steps
> Stay tuned for updates on first-class Java modules support in Gradle.

> You can use the Building Java 9 Modules guide to learn how to use Java modules with Gradle today.
