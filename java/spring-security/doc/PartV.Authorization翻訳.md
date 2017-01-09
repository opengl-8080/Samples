> The advanced authorization capabilities within Spring Security represent one of the most compelling reasons for its popularity.

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

