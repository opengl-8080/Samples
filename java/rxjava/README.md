# RxJava の基礎知識
- RxJava とは
    - Java でリアクティブプログラミングを行うためのライブラリ
    - 2.0 から Reative Streams の仕様を実装
        - Reactive Stream の API に依存している
    - Java 6 以上をサポート
    - 元は .NET Framework の Reactive Extensions というライブラリだった
    - オープンソース化され、 Netflix が Java に移植した
        - Java 以外にも JavaScript や Swift にも移植されている
    - 特徴
        - オブザーバーパターンを拡張している
            - 完了通知、エラー通知を追加
        - Reactive Streams のルールに従っている限り、スレッド管理のわずらわしさから解放される
        - 同期処理でも非同期処理でも、実装が大きく変わらない
        - 関数型プログラミングの影響を受けている
            - 関数型インターフェースを受け取るメソッド
- リアクティブプログラミングとは
    - プログラミングの考え方の１つ
    - データの通知に対して関連するプログラムが反応（リアクション）するようにプログラミングする
    - データストリーム
        - 全てのデータをまとめて扱うのではなく、次々と変化するデータが順次流れてくる
        - イベントもデータストリームとみなすことができる
            - 文字列入力
                - 入力のたびに入力情報が流れてくる
            - ボタンクリック
                - クリックしたというイベントが流れてくる
    - 必要なデータを自ら取得して処理するのではなく、データを受け取ったら、それに反応して処理をするというスタイル
    - リスナーを利用した実装との違い
        - 意識の違い
        - リアクティブプログラミングは、反応する側が流れてきたデータに対してアクションを取る、という意識
        - リスナーがサブスクライバに対して命令している意識だとリアクティブプログラミングではない
        - 意識の違いは、責任範囲に影響を与える
            - 単純な処理だと実装レベルでは違いがないかもしれない
            - 複雑になってくると、意識の違いによって責任範囲が変わり、実装に差が出てくる
    - 責任範囲
        - データの生産側
            - データを渡すまでが責任範囲
            - 消費者がどう使うかは知らない
            - 消費者の処理を待つ必要もない
                - 生産者と消費者を別スレッドにできる
                - ノンブロッキングと親和性が高い
- リアクティブシステム
    - リアクティブプログラミングで構築したシステム、**ではない**
    - メッセージを送ることで処理を行う
    - スケールアウト・インが自動的に行うことで障害に強くする
    - 常に迅速に応答できる
- Reactive Streams とは
    - データストリームを非同期で扱うための共通の仕組み
    - 言語やライブラリを跨って共通のインターフェースを定義する
    - リアクティブプログラミングが広まるにつれ、様々なライブラリが作られるようになった
    - API が統一されておらず、混沌とした状態になっていた
    - データストリームを非同期で扱うための最小限の API を定めたものとして Reactive Streams が策定された
    - Java 用の API
    - RxJava 2.0 は、この Reactive Streams を実装している
- 実験的な API
    - `@Beta`
    - `@Experimental`
    - 以上のアノテーションがついている API は実験的なものなので、使わないほうがいい

# 02 Reactive Streams について
- Reactive Streams が提供するのはインターフェースのみ
    - 各言語・各ライブラリが実装を提供する
- 生産者(Publisher)と消費者(Subscriber)に分かれる
- Publisher
    - データを生成する
- Subscriber
    - Publisher が生成したデータを購読（受け取って処理）する
- プロトコル
    1. Publisher は通知の準備ができたことを Subscriber に伝える
    2. Subscriber は、受け取るデータ数のリクエストを送る
        - Subscriber がデータ数をリクエストするまで、 Publisher は待機している
    3. Publisher はデータを生成し、 Subscriber に送る
    4. Subscriber はデータを受け取って処理を実行する
    5. 要求された数だけデータを送り終わったら、 Publisher は再び待機状態になる
    6. Subscriber は処理を終えたら、次のデータ数のリクエストを送る
    7. 全てのデータを送信し終えたら、 Publisher は完了を通知する
    8. 完了通知後は、 Publisher は何も通知を行わなくなる
    - エラーが発生した場合はエラーを通知する
- Subscriber がデータ数をリクエストすることで、 Publisher からのデータ数を抑制できる
    - Publisher のデータ生成速度が速い場合に、 Subscriber が溢れないようにするため
- インターフェース
    - `Publisher`
        - データの生産者
    - `Subscriber`
        - データの購読者
    - `Subscription`
        - データ数のリクエスト・購読のキャンセルを行う
        - `Subscriber` の `onSbscribe()` メソッドの引数で受け取る
    - `Processor`
        - Publisher と Subscriber 両方の性質を持つ
- Reactive Streams のルール
    - 根本ルール
        - 購読開始の通知は１度しか通知されない
            - `Subscriber` の `onSubscribe` は１度だけ呼ばれる
        - 通知はシーケンシャルに行う
            - 通知が複数同時に来ることはない
        - null を通知しない
        - Publisher は完了もしくはエラーを通知して終了する
    - その他のルール
        - データ数のリクエストに `Long.MAX_VALUE` を渡すと、データ数は無制限扱いになる
            - 最初にリクエストしたデータ数を処理し終わる前に次のデータ数をリクエストした場合、データ数が加算されていく
            - 加算の結果が `Long.MAX_VALUE` に達した場合も、無制限扱いになってしまうので注意
        - `Subscription` のメソッドは同期された状態で呼ばなければならない

# 03 RxJavaの基本となる仕組み
## 生産者と消費者
- RxJava の仕組みは「生産者」と「消費者」に分かれる
- Reactive Streams の対応有無によって、おおきく２つが用意されている
    - Reactive Streams の対応あり
        - 生産者 `Flowable`
        - 消費者 `Subscriber`
    - Reactive Streams の対応なし
        - 生産者 `Observable`
        - 消費者 `Observer`
- `Observable` にはバックプレッシャーの機能がない
    - 通知するデータ数を制御できない
        - データが作成されるとすぐに通知される
    - `Disposable` という購読解除だけができるインターフェースを使う
- `Observable` は Reactive Streams のインターフェースを実装していないが、基本的な使い方は同じ

## オペレータ
- 通知するデータを加工したり絞り込んだりしたりするメソッドを **オペレータ** と呼ぶ
- 実装的には、 `Flowable` と `Observable` からメソッドチェーンでつなげていく感じになる
    - Java 8 の Streams API みたいな感じ

```java
Flowable<Integer> flowable =
    Flowable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .filter(data -> data % 2 == 0)
            .map(data -> data * 100);

flowable.subscribe(data -> System.out.println("data=" + data));
```

- オペレータは関数型プログラミングを意識して使用したほうがいい
- 各オペレータに渡す処理は純粋関数にする
    - 副作用を持たせない
    - 同じ入力に対して同じ結果を返す

## 非同期処理
- 生産者と消費者が別れているため、それぞれを別スレッドで実行しやすくなっている
    - 消費側が、受け取るデータの内容だけに依存するようにしていれば、生産者側がどう動いていようが関係ない
- 処理の用途に合わせたスレッド管理を行う `Subscriber` が用意されている
- `Subscriber` を指定できる箇所は２つ
    - データを通知する箇所
        - 生産者側が `Subscriber` を指定する
    - データを受け取る箇所
        - 消費者側で制御する
- オペレータは必ず非同期実行しても問題ないように実装しなければならない
    - 外部の状態に依存しない
- 外部の状態にアクセスするのは次のときに絞る
    - 生産者がデータを作るとき
    - 消費者がデータの処理結果を外部に反映するとき
        - 外部が非同期アクセスしても大丈夫なようにする必要はある
        - 他のプログラムが同じデータにアクセスしていないかなど

## Cold と Hot の生産者
- 生産者には Cold と Hot の２種類が存在する
- Cold な生産者
    - １つの購読(Subscribe)に対して１つの消費者だけが結びつく
- Hot な生産者
    - １つの購読に対して複数の消費者が紐付ける
    - 後から消費者を増やすこともできる
        - すでに流れたデータは無視され、途中からデータを受け取るようになる
- RxJava で生成した生産者は、最初は Cold
- Hot にするには、 Cold な生産者の変換メソッドを使用する
    - 別の方法もあるが後述
- Hot な生産者を表すインターフェース
    - `ConnectableFlowable`
    - `ConnectableObservable`

## ConnectableFlowable/ConnectableObservable
- `subscribe()` メソッドを実行しても（購読を開始しても）データは流れてこない
- `connect()` メソッドを呼ぶ必要がある
- `Flowable`, `Observable` に変換するメソッドもある

### refCount
- `ConnectableFlowable`/`ConnectableObservable` から新しい `Flowable`/`Observable` を生成する
- ★ちょっとよくわからん...

### autoConnect
- 指定された数だけ購読(subscribe)されたら処理を開始する `Flowable`/`Observable`を生成する

### Flowable/Observable を「Cold」から「Hot」に変換するオペレータ
- `publish`
    - Cold な `Flowable`/`Observable` から `ConnectableFlowable`/ `ConnectableObservable` を生成する
    - 後から購読を開始した場合は、それ以降のデータだけを受け取ることができる
- `replay`
    - Cold な `Flowable`/`Observable` から `ConnectableFlowable`/ `ConnectableObservable` を生成する
    - 後から購読を開始した場合でも、最初のデータから受け取ることができる
        - データをキャッシュしている
        - キャッシュする個数や期間は引数で指定できる
- `share`
    - `ConnectableFlowable`/`ConnectableObservable` は生成しない
    - しかし、複数の消費者から購読できるようになる

# 04 マーブルダイアグラム
- リアクティブプログラミングで、時間とともにデータがどのように処理されていくかを表現した図
- 一番上のラインが、大本のデータの入力を表すタイムライン
- 中間処理が間に入り、縦の点線がデータが処理される様子を表現
- 一番下のラインが最終的に通知されるデータを表現するタイムライン
- 各データは色と形で表現される
- 左から右へ時間の流れを表現している
- 上から下が処理の流れを表現している

# 05 サンプルの作成
- `Flowable.create(FlowableOnSubscribe, BackpressureStrategy)` でデータの通知方法を制御できる `Flowable` を作成できる
- `FlowableOnSubscribe` は `create(FlowableEmitter)` を唯一持つ関数型インターフェース
- 引数の `FlowableEmitter` を通じて、 `Subscriber` にデータを通知する
    - `onNext()` で次のデータを通知する
    - 購読がキャンセルされているかどうかは `isCancelled()` で確認できる
        - 購読がキャンセルされた場合は、速やかに処理を終了させる必要がある
        - キャンセルされたあとで `onNext()` を読んでも、 RxJava 側で通知が行かないように制御はしてくれている
        - しかし、 `create()` 内の処理が中断されているわけではないので、自発的に処理を中断させないと、無駄な処理を続けてしまうことになる
    - 通知が正常に完了したら `onComplete()` を呼ぶ
- `create()` メソッドは `Exception` を `throws` 宣言に持つ
    - 例外がスローされたら、自動的にエラー終了が `Subscriber` に通知される
- `onComplete()`, `onError()` の後には、処理を書いてはいけない
    - もし `onComplete()` のあとに書いた処理が例外をスローしても、そのことは `Subscriber` に通知されない
- `create()` メソッドの第二引数にはバックプレッシャーの種類を指定する
    - `BackpressureStrategy` 列挙型に定義された定数を使用する
    - `BUFFER`
        - 通知されるまでバッファする
    - `DROP`
        - 通知できるようになるまで、新規データは破棄する
    - `LATEST`
        - 最新データのみバッファし、古いデータは破棄していく
    - `ERROR`
        - バッファサイズまでバッファし、オーバーした場合はエラーにする
    - `NONE`
        - 特に何もしない
        - `onBackpressure()` で具体的な処理を実装する
- `Subscriber` では４つのメソッドを実装する
    - `onSubscribe(Subscription)`
        - 通知の準備が完了したときに呼ばれる
        - 引数の `Subscription` で、次に受け取るデータ数をリクエストできる
            - データスをリクエストしないと通知が始まらないので注意
            - `request()` メソッドを呼ぶのは、 `onSubscribe()` の最後でないといけない
        - 引数の `Subscription` はその後の処理でも使用するので、インスタンス変数に保存しておく
        - `Subscription` には `cancel()` メソッドで購読を中止する機能もある
            - 普通 `onNext()` の中で特定の条件を満たしたときに `cancel()` することになる
            - しかし、そもそも通知がなく `onNext()` が呼ばれていないと `cancel()` すら実行されない可能性があることに注意
    - `onNext(T)`
        - `Flowable` が通知したデータを受け取ったときに呼ばれる
        - 通知されたデータは引数で受け取れる
        - メソッドの最後で `Subscription` のデータ数をリクエストするメソッドを再度実行することで、次のデータが通知される
    - `onComplete()`
        - 購読が全て正常終了したときに呼ばれる
    - `onError(Throwable)`
        - 購読でエラーが発生したときに呼ばれる
        - 引数には `Flowable` で発生した例外オブジェクトが渡される

## スレッド管理
- `Flowable` の `observeOn(Scheduler)` を使うと、 `Flowable` と `Subscriber` を別々のスレッドで動作させることができる
- `Scheduler` はスレッド管理を行うオブジェクト
- `Flowable` と `Subscriber` を同じスレッドで実行すると、 `Subscriber` の処理が終わるまで `Flowable` は処理を待機することになる
- ２つを別スレッドに分ければ、 `Subscriber` の処理の終了を待つことなく、 `Flowable` はデータの生成ができる
    - データの生成が早い場合は、バックプレッシャー機能によって通知が制御される
    - サンプルでは `BUFFER` を指定しているので、通知はバッファされていく

## Observable を使った場合
- バックプレッシャーの機能が無い以外は、 `Flowable` と使い方は同じ
- `isCancelled()` ではなく `isDisposed()` のように、若干のメソッド名の差はあるが、基本機能は一緒

## Observable と Flowable の使い分け
- `Flowable` を使うケース
    - 大量のデータを扱う(数万件)
    - ネットワーク・データベースの IO を使う
    - 通知されるデータが非常に多い
        - バックプレッシャーを利用して限界値を設定し、それを超えたら `MissingBackpressureException` をスローさせてエラーハンドリングをする
    - 通知されるデータが全て必要というわけではない
        - DROP を選択するなどの選択肢がある
- `Observable` を使うケース
    - GUI イベント
    - 少量のデータを扱う(数千件)
    - 同期的な処理
    - Stream API の代わり
- `Observable` のほうがオーバーヘッドは少ない
- 環境や通知するデータ、その使い方などごとに使い分けが必要
- それぞれの特性をよく理解しておくことが重要

# 06 RxJava の全体像
- `Flowable` と `Observable`
    - データを生産し通知するクラス
    - `Flowable` にはバックプレッシャー機能がある
    - `Observable` にはない
- `Subscriber` と `Observer`
    - 通知されたデータを処理するクラス
    - `Subscriber` はバックプレッシャー機能があるので、データ数のリクエストが必要
        - リクエストは `onSubscribe()` の最後に実行しないと、初期化処理の途中で通知が始まってしまう
    - `Observer` にはない
- `Processor`/`Subject`
    - `Processor`
        - `Publisher` と `Subscriber` の両方を継承したインターフェース
    - `Subject`
        - `Observable` と `Observer` の両方を継承したインターフェース   

# RxJava とデザインパターン
