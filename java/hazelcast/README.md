# Hazelcast
- https://hazelcast.org/

## Hello World
https://hazelcast.org/getting-started-with-hazelcast/

### やること
- 2つのノード(JVM)でインメモリ・データグリッドを作成する
- 最終的には、外部の Java アプリケーションからデータグリッドにアクセスする

### インストール
https://hazelcast.org/download/

```groovy
compile 'com.hazelcast:hazelcast:3.10.6'
```

## ノードを自動検出する仕組み
https://docs.hazelcast.org/docs/latest/manual/html-single/index.html#discovery-mechanisms

- 一度クラスタを組んだノード同士の通信は TCP/IP によって行われている
    - これは、ノードの発見方法とは関係しない
- 発見メカニズム (Discovery Mechanisms)
    - 単純な TCP による発見以外にも、 AWS 上でノードを見つける方法や Azure で見つける方法など、様々な方法が用意されている
- 基本は xml の設定ファイルでクラスタの情報を定義するっぽい
    - 設定がないときは、デフォルトでローカルホスの 5701, 5702 ポートに接続しようとするっぽい


