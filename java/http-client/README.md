# HTTP クライアント
## ドキュメント
- [java.net.http (Java SE 11 & JDK 11 )](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/package-summary.html)
- [jdk.incubator.http (Java SE 10 & JDK 10 )](https://docs.oracle.com/javase/jp/10/docs/api/jdk/incubator/http/package-summary.html)

## 概要
- 主要なクラス・インターフェースは４つ
    - [HttpClient](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.html)
    - [HttpRequest](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpRequest.html)
    - [HttpResponse](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpResponse.html)
    - [WebSocket](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/WebSocket.html)
- API は非同期で実行される
    - 同期的に実行する機能も提供している
    - 非同期で実行した場合は、 [CompletableFuture](https://docs.oracle.com/javase/jp/10/docs/api/java/util/concurrent/CompletableFuture.html) 型で返される
        - この API が返す `CompletableFuture` の [obtrudeValue() メソッド](https://docs.oracle.com/javase/jp/10/docs/api/java/util/concurrent/CompletableFuture.html#obtrudeValue(T)) と [obtrudeException() メソッド](https://docs.oracle.com/javase/jp/10/docs/api/java/util/concurrent/CompletableFuture.html#obtrudeException(java.lang.Throwable)) は、常に `UnsupportedOperationException` をスローする

## HttpClient
- 複数の `HttpRequest` に共通する構成を持つクラス
- すべての HTTP リクエストは、このクラスを使って送信する
- このクラスのインスタンスは不変
- ビルダーを使って生成する
    - [HttpClient.newBuilder()](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.html#newBuilder())
- すべてデフォルト設定でいい場合は、単純なファクトリメソッドも用意されている
    - [HttpClient.newHttpClient()](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.html#newHttpClient())
- 次のことを設定できる
    - 認証方法
        - [Authenticator](https://docs.oracle.com/javase/jp/10/docs/api/java/net/Authenticator.html)
    - クッキーの制御
        - [CookieHandler](https://docs.oracle.com/javase/jp/10/docs/api/java/net/CookieHandler.html)
    - スレッドの Executor
    - リダイレクトポリシー
        - [HttpRedirect](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpClient.Redirect.html)
    - プロキシ
        - [ProxySelector](https://docs.oracle.com/javase/jp/10/docs/api/java/net/ProxySelector.html)
    - SSL 関係の設定
        - [SSLContext](https://docs.oracle.com/javase/jp/10/docs/api/javax/net/ssl/SSLContext.html)
        - [SSLParameters](https://docs.oracle.com/javase/jp/10/docs/api/javax/net/ssl/SSLParameters.html)

## HttpRequest
- １つの HTTP リクエスト
- これもビルダーで構築する
    - [HttpRequest.newBuilder()](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpRequest.html#newBuilder())
    - [HttpRequest.newBuilder(URI)](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpRequest.html#newBuilder(java.net.URI))
- ビルダーで設定できる情報
    - URI
    - HTTP メソッド
    - ヘッダー
    - タイムアウト時間
    - `BodyPublisher`
        - 詳細後述
- `copy()` メソッドで、ビルダーのインスタンスをコピーできる
    - URI は同じだけどパラメータがちょっとだけ違うリクエストを投げたいときなどに、 URI までを設定したビルダーをコピーして作ることができる
- [BodyPublisher](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpRequest.BodyPublisher.html)
    - HTTP リクエストにボディを持つメソッドを送信するときに使用する
    - ボディを提供する
    - Reactive Streams の `Flow.Publisher` を継承したインターフェース
    - データの提供元を表す
    - [HttpRequest.BodyPublishers](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpRequest.BodyPublishers.html) にファクトリメソッドが用意されている
        - `ofString()`, `ofFile()` など

## HttpResponse
- HTTP リクエストのレスポンス
- レスポンスのボディをどう扱うかについては、リクエストを送信するときに [HttpResponse.BodyHandler](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpResponse.BodyHandler.html) で指定する
- 標準の `BodyHandler` は [HttpResponse.BodyHandlers](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpResponse.BodyHandlers.html) のファクトリメソッドで取得できる
    - [ofString()](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpResponse.BodyHandlers.html#ofString()) なら、レスポンスボディをそのまま文字列で受け取る
    - [ofFile()](https://docs.oracle.com/en/java/javase/11/docs/api/java.net.http/java/net/http/HttpResponse.BodyHandlers.html#ofFile(java.nio.file.Path)) なら、レスポンスをそのままファイルに書き込む

### CompletableFuture
- `HttpRequest` を非同期で実行した場合、戻り値は [CompletableFuture](https://docs.oracle.com/javase/jp/10/docs/api/java/util/concurrent/CompletableFuture.html) で返される
- [CompletionStage](https://docs.oracle.com/javase/jp/10/docs/api/java/util/concurrent/CompletionStage.html) を実装している
    - 他の `CompletionStage` が完了したときに実行する、非同期の可能性のある処理を表す
    - あるステージは他のステージの次に実行するように依存を定義できるようになっている
        - 実行の条件に指定できるパターン
            - １つのステージの完了
            - ２つのステージの
                - 片方の完了
                    - どちらのステージの結果が来るかは分からない
                - 両方の完了
    - ステージで実行できる処理は３種類ある
        - 引数を必要とする処理
            - `thenApply(Consumer)`
        - 引数を必要とし、結果を返す処理
            - `thenAccept(Function)`
        - 引数も結果も必要としない処理
            - `thenRun(Runnable)`
    - ステージの処理を実行するためのスレッドを指定する方法が３つある
        - デフォルト
            - 現在ステージを実行しているものと同じスレッドを使用する
            - `thenApply()` のように、メソッド名に `Async` がついていない
        - 非同期(Executor 未指定)
            - 内部で管理されているスレッドで実行する
            - `thenApplyAsync()` のようにメソッド名に `Async` がついている
        - 非同期(Executor 指定)
            - 指定された `Executor` から取得したスレッド上で実行される
            - `thenApply(Consumer, Executor)` のように引数に `Executor` を受け取る
    - [handle()](https://docs.oracle.com/javase/jp/10/docs/api/java/util/concurrent/CompletionStage.html#handle(java.util.function.BiFunction))
        - 正常終了、異常終了のいずれの場合も実行されるステージをつなげる
        - 関数は正常終了時の値と異常終了時の例外を受け取ることができる
            - null の可能性もある
    - [whenComplete()](https://docs.oracle.com/javase/jp/10/docs/api/java/util/concurrent/CompletionStage.html#whenComplete(java.util.function.BiConsumer))
        - `handle()` 同様、正常終了・異常終了時に呼ばれる
        - `handle()` との違いは、 `handle()` に渡したアクションは値を返すことができるが、 `whenComplete()` に渡したアクションは値を返さない
    - [exceptionally()](https://docs.oracle.com/javase/jp/10/docs/api/java/util/concurrent/CompletionStage.html#exceptionally(java.util.function.Function))
        - 異常終了したときに呼び出す処理を繋げられる
