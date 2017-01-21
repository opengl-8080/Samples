# Part IV. Web Application Security
> Most Spring Security users will be using the framework in applications which make user of HTTP and the Servlet API.
ほとんどの Spring Security ユーザーは、フレームワークを HTTP と Servlet API を使うアプリケーションで利用します。

> In this part, we’ll take a look at how Spring Security provides authentication and access-control features for the web layer of an application.
この章では、 Spring Security がどのようにして認証とアクセス制御の機能をアプリケーションの Web レイヤに適用しているか見ます。

> We’ll look behind the facade of the namespace and see which classes and interfaces are actually assembled to provide web-layer security.
背後にある namespace の入口を見て、 Web レイヤのセキュリティを提供するために、クラスやインターフェースが実際どう組み立てられているかを見ます。

> In some situations it is necessary to use traditional bean configuration to provide full control over the configuration, so we’ll also see how to configure these classes directly without the namespace.
いくつかのシチュエーションでは、設定を超えて完全な制御を提供するために伝統的な Bean 設定を使う必要があります。
そのため、 namespace なしで直接それらのクラスを設定する方法についても見ていきます。

## 13. The Security Filter Chain
> Spring Security’s web infrastructure is based entirely on standard servlet filters.
Spring Security の Web インフラストラクチャは、標準の Servlet フィルタ上に構築されています。

> It doesn’t use servlets or any other servlet-based frameworks (such as Spring MVC) internally, so it has no strong links to any particular web technology.
内部的には Servlet や Servlet ベースのフレームワーク（例えば Spring MVC）は使用していません。
そのため、特定の Web テクノロジーに依存しません。

> It deals in HttpServletRequest s and HttpServletResponse s and doesn’t care whether the requests come from a browser, a web service client, an HttpInvoker or an AJAX application.
HttpServletRequestとHttpServletResponseを扱い、リクエストがブラウザ、Webサービスクライアント、HttpInvoker、またはAJAXアプリケーションから来ているかどうかは気にしません。

> Spring Security maintains a filter chain internally where each of the filters has a particular responsibility and filters are added or removed from the configuration depending on which services are required.
Spring Security は内部的にフィルタの連鎖を持ちます。
そこには責務ごとのフィルタがあり、必要なサービスごとに設定で追加したり削除したりできます。

> The ordering of the filters is important as there are dependencies between them.
フィルターの順番はそれらの関係に依存するため重要です。

> If you have been using namespace configuration, then the filters are automatically configured for you and you don’t have to define any Spring beans explicitly but here may be times when you want full control over the security filter chain, either because you are using features which aren’t supported in the namespace, or you are using your own customized versions of classes.
もし namespace 設定を使っているなら、自動的にフィルタは適用され、 Spring の Bean を明示的に定義する必要はありません。
しかし、セキュリティのフィルタ連鎖を越えて完全に制御したい場合（たとえば、 namespace のサポートにない機能を追加する場合）、もしくはクラスをカスタマイズしたバージョンを使用したい場合は

### 13.1 DelegatingFilterProxy
> When using servlet filters, you obviously need to declare them in your web.xml, or they will be ignored by the servlet container.
Servlet フィルタを使用するとき、 web.xml に明示的に宣言する必要があります。
そうしないと、 Servlet コンテナはそれを無視します。

> In Spring Security, the filter classes are also Spring beans defined in the application context and thus able to take advantage of Spring’s rich dependency-injection facilities and lifecycle interfaces.
Spring Security では、フィルタクラスは Spring の Application Context で定義された Bean でもあります。
そのため、 Spring のリッチな依存関係の注入機能とライフサイクルのインターフェースを使用することができます。

> Spring’s DelegatingFilterProxy provides the link between web.xml and the application context.
Spring の DelegatingFilterProxy は、 web.xml と Application Context との橋渡しを提供します。

> When using DelegatingFilterProxy, you will see something like this in the web.xml file:
DelegatingFilterProxy を使用するには、次のように web.xml を記述します。

```xml
<filter>
    <filter-name>myFilter</filter-name>
    <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
</filter>

<filter-mapping>
    <filter-name>myFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

> Notice that the filter is actually a DelegatingFilterProxy, and not the class that will actually implement the logic of the filter.
注意として、実際のフィルタは DelegatingFilterProxy です。
そして、フィルタのロジックを実装したクラスではありません。

> What DelegatingFilterProxy does is delegate the Filter 's methods through to a bean which is obtained from the Spring application context.
DelegatingFilterProxy は、 Spring の Application Context から取得した Bean に、 Filter のメソッドを通して処理を委譲します。

> This enables the bean to benefit from the Spring web application context lifecycle support and configuration flexibility.
これにより、 Spring の Web Application Context のライフサイクルのサポートと設定の利便性を利用できるようになります。

> The bean must implement javax.servlet.Filter and it must have the same name as that in the filter-name element.
Bean は javax.servlet.Filter を実装している必要があります。
そして、その Bean は `<filter-name>` 要素で指定した名前と同じである必要があります。

> Read the Javadoc for DelegatingFilterProxy for more information
より詳しい情報は、 DelegatingFilterProxy の Javadoc を見てください。

### 13.2 FilterChainProxy
> Spring Security’s web infrastructure should only be used by delegating to an instance of FilterChainProxy.
Spring Security の Web インフラストラクチャは、 FilterChainProxy のインスタンスに委譲することによってのみ使用されます。

> The security filters should not be used by themselves.
セキュリティフィルタは、単独で使用すべきではありません。

> In theory you could declare each Spring Security filter bean that you require in your application context file and add a corresponding DelegatingFilterProxy entry to web.xml for each filter, making sure that they are ordered correctly, but this would be cumbersome and would clutter up the web.xml file quickly if you have a lot of filters.
理論的には、 Spring Security フィルタ Bean を Application Context ファイルに宣言し、 DelegatingFilterProxy に対応するエントリを web.xml に、順序に注意して追加することができます。
しかし、この方法はフィルタの数が多くなると、すぐに web.xml を面倒で混乱を招くものにしてしまいます。

> FilterChainProxy lets us add a single entry to web.xml and deal entirely with the application context file for managing our web security beans.
FilterChainProxy は、 web.xml に単一のエントリを提供し、詳細については web セキュリティ Bean を管理する Application Context ファイルに分けられるようにしてくれます。

> It is wired using a DelegatingFilterProxy, just like in the example above, but with the filter-name set to the bean name "filterChainProxy".
上述の例では DelegatingFilterProxy を使用していますが、 `<filter-name>` では `"filterChainProxy"` を指定しています。

> The filter chain is then declared in the application context with the same bean name. Here’s an example:
フィルタ連鎖は Application Context で同じ名前で宣言されます。
ここに例を書きます。

```xml
<bean id="filterChainProxy" class="org.springframework.security.web.FilterChainProxy">
    <constructor-arg>
        <list>
        <sec:filter-chain pattern="/restful/**" filters="
            securityContextPersistenceFilterWithASCFalse,
            basicAuthenticationFilter,
            exceptionTranslationFilter,
            filterSecurityInterceptor" />
        <sec:filter-chain pattern="/**" filters="
            securityContextPersistenceFilterWithASCTrue,
            formLoginFilter,
            exceptionTranslationFilter,
            filterSecurityInterceptor" />
        </list>
    </constructor-arg>
</bean>
```

> The namespace element filter-chain is used for convenience to set up the security filter chain(s) which are required within the application. [6].
namespace 要素の `<filter-chain>` を使用すると、アプリケーションに必要なセキュリティのフィルタ連鎖をセットアップするのに便利です。

> It maps a particular URL pattern to a list of filters built up from the bean names specified in the filters element, and combines them in a bean of type SecurityFilterChain.
それは個々の URL パターンを、 `<filters>` 要素で bean 名を指定され構築されたフィルタのリストにマップします。
そして、 SecurityFilterChain 型の Bean でそれらがまとめられます。

> The pattern attribute takes an Ant Paths and the most specific URIs should appear first [7].
`pattern` 属性は、 Ant パス形式で指定します。そして、ほとんどの指定の URI は最初に現れるべきです。

> At runtime the FilterChainProxy will locate the first URI pattern that matches the current web request and the list of filter beans specified by the filters attribute will be applied to that request.
FilterChainProxy が実行されるとき、現在の Web リクエストが最初の URI パターンにマッチすると、 `filters` 属性で指定されたフィルタリストの Bean がリクエストに適用されます。

> The filters will be invoked in the order they are defined, so you have complete control over the filter chain which is applied to a particular URL.
フィルタは定義された順番で実行されます。
そのため、個々の URL に適用するフィルタ連鎖を完全に制御することができます。

> You may have noticed we have declared two SecurityContextPersistenceFilter s in the filter chain (ASC is short for allowSessionCreation, a property of SecurityContextPersistenceFilter).
２つの SecurityContextPersistenceFilter がフィルタ連鎖の中に定義されていることに気付くでしょう。
（`ASC` は `allowSessionCreation` の略で、 SecurityContextPersistenceFilter のプロパティです）

> As web services will never present a jsessionid on future requests, creating HttpSession s for such user agents would be wasteful.
Web サービスとして、将来のリクエストに jsessionid を提示することはありません。
そのようなユーザーエージェントに HttpSession を作ることは無駄です。
※上の xml の例で、 `/restful/**` に対してセッションを作らないように定義している。 REST API ならセッションは不要なので、 jsessionid を作らないようにしている、ということ

> If you had a high-volume application which required maximum scalability, we recommend you use the approach shown above.
もしあなたが大容量で最大限スケーラビリティのあるアプリケーションを持つ場合、上記のようなアプローチをとることを推奨します。

> For smaller applications, using a single SecurityContextPersistenceFilter (with its default allowSessionCreation as true) would likely be sufficient.
似たようなアプリケーションでは、単一の SecurityContextPersistenceFilter （デフォルトで allowSessionCreation は true）で十分です。

> Note that FilterChainProxy does not invoke standard filter lifecycle methods on the filters it is configured with.
注意として、 `filters` で設定された FilterChainProxy は標準的なフィルタのライフサイクルメソッドが実行されません。

> We recommend you use Spring’s application context lifecycle interfaces as an alternative, just as you would for any other Spring bean.
私たちは Spring の Application Context のライフサイクルインターフェースを代わりに使用することを推奨します。
ちょうど、他の Spring Bean 達と同様です。

> When we looked at how to set up web security using namespace configuration, we used a DelegatingFilterProxy with the name "springSecurityFilterChain".
namespace 設定を使って Web セキュリティをどのようにセットアップしているか見ると、私たちは DelegatingFilterProxy を `"springSecurityFilterChain"` という名前で使っています。

> You should now be able to see that this is the name of the FilterChainProxy which is created by the namespace.
これ（`"springSecurityFilterChain"`）は、 namespace で作成した `FilterChainProxy` の名前であることが分かるでしょう。

#### 13.2.1 Bypassing the Filter Chain
> You can use the attribute filters = "none" as an alternative to supplying a filter bean list.
フィルタ Bean リストを供給する代わりに、 `filters` 属性に `"none"` を使用することもできます。

> This will omit the request pattern from the security filter chain entirely.
これはリクエストのパターンをセキュリティフィルタの連鎖から除外することになります。

> Note that anything matching this path will then have no authentication or authorization services applied and will be freely accessible.
このパスにマッチする場合、認証も認可のサービスも適用されず、自由にアクセスできるようになります。

> If you want to make use of the contents of the SecurityContext contents during a request, then it must have passed through the security filter chain.
リクエストで SecurityContext の内容を使用したい場合、セキュリティフィルタ連鎖を通す必要があります。

> Otherwise the SecurityContextHolder will not have been populated and the contents will be null.
さもないと、 SecurityContextHolder は完全な状態にはなっておらず、内容は null になります。

### 13.3 Filter Ordering
> The order that filters are defined in the chain is very important.
連鎖の中で定義されるフィルタの順序はとても重要です。

> Irrespective of which filters you are actually using, the order should be as follows:
実際にどのフィルタを使用しているかにかかわらず、順序は次のようになります。

- ChannelProcessingFilter, because it might need to redirect to a different protocol
ChannelProcessingFilter, 異なるプロトコルにリダイレクトする必要があります。

- SecurityContextPersistenceFilter, so a SecurityContext can be set up in the SecurityContextHolder at the beginning of a web request, and any changes to the SecurityContext can be copied to the HttpSession when the web request ends (ready for use with the next web request)
SecurityContextPersistenceFilter, Web リクエストの最初で SecurityContextHolder 内の SecurityContext がセットアップされます。
リクエストの終了時は SecurityContext への変更を HttpSession にコピーします（次のリクエストに備えて）。

- ConcurrentSessionFilter, because it uses the SecurityContextHolder functionality and needs to update the SessionRegistry to reflect ongoing requests from the principal
ConcurrentSessionFilter, SecurityContextHolder機能を使用し、SessionRegistryを更新してプリンシパルからの進行中の要求を反映する必要があるためです

- Authentication processing mechanisms - UsernamePasswordAuthenticationFilter, CasAuthenticationFilter, BasicAuthenticationFilter etc - so that the SecurityContextHolder can be modified to contain a valid Authentication request token
Authentication 実行メカニズム - UsernamePasswordAuthenticationFilter, CasAuthenticationFilter, BasicAuthenticationFilter, その他 - SecurityContextHolder は有効な Authentication リクエストトークンを含む形で変更できるようにする

- The SecurityContextHolderAwareRequestFilter, if you are using it to install a Spring Security aware HttpServletRequestWrapper into your servlet container
SecurityContextHolderAwareRequestFilter, もし Servlet コンテナに Spring Security の HttpServletRequestWrapper をインストールしている場合

- The JaasApiIntegrationFilter, if a JaasAuthenticationToken is in the SecurityContextHolder this will process the FilterChain as the Subject in the JaasAuthenticationToken
JaasApiIntegrationFilter, JaasAuthenticationToken が SecurityContextHolder の中にあり、 FilterChain によって JaasAuthenticationToken の Subject として処理される場合

- RememberMeAuthenticationFilter, so that if no earlier authentication processing mechanism updated the SecurityContextHolder, and the request presents a cookie that enables remember-me services to take place, a suitable remembered Authentication object will be put there
RememberMeAuthenticationFilter, 直前の認証処理メカニズムが SecurityContextHolder を更新しておらず、リクエストが remember-me サービスが有効な cookie を提供する場合、適切な記憶された Authentication オブジェクトを SecurityContextHolder に保存する。

- AnonymousAuthenticationFilter, so that if no earlier authentication processing mechanism updated the SecurityContextHolder, an anonymous Authentication object will be put there
AnonymousAuthenticationFilter, 直前の認証処理メカニズムが SecurityContextHolder を更新していない場合、匿名の Authentication オブジェクトをそこに保存する

- ExceptionTranslationFilter, to catch any Spring Security exceptions so that either an HTTP error response can be returned or an appropriate AuthenticationEntryPoint can be launched
ExceptionTranslationFilter, 何らかの Spring Security の例外をキャッチした場合、 HTTP エラーレスポンスを返すか、 AuthenticationEntryPoint の実行するかのいずれかを行う

- FilterSecurityInterceptor, to protect web URIs and raise exceptions when access is denied
FilterSecurityInterceptor, web の URI を保護し、アクセスが拒否された場合は例外をスローする

### 13.4 Request Matching and HttpFirewall
> Spring Security has several areas where patterns you have defined are tested against incoming requests in order to decide how the request should be handled.
Spring Security はあなたがパターンを定義したいくつかのエリアを持っています。
それらのエリアは、やってきたリクエストに対して、リクエストを制御すべきかどうか決定するために検証されます。

> This occurs when the FilterChainProxy decides which filter chain a request should be passed through and also when the FilterSecurityInterceptor decides which security constraints apply to a request.
これは、フィルタ連鎖の中の FilterChainProxy がリクエストを通過させるべきか決定させるときと、 FilterSecurityInterceptor がセキュリティの制約をリクエストに適用すべきか決定するときにおこります。

> It’s important to understand what the mechanism is and what URL value is used when testing against the patterns that you define.
あなたが定義したパターンに対して検証が行われるとき、メカニズムと、 URL の値がどのように使われるのかについて理解することは重要です。

> The Servlet Specification defines several properties for the HttpServletRequest which are accessible via getter methods, and which we might want to match against.
Servlet の仕様は、 Getter メソッドを通して取得できる HttpServletRequest のいくつかのプロパティを定義しています。

> These are the contextPath, servletPath, pathInfo and queryString.
contextPath, servletPath, pathInfo, そして queryString があります。

> Spring Security is only interested in securing paths within the application, so the contextPath is ignored.
Spring Security は、アプリケーションの保護するパスにだけ関心があります。
よって、 contextPath は無視します。

> Unfortunately, the servlet spec does not define exactly what the values of servletPath and pathInfo will contain for a particular request URI.
残念ながら、 Servlet の仕様は servletPath と pathInfo の値が個々のリクエストの URI を含むかどうかを明確に定義していません。

> For example, each path segment of a URL may contain parameters, as defined in RFC 2396 [8].
例えば、 URL のそれぞれのパスセグメントは RFC 2396 で定義されるようにパラメータを含むかもしれません。

> The Specification does not clearly state whether these should be included in the servletPath and pathInfo values and the behaviour varies between different servlet containers.
仕様は servletPath と pathInfo にこの値を含めるべきかどうか明示していないので、振る舞いは Servlet コンテナの実装によって異なります。

> There is a danger that when an application is deployed in a container which does not strip path parameters from these values, an attacker could add them to the requested URL in order to cause a pattern match to succeed or fail unexpectedly.
これらの値からパスパラメータを取り除かないコンテナにアプリケーションがデプロイされた場合、攻撃者は要求されたURLにそれらを追加して、パターンマッチが予期せず成功するか失敗する危険性があります。

> Other variations in the incoming URL are also possible.
やってきた URL に他の変種がある場合も可能です。

> For example, it could contain path-traversal sequences (like /../) or multiple forward slashes (//) which could also cause pattern-matches to fail.
例えば、 URL にパスをトラバースするシーケンスを含めることができる場合、もしくは複数のスラッシュを含めることができた場合、パターンマッチは失敗します。

> Some containers normalize these out before performing the servlet mapping, but others don’t.
いくつかのコンテナは servlet にマッピングする前に正規化をおこないますが、しないコンテナもあります。

> To protect against issues like these, FilterChainProxy uses an HttpFirewall strategy to check and wrap the request.
これらの問題を防ぐため、 FilterChainProxy は HttpFirewall でリクエストをラップしてチェックする戦略を取ります。

> Un-normalized requests are automatically rejected by default, and path parameters and duplicate slashes are removed for matching purposes.
正規化されていないリクエストは、デフォルトで自動的に排除され、パスパラメータと重複するスラッシュはマッチングのために取り除かれます。

> It is therefore essential that a FilterChainProxy is used to manage the security filter chain.
従って、セキュリティフィルタ連鎖の管理に FilterChainProxy を使うことは不可欠です。

> Note that the servletPath and pathInfo values are decoded by the container, so your application should not have any valid paths which contain semi-colons, as these parts will be removed for matching purposes.
servletPath と pathInfo の値はコンテナによってでコードされます。
よって、あなたのアプリケーションはセミコロンを持つ有効なパスを持ちません。
マッチングのときには削除されます。

> As mentioned above, the default strategy is to use Ant-style paths for matching and this is likely to be the best choice for most users.
上述のように、デフォルトの戦略は Ant スタイルのパスをマッチングに使用します。
これは多くのユーザーにとってベストな選択です。

> The strategy is implemented in the class AntPathRequestMatcher which uses Spring’s AntPathMatcher to perform a case-insensitive match of the pattern against the concatenated servletPath and pathInfo, ignoring the queryString.
この戦略は AntPathRequestMatcher という Spring の AntPathMatcher を使うクラスによって実装されています。
AntPathMatcher は servletPath と pathInfo, そしてやってきた queryString に対してパターンのマッチで大文字小文字を区別します。

> If for some reason, you need a more powerful matching strategy, you can use regular expressions.
もし何らかの理由があるのであれば、より強力なマッチングの戦略を必要とするなど、正規表現を使うこともできます。

> The strategy implementation is then RegexRequestMatcher.
その戦略の実装は、 RegexRequestMatcher です。

> See the Javadoc for this class for more information.
より多くの情報は Javadoc を参照してください。

> In practice we recommend that you use method security at your service layer, to control access to your application, and do not rely entirely on the use of security constraints defined at the web-application level.
推奨するのは、サービスレイヤのメソッドセキュリティを使うことです。アプリケーションのアクセス制御をするために。
そして、 web アプリケーションレイヤでセキュリティの制約に完全に依存しないようにすべきです。

> URLs change and it is difficult to take account of all the possible URLs that an application might support and how requests might be manipulated.
URL が変わった時に、アプリケーションが提供するすべての URL がサポートされていてリクエストがどのように処理されるか考慮することは困難です。

→要は、 Web レイヤの制約だけに依存していると、 URL が変わった時に抜け漏れが発生する危険性があるから、サービスレイヤでチェックするようにしておいたほうが良い、という話か。

> You should try and restrict yourself to using a few simple ant paths which are simple to understand.
簡単に理解できるシンプルな ant パスだけを使うように制限すべきです。

> Always try to use a "deny-by-default" approach where you have a catch-all wildcard ( / or ) defined last and denying access.
常に、"拒否をデフォルトとする" アプローチを使ってください。
全てをキャッチするワイドカード（もしくは `/`）で、アクセスを拒否します。

> Security defined at the service layer is much more robust and harder to bypass, so you should always take advantage of Spring Security’s method security options.
サービスレイヤで定義されたセキュリティは、はるかに堅牢でバイパスすることは困難です。
そのため、 Spring Security のメソッドセキュリティオプションのメリットを常に利用してください。

> The HttpFirewall also prevents HTTP Response Splitting by rejecting new line characters in the HTTP Response headers.
HttpFirewall は、 HTTP Response Splitting もまた防ぎます。
HTTP レスポンスヘッダー中の改行文字を除去することで。

### 13.5 Use with other Filter-Based Frameworks
> If you’re using some other framework that is also filter-based, then you need to make sure that the Spring Security filters come first.
他のフレームワークを使っており、それもまたフィルターをベースとしている場合、 Spring Security のフィルタが先頭にくるようにしなければなりません。

> This enables the SecurityContextHolder to be populated in time for use by the other filters.
これは他のフィルタが SecurityContextHolder を使う前に SecurityContextHolder が作られることで可能になります。

> Examples are the use of SiteMesh to decorate your web pages or a web framework like Wicket which uses a filter to handle its requests.
例えば、 SiteMesh を Web ページを装飾するために使っていたり、 Wicket のようなフィルタをリクエストをハンドリングするために使用しているフレームワークを使っている場合です。

### 13.6 Advanced Namespace Configuration
> As we saw earlier in the namespace chapter, it’s possible to use multiple http elements to define different security configurations for different URL patterns.
namespace の章でも説明しましたが、複数の `<http>` 要素を異なる URL ごとのセキュリティ設定のために定義することが可能です。

> Each element creates a filter chain within the internal FilterChainProxy and the URL pattern that should be mapped to it.
それぞれの要素は、 FilterChainProxy の中にフィルタの連鎖を作成します。
そして、 URL パターンはそのフィルタ連鎖にマッピングされます。

> The elements will be added in the order they are declared, so the most specific patterns must again be declared first.
要素は、宣言された順序で追加されます。
よって、最も特定されるパターンは先頭に宣言しなければなりません。

> Here’s another example, for a similar situation to that above, where the application supports both a stateless RESTful API and also a normal web application which users log into using a form.
ここではその他の例を示します。
以前と同じシチュエーションで、ステートレスな RESTful API と通常の Web アプリケーションの両方をサポートするアプリケーションでユーザーのログインについて設定するケースです。

```xml
<!-- Stateless RESTful service using Basic authentication -->
<http pattern="/restful/**" create-session="stateless">
    <intercept-url pattern='/**' access="hasRole('REMOTE')" />
    <http-basic />
</http>

<!-- Empty filter chain for the login page -->
<http pattern="/login.htm*" security="none"/>

<!-- Additional filter chain for normal users, matching all other requests -->
<http>
    <intercept-url pattern='/**' access="hasRole('USER')" />
    <form-login login-page='/login.htm' default-target-url="/home.htm"/>
    <logout />
</http>
```

## 14. Core Security Filters
> There are some key filters which will always be used in a web application which uses Spring Security, so we’ll look at these and their supporting classes and interfaces first.
Spring Security を使用する Web アプリケーションでは常に使用される主要なフィルタがあります。
まずは、これらをサポートするクラスとインターフェースについてみていきます。

> We won’t cover every feature, so be sure to look at the Javadoc for them if you want to get the complete picture.
全ての機能はカバーしません。
完全な図が欲しい場合は、 Javadoc を参照してください。

### 14.1 FilterSecurityInterceptor
> We’ve already seen FilterSecurityInterceptor briefly when discussing access-control in general, and we’ve already used it with the namespace where the <intercept-url> elements are combined to configure it internally.
すでに FilterSecurityInterceptor については一般的なアクセス制御の議論で紹介しました。
namespace の `<intercept-url>` 要素を設定すると、内部的にこれが組み込まれることを知っています。

> Now we’ll see how to explicitly configure it for use with a FilterChainProxy, along with its companion filter ExceptionTranslationFilter.
今度は、 FilterChainProxy を明示的に使用した場合に、 ExceptionTranslationFilter と組み合わせて使う方法についてみます。

> A typical configuration example is shown below:
典型的な設定は、次のような例になります。

```xml
<bean id="filterSecurityInterceptor"
    class="org.springframework.security.web.access.intercept.FilterSecurityInterceptor">
    <property name="authenticationManager" ref="authenticationManager"/>
    <property name="accessDecisionManager" ref="accessDecisionManager"/>
    <property name="securityMetadataSource">
        <security:filter-security-metadata-source>
            <security:intercept-url pattern="/secure/super/**" access="ROLE_WE_DONT_HAVE"/>
            <security:intercept-url pattern="/secure/**" access="ROLE_SUPERVISOR,ROLE_TELLER"/>
        </security:filter-security-metadata-source>
    </property>
</bean>
```

> FilterSecurityInterceptor is responsible for handling the security of HTTP resources.
FilterSecurityInterceptor は HTTP リソースのセキュリティを制御する責務を持ちます。

> It requires a reference to an AuthenticationManager and an AccessDecisionManager.
それは AuthenticationManager と AccessDecisionManager への参照を必要とします。

> It is also supplied with configuration attributes that apply to different HTTP URL requests.
また、異なるHTTP URL要求に適用される構成属性も提供されます。

> Refer back to the original discussion on these in the technical introduction.
技術的な説明については、以前のオリジナルの議論に戻って参照してください。

> The FilterSecurityInterceptor can be configured with configuration attributes in two ways.
FilterSecurityInterceptor は２つの方法で設定することができます。

> The first, which is shown above, is using the <filter-security-metadata-source> namespace element.
１つ目は、上記の見た通り、 `<filter-security-metadata-source>` という namespace の要素を使う方法です。

> This is similar to the <http> element from the namespace chapter but the <intercept-url> child elements only use the pattern and access attributes.
これは namespace の章の `<http>` 要素を使うことに似ていますが、 `<intercept-url>` 子要素のみを使用して pattern と access 属性のみを使用する方法になります。

> Commas are used to delimit the different configuration attributes that apply to each HTTP URL.
カンマは、それぞれの HTTP URL ごとに異なる設定属性を分けるために使用します。

> The second option is to write your own SecurityMetadataSource, but this is beyond the scope of this document.
２つ目のオプションは自分で SecurityMetadataSource を書くことです。
しかし、これはこのドキュメントの範囲からは外れます。

> Irrespective of the approach used, the SecurityMetadataSource is responsible for returning a List<ConfigAttribute> containing all of the configuration attributes associated with a single secure HTTP URL.
アプローチの方法に関係なく、 SecurityMetadataSource は単一の保護された HTTP URL に関係する全ての設定属性を持った `List<ConfigAttribute>` を返す責任を持ちます。

> It should be noted that the FilterSecurityInterceptor.setSecurityMetadataSource() method actually expects an instance of FilterInvocationSecurityMetadataSource.
FilterSecurityInterceptor.setSecurityMetadataSource（）メソッドは、実際にFilterInvocationSecurityMetadataSourceのインスタンスを期待していることに注意してください。

> This is a marker interface which subclasses SecurityMetadataSource.
これは、SecurityMetadataSourceをサブクラス化するマーカーインターフェイスです。

> It simply denotes the SecurityMetadataSource understands FilterInvocation s.
これは、SecurityMetadataSourceがFilterInvocationを理解していることを示しています

> In the interests of simplicity we’ll continue to refer to the FilterInvocationSecurityMetadataSource as a SecurityMetadataSource, as the distinction is of little relevance to most users.
簡単にするため、FilterInvocationSecurityMetadataSourceをSecurityMetadataSourceとして参照していきます。これは、ほとんどのユーザーとの区別がほとんどないためです。

> The SecurityMetadataSource created by the namespace syntax obtains the configuration attributes for a particular FilterInvocation by matching the request URL against the configured pattern attributes.
namespace シンタックスによって作成された SecurityMetadataSource は、`pattern` 属性に設定された値に対してマッチするリクエスト URL を用いて個々の FilterInvocation のために設定属性を取得します。

> This behaves in the same way as it does for namespace configuration.
これと同様のふるまいを、 namespace 設定は行います。

> The default is to treat all expressions as Apache Ant paths and regular expressions are also supported for more complex cases.
デフォルトは全ての式を Apache Ant パスとして取り扱います。正規表現やより複雑なケースもサポートします。

> The request-matcher attribute is used to specify the type of pattern being used.
リクエストマッチャー属性はパターンが使われる方法について指定します。

> It is not possible to mix expression syntaxes within the same definition.
同じ定義で複数の式シンタックスを含めることはできません。

> As an example, the previous configuration using regular expressions instead of Ant paths would be written as follows:
例えば、以前の Ant パスの代わりに正規表現を使用した設定は、次のように書きます。

```xml
<bean id="filterInvocationInterceptor"
    class="org.springframework.security.web.access.intercept.FilterSecurityInterceptor">
    <property name="authenticationManager" ref="authenticationManager"/>
    <property name="accessDecisionManager" ref="accessDecisionManager"/>
    <property name="runAsManager" ref="runAsManager"/>
    <property name="securityMetadataSource">
        <security:filter-security-metadata-source request-matcher="regex">
            <security:intercept-url pattern="\A/secure/super/.*\Z" access="ROLE_WE_DONT_HAVE"/>
            <security:intercept-url pattern="\A/secure/.*\" access="ROLE_SUPERVISOR,ROLE_TELLER"/>
        </security:filter-security-metadata-source>
    </property>
</bean>
```

> Patterns are always evaluated in the order they are defined.
パターンは通常定義された順に評価されます。

> Thus it is important that more specific patterns are defined higher in the list than less specific patterns.
そのため、最も限定的なパターンを他のパターンより上に定義することが重要です。

> This is reflected in our example above, where the more specific /secure/super/ pattern appears higher than the less specific /secure/ pattern.
上の例は、 `/secure/super` パターンが `/secure/` パターンより上に来ています。

> If they were reversed, the /secure/ pattern would always match and the /secure/super/ pattern would never be evaluated.
もし逆だと、 `/secure/` パターンが常に評価されるため、 `/secure/super` パターンは決して評価されません。

#### 14.2 ExceptionTranslationFilter