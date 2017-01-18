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
> Just to recap, the major building blocks of Spring Security that we’ve seen so far are:
要約すると、 Spring Security を構成する主要なブロックは次のようなものです。

- SecurityContextHolder, to provide access to the SecurityContext.
SecurityContextHolder は、 SecurityContext へのアクセスを提供します。

- SecurityContext, to hold the Authentication and possibly request-specific security information.
SecurityContext は、 Authentication を保持し、特定のセキュリティ情報への要求を可能とします。

- Authentication, to represent the principal in a Spring Security-specific manner.
Authentication は、Springセキュリティ固有の方法でプリンシパルを表します。

- GrantedAuthority, to reflect the application-wide permissions granted to a principal.
GrantedAuthority は、アプリケーション全体を通してプリンシパルに与えられた権限を反映します。

- UserDetails, to provide the necessary information to build an Authentication object from your application’s DAOs or other source of security data.
UserDetails は、アプリケーションの DAO や他のセキュリティデータソースから、 Authentication を構築するために必要となる情報を提供します。

- UserDetailsService, to create a UserDetails when passed in a String-based username (or certificate ID or the like).
UserDetailsService は、 String ベースのユーザー名（もしくは証明となる ID やそれに準じる何か）を受け取り UserDetails を生成します。

> Now that you’ve gained an understanding of these repeatedly-used components, let’s take a closer look at the process of authentication.
これで、あなたたちは繰り返し使われるこれらのコンポーネントについて理解しました。
次は認証プロセスについてみてみましょう。

## 9.3 Authentication
> Spring Security can participate in many different authentication environments.
Spring Security は多くの異なる認証環境で使用できます。

> While we recommend people use Spring Security for authentication and not integrate with existing Container Managed Authentication, it is nevertheless supported - as is integrating with your own proprietary authentication system.
コンテナ管理の既存の認証と統合するのではなく、 Spring Security を使用することを推奨します。
独自認証システムとの統合がサポートされていたとしても。

### 9.3.1 What is authentication in Spring Security?
> Let’s consider a standard authentication scenario that everyone is familiar with.
基本的な認証のシナリオについて考えてみましょう。

1. A user is prompted to log in with a username and password.
ユーザー名とパスワードをログインで求められます。

2. The system (successfully) verifies that the password is correct for the username.
システムは、パスワードとユーザー名が正しいことを検証します。

3. The context information for that user is obtained (their list of roles and so on).
ユーザーについてのコンテキスト情報（権限のリストやその他）が取得されます。

4. A security context is established for the user
セキュリティコンテキストがユーザーのために作成されます。

5. The user proceeds, potentially to perform some operation which is potentially protected by an access control mechanism which checks the required permissions for the operation against the current security context information.
アクセス制御が必要かもしれない処理を行う。

> The first three items constitute the authentication process so we’ll take a look at how these take place within Spring Security.
最初の３つは、認証のプロセスを構成します。
そのため、これらが Spring Security でどのように実現されているかについてみていきます。

1. The username and password are obtained and combined into an instance of UsernamePasswordAuthenticationToken (an instance of the Authentication interface, which we saw earlier).
ユーザー名とパスワードは UsernamePasswordAuthenticationToken にまとめられます。
(以前紹介した Authentication インターフェースを実装しています)

2. The token is passed to an instance of AuthenticationManager for validation.
トークンは AuthenticationManager の検証を通ります

3. The AuthenticationManager returns a fully populated Authentication instance on successful authentication.
AuthenticationManager は認証に成功すると、完全に構成された Authentication インスタンスを返します

4. The security context is established by calling SecurityContextHolder.getContext().setAuthentication(…​), passing in the returned authentication object.
セキュリティコンテキストは SecurityContextHolder.getContext().setAuthentication(...) を呼び、返された認証オブジェクトを通して構築されます。

> From that point on, the user is considered to be authenticated. Let’s look at some code as an example.
この時点から、ユーザーは認証されているとみなされます。
例を見てみましょう。

```java
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationExample {
    private static AuthenticationManager am = new SampleAuthenticationManager();

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            System.out.println("Please enter your username:");
            String name = in.readLine();
            System.out.println("Please enter your password:");
            String password = in.readLine();
            try {
                Authentication request = new UsernamePasswordAuthenticationToken(name, password);
                Authentication result = am.authenticate(request);
                SecurityContextHolder.getContext().setAuthentication(result);
                break;
            } catch(AuthenticationException e) {
                System.out.println("Authentication failed: " + e.getMessage());
            }
        }
        System.out.println("Successfully authenticated. Security context contains: " +
                SecurityContextHolder.getContext().getAuthentication());
    }
}

class SampleAuthenticationManager implements AuthenticationManager {
    static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        if (auth.getName().equals(auth.getCredentials())) {
            return new UsernamePasswordAuthenticationToken(auth.getName(),
                auth.getCredentials(), AUTHORITIES);
        }
        throw new BadCredentialsException("Bad Credentials");
    }
}
```

> Here we have written a little program that asks the user to enter a username and password and performs the above sequence.
ここで、私たちは小さなプログラムを書きました。
それはあなたにユーザー名とパスワードの入力を尋ね、上述のシーケンスを実行するものです。

> The AuthenticationManager which we’ve implemented here will authenticate any user whose username and password are the same.
ここで実装している AuthenticationManager は、ユーザー名とパスワードが一致する任意のユーザーを認証するものです。

> It assigns a single role to every user.
全てのユーザーに、１つのロールを割り当てます。

> The output from the above will be something like:
上記の出力は、次のようになります。

```
Please enter your username:
bob
Please enter your password:
password
Authentication failed: Bad Credentials
Please enter your username:
bob
Please enter your password:
bob
Successfully authenticated. Security context contains: \
org.springframework.security.authentication.UsernamePasswordAuthenticationToken@441d0230: \
Principal: bob; Password: [PROTECTED]; \
Authenticated: true; Details: null; \
Granted Authorities: ROLE_USER
```

> Note that you don’t normally need to write any code like this.
通常は、このようなコードは書かないようにしてください。

> The process will normally occur internally, in a web authentication filter for example.
普通、これらは内部的に行われます。例えば、 Web 認証のフィルター内部などです。

> We’ve just included the code here to show that the question of what actually constitutes authentication in Spring Security has quite a simple answer.
私たちがこのコードで示したことは、 Spring Security の内部の認証処理というのが実際どのようなことをしているのかという疑問に対する、シンプルな回答です。

> A user is authenticated when the SecurityContextHolder contains a fully populated Authentication object.
SecurityContextHolder が完全に構成された Authentication オブジェクトを含んだとき、ユーザーは認証されたものとなります。

### 9.3.2 Setting the SecurityContextHolder Contents Directly
> In fact, Spring Security doesn’t mind how you put the Authentication object inside the SecurityContextHolder.
実際、 Spring Security は SecurityContextHolder の内部の Authentication がどのように設定されたかについては気にしません。

> The only critical requirement is that the SecurityContextHolder contains an Authentication which represents a principal before the AbstractSecurityInterceptor (which we’ll see more about later) needs to authorize a user operation.
唯一の重要な要求は、ユーザーの認証処理をする必要が発生する前までに、 SecurityContextHolder がプリンシパルを表現する Authentication を持つことです。

> You can (and many users do) write their own filters or MVC controllers to provide interoperability with authentication systems that are not based on Spring Security.
あなたや、そして多くのユーザーは独自のフィルターや MVC コントローラをSpring Security ベースではない認証システムとの相互運用性を提供するために作成することができます。

> For example, you might be using Container-Managed Authentication which makes the current user available from a ThreadLocal or JNDI location.
例えば、現在のユーザーを ThreadLocal や JNDI に保持しているようなコンテナ管理の認証を使用しているとします。

> Or you might work for a company that has a legacy proprietary authentication system, which is a corporate "standard" over which you have little control.
もしくは、レガシーで独自の認証システムを持った会社で働き、それが社内標準であなたには少しの決定権しかないような場合です。

> In situations like this it’s quite easy to get Spring Security to work, and still provide authorization capabilities.
このような状況でも、 Spring Security は簡単に使うことができ、認証機能を提供することができます。

> All you need to do is write a filter (or equivalent) that reads the third-party user information from a location, build a Spring Security-specific Authentication object, and put it into the SecurityContextHolder.
あなたに必要なことは、サードパーティーのユーザー情報を取得するフィルターを記述し、 Spring Security が指定する Authentication オブジェクトを作り、 SecurityContextHolder に保存することです。

> In this case you also need to think about things which are normally taken care of automatically by the built-in authentication infrastructure.
この場合、あなたは組み込みの認証基盤によって自動的に処理されることについても考える必要があります。

> For example, you might need to pre-emptively create an HTTP session to cache the context between requests, before you write the response to the client footnote:[It isn’t possible to create a session once the response has been committed.
たとえば、クライアントの脚注に応答を書き込む前に、事前にHTTPセッションを作成して要求間のコンテキストをキャッシュする必要があります。[応答がコミットされるとセッションを作成することはできません。

> If you’re wondering how the AuthenticationManager is implemented in a real world example, we’ll look at that in the core services chapter.
もしあなたがどのようにして AuthenticationManager が実際の世界で実装されているのかについて疑問に思ったとしたら、コアのサービスの章で説明します。

## 9.4 Authentication in a Web Application
> Now let’s explore the situation where you are using Spring Security in a web application (without web.xml security enabled).
次は Web アプリケーションで Spring Security を使う場合のことについて見ていきましょう。
（web.xml のセキュリティは除外します）

> How is a user authenticated and the security context established?
どのようにしてユーザーの認証が行われ、セキュリティコンテキストが構築されるのでしょう？

> Consider a typical web application’s authentication process:
典型的な Web アプリケーションの認証プロセスについて考えます。

1. You visit the home page, and click on a link.
リンクをクリックして、ホームページにアクセスします。

2. A request goes to the server, and the server decides that you’ve asked for a protected resource.
サーバーにリクエストが飛び、サーバーはリソースへのアクセスが保護されるべきか決定します。

3. As you’re not presently authenticated, the server sends back a response indicating that you must authenticate. The response will either be an HTTP response code, or a redirect to a particular web page.
あなたが認証されていない場合、サーバーは認証されなければならないことを示すレスポンスを返します。
レスポンスは HTTP ステータスコードか特定の Web ページにリダイレクトするかのいずれかになります。

4. Depending on the authentication mechanism, your browser will either redirect to the specific web page so that you can fill out the form, or the browser will somehow retrieve your identity (via a BASIC authentication dialogue box, a cookie, a X.509 certificate etc.).
認証メカニズムによって、ブラウザは特定の Web ページにリダイレクトしてフォームを埋めるか、それ以外にあなたの識別の入力を要求してきます
（BASIC 認証ならダイアログボックスや、 Cookie、 x.599 など）

5. The browser will send back a response to the server. This will either be an HTTP POST containing the contents of the form that you filled out, or an HTTP header containing your authentication details.
ブラウザはサーバーに返事を返します。これは、あなたが form で入力した情報を持った HTTP POST か、 HTTP ヘッダーに認証情報の詳細を持ちます。

6. Next the server will decide whether or not the presented credentials are valid. If they’re valid, the next step will happen. If they’re invalid, usually your browser will be asked to try again (so you return to step two above).
次に、サーバーは提供された資格情報が有効かどうかを決定します。
もし有効なら、次のステップに進みます。
もし無効なら、通常はブラウザは再入力を促します。
（そのため、２つ前のステップに戻ります）

7. The original request that you made to cause the authentication process will be retried. Hopefully you’ve authenticated with sufficient granted authorities to access the protected resource. If you have sufficient access, the request will be successful. Otherwise, you’ll receive back an HTTP error code 403, which means "forbidden".
認証処理の発端となった最初のリクエストが再実行されます。
うまくいけば、あなたは認証に成功し、リソースにアクセスするための十分な権限を与えられています。
もしアクセスが可能なら、リクエストは成功します。
そうでないなら、 403 のエラーコードが返されます。それは「禁止である」ことを意味します。

> Spring Security has distinct classes responsible for most of the steps described above.
Spring Security には上記ステップのほとんどを実行する責務をもつクラスがあります。

> The main participants (in the order that they are used) are the ExceptionTranslationFilter, an AuthenticationEntryPoint and an "authentication mechanism", which is responsible for calling the AuthenticationManager which we saw in the previous section.
主な参加者は、呼び出される順番で ExceptionTranslationFilter, AuthenticationEntryPoint, そして前章で紹介した AuthenticationManager を呼び出す認証メカニズムと呼ばれるものです。

### 9.4.1 ExceptionTranslationFilter
> ExceptionTranslationFilter is a Spring Security filter that has responsibility for detecting any Spring Security exceptions that are thrown.
ExceptionTranslationFilter は Spring Security のフィルターで、 Spring Security がスローした例外を検出する責務を持ちます。

> Such exceptions will generally be thrown by an AbstractSecurityInterceptor, which is the main provider of authorization services.
そのような例外は、通常 AbstractSecurityInterceptor によってスローされます。
これ（AbstractSecurityInterceptor）は、認証サービスのメインとなるプロバイダーです。

> We will discuss AbstractSecurityInterceptor in the next section, but for now we just need to know that it produces Java exceptions and knows nothing about HTTP or how to go about authenticating a principal.
AbstractSecurityInterceptor については次の節で説明します。
現在のところ、Java例外を生成し、HTTPやプリンシパルの認証方法については何も知りません。

> Instead the ExceptionTranslationFilter offers this service, with specific responsibility for either returning error code 403 (if the principal has been authenticated and therefore simply lacks sufficient access - as per step seven above), or launching an AuthenticationEntryPoint (if the principal has not been authenticated and therefore we need to go commence step three).
代わりに ExceptionTranslationFilter はこのサービスを提供します。
そのサービスとは、 403 のエラーコードを返すか(もし、プリンシパルが認証されており、アクセスのための十分な権限を持たない場合)、もしくは、 AuthenticationEntryPoint を実行するか（もしプリンシパルが認証されていない場合）どうかを特定する責務です。


### 9.4.2 AuthenticationEntryPoint
> The AuthenticationEntryPoint is responsible for step three in the above list.
AuthenticationEntryPoint は、上記リストの３番目を実行する責務を持ちます。

> As you can imagine, each web application will have a default authentication strategy (well, this can be configured like nearly everything else in Spring Security, but let’s keep it simple for now).
ご想像の通り、個々の Web アプリケーションはデフォルトの認証戦略を持ちます。

> Each major authentication system will have its own AuthenticationEntryPoint implementation, which typically performs one of the actions described in step 3.
個々の主要な認証システムは、独自の AuthenticationEntryPoint の実装を持ちます。
典型的なふるまいは、ステップ３の１つを実行します。

### 9.4.3 Authentication Mechanism
> Once your browser submits your authentication credentials (either as an HTTP form post or HTTP header) there needs to be something on the server that "collects" these authentication details.
認証の資格情報をブラウザがサブミットすると、サーバーでは認証の詳細が正しいことを確認する必要があります。

> By now we’re at step six in the above list.
今、我々は上述のリストの６番目に居ます。

> In Spring Security we have a special name for the function of collecting authentication details from a user agent (usually a web browser), referring to it as the "authentication mechanism".
Spring Security では、ユーザーエージェント（通常は Web ブラウザ）から認証の詳細を収集する機能に特別な名前を持たせています。
それを "authentication mechanism" と呼んでいます。

> Examples are form-base login and Basic authentication.
例には、 form ベースのログインと Basic 認証があります。

> Once the authentication details have been collected from the user agent, an Authentication "request" object is built and then presented to the AuthenticationManager.
ユーザーエージェントから渡された認証の詳細が正しい場合、 Authentication のリクエストオブジェクトが作成され、 AuthenticationManager に提供されます。

> After the authentication mechanism receives back the fully-populated Authentication object, it will deem the request valid, put the Authentication into the SecurityContextHolder, and cause the original request to be retried (step seven above).
認証メカニズムが完全に構築された Authentication オブジェクトを返した後で、リクエストが正しいか考えます。
SecurityContextHolder に Authentication が設定され、元のリクエストが再実行されます。

> If, on the other hand, the AuthenticationManager rejected the request, the authentication mechanism will ask the user agent to retry (step two above).
もし、一方で、 AuthenticationManager がリクエストを拒否した場合、認証メカニズムはユーザーエージェントに尋ねなおします。

### 9.4.4 Storing the SecurityContext between requests
> Depending on the type of application, there may need to be a strategy in place to store the security context between user operations.
アプリケーションの種類によっては、ユーザー処理を跨ってセキュリティコンテキストを記録する戦略が必要になるかもしれません。

> In a typical web application, a user logs in once and is subsequently identified by their session Id.
典型的な Web アプリケーションでは、ユーザーが一度ログインすると、その後はセッションIDによって識別されるようになります。

> The server caches the principal information for the duration session.
サーバーは、プリンシパルの情報をセッションが維持される間、キャッシュします。

> In Spring Security, the responsibility for storing the SecurityContext between requests falls to the SecurityContextPersistenceFilter, which by default stores the context as an HttpSession attribute between HTTP requests.
Spring Security では、 SecurityContext をリクエストの間保存しておく役割を SecurityContextPersistenceFilter が担っています。
デフォルトでは、コンテキストは HttpSession の属性として保存されます。

> It restores the context to the SecurityContextHolder for each request and, crucially, clears the SecurityContextHolder when the request completes.
リクエストごとに SecurityContextHolder にコンテキストを保存しなおし、リクエストごとに完全に SecurityContextHolder をクリアします。

> You shouldn’t interact directly with the HttpSession for security purposes.
セキュリティを考慮すると、 HttpSession に直接アクセスすべきではありません。

> There is simply no justification for doing so - always use the SecurityContextHolder instead.
それを正当化する理由はありません。代わりに、常に SecurityContextHolder を使用してください。

> Many other types of application (for example, a stateless RESTful web service) do not use HTTP sessions and will re-authenticate on every request.
多くの他のアプリケーション（例えば、ステートレスな RESTful Web サービス）は、 Http セッションを使わず、リクエストのたびに認証を繰り返します。

> However, it is still important that the SecurityContextPersistenceFilter is included in the chain to make sure that the SecurityContextHolder is cleared after each request.
しかしながら、それでも SecurityContextPersistenceFilter は依然重要で、リクエストの後に毎回 SecurityContextHolder を作成します。

> In an application which receives concurrent requests in a single session, the same SecurityContext instance will be shared between threads.
単一のセッションで同時にリクエストを受け取るようなアプリケーションの場合、同じ SecurityContext インスタンスがスレッド間で共有されます。

> Even though a ThreadLocal is being used, it is the same instance that is retrieved from the HttpSession for each thread.
ThreadLocal が使用されていたとしても、それぞれのスレッドで HttpSession から取得したインスタンスは同じになります。

> This has implications if you wish to temporarily change the context under which a thread is running.
これは、もし必要であればスレッド上からコンテキストを変更できるということを意味しています。

> If you just use SecurityContextHolder.getContext(), and call setAuthentication(anAuthentication) on the returned context object, then the Authentication object will change in all concurrent threads which share the same SecurityContext instance.
もし、 SecurityContextHolder.getContext() を使用し、 setAuthentication(anAuthentication) を実行した場合、 Authentication オブジェクトは SecurityContext インスタンスを共有している同時実行されている全てのスレッドで変更されます。

> You can customize the behaviour of SecurityContextPersistenceFilter to create a completely new SecurityContext for each request, preventing changes in one thread from affecting another.
あるスレッドでの変更が他のスレッドに影響を与えないように、それぞれのリクエストごとに新しい SecurityContext を作成するよう SecurityContextPersistenceFilter のふるまいをカスタマイズすることが可能です。

> Alternatively you can create a new instance just at the point where you temporarily change the context.
他の手段として、コンテキストが一時的に変更されるポイントで新しいインスタンスを生成することができます。

> The method SecurityContextHolder.createEmptyContext() always returns a new context instance.
SecurityContextHolder.createEmptyContext() メソッドは、常に新しいコンテキストインスタンスを返します。

## 9.5 Access-Control (Authorization) in Spring Security
> The main interface responsible for making access-control decisions in Spring Security is the AccessDecisionManager.
Spring Security でアクセス制御を行う責務を持つ主要なインターフェースは、 AccessDecisionManager です。

> It has a decide method which takes an Authentication object representing the principal requesting access, a "secure object" (see below) and a list of security metadata attributes which apply for the object (such as a list of roles which are required for access to be granted).
これは、 Authentication オブジェクトを受け取り、その Authentication が表すプリンシパルに対してリクエストへのアクセスを決定するメソッドを持ちます。
セキュリティオブジェクトとセキュリティメタデータ属性のリクエストをオブジェクト（アクセスに必要なロールのリスト）に対して適用します。

### 9.5.1 Security and AOP Advice
> If you’re familiar with AOP, you’d be aware there are different types of advice available: before, after, throws and around.
もし AOP を知っているなら、利用可能なアドバイスは before, after, throws, そして around であることに気付くでしょう。

> An around advice is very useful, because an advisor can elect whether or not to proceed with a method invocation, whether or not to modify the response, and whether or not to throw an exception.
around アドバイスはとても便利です。
なぜなら、アドバイザーはメソッドの実行を続けるかどうか、レスポンスを書き換えるかどうか、そして例外をスローするかどうかを選択できるからです。

> Spring Security provides an around advice for method invocations as well as web requests.
Spring Security は、 web リクエストに around アドバイスをメソッド実行に提供しています。

> We achieve an around advice for method invocations using Spring’s standard AOP support and we achieve an around advice for web requests using a standard Filter.
Spring 標準の AOP でメソッドに対して around アドバイスを適用しています。
そして、標準の Filter を使用して Web リクエストに対して around アドバイスを実現しています。

> For those not familiar with AOP, the key point to understand is that Spring Security can help you protect method invocations as well as web requests.
AOP を知らない場合、理解のためのキーポイントは、 Spring Security は Web リクエストと同様にメソッド実行を守ることができるということです。

> Most people are interested in securing method invocations on their services layer.
ほとんどの人は、サービスレイヤでのメソッド実行のセキュリティについて興味があります。

> This is because the services layer is where most business logic resides in current-generation Java EE applications.
なぜなら、サービスレイヤは、現代の Java EE アプリケーションで最もビジネスロジックを含んでいるからです。

> If you just need to secure method invocations in the services layer, Spring’s standard AOP will be adequate.
もしサービスレイヤでセキュアなメソッド実行が必要であれば、 Spring の標準 AOP が適切です。

> If you need to secure domain objects directly, you will likely find that AspectJ is worth considering.
ドメインオブジェクトに直接セキュリティを必要とする場合は、 AspectJ を検討する価値はあるでしょう。

> You can elect to perform method authorization using AspectJ or Spring AOP, or you can elect to perform web request authorization using filters.
AspectJ か Spring AOP のどちらをメソッド認証に使用するか、また Web リクエストの認証にフィルターを使うかどうかは選択できます。

> You can use zero, one, two or three of these approaches together.
これらのアプローチをまったく使わないか、もしくは２つ、３つと組み合わせることも可能です。

> The mainstream usage pattern is to perform some web request authorization, coupled with some Spring AOP method invocation authorization on the services layer.
主流なのは、 Web リクエスト認証とサービスレイヤに Spring AOP を適用する組み合わせです。

### 9.5.2 Secure Objects and the AbstractSecurityInterceptor
> So what is a "secure object" anyway? Spring Security uses the term to refer to any object that can have security (such as an authorization decision) applied to it.
ところで、セキュアオブジェクトは何を指しているのでしょう？
Spring Security は、セキュリティを適用するあらゆるオブジェクトを言及するのにこの用語を使用しています。
たとえば、認証の決定などです。

> The most common examples are method invocations and web requests.
最も一般的な例は、メソッド実行と、 Web のリクエストです。

> Each supported secure object type has its own interceptor class, which is a subclass of AbstractSecurityInterceptor.
それぞれのサポートされたセキュアオブジェクト型は、それ自身のインターセプタクラスを持ちます。
それは、 AbstractSecurityInterceptor のサブクラスになります。

> Importantly, by the time the AbstractSecurityInterceptor is called, the SecurityContextHolder will contain a valid Authentication if the principal has been authenticated.
重要なことは、 AbstractSecurityInterceptor が呼び出されるときには、プリンシパルが認証済みであれば SecurityContextHolder に有効な Authentication が格納されるということです。

> AbstractSecurityInterceptor provides a consistent workflow for handling secure object requests, typically:
AbstractSecurityInterceptor は、一貫性のあるセキュアオブジェクトのリクエストをハンドリングするワークフローを提供します。
典型的な例は、

1. Look up the "configuration attributes" associated with the present request
現在のリクエストに関連付けられた "設定属性" を見つける。

2. Submitting the secure object, current Authentication and configuration attributes to the AccessDecisionManager for an authorization decision
セキュアオブジェクトを、現在の Authentication と設定属性を認証決定のために AccessDecisionManager に提供する。

3. Optionally change the Authentication under which the invocation takes place
実行される場所に合わせて任意に Authentication を変更する

4. Allow the secure object invocation to proceed (assuming access was granted)
セキュアオブジェジェクトの実行を許可する（アクセスが付与されていると判断できる場合は）

5. Call the AfterInvocationManager if configured, once the invocation has returned. If the invocation raised an exception, the AfterInvocationManager will not be invoked.
AfterInvocationManagerが設定されている場合は、呼び出しが返された後にAfterInvocationManagerを呼び出します。
呼び出しによって例外が発生した場合、AfterInvocationManagerは呼び出されません。

#### What are Configuration Attributes?
> A "configuration attribute" can be thought of as a String that has special meaning to the classes used by AbstractSecurityInterceptor.
"設定属性" は、 AbstractSecurityInterceptor で使用されるクラスを意味する特別な文字列と考えることができます。

> They are represented by the interface ConfigAttribute within the framework.
それらはフレームワークに含まれる ConfigAttribute インターフェースを実装したものです。

> They may be simple role names or have more complex meaning, depending on the how sophisticated the AccessDecisionManager implementation is.
それらは単純なロール名やより複雑な意味を持ちます。
どちらになるかは、 AccessDecisionManager の実装がどの程度洗練されているかに依存します。

> The AbstractSecurityInterceptor is configured with a SecurityMetadataSource which it uses to look up the attributes for a secure object.
AbstractSecurityInterceptor は、セキュアオブジェクトの属性を見つけるために使用される SecurityMetadataSource によって構成されます。

> Usually this configuration will be hidden from the user.
通常、この設定はユーザーからは隠されます。

> Configuration attributes will be entered as annotations on secured methods or as access attributes on secured URLs.
設定属性は、セキュアメソッド上のアノテーションか、セキュアな URL の属性として宣言されます。

> For example, when we saw something like <intercept-url pattern='/secure/**' access='ROLE_A,ROLE_B'/> in the namespace introduction, this is saying that the configuration attributes ROLE_A and ROLE_B apply to web requests matching the given pattern.
たとえば、 namespace の紹介で `<intercept-url pattern='/secure/**' access='ROLE_A,ROLE_B'/>` というものを見ました。
これは、 ROLE_A と ROLE_B を指定されたパターンにマッチする Web リクエストに適用するという設定属性です。

> In practice, with the default AccessDecisionManager configuration, this means that anyone who has a GrantedAuthority matching either of these two attributes will be allowed access.
実際には、デフォルトの AccessDecisionManager の設定では、この設定が意味するのは、２つの属性（ROLE_A, ROLE_B）のいずれかにマッチする GrantedAuthority を持つ人であればだれでも、アクセスが許可される、というものです。

> Strictly speaking though, they are just attributes and the interpretation is dependent on the AccessDecisionManager implementation.
厳密に言うと、それらはただの属性であり、どう解釈されるかは AccessDecisionManager に依存します。

> The use of the prefix ROLE_ is a marker to indicate that these attributes are roles and should be consumed by Spring Security’s RoleVoter.
プレフィックスとして ROLE_ が使用されるのは、これらの属性がロールであり、 Spring Security の RoleVoter によって処理されることを意図した印です。

> This is only relevant when a voter-based AccessDecisionManager is in use.
これは、投票ベースの AccessDecisionManager を使っているときだけ関係することです。

> We’ll see how the AccessDecisionManager is implemented in the authorization chapter.
AccessDecisionManager をどのように実装するかについては、認証の章で説明します。

#### RunAsManager
> Assuming AccessDecisionManager decides to allow the request, the AbstractSecurityInterceptor will normally just proceed with the request.
AccessDecisionManager がリクエストの許可を決定すると、 AbstractSecurityInterceptor は普通、リクエストを処理するだけです。

> Having said that, on rare occasions users may want to replace the Authentication inside the SecurityContext with a different Authentication, which is handled by the AccessDecisionManager calling a RunAsManager.
しかし、まれに、SecurityContext内のAuthenticationを、RunAsManagerを呼び出すAccessDecisionManagerによって処理される別のAuthenticationに置き換えたい場合があります。

> This might be useful in reasonably unusual situations, such as if a services layer method needs to call a remote system and present a different identity.
これは、サービスレイヤメソッドがリモートシステムを呼び出して別のアイデンティティーを提示する必要があるような、珍しい状況では有益です。

> Because Spring Security automatically propagates security identity from one server to another (assuming you’re using a properly-configured RMI or HttpInvoker remoting protocol client), this may be useful.
このため、 Spring Security は自動的に他のサーバーからのセキュリティアイデンティティを伝播するので便利です。
（RMI か HttpInvoker のリモートクライアントを設定している場合は）

#### AfterInvocationManager
> Following the secure object invocation proceeding and then returning - which may mean a method invocation completing or a filter chain proceeding - the AbstractSecurityInterceptor gets one final chance to handle the invocation.
セキュリティ保護されたオブジェクト呼び出しの処理が完了した後、メソッド呼び出しの完了またはフィルタチェーンの進行を意味する戻り値に続いて、AbstractSecurityInterceptorは呼び出しを処理する最後のチャンスを得ます。

> At this stage the AbstractSecurityInterceptor is interested in possibly modifying the return object.
この段階で、 AbstractSecurityInterceptor は戻り値を変更する可能性があります。

> We might want this to happen because an authorization decision couldn’t be made "on the way in" to a secure object invocation.
セキュアオブジェクトの実行中に認証の決定ができない場合に、この問題が発生するかもしれません。

> Being highly pluggable, AbstractSecurityInterceptor will pass control to an AfterInvocationManager to actually modify the object if needed.
高度にプラガブルなので、AbstractSecurityInterceptorはAfterInvocationManagerに制御を渡し、必要に応じて実際にオブジェクトを変更します。

> This class can even entirely replace the object, or throw an exception, or not change it in any way as it chooses.
このクラスは、オブジェクトを完全に置き換えることも、例外をスローすることもできますし、選択したとおりに変更させないようにすることもできます。

> The after-invocation checks will only be executed if the invocation is successful.
実行後のチェックは、実行が成功したときにだけ行われます。

> If an exception occurs, the additional checks will be skipped.
例外が発生した場合は、後続のチェックはスキップされます。

> AbstractSecurityInterceptor and its related objects are shown in Figure 9.1, “Security interceptors and the "secure object" model”
AbstractSecurityInterceptor とそれに関連するオブジェクトは、図 9.1 で見れます。

http://docs.spring.io/spring-security/site/docs/4.2.1.RELEASE/reference/htmlsingle/#abstract-security-interceptor

#### Extending the Secure Object Model
> Only developers contemplating an entirely new way of intercepting and authorizing requests would need to use secure objects directly.
リクエストをインターセプトして新規に認証を追加する方法について考えている開発者に限り、セキュアオブジェクトを直接使う必要があるかもしれません。

> For example, it would be possible to build a new secure object to secure calls to a messaging system.
例えば、メッセージングシステムを呼ぶ新しいセキュアオブジェクトを作ることが可能です。

> Anything that requires security and also provides a way of intercepting a call (like the AOP around advice semantics) is capable of being made into a secure object.
セキュリティを必要とし、 AOP の around のようにインターセプトする方法を提供するものは全て、セキュアオブジェクトにすることができます。

> Having said that, most Spring applications will simply use the three currently supported secure object types (AOP Alliance MethodInvocation, AspectJ JoinPoint and web request FilterInvocation) with complete transparency.
しかし、ほとんどの Spring アプリケーションは３つの単純なセキュアオブジェクトの種類を完全に透過的に使用します。
AOP Alliance MethodInvocation, AspectJ JoinPoint, そして Web リクエストの FilterInvocation

## 9.6 Localization
> Spring Security supports localization of exception messages that end users are likely to see.
Spring Security は、エンドユーザーが見るような例外のメッセージのローカライズをサポートしています。

> If your application is designed for English-speaking users, you don’t need to do anything as by default all Security messages are in English.
もしアプリケーションが英語話者のために設計されているならば、全てのメッセージはデフォルトで英語なので何もする必要はありません。

> If you need to support other locales, everything you need to know is contained in this section.
もしあなたが他のロケールをサポートしなければならないなら、このセクションで記載されていることをすべて理解しなければなりません。

> All exception messages can be localized, including messages related to authentication failures and access being denied (authorization failures).
全ての例外メッセージはローカライズできます。
例外メッセージは、認証エラーとアクセス拒否に関連しています。

> Exceptions and logging messages that are focused on developers or system deployers (including incorrect attributes, interface contract violations, using incorrect constructors, startup time validation, debug-level logging) are not localized and instead are hard-coded in English within Spring Security’s code.
開発者かシステム管理者にフォーカスした例外とロギングメッセージ(不正な属性、インターフェースの規約違反、間違ったコンストラクタの使用、起動時の検証、デバッグレベルのロギング)は、ローカライズされていません。
Spring Security のコードでは、代わりに英語をハードコードしています。

> Shipping in the spring-security-core-xx.jar you will find an org.springframework.security package that in turn contains a messages.properties file, as well as localized versions for some common languages.
spring-security-core-xx.jar の中で org.springframework.security パッケージを見ることができます。
そこには messages.property ファイルや他の共通言語バージョンのローカライズファイルがあります。

> This should be referred to by your ApplicationContext, as Spring Security classes implement Spring’s MessageSourceAware interface and expect the message resolver to be dependency injected at application context startup time.
このファイルは ApplicationContext から参照される。
Spring Security のクラス達は、 Spring の MessageSourceAware を実装します。
アプリケーションコンテキストが起動するときに、依存関係を注入するメッセージ resolver が期待される。

> Usually all you need to do is register a bean inside your application context to refer to the messages.
通常、メッセージを参照するためにアプリケーションコンテキストに Bean を登録する必要があります。

> An example is shown below:
例は以下のような感じです

```xml
<bean id="messageSource"
    class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
    <property name="basename" value="classpath:org/springframework/security/messages"/>
</bean>
```

> The messages.properties is named in accordance with standard resource bundles and represents the default language supported by Spring Security messages.
message.properties は標準のリソースバンドルと Spring Security メッセージによるデフォルトの言語サポートに従って名前付けされている。

> This default file is in English.
デフォルトは英語です。

> If you wish to customize the messages.properties file, or support other languages, you should copy the file, rename it accordingly, and register it inside the above bean definition.
もし messages.properties ファイルをカスタマイズしたり、他の言語をサポートしたいのであれば、ファイルをコピーし、適切な名前にリネームし、 Bean 定義に登録してください。

> There are not a large number of message keys inside this file, so localization should not be considered a major initiative.
このファイルには多くのメッセージはありません。
よって、ローカライズは主要なイニシアチブと考えてはいけません。

> If you do perform localization of this file, please consider sharing your work with the community by logging a JIRA task and attaching your appropriately-named localized version of messages.properties.
もしこのファイルのローカライズをしたい場合、 JIRA タスクで記録しているコミュニティに参加を検討してください。
あなたが適切に名前付けしローカライズされたバージョンの messages.properties とともに。

> Spring Security relies on Spring’s localization support in order to actually lookup the appropriate message.
Spring Security は、適切なメッセージを探し出すために Spring のローカライズサポートを信頼しています。

> In order for this to work, you have to make sure that the locale from the incoming request is stored in Spring’s org.springframework.context.i18n.LocaleContextHolder.
このため、あなたはやってきたリクエストから取得し、 Spring の LocaleContextHolder に保存されたロケールを確認できます。

> Spring MVC’s DispatcherServlet does this for your application automatically, but since Spring Security’s filters are invoked before this, the LocaleContextHolder needs to be set up to contain the correct Locale before the filters are called.
Spring MVC の DispatcherServlet は、自動的にこれをします。
しかし、 Spring Security のフィルターは、この処理(DispatcherServlet の処理)の前に実行されるので、 LocaleContextHolder はフィルターが呼び出されるまえに正しくセットアップされている必要があります。

> You can either do this in a filter yourself (which must come before the Spring Security filters in web.xml) or you can use Spring’s RequestContextFilter.
自作のフィルターを用意して実現する（web.xml で Spring Security のフィルターより前にくるように定義する必要がある）か、 Spring の RequestContextFilter を使うことで実現できます。

> Please refer to the Spring Framework documentation for further details on using localization with Spring.
Spring Framework のドキュメントを参照し、 Spring でローカライズをどうするか詳細について参照してください。

> The "contacts" sample application is set up to use localized messages.
"contacts" サンプルアプリケーションは、ローカライズされたメッセージを使用しています。

## 10. Core Services
> Now that we have a high-level overview of the Spring Security architecture and its core classes, let’s take a closer look at one or two of the core interfaces and their implementations, in particular the AuthenticationManager, UserDetailsService and the AccessDecisionManager.
私たちはハイレベルな Spring Security のアーキテクチャとコアクラスについて見ました。
次はさらに詳細に踏み込み、それらのインターフェースの実装をいくつか見ていきましょう。
例えば AuthenticationManager や UserDetailsService そして AccessDecisionManager です。

> These crop up regularly throughout the remainder of this document so it’s important you know how they are configured and how they operate.
これらは、この文書の残りの部分で定期的にまとめられていますので、設定方法と動作方法を理解することが重要です。

### 10.1 The AuthenticationManager, ProviderManager and AuthenticationProvider
> The AuthenticationManager is just an interface, so the implementation can be anything we choose, but how does it work in practice? 
AuthenticationManager はただのインターフェースです。
なので、実装はなんでも選択できます。
しかし、それはどのようにして働くのでしょうか？

> What if we need to check multiple authentication databases or a combination of different authentication services such as a database and an LDAP server?
複数の認証データベースや異なる認証サービス（例えばデータベースと LDAP サーバー）の組み合わせのチェックでは、何をしなければならないでしょうか？

> The default implementation in Spring Security is called ProviderManager and rather than handling the authentication request itself, it delegates to a list of configured AuthenticationProvider s, each of which is queried in turn to see if it can perform the authentication.
デフォルトの Spring Security の実装は、 ProviderManager と呼ばれ、認証処理自体を制御するのではなく、設定された AuthenticationProvider のリストに委譲し、それらに認証ができるか問い合わせます。

> Each provider will either throw an exception or return a fully populated Authentication object.
それぞれのプロバイダは、例外をスローするか、完全に構築された Authentication オブジェクトを返します。

> Remember our good friends, UserDetails and UserDetailsService? If not, head back to the previous chapter and refresh your memory.
友よ、 UserDetails と UserDetailsService について覚えていますか？
もし覚えてないなあら、前節に戻って記憶をリフレッシュしてください。

> The most common approach to verifying an authentication request is to load the corresponding UserDetails and check the loaded password against the one that has been entered by the user.
認証リクエストの検証のための多くの一般的なアプローチは、対応する UserDetails を読み込み、ロードされたパスワードとユーザーによって入力されたパスワードをチェックすることです。

> This is the approach used by the DaoAuthenticationProvider (see below).
これは DaoAuthenticationProvider によって使用されるアプローチです（以下をご覧ください）。

> The loaded UserDetails object - and particularly the GrantedAuthority s it contains - will be used when building the fully populated Authentication object which is returned from a successful authentication and stored in the SecurityContext.
ロードされた UserDetails オブジェクト（個々の GrantedAuthority を含む）は、完全に構築された Authentication オブジェクトを構築する際に使用され、認証に成功すると return され、 SecurityContext に保存される。

> If you are using the namespace, an instance of ProviderManager is created and maintained internally, and you add providers to it by using the namespace authentication provider elements (see the namespace chapter).
もし namespace を使用しているなら、 ProviderManager のインスタンスは内部的に作成され維持される。
namespace の authentication provider タグを使ってプロバイダを追加できる（namespace の章を見てください）。

> In this case, you should not declare a ProviderManager bean in your application context.
この場合、あなたは ProviderManager Bean をアプリケーションコンテキスト内で宣言する必要はありません。

> However, if you are not using the namespace then you would declare it like so:
しかしながら、もしあなたが namespace を使用していない場合は、次のように宣言しなければなりません。

```xml
<bean id="authenticationManager"
        class="org.springframework.security.authentication.ProviderManager">
    <constructor-arg>
        <list>
            <ref local="daoAuthenticationProvider"/>
            <ref local="anonymousAuthenticationProvider"/>
            <ref local="ldapAuthenticationProvider"/>
        </list>
    </constructor-arg>
</bean>
```

> In the above example we have three providers.
上記例には３つのプロバイダがいます。

> They are tried in the order shown (which is implied by the use of a List), with each provider able to attempt authentication, or skip authentication by simply returning null.
それらはまたままの順序で実行され、それぞれのプロバイダが認証可能か、シンプルな null を返してスキップするかを試みます。

> If all implementations return null, the ProviderManager will throw a ProviderNotFoundException.
もしすべての実装が null を返した場合、 ProviderManager は ProviderNotFoundException をスローします。

> If you’re interested in learning more about chaining providers, please refer to the ProviderManager Javadoc.
プロバイダの連結についてもっと知りたい場合は、 ProviderManager の Javadoc を参照してください。

> Authentication mechanisms such as a web form-login processing filter are injected with a reference to the ProviderManager and will call it to handle their authentication requests.
Web の form ログインを処理するのフィルターような認証メカニズムは、 ProviderManager への参照とともにインジェクションされ、認証リクエストによってハンドリングできます。

> The providers you require will sometimes be interchangeable with the authentication mechanisms, while at other times they will depend on a specific authentication mechanism.
あなたが求めるプロバイダは、ときどき認証メカニズムとともに取り換えが可能です。
それは他の場合は特定の認証メカニズムに依存します。

> For example, DaoAuthenticationProvider and LdapAuthenticationProvider are compatible with any mechanism which submits a simple username/password authentication request and so will work with form-based logins or HTTP Basic authentication.
たとえば、 DaoAuthenticationProvider と LdapAuthenticationProvider は、任意のメカニズムで交換可能です。
それら（メカニズム）は、単純にユーザー名とパスワードの認証リクエストで、 form ベースのログインが HTTP Basic 認証で動作します。

> On the other hand, some authentication mechanisms create an authentication request object which can only be interpreted by a single type of AuthenticationProvider.
言い換えると、いくつかの認証メカニズムは、単一の AuthenticationProvider の実装型にだけ解釈可能な認証リクエストオブジェクトを作ります。

> An example of this would be JA-SIG CAS, which uses the notion of a service ticket and so can therefore only be authenticated by a CasAuthenticationProvider.
例えば、 JA-SIG CAS です。
これは、サービスチケットという概念を使うので、 CasAuthenticationProvider にだけ認証が可能です。

> You needn’t be too concerned about this, because if you forget to register a suitable provider, you’ll simply receive a ProviderNotFoundException when an attempt to authenticate is made.
これについて考える必要はありません。
なぜなら、適用可能なプロバイダを登録することを忘れていたなら、単に認証のときに ProviderNotFoundException を受け取るからです。

#### 10.1.1 Erasing Credentials on Successful Authentication
> By default (from Spring Security 3.1 onwards) the ProviderManager will attempt to clear any sensitive credentials information from the Authentication object which is returned by a successful authentication request.
Spring Security 3.1 以降のデフォルトの ProviderManager は、慎重に扱わなければならに資格情報は、認証に成功したリクエストから返された Authentication オブジェクトからクリアするようになっています。

> This prevents information like passwords being retained longer than necessary.
この保護される情報は、例えばパスワードです。
これはそれ以上の間持っている必要がありません。

> This may cause issues when you are using a cache of user objects, for example, to improve performance in a stateless application.
これは、ユーザーオブジェクトのキャッシュを使っているときに問題になります。
たとえば、ステートレスなアプリケーションのパフォーマンスを改善するときなどです。

> If the Authentication contains a reference to an object in the cache (such as a UserDetails instance) and this has its credentials removed, then it will no longer be possible to authenticate against the cached value.
もし Authentication がキャッシュされたオブジェクト（例えば UserDetails）への参照を持つ場合、そしてそれが削除される資格情報を持つ場合、キャッシュされた値に対する認証はもうできない。

> You need to take this into account if you are using a cache.
キャッシュを使用しているなら、これを考慮する必要がある、。

> An obvious solution is to make a copy of the object first, either in the cache implementation or in the AuthenticationProvider which creates the returned Authentication object.
明らかな解決策は、キャッシュの実装か、返されたAuthenticationオブジェクトを作成するAuthenticationProviderのどちらかでオブジェクトのコピーを作成することです。

> Alternatively, you can disable the eraseCredentialsAfterAuthentication property on ProviderManager. See the Javadoc for more information.
もしくは、 ProviderManager の eraseCredentialsAfterAuthentication プロパティを無効にすることです。
さらに情報が欲しい場合は Javadoc を参照してください。

### 10.1.2 DaoAuthenticationProvider
> The simplest AuthenticationProvider implemented by Spring Security is DaoAuthenticationProvider, which is also one of the earliest supported by the framework.
最も単純な AuthenticationProvider は、 Spring Security によって実装された DaoAuthenticationProvider です。
それはフレームワークによって最も最初期にサポートされたものでもあります。

> It leverages a UserDetailsService (as a DAO) in order to lookup the username, password and GrantedAuthority s.
それは UserDetailsService を ユーザー名とパスワード、そして GrantedAuthority を検索するための DAO として力点をおいています。

> It authenticates the user simply by comparing the password submitted in a UsernamePasswordAuthenticationToken against the one loaded by the UserDetailsService.
それは UserDetailsService によってロードされたユーザーを UsernamePasswordAuthenticationToken でサブミットされた単純なパスワードの比較で認証します。

> Configuring the provider is quite simple:
プロバイダの設定はシンプルです。

```xml
<bean id="daoAuthenticationProvider"
    class="org.springframework.security.authentication.dao.DaoAuthenticationProvider">
    <property name="userDetailsService" ref="inMemoryDaoImpl"/>
    <property name="passwordEncoder" ref="passwordEncoder"/>
</bean>
```

> The PasswordEncoder is optional.
PasswordEncoder はオプションです。

> A PasswordEncoder provides encoding and decoding of passwords presented in the UserDetails object that is returned from the configured UserDetailsService.
PasswordEncoder は、設定された UserDetailsService によって返された UserDetails のパスワードのエンコードとデコードを提供します。

> This will be discussed in more detail below.
これは、次で詳細について議論します。

### 10.2 UserDetailsService Implementations
> As mentioned in the earlier in this reference guide, most authentication providers take advantage of the UserDetails and UserDetailsService interfaces.

> Recall that the contract for UserDetailsService is a single method:

```java
UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
```

> The returned UserDetails is an interface that provides getters that guarantee non-null provision of authentication information such as the username, password, granted authorities and whether the user account is enabled or disabled.
返された UserDetails は、ユーザー名、パスワード、与えられた権限、そしてユーザーのアカウントが有効か無効かなど、 null ではない認証のための情報を提供するゲッターを定義したインターフェースです。

> Most authentication providers will use a UserDetailsService, even if the username and password are not actually used as part of the authentication decision.
ほとんどの認証プロバイダは、 UserDetailsService を使用します。
たとえ、ユーザー名とパスワードを認証の決定の一部で実際には使っていなかったとしてもです。

> They may use the returned UserDetails object just for its GrantedAuthority information, because some other system (like LDAP or X.509 or CAS etc) has undertaken the responsibility of actually validating the credentials.
UserDetailsService は GrantedAuthority のために返された UserDetails を使用することがあります。
なぜなら、他のシステム（例えば LDAP や X.509, CAS など）が資格情報の検証を行うための責任を持つためです。

> Given UserDetailsService is so simple to implement, it should be easy for users to retrieve authentication information using a persistence strategy of their choice.
与えられた UserDetailsService はシンプルな実装です。
選択した永続化の戦略を使用してユーザーについての認証情報を検索することは単純であるべきです。

> Having said that, Spring Security does include a couple of useful base implementations, which we’ll look at below.
つまり、Spring Securityにはいくつかの便利な基本実装が含まれています。