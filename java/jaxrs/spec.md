# JAX-RS とは
- Java API for RESTful Web Services の略。
- Java で RESTful な Web サービスを構築するために必要になる仕組み（API とか）を定義した仕様。
- 参照実装は [Jersey](https://jersey.java.net/)。
- Java EE の中では、珍しく人気が高い（気がする）。

# 用語定義
## Resource class
- `@Path` でアノテートされた Java クラス。
- REST における Web リソースと一致する。

## Root resource class
- リソースクラスのツリー構造と、サブリソースへのアクセスを提供する入り口となるクラス。

## Request method designator
- `@HttpMethod` でアノテートされたアノテーション。
- HTTP リクエストのメソッドに合わせてリソースメソッドのハンドリングを行うために使用する。

## Resource method
- リソースクラスが持つ、 Request method designator でアノテートされたメソッド。

## Sub-resource locator
- サブリソースを特定するための、リソースクラスのメソッド。

## Sub-resource method
- サブリソースのリクエストをハンドリングするために使用するリソースクラスのメソッド。

## Provider
- JAX-RS の拡張用インターフェースを実装したクラス。
- JAX-RS の機能を実行時に拡張する。

## Filter
- プロバイダーの一種。
- リクエストとレスポンスのフィルタリングをする。

## Entity Interceptor
- プロバイダーの一種。
- メッセージボディの読み書きに割り込んで処理を行うのに使用する。

## Invocation
- HTTP リクエストの設定ができるクライアント側の API オブジェクト。

## WebTarget
- Invocation を受け取るもの。
- URL で一意に特定できる。

## Link
- URI にメディアタイプや関連・タイトルなどのメタデータを追加したもの。

# Application クラス
- JAX-RS の設定は、 `Application` クラスのサブクラスによって提供される。
- Servlet API 3 の pluggability mechanism を使った Application クラスの自動設定をサポートすることが推奨されている。
    - `Application` を継承したクラスを作らない場合。
        - `web.xml` に `javax.ws.rs.core.Application` という名前で `<servlet>` を定義する。
    - `Application` を継承したクラスを作る場合。
        - 既存のサーブレットにハンドリングされている場合、どうなるかよくわからない。
            - `Application subclass handled by existing servlet` がどういう意味がよくわからん。
        - 既存のサーブレットにハンドリングされていない場合。
            - URL マッピング
                - `Application` のサブクラスが `@ApplicationPath` でアノテートされている場合。
                    - そこで指定したパスの後ろに `/*` を付けたパスにマッピングされる。
                - `@ApplicationPath` でアノテートしない場合は、 `web.xml` でマッピングを定義しなければならない。
            - リソースの登録
                - 全てのルートリソースが `getClasses()` 及び `getSingletons()` が空のコレクションを帰す場合、 war ファイル内からスキャンする。
                - 空のコレクションを返さない場合は、それを登録する。
                    - スキャンは行われない。

# Resource クラス
- リソースクラスとは
    - JAX-RS のアノテーションを使った Java クラス。
    - Web のリソースと一致する。
    - リソースクラスには、1つ以上の `@Path` でアノテートされたメソッドか、リクエストメソッドとして氏名されたものが存在する。
- ライフサイクル
    - デフォルトでは、リクエストごとにリソースクラスのインスタンスが生成される。
    - まずコンストラクターによってインスタンスが生成され、依存関係が解決され、メソッドが実行され、最後にオブジェクトがGCされる。
    - 実装によっては、これ以外のライフサイクルを提供しても構わない。
- コンストラクター
    - ルートリソースクラスは、 JAX-RS のランタイムによってインスタンス化される。
    - ルートリソースは、 public なコンストラクターを持たなければならない。
        - コンストラクタが引数を持つ場合、それらは JAX-RS のランタイムによって解決可能なものである必要がある。
        - この場合、引数無しのコンストラクタは許容される。
        - 引数は以下のいずれかのアノテーションで注釈されているかもしれない。
            - `@Context`, `@HeaderParam`, `@CookieParam`, `@MatrixParam`, `@QueryParam`, `@PathParam`
        - コンストラクタが複数ある場合、最も引数の多いパラメータが優先される。
        - 適当なコンストラクタが複数見つかった場合は、警告を表示すべきとされている。
    - ルートリソースでないクラスは、アプリケーションによってインスタンスが生成され、上記のような public なコンストラクタは要求されない。
        - private なコンストラクタだけだとエラーになった。。。
        - JAX-RS ではなく、 HK2 (Java DI) がエラーを吐いた？
    -



# 参考
- [The Java Community Process(SM) Program - JSRs: Java Specification Requests - detail JSR# 339](https://jcp.org/en/jsr/detail?id=339)

