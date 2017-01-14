> Once you are familiar with setting up and running some namespace-configuration based applications, you may wish to develop more of an understanding of how the framework actually works behind the namespace facade.
namespace コンフィギュレーションベースでアプリケーションをセッティングしたことがある場合、namespace ファサードの裏でフレームワークが実際に何をしているか理解したくなることでしょう。

> Like most software, Spring Security has certain central interfaces, classes and conceptual abstractions that are commonly used throughout the framework.
多くのソフトウェア同様、 Spring Security は中心となるインターフェースやクラス、コンセプトを実装した共通的な抽象クラスを含んでいます。

> In this part of the reference guide we will look at some of these and see how they work together to support authentication and access-control within Spring Security.
この章では、これらについて見ていき、 Spring Security の認証とアクセス制御について見ていきます。

# 9. Technical Overview
## 9.1 Runtime Environment
> Spring Security 3.0 requires a Java 5.0 Runtime Environment or higher.
Spring Security 3.0 は Java 5.0 以上で動作します。

> As Spring Security aims to operate in a self-contained manner, there is no need to place any special configuration files into your Java Runtime Environment.
Spring Security が掲げる独自の目標として、 Java の実行環境に特別な設定を追加する必要はありません。

> In particular, there is no need to configure a special Java Authentication and Authorization Service (JAAS) policy file or place Spring Security into common classpath locations.
特に、 JAAS (Java Authentication and Authorization Service)のポリシーファイルや場所を Spring Security の共通のクラスパスなどに追加する必要はありません。

> Similarly, if you are using an EJB Container or Servlet Container there is no need to put any special configuration files anywhere, nor include Spring Security in a server classloader.
同様に、 EJB コンテナやサーブレットコンテナを使っていた場合であっても、特別な設定や何かを配置する必要はありません。
サーバーのクラスローダーに Spring Security を含める必要もありません。

> All the required files will be contained within your application.
必要なファイルは全てアプリケーションに含まれます。

> This design offers maximum deployment time flexibility, as you can simply copy your target artifact (be it a JAR, WAR or EAR) from one system to another and it will immediately work.
この設計は最大限のデプロイ可搬性を提供します。
このため、あるシステムを別の環境に簡単に移動させることができます。

## 9.2 Core Components
> In Spring Security 3.0, the contents of the spring-security-core jar were stripped down to the bare minimum.
Spring Security 3.0 では、 spring-security-core の jar には最小限のものだけが含まれています。

> It no longer contains any code related to web-application security, LDAP or namespace configuration.
web アプリケーションのセキュリティ、 LDAP、 namespace コンフィギュレーションに関係するコードは含まれていません。

> We’ll take a look here at some of the Java types that you’ll find in the core module.
ここでお見せする Java クラスは、コアモジュール内で見つけることができます。

> They represent the building blocks of the framework, so if you ever need to go beyond a simple namespace configuration then it’s important that you understand what they are, even if you don’t actually need to interact with them directly.
それらのクラスは、フレームワークを構成するブロックを表現します。
よって、もしあなたが単純な namespace コンフィギュレーションを超えて使用する場合は、それらのクラスを直接触るわけではなかったとしても、それらについて知っておくことは重要です。

### 9.2.1 SecurityContextHolder, SecurityContext and Authentication Objects
> The most fundamental object is SecurityContextHolder.
もっとも基本的なクラスは SecurityContextHolder です。

> This is where we store details of the present security context of the application, which includes details of the principal currently using the application.
これはアプリケーションのセキュリティのコンテキストについての詳細を保持しています。
そこには現在アプリケーションを使用しているプリンシパルの詳細が含まれます。

> By default the SecurityContextHolder uses a ThreadLocal to store these details, which means that the security context is always available to methods in the same thread of execution, even if the security context is not explicitly passed around as an argument to those methods.
デフォルトでは、 SecurityContextHolder は ThreadLocal を詳細を保存するために使用しています。
それが意味することは、セキュリティコンテキストは実行されている同一スレッド内であれば常に参照できるということです。
たとえセキュリティコンテキストがメソッドの引数で明示的に渡されていなかったっとしてもです。

> Using a ThreadLocal in this way is quite safe if care is taken to clear the thread after the present principal’s request is processed.
ThreadLocal を使用する方法は、１つ前のスレッドで使用されていたプリンシパルがしっかり削除されていれば、かなり安全です。

> Of course, Spring Security takes care of this for you automatically so there is no need to worry about it.
もちろん、 Spring Security はこれ（プリンシパルのクリア）を自動で実行するので、このことについて気にする必要はありません。

> Some applications aren’t entirely suitable for using a ThreadLocal, because of the specific way they work with threads.
いくつかのアプリケーションは、 ThreadLocal の使用が適切ではないかもしれません。
なぜなら、特定のスレッドでのみ動いているかもしないからです。

> For example, a Swing client might want all threads in a Java Virtual Machine to use the same security context.
たとえば、 Swing クライアントは JVM のすべてのスレッドで同じセキュリティコンテキストが使用されることを求めるかもしれません。
→ Swing でマルチスレッドを実行した場合であっても、スレッドごとにセキュリティコンテキストは不要で、全て同じセキュリティコンテキストにしたい（Swing はクライアントマシン上で動くので、マルチユーザーではない可能性がある）

> SecurityContextHolder can be configured with a strategy on startup to specify how you would like the context to be stored.
SecurityContextHolder はコンテキストをどのように保存するかを指定するストラテジを設定することができます。

> For a standalone application you would use the SecurityContextHolder.MODE_GLOBAL strategy.
スタンドアロンのアプリケーションでは、 SecurityContextHolder.MODE_GLOBAL ストラテジを使用できます。

> Other applications might want to have threads spawned by the secure thread also assume the same security identity.
他のアプリケーションは、セキュアなスレッドによって生成されたスレッドは、同じセキュリティコンテキストを求めるかもしれません。

> This is achieved by using SecurityContextHolder.MODE_INHERITABLETHREADLOCAL.
これは、 SecurityContextHolder.MODE_INHERITABLETHREADLOCAL を使えば実現できます。

> You can change the mode from the default SecurityContextHolder.MODE_THREADLOCAL in two ways.
デフォルトの SecurityContextHolder.MODE_THREADLOCAL から変更する手段は２つあります。

> The first is to set a system property, the second is to call a static method on SecurityContextHolder.
最初の方法は、システムプロパティを使用する方法です。
２つ目の方法は、 SecurityContextHolder の static メソッドを使用する方法です。

> Most applications won’t need to change from the default, but if you do, take a look at the JavaDoc for SecurityContextHolder to learn more.
多くのアプリケーションはデフォルトを変更する必要はないでしょう。
しかし、もしそれが必要となった場合は、 SecurityContextHolder の Javadoc を見て勉強してください。

#### Obtaining information about the current user
> Inside the SecurityContextHolder we store details of the principal currently interacting with the application.
SecurityContextHolder 内部では、現在アプリケーションにアクセスしているプリンシパルの詳細を保存しています。

> Spring Security uses an Authentication object to represent this information.
Spring Security は Authentication オブジェクトを、この情報を表現するために使用しています。

> You won’t normally need to create an Authentication object yourself, but it is fairly common for users to query the Authentication object.
Authentication オブジェクトは自分で作る必要はありません。
しかし、 Authentication オブジェクトへの問い合わせは非常に共通的です。

> You can use the following code block - from anywhere in your application - to obtain the name of the currently authenticated user, for example:
以下のコードブロックを使用することで（どのアプリケーションであっても）、現在認証されているユーザーの名前を参照することができます。
例えば、

```java
Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

if (principal instanceof UserDetails) {
    String username = ((UserDetails)principal).getUsername();
} else {
    String username = principal.toString();
}
```

> The object returned by the call to getContext() is an instance of the SecurityContext interface.
getContext() メソッドを呼び出した戻り値のオブジェクトは、 SecurityContext インターフェースのインスタンスです。

> This is the object that is kept in thread-local storage.
これはスレッドローカルに保存されていたオブジェクトです。

> As we’ll see below, most authentication mechanisms within Spring Security return an instance of UserDetails as the principal.
上記のように、多くの Spring Security の認証機構は、プリンシパルとして UserDetails インスタンスを返します。

### 9.2.2 The UserDetailsService
> Another item to note from the above code fragment is that you can obtain a principal from the Authentication object.
上述のコードで他に注意すべきことは、 Authentication オブジェクトからプリンシパルを取得できるということです。

> The principal is just an Object.
プリンシパルは、 Object 型です。

> Most of the time this can be cast into a UserDetails object.
多くの場合、これは UserDetails のオブジェクトにキャストすることができます。

> UserDetails is a core interface in Spring Security.
UserDetails は Spring Security に含まれるコアのインターフェースです。

> It represents a principal, but in an extensible and application-specific way.
UserDetails は、プリンシパルを表現します。
しかし、それはアプリケーションの特性に合わせて拡張することが可能です。

> Think of UserDetails as the adapter between your own user database and what Spring Security needs inside the 
SecurityContextHolder.
UserDetails をあなたの使用するデータベースと Spring Security が SecurityContextHolder 内部で必要とするものとの間で適合させることを考えてください。

> Being a representation of something from your own user database, quite often you will cast the UserDetails to the original 
object that your application provided, so you can call business-specific methods (like getEmail(), getEmployeeNumber() and so on).
あなたのデータベースから取得した何かしらの表現は、よく UserDetails をあなたのアプリケーションが使用する独自のオブジェクトにきゃするすることになるはずです。
そのおかげで、あなたはビジネス固有のメソッドをコールできるのです。
（getEmail() や getEmployeeNumber()など）
 
> By now you’re probably wondering, so when do I provide a UserDetails object? How do I do that? 
おそらく、あなたは今、「どうやって UserDetails オブジェクトを提供すればいいんだ？」と疑問に思っていることでしょう。

> I thought you said this thing was declarative and I didn’t need to write any Java code - what gives? The short answer is that there is a special interface called UserDetailsService.
お答えしましょう。それは宣言的であって、 Java コードを書く必要はありません。
どうするかって？
答えは簡単で、 UserDetailsService と呼ばれるインターフェースがあります。

> The only method on this interface accepts a String-based username argument and returns a UserDetails:
このインターフェースにある唯一のメソッドは、 String で表現されたユーザー名の引数と UserDetails の戻り値を持ちます。

```java
UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
```

> This is the most common approach to loading information for a user within Spring Security and you will see it used throughout the framework whenever information on a user is required.
これは Spring Security でユーザーの情報をロードするためのアプローチとして最も一般的です。
そして、ユーザー情報を必要とするときはいつでもフレームワーク全体で使用されます。

> On successful authentication, UserDetails is used to build the Authentication object that is stored in the SecurityContextHolder (more on this below).
認証に成功すると、 UserDetails が Authentication オブジェクトを生成するために使用されます。
Authentication オブジェクトは SecurityContextHolder に保存されます。

> The good news is that we provide a number of UserDetailsService implementations, including one that uses an in-memory map (InMemoryDaoImpl) and another that uses JDBC (JdbcDaoImpl).
いいニュースとして、我々は UserDetailsService の実装をいくつか提供しています。
インメモリでユーザーのマップを持つものと、 JDBC を使用したものです。

> Most users tend to write their own, though, with their implementations often simply sitting on top of an existing Data Access Object (DAO) that represents their employees, customers, or other users of the application.
多くのユーザーは、それを独自で実装するでしょう。
その実装はよく、従業員や顧客、その他のユーザーを表現する DAO を使用するだけの単純なものになります。

> Remember the advantage that whatever your UserDetailsService returns can always be obtained from the SecurityContextHolder using the above code fragment.
UserDetailsService が返したものであればなんであれ、常に SecurityContextHolder から取得できるということがメリットである、ということを覚えておいてください。

> There is often some confusion about UserDetailsService.
UserDetailsService はしばしば混乱を生みます。

> It is purely a DAO for user data and performs no other function other than to supply that data to other components within the framework.
それは、単純なユーザーデータのための DAO であり、フレームワーク内の他のコンポーネントにデータを供給する以上の機能を持たない。

> In particular, it does not authenticate the user, which is done by the AuthenticationManager.
特に、それはユーザーの認証は行いません。
それは AuthenticationManager の仕事です。

> In many cases it makes more sense to implement AuthenticationProvider directly if you require a custom authentication process.
多くの場合、 AuthenticationProvider を直接実装することで、あなたが必要とするカスタムの認証処理を実現できます。

### 9.2.3 GrantedAuthority
> Besides the principal, another important method provided by Authentication is getAuthorities().
一方プリンシパルでは、 Authentication によって提供される重要な他のメソッドとして getAuthorities() というものがあります。

> This method provides an array of GrantedAuthority objects.
このメソッドは、 GrantedAuthority オブジェクトの配列を返します。

> A GrantedAuthority is, not surprisingly, an authority that is granted to the principal.
１つの GrantedAuthority は、（驚くことはなく）プリンシパルに与えられた権限を表します。

> Such authorities are usually "roles", such as ROLE_ADMINISTRATOR or ROLE_HR_SUPERVISOR.
そのような権限は、普通ロールと言います。
例えば、 ROLE_ADMINISTRATOR や ROLE_HR_SUPERVISOR などです。

> These roles are later on configured for web authorization, method authorization and domain object authorization.
ロールは Web 認証やメソッド認証、ドメインオブジェクト認証などで設定されます。

> Other parts of Spring Security are capable of interpreting these authorities, and expect them to be present.
Spring Security の他の部分は、この権限を解釈することで、権限が存在するかを判断することが期待されます。

> GrantedAuthority objects are usually loaded by the UserDetailsService.
GrantedAuthority オブジェクトは普通 UserDetailsService によってロードされます。

> Usually the GrantedAuthority objects are application-wide permissions.
通常、 GrantedAuthority オブジェクトは、アプリケーション全体のパーミッションを表します。

> They are not specific to a given domain object.
それは、特定のドメインオブジェクトに限定されるものではありません。

> Thus, you wouldn’t likely have a GrantedAuthority to represent a permission to Employee object number 54, because if there are thousands of such authorities you would quickly run out of memory (or, at the very least, cause the application to take a long time to authenticate a user).
つまり、番号が 54 の Employee オブジェクトへのパーミッションを GrantedAuthority で与えるようなことはできません。
なぜなら、数千の権限が必要になると、すぐに OutOfMemory になるからです。
もしくは、すくなくとも、アプリケーションは認証に時間がかかるようになる。

> Of course, Spring Security is expressly designed to handle this common requirement, but you’d instead use the project’s domain object security capabilities for this purpose.
もちろん、 Spring Security はこのような共通の要求も制御できるように明確に設計されています。
そのような目的には、ドメインオブジェクトセキュリティの機能を代わりに使用してください。

### 9.2.4 Summary
