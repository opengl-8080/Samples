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


