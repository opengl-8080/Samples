# OpenJFX
- License
    - [GPL Classpath Exception](https://wiki.openjdk.java.net/display/OpenJFX/Main)
- Project
    - OpenJDK オープンソースコミュニティの一部として開発されている
- 開発方法
    - 2つの方法がある
        1. JavaFX SDK を使う方法
        2. Maven/Gradle などのビルドツールを使う方法
    - JavaFX SDK を使う方法
        - https://openjfx.io/openjfx-docs/#install-javafx に手順が書いてある
        - [Gluon](https://gluonhq.com/products/javafx/) のページからインストールする
        - 各プラットフォームごとに選択する
            - JavaFX <OS> SDK をダウンロードする
            - jmods は後述のカスタム JRE を作るときに使用する
        - SDK を任意の場所に unzip する
        - 任意の環境変数を定義し lib の下のパスを設定する
            - `PATH_TO_JFX=/path/to/javafx-sdk-11/lib`
        - コンパイル・実行時に `PATH_TO_JFX` をモジュールパスに設定する
            - `javac --module-path %PATH_TO_JFX% ...`
    - Maven/Gradle などのビルドツールを使う方法