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