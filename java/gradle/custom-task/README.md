https://docs.gradle.org/current/userguide/custom_tasks.html#header

- Gradle でタスクを定義する方法は２種類存在する
    - 普通に `task xxx {}` で定義する方法
        - そのビルドスクリプトでだけ使用するようなタスクを定義するのに適している
        - タスクの振る舞いをクロージャの中で定義する
    - 拡張タスク (enhanced task)
        - タスクの振る舞いは、タスク自身の中に存在する
        - 外部からは、振る舞いを調整するためのプロパティを指定する
        - 複数のビルドタスクで使いまわしやすくなる
- 使用言語
    - JVM で実行できる形になれば、なんでもOK
    - Groovy, Java, Kotlin
    - 静的型付言語のほうがパフォーマンスはいいらしい
- 実装を定義できる場所
    - ビルドスクリプトの中に直接書き込む
        - 作成するのは楽
        - そのスクリプト内でしか利用できない

------
https://docs.gradle.org/current/userguide/more_about_tasks.html

# Task outcomes
- タスクを実行すると、タスクに状態を表すラベルが付与される
    - `(no label) or EXECUTED`
        - タスクは実行すると判断された
    - `UP-TO-DATE`
        - タスクの出力が変化していない
        - タスクは出力を持つが、入力が変化していない
        - タスクが、 Gradle に対して出力が変化しないことを通知した
    - `FROM-CACHE`
        - タスクの出力結果が、以前のキャッシュから見つかった
    - `SKIPPED`
        - タスクは実行されなかった
        - コマンドラインから明示的に除外された
        - `onlyIf` で指定した条件が false を返した
    - `NO-SOURCE`
        - タスクを実行する必要はない
        - 入力に指定されているものがなかった

## Defining tasks
- `task('xxxx')`, `task('xxxx', type: Copy)` というタスク定義は、それぞれ
- `tasks.create('xxxx')`, `tasks.create('xxxx', Copy)` のシンタックスシュガーになっている
    - `tasks` は、 `TaskContainer` のインスタンス

## Locating tasks
- タスクの定義や依存関係を定義するために、タスクを取得（見つけ出す）必要が生まれる
- 動的プロパティの仕組みを利用して、 DSL 的にアクセスする方法
    - タスクを定義すると、 `project` に動的にタスク名のプロパティが追加される
    - 従って、タスク名をそのまま書くことで、直接タスクにアクセスできる
- `tasks` からパスを指定して検索する方法
    - `tasks[':foo:bar']` のように、パスをキーにして取得できる

## Configuring tasks
- `task foo(type: Copy)` のようにすることで、 `Copy` 型のタスクを生成できる
- この方法なら、名前を変えて複数の `Copy` 型のタスクを定義できる
- 前述の方法でタスクを取得すれば、あとは `Copy` が提供するプロパティにプログラミング的に値を設定できる
- しかし、 configuration block を利用した方法のほうが、より可読性の高い設定を記述できる
- configuration block の直下は設定フェーズで実行される点に注意（実行フェーズではない）
    - つまり、タスクが実行されなくても、 configuration block 内は実行される

## Passing arguments to a task constructor
- タスクのコンストラクタ引数に値を渡す方法がある
- コンストラクタを `@javax.inject.Inject` でアノテートする
    - `@Inject` でアノテートできるコンストラクタは１つだけ
- 呼び出し方は、 `task xxx(type: Xxx, constructorArgs: [...])`




