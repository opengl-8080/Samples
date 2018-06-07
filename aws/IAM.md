[IAM とは - AWS Identity and Access Management](https://docs.aws.amazon.com/ja_jp/IAM/latest/UserGuide/introduction.html)

- Identity and Access Management
- 最初に AWS 用に作ったアカウントは、ルートユーザと呼ばれる
- ルートユーザは何でもできる権限を持っているため、これを使って日常の運用をするのは危険とされている
- 代わりに、 IAM で権限が制限されたユーザを作り、そちらで運用するのが良いとされている

## IAM ユーザの作り方
[最初の IAM 管理者のユーザーおよびグループの作成 - AWS Identity and Access Management](https://docs.aws.amazon.com/ja_jp/IAM/latest/UserGuide/getting-started_create-admin-group.html)

- 上の手順通りにつくれば簡単に作成できる
- 作成したユーザでログインするには、ユーザに割り当てられる 12 桁の一意なIDが必要になる
- この ID は、ユーザの概要ページの「ユーザの ARN」欄で確認できる
    - ARN は Amazon Resource Name の略
    - あらゆる AWS 上のリソースを一意に識別できるらしい
    - [Amazon リソースネーム (ARN) と AWS サービスの名前空間 - アマゾン ウェブ サービス](https://docs.aws.amazon.com/ja_jp/general/latest/gr/aws-arns-and-namespaces.html)
- 一旦コンソールをサインアウトしたら、「別のユーザでログイン」を選び、 12 桁の ID を入力して次に進む
- アカウント名とパスワードを聞かれるので、作成したユーザの名前と設定したパスワードを入力する
