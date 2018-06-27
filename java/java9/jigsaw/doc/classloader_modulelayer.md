# ClassLoader と ModuleLayer の関係について
- ClassLoader はクラスを検索するときに ModuleLayer を参照する？
- 両者はどういう関係になっている？
- Module が ClassLoader を持っている
    - ただし、無名モジュールは ClassLoader がインスタンス変数として内部に持っている
    - ClassLoader 内部では、無名モジュールは利用されていない
        - Getter があるので、そこから取得した外部のどこかで設定や利用がされている？
- システムクラスローダの具体的な型は `jdk.internal.loader.ClassLoaders$AppClassLoader`
- ブートクラスローダ、プラットフォームクラスローダ、アプリケーションクラスローダ
    - すべて ClassLoaders クラスの private な内部クラスとして定義されている
    - どれも BuiltinClassLoader を継承しているため、仕組みとしてはそちらを見ればいい
    - それぞれの親子関係が定義されているくらいで、中身に特別な実装の差異はほぼない
        - アプリケーションクラスローダはちょっと独自実装あり
- BuiltinClassLoader は、「パッケージ名→モジュール参照」というマップを持っており、パッケージ名から使用するモジュール参照を解決できるようになっている
    - モジュールをロードしたときにキャッシュしておく
    - モジュール参照から ModuleReader を取り出し、リソースとして class ファイルを検索、クラスを読み込んでいる
- どのモジュールのロードはどのクラスローダを使うかは、 `ModuleLoaderMap` クラスで定義している
    - ブートローダ用・プラットフォームローダ用に配列で固定でモジュール名が定義されている
    - どれにも属さないモジュールはアプリケーションローダ扱いになる
- ModuleBootstrap 内で最初のモジュールグラフの構築が行われている
    - モジュールグラフが出来上がったら、各クラスローダの loadModule() を使ってモジュールとクラスローダを紐づけている
    - ※loadModule() は BuiltinClassLoader 固有のメソッドで普通の java.lang.ClassLoader にはない点に注意

# 疑問
- あるモジュールの型が、そのモジュールからは読み取ることのできない別のモジュールの型を参照していた場合、実行時にそれをどうやって判定している？
    - 実行時はクラスをロードしたタイミングでその辺の判定がされそうな気がする
    - となると、 ClassLoader の中でモジュールの定義を参照しなければならなくなりそうだが、 java.lang.ClassLoader はそもそも Module にほとんど依存していない
    - どうやって判定している？

```
Exception in thread "main" java.lang.NoClassDefFoundError: java/sql/Connection
        at sample/sample.Main.main(Main.java:7)
Caused by: java.lang.ClassNotFoundException: java.sql.Connection
        at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:582)
        at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:190)
        at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:499)
        ... 1 more
```

- 試しに、コンパイル時は普通に通るようにしておいて、実行時に module-info.class を requires していないものに置き換えて実行すると、実行時のエラーは `NoClassDefFoundError` になる
    - つまり、「パッケージが不可視」的なモジュール解決に関係する類のエラーではない！
    - モジュールグラフ的な解決はすり抜けていて、実行時は別に requires しているパッケージかどうかのチェックはしていないっぽい