> The advanced authorization capabilities within Spring Security represent one of the most compelling reasons for its popularity.
SID
    - 
Spring Security が持つ先進的な認可の機能は、 Spring Security が好かれる理由の代表的な１つです。

> Irrespective of how you choose to authenticate - whether using a Spring Security-provided mechanism and provider, or integrating with a container or other non-Spring Security authentication authority - you will find the authorization services can be used within your application in a consistent and simple way.

どのような認証を選んでいるかに関係なく、認可のしくみは単一の方法で適用できる。
要は、認可の仕組みは他の仕組みと独立している、ってことを言いたいのだと思う。

> In this part we’ll explore the different AbstractSecurityInterceptor implementations, which were introduced in Part I.
> We then move on to explore how to fine-tune authorization through use of domain access control lists.
このパートで説明すること、認可の微調整性について

> 24. Authorization Architecture
> 24.1 Authorities

> As we saw in the technical overview, all Authentication implementations store a list of GrantedAuthority objects.
`Authentication` インターフェースの実装には、 `GrantedAuthority` オブジェクトのリストが含まれる。

> These represent the authorities that have been granted to the principal.
これは認証された対象に与えられた権限を表す。

> the GrantedAuthority objects are inserted into the Authentication object by the AuthenticationManager and are later read by AccessDecisionManager s when making authorization decisions.
`GrantedAuthority` オブジェクトは `AuthenticationManager` によって `Authentication` オブジェクトに格納される。
そして、 `AccessDecisionManager` によって読み取られる。

> GrantedAuthority is an interface with only one method:
`GrantedAuthority` インターフェースには以下の１つのメソッドがある。

```java
String getAuthority();
```

> This method allows AccessDecisionManager s to obtain a precise String representation of the GrantedAuthority.
このメソッドを使うことで、 `AccessDecisionManager` は `GrantedAuthority` の String 表現を取得できる。

> By returning a representation as a String, a GrantedAuthority can be easily "read" by most AccessDecisionManager s.
String で返すことで、ほとんどの `AccessDecisionManager` で `GrantedAuthority` を簡単に読み取ることができるようになる。

> If a GrantedAuthority cannot be precisely represented as a String, the GrantedAuthority is considered "complex" and getAuthority() must return null.
`GrantedAuthority` を単純な String で表現できない場合、 `GrantedAuthority` は `null` を返さなければならない。

> An example of a "complex" GrantedAuthority would be an implementation that stores a list of operations and authority thresholds that apply to different customer account numbers.
複雑な `GrantedAuthority` の例としては、顧客のアカウント番号ごとに適用する操作や権限・閾値のリストを保持した実装などがあります。

> Representing this complex GrantedAuthority as a String would be quite difficult, and as a result the getAuthority() method should return null.
この実装を String で表現することは困難なので、結果として `getAuthority()` メソッドは null をかえさなければならない。

> This will indicate to any AccessDecisionManager that it will need to specifically support the GrantedAuthority implementation in order to understand its contents.
これは、 `AccessDecisionManager` が `GrantedAuthority` の内容を理解するために、 `GrantedAuthority` の実装ごとに特別なサポートを必要とすることを示しています。

> Spring Security includes one concrete GrantedAuthority implementation, GrantedAuthorityImpl.
Spring Security には、 `GrantedAuthority` を実装したクラスが１つ用意されています。

> This allows any user-specified String to be converted into a GrantedAuthority.
これは、ユーザーが指定した任意の String を `GrantedAuthority` に変換できます。

> All AuthenticationProvider s included with the security architecture use GrantedAuthorityImpl to populate the Authentication object.
すべての `AuthenticationProvider` は、 `GrantedAuthorityImpl` を使って `Authentication` オブジェクトを生成します。

> 24.2 Pre-Invocation Handling
> As we’ve also seen in the Technical Overview chapter, Spring Security provides interceptors which control access to secure objects such as method invocations or web requests.
Spring Security にはオブジェクトへのアクセスを制御するためのインターセプタが存在する。

> A pre-invocation decision on whether the invocation is allowed to proceed is made by the AccessDecisionManager.
呼び出し前に実行を許可するかどうかを決定するのは、 `AccessDecisionManager` によって処理されます。

> 24.2.1 The AccessDecisionManager
> The AccessDecisionManager is called by the AbstractSecurityInterceptor and is responsible for making final access control decisions. the AccessDecisionManager interface contains three methods:
`AccessDecisionManager` は、 `AbstractSecurityInterceptor` によって呼び出さ、最終的なアクセス制御を決定する。

```java
void decide(Authentication authentication, Object secureObject,
    Collection<ConfigAttribute> attrs) throws AccessDeniedException;

boolean supports(ConfigAttribute attribute);

boolean supports(Class clazz);
```

> The AccessDecisionManager's decide method is passed all the relevant information it needs in order to make an authorization decision.
`AccessDecisionManager` の `decide()` メソッドには、認可決定を行うために必要なすべての情報が渡される。

> In particular, passing the secure Object enables those arguments contained in the actual secure object invocation to be inspected.
特に、セキュアオブジェクトを渡すことで、実際のセキュアオブジェクトに含まれる引数を検査することができる。

> For example, let’s assume the secure object was a MethodInvocation.
例えば、セキュアオブジェクトが `MethodInvocation` であったとします。

> It would be easy to query the MethodInvocation for any Customer argument, and then implement some sort of security logic in the AccessDecisionManager to ensure the principal is permitted to operate on that customer.
`MethodInvocation` から引数の情報を問い合わせて、許可された操作かどうかを確実に判定するロジックを `AccessDecisionManager` に実装することは容易、みたいな話？

> Implementations are expected to throw an AccessDeniedException if access is denied.
アクセスが拒否された場合は `AccessDeniedException` をスローする実装が期待される。

> The supports(ConfigAttribute) method is called by the AbstractSecurityInterceptor at startup time to determine if the AccessDecisionManager can process the passed ConfigAttribute.
`supports(ConfigAttribute)` メソッドは `AbstractSecurityInterceptor` によって起動時に呼び出され、 `ConfigAttribute` を `AccessDecisionManager` が処理できるか判定する。

> The supports(Class) method is called by a security interceptor implementation to ensure the configured AccessDecisionManager supports the type of secure object that the security interceptor will present.
`supports(Class)` メソッドは、セキュリティインターセプターの実装によって呼び出され、設定されたAccessDecisionManagerがセキュリティインターセプタが提示するセキュリティオブジェクトのタイプを確実にサポートするようにします？？？

> 24.2.2 Voting-Based AccessDecisionManager Implementations

> Whilst users can implement their own AccessDecisionManager to control all aspects of authorization, Spring Security includes several AccessDecisionManager implementations that are based on voting.
`AccessDecisionManager` を実装すれば任意の認可制御が可能。
Spring Security にはデフォルトでいくつかの実装が用意されている。

> Figure 24.1, “Voting Decision Manager” illustrates the relevant classes.

> Using this approach, a series of AccessDecisionVoter implementations are polled on an authorization decision.
このアプローチでは、一連の `AccessDecisionVoter` の実装が、認可決定について投票を行います。

> The AccessDecisionManager then decides whether or not to throw an AccessDeniedException based on its assessment of the votes.
`AccessDecisionManager` は、投票の結果に基づいて `AccessDeniedException` をスローするかどうかを決めます。

> The AccessDecisionVoter interface has three methods:
`AccessDecisionVoter` インターフェースは３つのメソッドを持ちます。

```java
int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attrs);

boolean supports(ConfigAttribute attribute);

boolean supports(Class clazz);
```

> Concrete implementations return an int, with possible values being reflected in the AccessDecisionVoter static fields ACCESS_ABSTAIN, ACCESS_DENIED and ACCESS_GRANTED.
`AccessDecisionVoter` に定義されている３つの static フィールドを返す。

> A voting implementation will return ACCESS_ABSTAIN if it has no opinion on an authorization decision.
認可の決定について意見がない場合は、 `ACCESS_ABSTAIN` を返す。

> If it does have an opinion, it must return either ACCESS_DENIED or ACCESS_GRANTED.
意見がある場合は、 `ACCESS_DENIED` か `ACCESS_GRANTED` のいずれかを返す。

> There are three concrete AccessDecisionManager s provided with Spring Security that tally the votes.
Spring Security は３つの `AccessDecisionManager` の実装を提供している。

> The ConsensusBased implementation will grant or deny access based on the consensus of non-abstain votes.
`ConsensusBased` は、棄権以外の投票の多数決で決める。

> Properties are provided to control behavior in the event of an equality of votes or if all votes are abstain.
付与と拒否の投票数が等しい場合や、全ての投票が棄権だった場合の振る舞いはプロパティによって制御できる。

> The AffirmativeBased implementation will grant access if one or more ACCESS_GRANTED votes were received (i.e. a deny vote will be ignored, provided there was at least one grant vote).
`AffirmativeBased` は、１つ以上の `ACCESS_GRANTED` の投票を受け取るとアクセスを付与する。

> Like the ConsensusBased implementation, there is a parameter that controls the behavior if all voters abstain.
`ConsensusBased` のように、全てが棄権だった場合のふるまいはパラメータで制御できる。

> The UnanimousBased provider expects unanimous ACCESS_GRANTED votes in order to grant access, ignoring abstains.
`UnanimousBased` は、全会一致で `ACCESS_GRANTED` であることを期待する。棄権については無視する。

> It will deny access if there is any ACCESS_DENIED vote.
`ACCESS_DENIED` が１つでもあれば、アクセスを拒否する。

> Like the other implementations, there is a parameter that controls the behaviour if all voters abstain.
他の実装と同じで、全てが棄権だった場合の制御はパラメータで可能。

> It is possible to implement a custom AccessDecisionManager that tallies votes differently.
異なる制御の `AccessDecisionManager` を実装することは可能です。

> For example, votes from a particular AccessDecisionVoter might receive additional weighting, whilst a deny vote from a particular voter may have a veto effect.
例えば、個々の `AccessDecisionVoter` からの投票を受け取ったら重みづけを行い、特定の投票者からの拒否を優先させるようなことも可能です。

> RoleVoter
> The most commonly used AccessDecisionVoter provided with Spring Security is the simple RoleVoter, which treats configuration attributes as simple role names and votes to grant access if the user has been assigned that role.
Spring Security が提供する、もっとも共通的に使用される `AccessDecisionVoter` は、単純な `RoleVoter` です。
`RoleVoter` は単純なロール名による属性に設定された値がユーザーの権限に設定されているかどうかを見て、アクセスを許可するか投票します。

> It will vote if any ConfigAttribute begins with the prefix ROLE_.
`RoleVoter` は、 `ConfigAttribute` が `ROLE_` で始まっている場合に投票を行います。

> It will vote to grant access if there is a GrantedAuthority which returns a String representation (via the getAuthority() method) exactly equal to one or more ConfigAttributes starting with the prefix ROLE_.
`GrantedAuthority` が返す String 表現が、 `ConfigAttribute` の `ROLE_` で始まるいずれかに一致すれば、アクセスを付与するために投票を行います。

> If there is no exact match of any ConfigAttribute starting with ROLE_, the RoleVoter will vote to deny access.
`ROLE_` で始まる `ConfigAttribute` のいずれかに一致するものがない場合、 `RoleVoter` はアクセスを拒否する投票をします。

> If no ConfigAttribute begins with ROLE_, the voter will abstain.
もし `ROLE_` で始まる `ConfigAttribute` が存在しない場合は、棄権します。

> AuthenticatedVoter
> Another voter which we’ve implicitly seen is the AuthenticatedVoter, which can be used to differentiate between anonymous, fully-authenticated and remember-me authenticated users.
私たちが暗黙的に見てきた投票者は、 `AuthenticatedVoter` です。
それは、匿名、完全認証済み、RememberMe 認証ユーザーに分けることができます。

> Many sites allow certain limited access under remember-me authentication, but require a user to confirm their identity by logging in for full access.
多くのサイトでは、 RememberMe 認証による一定の制限付きのアクセスを許可していますが、フルアクセスを許可するにはログインして身元を確認する必要があります。

> When we’ve used the attribute IS_AUTHENTICATED_ANONYMOUSLY to grant anonymous access, this attribute was being processed by the AuthenticatedVoter. See the Javadoc for this class for more information.
匿名アクセスを許可するために属性IS_AUTHENTICATED_ANONYMOUSLYを使用した場合、この属性はAuthenticatedVoterによって処理されていました。詳細については、このクラスのJavadocを参照してください。

> Custom Voters
> Obviously, you can also implement a custom AccessDecisionVoter and you can put just about any access-control logic you want in it.
`AccessDecisionVoter` を自作することも、必要なアクセス制御ロジックを配置することも可能です。

> It might be specific to your application (business-logic related) or it might implement some security administration logic.
アプリケーション固有のセキュリティ管理ロジックを実装することも可能です。

> For example, you’ll find a blog article on the Spring web site which describes how to use a voter to deny access in real-time to users whose accounts have been suspended.
例えば、 Web 記事には、一時停止アカウントのアクセス拒否を行う Voter について記載されています。
https://spring.io/blog/2009/01/03/spring-security-customization-part-2-adjusting-secured-session-in-real-time

> 24.4 Hierarchical Roles
> It is a common requirement that a particular role in an application should automatically "include" other roles.
アプリケーションにおける特定のロールが他のロールを自動的に含むというのは、共通する要件です。

> For example, in an application which has the concept of an "admin" and a "user" role, you may want an admin to be able to do everything a normal user can.
例えば、管理者とユーザーというロールを持つアプリケーションでは、管理者は一般ユーザーができることをすべてできるようにしたいと考えるでしょう。

> To achieve this, you can either make sure that all admin users are also assigned the "user" role.
このためには、全ての管理者ユーザーに、ユーザーロールもまたアサインすることになります。

> Alternatively, you can modify every access constraint which requires the "user" role to also include the "admin" role.
？？？

> This can get quite complicated if you have a lot of different roles in your application.
このことは、アプリケーションに複数の役割がある場合に複雑になります。

> The use of a role-hierarchy allows you to configure which roles (or authorities) should include others.
ロール階層化を使うと、ロール（もしくは権限）を他のものに含ませることができるようになります。

> An extended version of Spring Security’s RoleVoter, RoleHierarchyVoter, is configured with a RoleHierarchy, from which it obtains all the "reachable authorities" which the user is assigned.
Spring Security の `RoleVoter` を継承した `RoleHierarchyVoter` は RoleHierarchy で構成されている。
それはユーザーがアサインした到達可能なすべての権限を含む。

> A typical configuration might look like this:
一般的な設定は次のようになる。

```xml
<bean id="roleVoter" class="org.springframework.security.access.vote.RoleHierarchyVoter">
    <constructor-arg ref="roleHierarchy" />
</bean>
<bean id="roleHierarchy"
        class="org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl">
    <property name="hierarchy">
        <value>
            ROLE_ADMIN > ROLE_STAFF
            ROLE_STAFF > ROLE_USER
            ROLE_USER > ROLE_GUEST
        </value>
    </property>
</bean>
```

> Here we have four roles in a hierarchy ROLE_ADMIN ⇒ ROLE_STAFF ⇒ ROLE_USER ⇒ ROLE_GUEST.
４つのロールによる階層があります。
ROLE_ADMIN > ROLE_STAFF > ROLE_USER > ROLE_GUEST

> A user who is authenticated with ROLE_ADMIN, will behave as if they have all four roles when security constraints are evaluated against an AccessDecisionManager cconfigured with the above RoleHierarchyVoter.
ROLE_ADMIN によって認証されたユーザーは、全てのロールを持つものとして処理される。

> The > symbol can be thought of as meaning "includes".
`>` シンボルは、「含む」の意味を表している。
ROLE_ADMIN は ROLE_STAFF を含む。

> Role hierarchies offer a convenient means of simplifying the access-control configuration data for your application and/or reducing the number of authorities which you need to assign to a user.
ロール階層は、あなたのアプリケーションに単純にアクセス制御を設定する便利な手段を提供します。
そして、もしくは、ユーザーに設定しなければならない権限の数を減らします。

> For more complex requirements you may wish to define a logical mapping between the specific access-rights your application requires and the roles that are assigned to users, translating between the two when loading the user information.
より複雑で、特定のアクセス権に関する論理的なマッピングを定義したい場合は、あなたのアプリケーションはユーザーに適用するロール（ユーザー情報をロードするときに２つを変換する）を必要とする。

ユーザーに割り当てられた権限を、事前にマッピング定義された他の権限に割り当てなおすようなことで、より複雑な権限の制御が可能になる。みたいな話。

> 26. Expression-Based Access Control

アクセスコントロールを単一の boolean 式で表すことができる。
Spring EL を利用している。

ルートオブジェクトとして、 `SecurityExpressionRoot` が使用される。

hasPermission(Object target, Object permission) で、特定のオブジェクトへのアクセス権限を判定できる？

> 26.2 Web Security Expressions
> To use expressions to secure individual URLs, you would first need to set the use-expressions attribute in the <http> element to true.
個々の URL をセキュアにするために式を使用する。
<http> 要素のuse-expressions 属性に true を設定する必要がある。

> Spring Security will then expect the access attributes of the <intercept-url> elements to contain Spring EL expressions.
Spring Security は <intercept-url> 要素の access 属性に Spring EL 式を求める。

> The expressions should evaluate to a Boolean, defining whether access should be allowed or not. For example:
式は boolean として評価されなければならない。
アクセスが許可されるか否かを定義する。

> Here we have defined that the "admin" area of an application (defined by the URL pattern) should only be available to users who have the granted authority "admin" and whose IP address matches a local subnet.
ここに、アプリケーションの admin 領域（URLパターンで定義）を定義します。
この領域は、 admin 権限を付与された、ローカルサブネットのユーザーのみがアクセスできるようにします。

> We’ve already seen the built-in hasRole expression in the previous section. The expression hasIpAddress is an additional built-in expression which is specific to web security.
すでに hasRole 式は以前のセクションで見ています。
hasIpAddress 式は、組み込みの式で、 Web セキュリティを指定するために追加されています。

> It is defined by the WebSecurityExpressionRoot class, an instance of which is used as the expression root object when evaluation web-access expressions.
それは、 WebSecurityExpressionRoot クラスに定義されています。
このクラスのインスタンスは、式のルートオブジェクトとして使用されます。
web アクセス式を評価するときに。

> This object also directly exposed the HttpServletRequest object under the name request so you can invoke the request directly in an expression.
このオブジェクトは、 "request" という名前で直接 HttpServletRequest を直接露出しています。
そのため、あなたはリクエストオブジェクトに直接アクセスできます。

> If expressions are being used, a WebExpressionVoter will be added to the AccessDecisionManager which is used by the namespace.
式が使用されると、 WebExpressionVoter が AccessDecisionManager に追加され、名前空間として使用されます。？？？

> So if you aren’t using the namespace and want to use expressions, you will have to add one of these to your configuration.
そのためもしあなたが名前空間を使用していない場合で、式を使いたい場合は、設定に追加しなければなりません。

> 26.2.1 Referring to Beans in Web Security Expressions
> If you wish to extend the expressions that are available, you can easily refer to any Spring Bean you expose.
式を拡張したい場合は、簡単に Spring Bean を参照に追加することができます。

> For example, assuming you have a Bean with the name of webSecurity that contains the following method signature:
たとえば、 "webSecurity" という名前の Bean が次のようなメソッドを持つ場合、

```java
public class WebSecurity {
        public boolean check(Authentication authentication, HttpServletRequest request) {
                ...
        }
}
```

> You could refer to the method using:
メソッドを次のように参照できる

```xml
<http>
    <intercept-url pattern="/user/**"
        access="@webSecurity.check(authentication,request)"/>
    ...
</http>
```

要は `@ビーン名` で Spring Bean を参照できる。

> 26.2.2 Path Variables in Web Security Expressions
> At times it is nice to be able to refer to path variables within a URL.
URL のパス変数を参照するためのナイスな方法がある。

> For example, consider a RESTful application that looks up a user by id from the URL path in the format /user/{userId}.
例えば、 RESTful アプリケーションについて考えてください。
URL パスからユーザーの id を取得するようなフォーマットです。

/user/{userId}

```xml
<http>
    <intercept-url pattern="/user/{userId}/**"
        access="@webSecurity.checkUserId(authentication,#userId)"/>
    ...
</http>
```

> 26.3 Method Security Expressions
> Method security is a bit more complicated than a simple allow or deny rule.
メソッドセキュリティはすこし複雑です。
単純な許可・不許可のルールと比較すると。

> Spring Security 3.0 introduced some new annotations in order to allow comprehensive support for the use of expressions.
Spring Security 3.0 が式を使用するための包括的なサポートを許可する手段としていくつかの新しいアノテーションを導入した。

> 26.3.1 @Pre and @Post Annotations
> There are four annotations which support expression attributes to allow pre and post-invocation authorization checks and also to support filtering of submitted collection arguments or return values.
４つのアノテーションは、
事前と事後の認証チェックと、サブミットされた引数のコレクションや値の戻り値をフィルターするための実行を許可するための式属性をサポートする

> They are @PreAuthorize, @PreFilter, @PostAuthorize and @PostFilter. Their use is enabled through the global-method-security namespace element:
@PreAuthorize
@PreFilter
@PostAuthorize
@PostFilter
がある

`<global-method-security>` 要素で有効にできる。

> Access Control using @PreAuthorize and @PostAuthorize
> The most obviously useful annotation is @PreAuthorize which decides whether a method can actually be invoked or not.
最も明確で使いやすいアノテーションは `@PreAuthorize` です。
それは、メソッドを実際に実行するかどうかを決定します。

> For example (from the"Contacts" sample application)
例

```java
@PreAuthorize("hasRole('USER')")
public void create(Contact contact);
```

> which means that access will only be allowed for users with the role "ROLE_USER".
この意味は、 "ROLE_USER" ロールを持つユーザーのみがアクセスできる、という意味です。

> Obviously the same thing could easily be achieved using a traditional configuration and a simple configuration attribute for the required role.
明らかに、同じことが伝統的な設定とシンプルなロールを要求する属性の設定で簡単に行うことができる。

> But what about:
しかし、これについて、

```java
@PreAuthorize("hasPermission(#contact, 'admin')")
public void deletePermission(Contact contact, Sid recipient, Permission permission);
```

> Here we’re actually using a method argument as part of the expression to decide whether the current user has the "admin"permission for the given contact.
これは、私たちは実際にメソッドの引数を式の一部として使用でき、現在のユーザーが admin 認可を与えられた contact に対して持っているか検証することができる。

> The built-in hasPermission() expression is linked into the Spring Security ACL module through the application context, as we’llsee below.
組み込みの hasPermission() 式は、 Spring Security の ACL モジュールとアプリケーション古典期ストを通じてリンクしており、以下のように見ることができる。

> You can access any of the method arguments by name as expression variables.
あなたは式の変数として任意の引数を名前で参照することができる。

> There are a number of ways in which Spring Security can resolve the method arguments.
Spring Security がメソッド引数を解決する方法はいくつかある。

> Spring Security uses DefaultSecurityParameterNameDiscoverer to discover the parameter names.
Spring Security は、 `DefaultSecurityParameterNameDiscoverer` をパラメータ名を見つけるために使用する。

> By default, the following options are tried for a method as a whole.
デフォルトでは、以下のオプションがメソッド全体に対して試される。

> If Spring Security’s @P annotation is present on a single argument to the method, the value will be used.
もし Spring Security の `@P` アノテーションがメソッドの単一の引数に対して適用されている場合は、値が使用できます。

> This is useful for interfaces compiled with a JDK prior to JDK 8 which do not contain any information about the parameter names. For example:
これは、 JDK8 以上でコンパイルされたインターフェースに対して便利です。
パラメータ名につちえの情報を含んでいない
たとえば、

```java
import org.springframework.security.access.method.P;

...

@PreAuthorize("#c.name == authentication.name")
public void doSomething(@P("c") Contact contact);
```

> Behind the scenes this use implemented using AnnotationParameterNameDiscoverer which can be customized to support the value attribute of any specified annotation.
この背後では、 `AnnotationParameterNameDiscoverer` が使用されている。
このクラスは指定されたアノテーションの value 属性をサポートするように拡張できる。

> If Spring Data’s @Param annotation is present on at least one parameter for the method, the value will be used.
Spring Data の `@Param` アノテーションが少なくとも１つのメソッドパラメータに指定されている場合、値を使用できます。

> This is useful for interfaces compiled with a JDK prior to JDK 8 which do not contain any information about the parameter names. For example:

> If JDK 8 was used to compile the source with the -parameters argument and Spring 4+ is being used, then the standard JDK reflection API is used to discover the parameter names.
もし JDK8 で `-parameters` 引数とともにソースのコンパイルに使用されている場合で、 Spring 4 以上を使用している場合、一般の JDK のリフレクション API はパラメータ名を発見することができる。

> This works on both classes and interfaces.
この機能は、クラスとインターフェース両方で使える。

> Last, if the code was compiled with the debug symbols, the parameter names will be discovered using the debug symbols.
最後に、もしコードがデバッグ記号とともにコンパイルされている場合、パラメータ名はデバッグシンボルを使用して発見される。

> This will not work for interfaces since they do not have debug information about the parameter names.
これは、インターフェースでは機能しない。
なぜなら、それらはパラメータ名についてのデバッグ情報を保持していないため。

> For interfaces, annotations or the JDK 8 approach must be used.
インターフェースには、アノテーションか JDK8 によるアプローチが必要になる。

> Any Spring-EL functionality is available within the expression, so you can also access properties on the arguments.
いくつかの Spring EL 機能は、式の中で使用できる。
よって、あなたは引数のプロパティにアクセスすることもできる。

> For example, if you wanted a particular method to only allow access to a user whose username matched that of the contact, you could write
たとえば、特定のメソッドを contact のユーザー名と一致するユーザーにだけアクセスをゆるようにしたい場合、以下のよう書ける。

```java
@PreAuthorize("#contact.name == authentication.name")
public void doSomething(Contact contact);
```

> Here we are accessing another built-in expression, authentication, which is the Authentication stored in the security context.
ここで、セキュリティコンテキストに保存された `Authentication` オブジェクトや他の組み込み式にアクセスしている。

> You can also access its "principal" property directly, using the expression principal.
`principal` にも直接アクセスできる。

> The value will often be a UserDetails instance, so you might use an expression like principal.username or principal.enabled.
値が `UserDetails` インスタンスになることが多いので、 `principal.username` や `principal.enabled` というふうにアクセスすることもできる。

> Less commonly, you may wish to perform an access-control check after the method has been invoked.
あまり一般的ではないが、アクセスコントロールのチェックをメソッドの実行後にしたいこともある。

> This can be achieved using the @PostAuthorize annotation.
これは、 `@PostAuthorize` アノテーションで実現できる。

> To access the return value from a method, use the built-in name returnObject in the expression.
return された値は、 `returnObject` という名前で式の中で参照することができる。

> Filtering using @PreFilter and @PostFilter
> As you may already be aware, Spring Security supports filtering of collections and arrays and this can now be achieved using expressions.
すでにご存じのように、 Spring Security はコレクションと配列のフィルターをサポートしていて、式で使用できる。

> This is most commonly performed on the return value of a method. For example:
これは、もっとも一般的に、戻り値の値でも使用できる。

```java
@PreAuthorize("hasRole('USER')")
@PostFilter("hasPermission(filterObject, 'read') or hasPermission(filterObject, 'admin')")
public List<Contact> getAll();
```

> When using the @PostFilter annotation, Spring Security iterates through the returned collection and removes any elements for which the supplied expression is false.
`@PostFilter` アノテーションを使用するとき、 Spring Security は返却されたコレクションを反復し、式が false になった要素を削除します。

> The name filterObject refers to the current object in the collection.
`filterObject` という名前は現在のコレクションのオブジェクトを参照する。

> You can also filter before the method call, using @PreFilter, though this is a less common requirement. 
メソッドの呼び出し前でもフィルターすることができます。
`@PreFilter` を使うことで。
しかし、あまり一般的ではない。

> The syntax is just the same, but if there is more than one argument which is a collection type then you have to select one by name using the filterTarget property of this annotation.
シンタックスは同じです。
しかし、コレクション型の引数が２つ以上ある場合、 `filterTarget` 属性で引数の名前を指定する必要がある。

> Note that filtering is obviously not a substitute for tuning your data retrieval queries.
注意として、フィルタリングは明らかにデータ取得クエリのチューニングに使用するためのものではありません。

> If you are filtering large collections and removing many of the entries then this is likely to be inefficient.
もし巨大なコレクションをフィルタ氏、多くのエントリを削除する場合、非効率である可能性があります。

> The PermissionEvaluator interface
`hasPermission()` は、 `PermissionEvaluator` インターフェースを実装して作成する。

次のメソッドを定義する。

```java
boolean hasPermission(Authentication authentication, Object targetDomainObject,
                            Object permission);

boolean hasPermission(Authentication authentication, Serializable targetId,
                            String targetType, Object permission);
```

更新対象となるドメインオブジェクトに対して、現在アクセスしているユーザーがパーミッションを持つ場合、 true を返すように実装する。

前者はドメインオブジェクトがロードされている場合に使用する。
後者はドメインオブジェクト自体はまだロードされていないが、検索のためのキーが分かっている場合に使用する。

`hasPermission()` 式を使用する場合は、 `PermissionEvaluator` を実装した Bean を定義して、式を評価するハンドラーに設定する必要がある。

デフォルト実装？として `AclPermissionEvaluator` というのがあるらしい。

詳細はサンプル実装の "Contacts" を参照。

> Method Security Meta Annotations
同じ設定のアノテーションを何度も使うのはメンテナンス性が落ちるので、メタアノテーションを定義して使いまわすことができる。

```java
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("#contact.name == authentication.name")
public @interface ContactPermission {}
```

> Part VI. Additional Topics
> 27. Domain Object Security (ACLs)
> 27.1 Overview
複雑なアプリケーションでは、 URL やメソッドレベルでのアクセスコントロールでは足りない。
ドメインオブジェクトごとに、アクセス可能かコントロールできなければならないことが多い。

例えばあるペットショップのアプリを作ることを考える。
アプリには２種類のユーザーがいて、スタッフと客に分かれる。

スタッフは全てのデータにアクセスできるが、客は自分のデータにしかアクセスできない。
（場合によっては、他の客に対して閲覧を許可できるようにする必要があるかもしれない）

- 実現しないといけないこと
    - スタッフは全ての顧客レコードを参照できる
    - 顧客は自分のレコードしか見れない
    - ただし、他の顧客に参照の許可を与えることができる

３つのアプローチが考えられる。

1. ビジネスロジック内に、認可のロジックを書く
現在のユーザーについては ThreadLocal から Authentication オブジェクトを取得できる。

## 問題点
- ビジネスロジックと認可のロジックが密結合になり、テストがしづらくなる
- 認可処理の使いまわしが難しくなる

2. ある顧客レコードにアクセス可能であることを GrantedAuthority で表現し、 AccessDecisionVoter で判断させる
現在のユーザーには、アクセス可能な顧客を示す GrantedAuthority が全て設定されていることになる。

## 問題点
- アクセス可能な顧客が何千にも上る場合、メモリ消費がバカにならない
- GrantedAuthority インスタンスの生成だけでも、 CPU 資源の無駄遣いになる

3. AccessDecisionVoter を実装し、直接 Customer にアクセスして判断する
AccessDecisionVoter が DAO を使って Customer を検索し、参照を許可しているユーザーの一覧を取得し、現在のユーザーがアクセス可能かどうかを判定する。

## 問題点
- AccessDecisionVoter とビジネスロジックとで、同じ Customer が二度、同じ DAO 経由でロードされる
- １回の処理で、毎回２度同じ検索が走るのは明らかに非効率

## 全体通しても問題点
- どのアプローチでも、 ACL の保存やロジックをゼロから構築しないといけない点で微妙

> Write your business methods to enforce the security.
セキュリティを強化するビジネスロジックを書きます。

> You could consult a collection within the Customer domain object instance to determine which users have access.
顧客のドメインオブジェクトインスタンスのコレクションを見て、ユーザーのアクセスを決定することができます。

> By using the SecurityContextHolder.getContext().getAuthentication(), you’ll be able to access the Authentication object.
`SecurityContextHolder.getContext().getAuthentication()` を使うことで、 `Authentication` オブジェクトにアクセスすることができます。

> Write an AccessDecisionVoter to enforce the security from the GrantedAuthority[] s stored in the Authentication object.
`AccessDecisionVoter` を作成し、`Authentication` オブジェクトに保存された `GrantedAuthority[]` 達のセキュリティを強化します。

> This would mean your AuthenticationManager would need to populate the Authentication with custom GrantedAuthority[]s representing each of the Customer domain object instances the principal has access to.
これは、AuthenticationManagerが、プリンシパルがアクセスできる各Customerドメインオブジェクトインスタンスを表すカスタムGrantedAuthority []で認証を設定する必要があることを意味します。

> Write an AccessDecisionVoter to enforce the security and open the target Customer domain object directly.
`AccessDecisionVoter` を書きセキュリティを強化し、対象となる顧客ドメインオブジェクトを直接触るための道を開きます。

> This would mean your voter needs access to a DAO that allows it to retrieve the Customer object.
このことは、 voter が DAO にアクセスしなければならないことを意味します。
顧客オブジェクトを検索するために。

> It would then access the Customer object’s collection of approved users and make the appropriate decision.
その後、Customerオブジェクトの承認済みユーザーのコレクションにアクセスし、適切な決定を行います。

> Each one of these approaches is perfectly legitimate.
これらのアプローチは、どれも完全に正しいです。

> However, the first couples your authorization checking to your business code.
しかし、最初のアプローチは、認証とビジネスロジックのコードが密に結合されます。

> The main problems with this include the enhanced difficulty of unit testing and the fact it would be more difficult to reuse the Customer authorization logic elsewhere.
これは、ユニットテストを難しくするという問題をはらんでいます。
そして、実際に、顧客の認証ロジックを別の場所で再利用するのが困難になります。

> Obtaining the GrantedAuthority[] s from the Authentication object is also fine, but will not scale to large numbers of Customers.
`Authentication` オブジェクトから `GrantedAuthority[]` を取得する方法も同じく正しい。
しかし、顧客の数が増えたときにスケールしない。

> If a user might be able to access 5,000 Customer s (unlikely in this case, but imagine if it were a popular vet for a large Pony Club!) the amount of memory consumed and time required to construct the Authentication object would be undesirable.
もしユーザーが 5000 人の顧客にアクセスすることができたとすると、非常に多くのメモリが消費され、 `Authentication` オブジェクトの生成に時間が必要となる。

> The final method, opening the Customer directly from external code, is probably the best of the three.
最後のアプローチ（外部のコードから直接顧客を見れるようにする）は、３つの中ではおそらく最も良い。

> It achieves separation of concerns, and doesn’t misuse memory or CPU cycles, but it is still inefficient in that both the AccessDecisionVoter and the eventual business method itself will perform a call to the DAO responsible for retrieving the Customer object.
この方法はビジネスロジックと認可の関係を分離している。
メモリや CPU サイクルを無駄に使用しない。
しかし、まだ非効率だ。
AccessDecisionVoterと最終的なビジネスメソッド自体がCustomerオブジェクトの取得を担当するDAOを呼び出すという点ではまだ効率的ではありません。

> Two accesses per method invocation is clearly undesirable.
メソッドごとに２つのアクセスがあるというのは、明らかに望ましくない。

> In addition, with every approach listed you’ll need to write your own access control list (ACL) persistence and business logic from scratch.
加えて、全てのアプローチはあなた自身に ACL の永続化手段とビジネスロジックをスクラッチで書くことを必要とさせる。


> Fortunately, there is another alternative, which we’ll talk about below.
幸運なことに、他の代替手段があります。
以下でそれについて説明します。

> 27.2 Key Concepts
> Spring Security’s ACL services are shipped in the spring-security-acl-xxx.jar.
Spring Security の ACL 機能は、 spring-security-acl-xxx.jar によって提供されます。

> You will need to add this JAR to your classpath to use Spring Security’s domain object instance security capabilities.
この jar をクラスパスに追加することで、ドメインオブジェクトインスタンスでセキュリティ機能を使うことができるようになります。

> Spring Security’s domain object instance security capabilities centre on the concept of an access control list (ACL).
Spring Security のドメインオブジェクトインスタンスセキュリティ機能は、 ACL のコンセプトのに基づいています。

> Every domain object instance in your system has its own ACL, and the ACL records details of who can and can’t work with that domain object.
全てのドメインオブジェクトインスタンスは、それ自身の ACL を持ちます。
そして、 ACL は誰がドメインオブジェクトを処理できて、誰が処理できないかについて詳細を記録しています。

> With this in mind, Spring Security delivers three main ACL-related capabilities to your application:
この考えに基づき、 Spring Security は３つの主な ACL に関係した機能をあなたのアプリケーションに提供します。

> A way of efficiently retrieving ACL entries for all of your domain objects (and modifying those ACLs)
すべてのドメインオブジェクトの ACL を効率的にアクセスし、変更する手段を提供します。

> A way of ensuring a given principal is permitted to work with your objects, before methods are called
指定されたプリンシパルがメソッドを呼び出す前にオブジェクトで作業することを許可する方法

> A way of ensuring a given principal is permitted to work with your objects (or something they return), after methods are called
特定のプリンシパルを確実にする方法は、メソッドが呼び出された後でオブジェクト（またはそれらが返すもの）を処理することが許可されています

> As indicated by the first bullet point, one of the main capabilities of the Spring Security ACL module is providing a high-performance way of retrieving ACLs.
最初の箇条書きで示されているように、Spring Security ACLモジュールの主な機能の1つは、ACLを検索する高性能な方法を提供することです。

> This ACL repository capability is extremely important, because every domain object instance in your system might have several access control entries, and each ACL might inherit from other ACLs in a tree-like structure (this is supported out-of-the-box by Spring Security, and is very commonly used).
この ACL リポジトリ機能は、非常に重要です。
なぜなら、全てのドメインオブジェクトインスタンスは、個々のアクセス制御エントリを持ち、それぞれの ACL は他の ACL を継承するツリーのような構造を持つからです。
これは、 out-of-the-box でサポートされ、非常に一般的に利用されています。

> Spring Security’s ACL capability has been carefully designed to provide high performance retrieval of ACLs, together with pluggable caching, deadlock-minimizing database updates, independence from ORM frameworks (we use JDBC directly), proper encapsulation, and transparent database updating.
Spring Security の ACL 機能は、慎重に設計されています。
ACL の取得をハイ・パフォーマンスに行えるように。
プラグイン可能なキャッシュ化、データベース更新のデッドロックの最小化、 ORM への非依存（私たちは JDBC を直接使用している）、正当なカプセル化、そして等価的なデータベース更新も考慮して設計している。

Spring Security ACL は、４つのデータベーステーブルを必要とする

https://github.com/spring-projects/spring-security/blob/master/samples/xml/contacts/src/main/java/sample/contact/DataSourcePopulator.java

> Given databases are central to the operation of the ACL module, let’s explore the four main tables used by default in the implementation.

与えられたデータベースは ACL モジュールの処理の中心となります。では、デフォルトの実装で使用される４つの主なテーブルについてみていきましょう。

> The tables are presented below in order of size in a typical Spring Security ACL deployment, with the table with the most rows listed last:

テーブルは典型的な Spring Security の ACL デプロイメントでのサイズ順で、最も行数が多いテーブルを最後にしています。

## ACL_SID
プリンシパル、または権限を一意に識別する。
SID は、 Security ID の略。

- ID
    - 主キー
- SID
    - 権限またはプリンシパルを識別する文字列表現
- PRINCIPAL
    - プリンシパルかどうかの識別子

SID と PRINCIPAL でユニーク。

## ACL_CLASS
ACL が必要なドメインオブジェクトのクラス名を格納するためのテーブル。

- ID
    - 主キー
- CLASS
    - ドメインオブジェクトのクラス名

CLASS でユニーク。

## ACL_OBJECT_IDENTITY
ドメインオブジェクトインスタンスを一意に特定するための情報を格納する。

- ID
    - 主キー
- OBJECT_ID_CLASS
    - ACL_CLASS の主キーと外部参照制約を持つ
    - NULL 不可
- OBJECT_ID_IDENTITY
    - ドメインオブジェクトインスタンスを識別する情報
    - 数値型
    - NULL 不可
- PARENT_OBJECT
    - ACL_OBJECT_IDENTITY の主キーと外部参照制約を持つ
    - NULL 可
- OWNER_ID
    - ACL_SID の主キーと外部参照制約を持つ
    - NULL 可
    - ドメインオブジェクトインスタンスの所有者を設定する
- ENTRIES_INHERITING
    - 親となるドメインオブジェクトインスタンスの権限を継承する場合に true
    - NULL 不可

## ACL_ENTRY
ドメインオブジェクトインスタンスごとにパーミッションの情報を保存する

- ID
    - 主キー
- ACL_OBJECT_IDENTITY
    - ACL_OBJECT_IDENTITY の主キーと外部参照制約を持つ
    - NULL 不可

> As mentioned in the last paragraph, the ACL system uses integer bit masking.

最後のパラグラフで言及されているとおり、 ACL システムは正数のビットマスクを使用しています。

> Don’t worry, you need not be aware of the finer points of bit shifting to use the ACL system, but suffice to say that we have 32 bits we can switch on or off.

安心してください、あなたは ACL システムを使うためにビットシフトを明確に理解しておく必要はありません。32ビットでオンオフを切り替えられることを知っていれば十分です。

> Each of these bits represents a permission, and by default the permissions are read (bit 0), write (bit 1), create (bit 2), delete (bit 3) and administer (bit 4).

それぞれのビットは、パーミッションを表し、デフォルトでは read(0ビット目)、 write(1ビット目)、create(bit2), delete(bit 3), そして管理者 (bit 4)です。

> It’s easy to implement your own Permission instance if you wish to use other permissions, and the remainder of the ACL framework will operate without knowledge of your extensions.

他のパーミッションを使いたい場合は、簡単に独自のパーミッションを実装することができます。そして、 ACL フレームワークはあなたの拡張について知ることなく処理することができることを思い出してください。

> It is important to understand that the number of domain objects in your system has absolutely no bearing on the fact we’ve chosen to use integer bit masking.

システムのドメインオブジェクトの数と整数のビットマスクには全く関係ないということを理解しておくことは重要です。

> Whilst you have 32 bits available for permissions, you could have billions of domain object instances (which will mean billions of rows in ACL_OBJECT_IDENTITY and quite probably ACL_ENTRY).

しばらく、あなたはパーミッションのために有効な３２ビットを持ち、百万のドメインオブジェクトを持つことができます（これは ACL_OBJECT_IDENTITY かもしくは ACL_ENTRY に百万の行があることを意味します）

> We make this point because we’ve found sometimes people mistakenly believe they need a bit for each potential domain object, which is not the case.

なぜこのことに注意を促すかというと、私たちはドメインオブジェクトごとにビットが必要だと勘違いする人々をときどき見るからです。

> Now that we’ve provided a basic overview of what the ACL system does, and what it looks like at a table structure, let’s explore the key interfaces.

今、私たちは ACL システムが行うことについての基本的な概要を提供しました。そして、次のテーブル構造でキーとなるインターフェースについてみていきましょう。

> The key interfaces are

キーとなるインターフェースは、

### Acl
> Every domain object has one and only one Acl object, which internally holds the AccessControlEntrys as well as knows the owner of the Acl

全てのドメインオブジェクトはたった１つの `Acl` オブジェクトを持ちます。それ（`Acl` オブジェクト）は `Acl` のオーナー（ドメインオブジェクト？）を知っているのと同様に `AccessControlEntry` を内部に持っています。

> An Acl does not refer directly to the domain object, but instead to an ObjectIdentity

`Acl` は、ドメインオブジェクトを直接参照しない代わりに `ObjectIdentity` を参照します。

> The Acl is stored in the ACL_OBJECT_IDENTITY table.

`Acl` は `ACL_OBJECT_IDENTITY` テーブルに格納されます。

### AccessControlEntry
> An Acl holds multiple AccessControlEntry s, which are often abbreviated as ACEs in the framework

> Each ACE refers to a specific tuple of Permission, Sid and Acl

> An ACE can also be granting or non-granting and contain audit settings

> The ACE is stored in the ACL_ENTRY table.

### Permission
> A permission represents a particular immutable bit mask, and offers convenience functions for bit masking and outputting information

> The basic permissions presented above (bits 0 through 4) are contained in the BasePermission class.

### Sid
> The ACL module needs to refer to principals and GrantedAuthority[] s

> A level of indirection is provided by the Sid interface, which is an abbreviation of "security identity"

> Common classes include PrincipalSid (to represent the principal inside an Authentication object) and GrantedAuthoritySid

> The security identity information is stored in the ACL_SID table.

### ObjectIdentity
> Each domain object is represented internally within the ACL module by an ObjectIdentity

> The default implementation is called ObjectIdentityImpl.

### AclService
> Retrieves the Acl applicable for a given ObjectIdentity

> In the included implementation (JdbcAclService), retrieval operations are delegated to a LookupStrategy

> The LookupStrategy provides a highly optimized strategy for retrieving ACL information, using batched retrievals (BasicLookupStrategy) and supporting custom implementations that leverage materialized views, hierarchical queries and similar performance-centric, non-ANSI SQL capabilities.

### MutableAclService
> Allows a modified Acl to be presented for persistence

> It is not essential to use this interface if you do not wish.

```sql
CREATE TABLE ACL_ENTRY(
    ID                   BIGINT  GENERATED BY DEFAULT AS IDENTITY(START WITH 100) NOT NULL PRIMARY KEY
    ,ACL_OBJECT_IDENTITY BIGINT  NOT NULL
    ,ACE_ORDER           INT     NOT NULL
    ,SID                 BIGINT  NOT NULL
    ,MASK                INTEGER NOT NULL
    ,GRANTING            BOOLEAN NOT NULL
    ,AUDIT_SUCCESS       BOOLEAN NOT NULL
    ,AUDIT_FAILURE       BOOLEAN NOT NULL
    ,CONSTRAINT UNIQUE_UK_4  UNIQUE (ACL_OBJECT_IDENTITY, ACE_ORDER)
    ,CONSTRAINT FOREIGN_FK_4 FOREIGN KEY (ACL_OBJECT_IDENTITY) REFERENCES ACL_OBJECT_IDENTITY(ID)
    ,CONSTRAINT FOREIGN_FK_5 FOREIGN KEY (SID) REFERENCES ACL_SID(ID)
)
```

パーミッションは整数のビットマスクで実現しているが、その詳細を知っておく必要はない。
独自のパーミッションを定義したい場合は、拡張も可能。

> 27.3 Getting Started
> To get starting using Spring Security’s ACL capability, you will need to store your ACL information somewhere.
Spring Security の ACL 機能を使い始めるには、 ACL の情報がどこかに保存する必要があります。

> This necessitates the instantiation of a DataSource using Spring.
このことは、 Spring を使って DataSource インスタンスを生成することを必要とします。

> The DataSource is then injected into a JdbcMutableAclService and BasicLookupStrategy instance.
DataSource は、 JdbcMutableAclService と BasicLookupStrategy インスタンスに注入されます。

> The latter provides high-performance ACL retrieval capabilities, and the former provides mutator capabilities.
後者（BasicLoopupStrategy）は高機能な ACL に必要で、前者は ACL の変更機能で必要になります。

> Refer to one of the samples that ship with Spring Security for an example configuration.
サンプルの設定ファイルを参照してください。

> You’ll also need to populate the database with the four ACL-specific tables listed in the last section (refer to the ACL samples for the appropriate SQL statements).
４つのテーブルの作成も必要です。

> Once you’ve created the required schema and instantiated JdbcMutableAclService, you’ll next need to ensure your domain model supports interoperability with the Spring Security ACL package.
必要なスキーマを作成したら、 JdbcMutableAclService をインスタンス化し、
ドメインオブジェクトが ACL を使える形になっているか確認が必要です。

> Hopefully ObjectIdentityImpl will prove sufficient, as it provides a large number of ways in which it can be used.
上手くいけば、 ObjectIdentityImpl で必要十分な機能が提供されているでしょう。

> Most people will have domain objects that contain a public Serializable getId() method.
ほとんどの人々は、ドメインオブジェクトにシリアライズ可能な getId() メソッドを持たせています。

> If the return type is long, or compatible with long (eg an int), you will find you need not give further consideration to ObjectIdentity issues.
long 型か long に変換可能な型を返すなら、 ObjectIdentity について考えることは何もありません。

> Many parts of the ACL module rely on long identifiers.
ACL モジュールの多くの部品は、 long の識別子に依存しています。

> If you’re not using long (or an int, byte etc), there is a very good chance you’ll need to reimplement a number of classes.
もし long を使っていないのであれば、いくつかのクラスを再実装するいい機会です。

> We do not intend to support non-long identifiers in Spring Security’s ACL module, as longs are already compatible with all database sequences, the most common identifier data type, and are of sufficient length to accommodate all common usage scenarios.
Spring Security の ACL モジュールは、 long でない識別子をサポートするつもりはありません。
long はすでに多くのデータベースシーケンスで互換性があります。
識別子として最も共通的なデータ型です。
多くのシナリオで十分に大きい。

> The following fragment of code shows how to create an Acl, or modify an existing Acl:
以下のコードは、 Acl を作成し、変更の方法を示しています。

```java
// Prepare the information we'd like in our access control entry (ACE)
ObjectIdentity oi = new ObjectIdentityImpl(Foo.class, new Long(44));
Sid sid = new PrincipalSid("Samantha");
Permission p = BasePermission.ADMINISTRATION;

// Create or update the relevant ACL
MutableAcl acl = null;
try {
    acl = (MutableAcl) aclService.readAclById(oi);
} catch (NotFoundException nfe) {
    acl = aclService.createAcl(oi);
}

// Now grant some permissions via an access control entry (ACE)
acl.insertAce(acl.getEntries().length, p, sid, true);
aclService.updateAcl(acl);
```

> In the example above, we’re retrieving the ACL associated with the "Foo" domain object with identifier number 44.
上記の例では、識別子が数値の 44 である Foo というドメインオブジェクトに関連する ACL を取得しています。

> We’re then adding an ACE so that a principal named "Samantha" can "administer" the object.
そして、 "Samantha" という名前のプリンシパルがオブジェクトを管理者となるよう ACE を追加しています。

> The code fragment is relatively self-explanatory, except the insertAce method.
insertAce() メソッド以外は自明です。

> The first argument to the insertAce method is determining at what position in the Acl the new entry will be inserted.
insertAce() メソッドの最初の引数は、 Acl のどの場所に新しいエントリを挿入するかを決定します。

> In the example above, we’re just putting the new ACE at the end of the existing ACEs.
上記例では、新しい ACE を既存の ACE の末尾に追加しています。

> The final argument is a Boolean indicating whether the ACE is granting or denying.
最後の引数にある boolean は、 ACL が付与なのか拒否なのかを指定しています。

> Most of the time it will be granting (true), but if it is denying (false), the permissions are effectively being blocked.
多くの場合、それは true です。
しかし、拒否なら false を指定します。
パーミッションはブロックするように働きます。

> Spring Security does not provide any special integration to automatically create, update or delete ACLs as part of your DAO or repository operations.
Spring Security は自動生成するような特別な統合を提供していません。
DAO やレポジトリの処理の一部としてACL の更新や削除するような機能。

> Instead, you will need to write code like shown above for your individual domain objects.
代わりに、個々のドメインオブジェクトに上記のようなコードを各必要があるでしょう。

> It’s worth considering using AOP on your services layer to automatically integrate the ACL information with your services layer operations.
サービス層に AOP を当て、 ACL の操作を自動化する統合は検討の価値があります。

> We’ve found this quite an effective approach in the past.
すでに効果的なアプローチは見つけています。

> Once you’ve used the above techniques to store some ACL information in the database, the next step is to actually use the ACL information as part of authorization decision logic.
データベースに ACL の情報を格納する上記テクニックを使用したら、次のステップは ACL の情報を認可決定ロジックの一部として実際に使用することです。

> You have a number of choices here.
ここにいくつか選択肢があります。

> You could write your own AccessDecisionVoter or AfterInvocationProvider that respectively fires before or after a method invocation.
あなたは、あなた自身の AccessDecisionVoter か AfterInvocationProvider を書くことができます。

> Such classes would use AclService to retrieve the relevant ACL and then call Acl.isGranted(Permission[] permission, Sid[] sids, boolean administrativeMode) to decide whether permission is granted or denied.
AclService を使用して関連する ACL を取得し、 Acl の isGranted() メソッドを実行し、パーミッションが許可か拒否かを決定します。

> Alternately, you could use our AclEntryVoter, AclEntryAfterInvocationProvider or AclEntryAfterInvocationCollectionFilteringProvider classes.
別の手段としては、 AclEntryVoter を使う方法があります。
AclEntryAfterInvocationProvider か AclEntryAfterInvocationCollectionFilteringProvider クラスを使うことができます。

> All of these classes provide a declarative-based approach to evaluating ACL information at runtime, freeing you from needing to write any code.
これらのクラスは全て、宣言的なアプローチを採用しています。
ACL の情報は実行時に評価されます。
コードを各必要を減らします。

> Please refer to the sample applications to learn how to use these classes.
サンプルアプリを見てさらに勉強してください。
