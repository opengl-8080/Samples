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
> The ExceptionTranslationFilter sits above the FilterSecurityInterceptor in the security filter stack.
ExceptionTranslationFilter はセキュリティフィルタのスタックの FilterSecurityInterceptor の前にセットされます。

> It doesn’t do any actual security enforcement itself, but handles exceptions thrown by the security interceptors and provides suitable and HTTP responses.
これはセキュリティについて特に何もしません。
代わりに、セキュリティインターセプタとプロバイダが例外をスローしたときに、適切な HTTP レスポンスを返します。

```xml
<bean id="exceptionTranslationFilter"
      class="org.springframework.security.web.access.ExceptionTranslationFilter">
    <property name="authenticationEntryPoint" ref="authenticationEntryPoint"/>
    <property name="accessDeniedHandler" ref="accessDeniedHandler"/>
</bean>

<bean id="authenticationEntryPoint"
      class="org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint">
    <property name="loginFormUrl" value="/login.jsp"/>
</bean>

<bean id="accessDeniedHandler"
      class="org.springframework.security.web.access.AccessDeniedHandlerImpl">
    <property name="errorPage" value="/accessDenied.htm"/>
</bean>
```

#### 14.2.1 AuthenticationEntryPoint
> The AuthenticationEntryPoint will be called if the user requests a secure HTTP resource but they are not authenticated.
AuthenticationEntryPoint は、ユーザーが保護された HTTP リソースにアクセスしたが、権限を持たない場合に呼び出されます。

> An appropriate AuthenticationException or AccessDeniedException will be thrown by a security interceptor further down the call stack, triggering the commence method on the entry point.
適切な AuthenticationException か AccessDeniedException が下位層のセキュリティインターセプタによってスローされると、エントリポイントの commence メソッドが実行されます。

> This does the job of presenting the appropriate response to the user so that authentication can begin.
これにより、ユーザーに適切な応答を提示して認証を開始できるようになります。

> The one we’ve used here is LoginUrlAuthenticationEntryPoint, which redirects the request to a different URL (typically a login page).
ここで使用されるものの１つは LoginUrlAuthenticationEntryPoint です。
これはリクエストを異なる URL （典型的なものはログインページ）にリダイレクトします。

> The actual implementation used will depend on the authentication mechanism you want to be used in your application.
実際の実装が使われるかは、アプリケーションで必要とする認証メカニズムに依存します。

#### 14.2.2 AccessDeniedHandler
> What happens if a user is already authenticated and they try to access a protected resource?
認証済みのユーザーが保護されたリソースにアクセスすると、何が起こるのでしょうか？

> In normal usage, this shouldn’t happen because the application workflow should be restricted to operations to which a user has access.
通常の利用では、これは起こるべきではありません。
なぜなら、アプリケーションのフローはユーザーがアクセスできる制御だけに制限すべきだからです。

> For example, an HTML link to an administration page might be hidden from users who do not have an admin role.
たとえば、管理画面への HTML リンクは管理者権限を持たないユーザーには見えないようにします。

> You can’t rely on hiding links for security though, as there’s always a possibility that a user will just enter the URL directly in an attempt to bypass the restrictions.
セキュリティのためにリンクを隠すだけで信頼することはできません。
ユーザーが制限をバイパスするために、URLを直接入力することは常にありえます。

> Or they might modify a RESTful URL to change some of the argument values.
もしくは、 RESTful URL の引数の値を変更するかもしれません。

> Your application must be protected against these scenarios or it will definitely be insecure.
アプリケーションはこれらのシナリオに対して防御しなければなりません。
そうしないと、完全に安全でないものになります。

> You will typically use simple web layer security to apply constraints to basic URLs and use more specific method-based security on your service layer interfaces to really nail down what is permissible.
通常、単純なWebレイヤセキュリティを使用して基本URLに制約を適用し、サービスレイヤインターフェイスでより具体的なメソッドベースのセキュリティを使用して、実際に許可されているものを特定します。

> If an AccessDeniedException is thrown and a user has already been authenticated, then this means that an operation has been attempted for which they don’t have enough permissions.
もし AccessDeniedException がスローされてユーザーが認証されている場合は、その処理に対してユーザーに十分な許可が許されていないということを意味します。

> In this case, ExceptionTranslationFilter will invoke a second strategy, the AccessDeniedHandler.
この場合、 ExceptionTranslationFilter は２つ目の戦略を実行します。
それは AccessDeniedHandler です。

> By default, an AccessDeniedHandlerImpl is used, which just sends a 403 (Forbidden) response to the client.
デフォルトでは、 AccessDeniedHandler が使用されると、単純に 403 レスポンスをクライアントに返します。

> Alternatively you can configure an instance explicitly (as in the above example) and set an error page URL which it will forwards the request to [11].
代わりに、インスタンスを明示的に設定することもできます。
そして、エラーページの URL を指定します。

> This can be a simple "access denied" page, such as a JSP, or it could be a more complex handler such as an MVC controller.
シンプルな "アクセス拒否" ページにすることができます。
JSP や、より複雑な制御をする MVC コントローラなどを指定できます。

> And of course, you can implement the interface yourself and use your own implementation.
そしてもちろん、インターフェースを自分で実装することもできます。

> It’s also possible to supply a custom AccessDeniedHandler when you’re using the namespace to configure your application.
カスタムの AccessDeniedHandler を供給することも可能です。
namespace で設定している場合でも。

> See the namespace appendix for more details.
namespace の付録を見てください。

##### 14.2.3 SavedRequest s and the RequestCache Interface
> Another responsibility of ExceptionTranslationFilter responsibilities is to save the current request before invoking the AuthenticationEntryPoint.
ExceptionTranslationFilter の別の責務として、 AuthenticationEntryPoint が実行される前に現在のリクエストを保存する機能があります。

> This allows the request to be restored after the use has authenticated (see previous overview of web authentication).
これは認証が終わった後に、リクエストを復元することができます。

> A typical example would be where the user logs in with a form, and is then redirected to the original URL by the default SavedRequestAwareAuthenticationSuccessHandler (see below).
典型的な例としては、ユーザーがフォームログインしたあとで、デフォルトの SavedRequestAwareAuthenticationSuccessHandler を使ってオリジナルの URL にリダイレクトします。

> The RequestCache encapsulates the functionality required for storing and retrieving HttpServletRequest instances.
RequestCache は HttpServletRequest インスタンスの保存と検索を必要とする機能をカプセル化します。

> By default the HttpSessionRequestCache is used, which stores the request in the HttpSession.
デフォルトでは、 HttpSessionRequestCache が使われて、リクエストを HttpSession に保存します。

> The RequestCacheFilter has the job of actually restoring the saved request from the cache when the user is redirected to the original URL.
RequestCacheFilter は、ユーザーがオリジナルの URL にリダイレクトするときに、実際に保存されたリクエストをキャッシュから復元する仕事を持ちます。

> Under normal circumstances, you shouldn’t need to modify any of this functionality, but the saved-request handling is a "best-effort" approach and there may be situations which the default configuration isn’t able to handle.
通常の状況では、この機能を変更する必要はありませんが、保存された要求の処理は「ベストエフォート型」のアプローチであり、デフォルト構成では処理できない状況があります。

> The use of these interfaces makes it fully pluggable from Spring Security 3.0 onwards.
Spring Security 3.0 からは、これらのインターフェースを使うと、完全にプラガブルになります。

### 14.3 SecurityContextPersistenceFilter
> We covered the purpose of this all-important filter in the Technical Overview chapter so you might want to re-read that section at this point.
私たちは Technical Overview の章のすべての重要なフィルタの目的をカバーしました。
よって、あなたはここで節を見直したくなるかもしれません。

> Let’s first take a look at how you would configure it for use with a FilterChainProxy.
まずは、どのようにして FilterChainProxy を使うように設定するか見てみましょう。

> A basic configuration only requires the bean itself
基本的な設定は、唯一 Bean だけを要求します。

```xml
<bean id="securityContextPersistenceFilter"
class="org.springframework.security.web.context.SecurityContextPersistenceFilter"/>
```

> As we saw previously, this filter has two main tasks.
以前見た通り、このフィルタは２つの主要なタスクを持ちます。

> It is responsible for storage of the SecurityContext contents between HTTP requests and for clearing the SecurityContextHolder when a request is completed.
それは、 HTTP リクエストの間の SecurityContext の内容を保存する責務と、リクエストが完了したときに SecurityContextHolder をクリアする責務です。

> Clearing the ThreadLocal in which the context is stored is essential, as it might otherwise be possible for a thread to be replaced into the servlet container’s thread pool, with the security context for a particular user still attached.
コンテキストが保存された ThreadLocal をクリアすることは必要不可欠です。
さもないと、 Servlet コンテナのスレッドプールに保存され、特定のユーザーの Security Context が紐づいたスレッドに置き換えられることが可能になるかもしれません。

> This thread might then be used at a later stage, performing operations with the wrong credentials.
このスレッドは、後の段階で、間違った資格情報として処理に使われるかもしれません。

#### 14.3.1 SecurityContextRepository
> From Spring Security 3.0, the job of loading and storing the security context is now delegated to a separate strategy interface:
Spring Security 3.0 からは、 Security Context の読み込みと保存の仕事は、インターフェースに切り分けられました。

```java
public interface SecurityContextRepository {

SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder);

void saveContext(SecurityContext context, HttpServletRequest request,
        HttpServletResponse response);
}
```

> The HttpRequestResponseHolder is simply a container for the incoming request and response objects, allowing the implementation to replace these with wrapper classes.
HttpRequestResponseHolder は、やってきたリクエストとレスポンスの単純なコンテナです。
ラッパークラスに置き換えることができます。

> The returned contents will be passed to the filter chain.
返された内容は、フィルタ連鎖を通過したものになります。

> The default implementation is HttpSessionSecurityContextRepository, which stores the security context as an HttpSession attribute [12].
デフォルトの実装は HttpSessionSecurityContextRepository です。
これは Security Context を HttpSession の attribute として保存します。

> The most important configuration parameter for this implementation is the allowSessionCreation property, which defaults to true, thus allowing the class to create a session if it needs one to store the security context for an authenticated user (it won’t create one unless authentication has taken place and the contents of the security context have changed).
この実装で最も重要な設定パラメータは、 `allowSessionCreation` プロパティです。
このプロパティは、デフォルトで true です。
これは、クラスが、 Security Context を認証されたユーザーのために保存する必要であればセッションを作成することを許可します。
（認証が行われ、セキュリティコンテキストの内容が変更されない限り、セキュリティコンテキストを作成しません）

> If you don’t want a session to be created, then you can set this property to false:
もしセッションを作成してほしくない場合は、このプロパティに false を設定することができます。

```xml
<bean id="securityContextPersistenceFilter"
    class="org.springframework.security.web.context.SecurityContextPersistenceFilter">
<property name='securityContextRepository'>
    <bean class='org.springframework.security.web.context.HttpSessionSecurityContextRepository'>
    <property name='allowSessionCreation' value='false' />
    </bean>
</property>
</bean>
```

> Alternatively you could provide an instance of NullSecurityContextRepository, a null object implementation, which will prevent the security context from being stored, even if a session has already been created during the request.
他には、 NullSecurityContextRepository のインスタンスを提供することもできます。
null オブジェクトの実装、これは Security Context が保存されることを防ぎます。
たとえセッションが既に作られていてもです。

### 14.4 UsernamePasswordAuthenticationFilter
> We’ve now seen the three main filters which are always present in a Spring Security web configuration.
私たちは今、３つの主要なフィルタを見ました。
それらは常に Spring Security の Web 設定で存在します。

> These are also the three which are automatically created by the namespace <http> element and cannot be substituted with alternatives.
これら３つは、 `<http>` 要素によって自動的に作られます。
別の手段で置換することはできません。

> The only thing that’s missing now is an actual authentication mechanism, something that will allow a user to authenticate.
まだ説明していない唯一のことは、実際の認証メカニズムです。
ユーザーをどのように認証できるようにするかです。

> This filter is the most commonly used authentication filter and the one that is most often customized [13].
このフィルタは、認証フィルタによって最も共通的に利用されます。
そして、これはもっともよくカスタマイズされものでもあります。

> It also provides the implementation used by the <form-login> element from the namespace.
それは、 namespace の `<form-login>` 要素による実装も提供します。

> There are three stages required to configure it.
そこには３つの設定を必要とする段階があります。

- Configure a LoginUrlAuthenticationEntryPoint with the URL of the login page, just as we did above, and set it on the ExceptionTranslationFilter.
ログインページの URL で LoginUrlAuthenticationEntryPoint を設定します。
ちょうど、私たちは以前それをしました。
そして、 ExceptionTranslationFilter をセットします。

- Implement the login page (using a JSP or MVC controller).
ログインページを実装します。（JSP か MVC コントローラを使って）

- Configure an instance of UsernamePasswordAuthenticationFilter in the application context
アプリケーションコンテキストの UsernamePasswordAuthenticationFilter のインスタンスを設定します。

- Add the filter bean to your filter chain proxy (making sure you pay attention to the order).
フィルタ Bean を FilterChainProxy に追加します。（順番に注意してください）

> The login form simply contains username and password input fields, and posts to the URL that is monitored by the filter (by default this is /login).
ログインフォームは単純に `username` と `password` の入力フィールドを含み、フィルタによって関しされた URL にポストします。
（デフォルトは `/login` です）

> The basic filter configuration looks something like this:
基本的なフィルタの設定は、このようになります。

```xml
<bean id="authenticationFilter" class=
"org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter">
<property name="authenticationManager" ref="authenticationManager"/>
</bean>
```

#### 14.4.1 Application Flow on Authentication Success and Failure
> The filter calls the configured AuthenticationManager to process each authentication request.
フィルタは設定された AuthenticationManager を、それぞれの認証リクエストプロセスで呼びます。

> The destination following a successful authentication or an authentication failure is controlled by the AuthenticationSuccessHandler and AuthenticationFailureHandler strategy interfaces, respectively.
認証の成功か失敗のあとの目的地は、 AuthenticationSuccessHandler か AuthenticationFailureHandler の戦略インターフェースによってそれぞれ制御されます。

> The filter has properties which allow you to set these so you can customize the behaviour completely [14].
フィルタは、あなたにこれらの振る舞いを完全にカスタマイズできる設定を許可するプロパティを持ちます。

> Some standard implementations are supplied such as SimpleUrlAuthenticationSuccessHandler, SavedRequestAwareAuthenticationSuccessHandler, SimpleUrlAuthenticationFailureHandler, ExceptionMappingAuthenticationFailureHandler and DelegatingAuthenticationFailureHandler.
いくつかの標準的な実装が提供されています。
たとえば、 SimpleUrlAuthenticationSuccessHandler, SavedRequestAwareAuthenticationSuccessHandler, SimpleUrlAuthenticationFailureHandler, ExceptionMappingAuthenticationFailureHandler, そして DelegatingAuthenticationFailureHandler です。

> Have a look at the Javadoc for these classes and also for AbstractAuthenticationProcessingFilter to get an overview of how they work and the supported features.
これらのクラスについては Javadoc を見てください。そして、また AbstractAuthenticationProcessingFilter についてもどのように働き、機能をサポートするかの概要を知るために見てください。

> If authentication is successful, the resulting Authentication object will be placed into the SecurityContextHolder.
もし認証が成功すると、戻された Authentication オブジェクトが SecurityContextHolder に配置される。

> The configured AuthenticationSuccessHandler will then be called to either redirect or forward the user to the appropriate destination.
設定された AuthenticationSuccessHandler は、ユーザーをそれぞれ適切な目的地にリダイレクトかフォワードするために呼び出されるだろう。

> By default a SavedRequestAwareAuthenticationSuccessHandler is used, which means that the user will be redirected to the original destination they requested before they were asked to login.
デフォルトでは、 SavedRequestAwareAuthenticationSuccessHandler が使用されます。
これは、ユーザーは、彼らがログインする前にリクエストしたオリジナルの目的地にリダイレクトされることを意味します。

> The ExceptionTranslationFilter caches the original request a user makes.
ExceptionTranslationFilter は、ユーザーが作成したオリジナルのリクエストをキャッシュします。

> When the user authenticates, the request handler makes use of this cached request to obtain the original URL and redirect to it.
ユーザーを認証するとき、リクエストのハンドラは、オリジナルの URL を取得してリダイレクトするためにこのキャッシュされたリクエストを使用します。

> The original request is then rebuilt and used as an alternative.
オリジナルのリクエストは再構築され、代替として利用されます。

> If authentication fails, the configured AuthenticationFailureHandler will be invoked.
もし認証が失敗すると、設定された AuthenticationFailureHandler が実行されます。

## 17. Remember-Me Authentication
### 17.1 Overview
> Remember-me or persistent-login authentication refers to web sites being able to remember the identity of a principal between sessions.
Rmember-me もしくは永続ログイン認証は、セッションの間プリンシパルの身元を記録できることを示します。

> This is typically accomplished by sending a cookie to the browser, with the cookie being detected during future sessions and causing automated login to take place.
これは、典型的にはブラウザにクッキーを送信することで実現され、将来のセッションでクッキーが検出され、自動的にログインが実行されます。

> Spring Security provides the necessary hooks for these operations to take place, and has two concrete remember-me implementations.
Spring Security はこれらの処理を実行するのに必要となるフックを提供し、２つの Remember-me 実装を持ちます。

> One uses hashing to preserve the security of cookie-based tokens and the other uses a database or other persistent storage mechanism to store the generated tokens.
１つはハッシュ化を使用しクッキーベースでトークンを保存し、もう１つはデータベースかもしくはそれ以外の永続化ストレージメカニズムに生成されたトークンを保存します。

> Note that both implementations require a UserDetailsService.
注意として、両方の実装は `UserDetailsService` を必要とします。

> If you are using an authentication provider which doesn’t use a UserDetailsService (for example, the LDAP provider) then it won’t work unless you also have a UserDetailsService bean in your application context.
もしあなたが `UserDetailsService` を使わない認証プロバイダを使っている場合（例えば LDAP プロバイダ）、アプリケーションコンテキストに `UserDetailsService` Bean が無ければ動作しません。

### 17.2 Simple Hash-Based Token Approach
シンプルなハッシュベースのトークンアプローチ

> This approach uses hashing to achieve a useful remember-me strategy.
このアプローチは、ハッシュ化を利用し、便利な Remember-me 戦略を実現します。

> In essence a cookie is sent to the browser upon successful interactive authentication, with the cookie being composed as follows:
本質的には、クッキーは成功した対話型の認証を通じてブラウザに送信されます。
クッキーには次のように構成されています。

```
base64(username + ":" + expirationTime + ":" +
md5Hex(username + ":" + expirationTime + ":" password + ":" + key))

username:          As identifiable to the UserDetailsService
password:          That matches the one in the retrieved UserDetails
expirationTime:    The date and time when the remember-me token expires, expressed in milliseconds
key:               A private key to prevent modification of the remember-me token
```

> As such the remember-me token is valid only for the period specified, and provided that the username, password and key does not change.
Remember-me トークンは、ユーザー名・パスワード・キーが変わらない限り、特定の期間だけ有効になります。

> Notably, this has a potential security issue in that a captured remember-me token will be usable from any user agent until such time as the token expires.
注目すべきなのは、期限切れになるまで任意のユーザーエージェントが利用可能な Remember-me トークンを盗まれることは、セキュリティの問題になりえるということです。

> This is the same issue as with digest authentication.
これは、ダイジェスト認証と同じ問題です。

> If a principal is aware a token has been captured, they can easily change their password and immediately invalidate all remember-me tokens on issue.
もしトークンを保有するプリンシパルが盗まれると、パスワードを簡単に変更され、即座に Remember-me のトークンが無効になります。

> If more significant security is needed you should use the approach described in the next section.
もしより重要なセキュリティが必要であれば、次のセクションで説明するアプローチを使ってください。

> Alternatively remember-me services should simply not be used at all.
もしくは、単純に Remember-me サービスを使わないでください。

> If you are familiar with the topics discussed in the chapter on namespace configuration, you can enable remember-me authentication just by adding the <remember-me> element:
もし、 namespace 設定の章で紹介した方法に慣れているのであれば、 Remember-me 認証は `<remember-me>` 要素を追加するだけで有効にすることができます。

```xml
<http>
...
<remember-me key="myAppKey"/>
</http>
```

> The UserDetailsService will normally be selected automatically.
`UserDetailsService` は、一般的に自動的に選択されます。

> If you have more than one in your application context, you need to specify which one should be used with the user-service-ref attribute, where the value is the name of your UserDetailsService bean.
もしあなたがより多くのアプリケーションコンテキストを持つ場合、どれを使うのか `user-service-ref` 属性で `UserDetailsService` Bean の名前を指定する必要があります。

### 17.3 Persistent Token Approach
トークンを永続化するアプローチ

> This approach is based on the article http://jaspan.com/improved_persistent_login_cookie_best_practice with some minor modifications [16].
このアプローチは `http://jaspan.com/improved_persistent_login_cookie_best_practice` の記事をベースとして、いくつかの修正を加えています。

#### Improved Persistent Login Cookie Best Practice
> 改善された永続化ログイン cookie のベストプラクティス

> Charles Miller's article, "Persistent Login Cookie Best Practice,"[1] describes a relatively secure approach to implementing the familiar "Remember Me" option for web sites.
Charles Miller の記事、 "Persistent Login Cookie Best Practice" は、 Web サイトのオプションとして有名な Remember Me のセキュアな実装アプローチについて説明しています。

> In this article, I propose an improvement that retains all the benefits of that approach but also makes it possible to detect when a persistent login cookie has been stolen and used by an attacker.
この記事では、このアプローチの持つメリットを残したまま、永続化されたログイン Cookie が攻撃者によって盗まれたものかどうかを検出できるよう改善することを目的とします。

> To summarize Miller's design:
Miller の設計を要約すると、

> When the user successfully logs in with Remember Me checked, a login cookie is issued in addition to the standard session management cookie.[2]
ユーザが Remember Me のチェックに成功しログインしたとき、ログイン Cookie は通常のセッション管理の Cookie に追加されて発行される.

> The login cookie contains the user's username and a random number (the "token" from here on) from a suitably large space.
ログイン Cookie は、ユーザ名と適切な大きさのランダムな数字（以後トークンと書く）で構成されている。

> The username and token are stored as a pair in a database table.
ユーザー名とトークンは、データベーステーブルにペアで保存される。

> When a non-logged-in user visits the site and presents a login cookie, the username and token are looked up in the database.
ログインしていないユーザがサイトに訪れ、ログインクッキーが提示された場合、ユーザー名とトークンはデータベースから検索される。

> If the pair is present, the user is considered authenticated.
ペアが存在する場合、ユーザーは認証されたものと考えられる。

> The used token is removed from the database.
使用されたトークンはデータベースから削除される。

> A new token is generated, stored in database with the username, and issued to the user via a new login cookie.
新しいトークンが生成され、データベースにユーザー名とともに保存され、そして新しいログイン Cookie が発行される。

> If the pair is not present, the login cookie is ignored.
もしペアが存在しない場合、ログインクッキーは無視される。

> Users that are only authenticated via this mechanism are not permitted to access certain protected information or functions such as changing a password, viewing personally identifying information, or spending money.
このメカニズムだけで認証されたユーザー達は、本当に守らなければならない情報や機能（例えばパスワードの変更や個人を特定する情報の参照）へのアクセスは許可しないようにする。

> To perform those operations, the user must first successfully submit a normal username/password login form.
これらの処理を行う場合、ユーザーは通常のユーザー名とパスワードによるフォームログインを最初に成功させなければならない。

> Since this approach allows the user to have multiple remembered logins from different browsers or computers, a mechanism is provided for the user to erase all remembered logins in a single operation.
このアプローチは異なるブラウザやコンピュータからのログインを記録することを許すため、単一の処理で全ての記録されたログインを消す機能が提供されている。

##### The Problem
問題点

> Miller correctly describes the many advantages of this approach.
Miller はこのアプローチの多くの有用性を正しく説明しています。

> One disadvantage, however, is that if an attacker successfully steals a victim's login cookie and uses it before the victim next accesses the site, the cookie will work and the site will issue a new valid login cookie to the attacker (this disadvantage is far from unique to Miller's design).
しかしながら、ある問題点は攻撃者が被害者のログインクッキーを盗むことに成功し、被害者がサイトに次のアクセスをする前に使用されると、クッキーは動作し、差異とは新しい有効なログインクッキーを攻撃者に対して発行してしまいます（この問題点は Miller の設計独自の問題ではありません）。

> The attacker will be able to continue accessing the site as the victim until the remembered login session expires.
攻撃者は被害者の記録されたログインのセッション期限がくるまでのあいだ、アクセスを続けることができます。

> When the victim next accesses the site his remembered login will not work (because each token can only be used one time) but he's much more likely to think that "something broke" and just log in again than to realize that his credentials were stolen.
被害者が次にサイトにアクセスすると、上手く動作しません（なぜなら、個々のトークンは一度しか使用できないからです）。
彼は資格情報が盗まれたことには気づかず何かが壊れたとだけ思い再びログインを試すだけです。

> Displaying a "last login time" may help the user notice the problem but, frequently, it will go undetected.
最後にログインした時間の表示は、ユーザーに問題を気付かせる助けになるかもしれません。しかし、すぐに気付くことはないでしょう。

> One possible solution to this problem is to treat the presentation of an invalid login cookie as evidence of a previously successful attack.
この問題についてのひとつの解決策は、以前成功した攻撃の証拠として無効なログインクッキーを提示することです。

> The site could then present an impossible to miss security warning and automatically invalidate all of the user's remembered login sessions.
Web サイトはセキュリティの警告を無視することはできないので、自動的にユーザーの記録されたセッションを全て無効にします。

> This approach would create a denial of service attack: since usernames are easy to come by or guess, an attacker could submit invalid login cookies for every user and thus disable the entire system.
このアプローチは、サービス無効化攻撃を成立させてしまいます。
ユーザー名が単純だったり推測可能だったりした場合、攻撃者は無効なログインクッキーを送信することで、簡単にユーザがシステムは入れないようにすることができてしまいます。

> The Solution
解決策

> My solution to this problem is based on the observation that since each token can only be used once, a remembered login session actually consists of a series of such tokens.
この問題に対する私の解決策は、個々のトークンは一度しか使用できないが、実際はそれぞれのトークンは連続しているという考察結果に基づいています。

> When an attacker successfully steals and uses T_0 he is issued T_1, but the victim is still holding T_0.

攻撃者がトークンを盗むことに成功すると、ユーザーのトークン０はトークン１を発行します。しかし、被害者はまだトークン０を持っています。

> The database no longer has a copy of T_0 and thus cannot differentiate it from an arbitrary invalid token.

データベースはもうトークン０のコピーを持っていません。
よって、任意の無効なトークンと区別することはできません。

> However, if the series of tokens is itself given an identity that must be presented along with the current token, the system can notice that the victim is presenting a valid series identifier along with an invalid token.

しかしながら、もしトークンのシリーズが、現在のトークンに関係なくそれ自身を識別するものを提供する場合、システムは被害者によって提供された有効なシリーズの識別が無効なトークンとは区別し、気付くことができる。

> Assuming that the series identifiers are as hard to guess as tokens, the only way a user could present a valid series identifier with an invalid token is if some other user previously presented the same valid series identifier with a valid token.

シリーズ識別子がトークンとして推測するのが難しいと仮定すると、無効なトークンを持つ有効な一連の識別子を提示できる唯一の方法は、他のユーザーが以前に有効なトークンを持つ有効な一連の識別子を提示した場合です。

> This requires that two different users held the same series and token pair at the same time and therefore indicates that a theft has occurred.

この結果、二人の異なるユーザーに対して同時に同じトークンペアの異なるシリーズを持つ二人のユーザーを要求することになり、盗難が起こったことを見分けることができます。

> The implementation is no more difficult and requires no more resources than Miller's design.

実装に関して Miller の設計と比較して追加の複雑さやリソースは必要ありません。

> From the summary above, only items 2 and 3 change:

上記概要から、２，３か所変更するだけです。

> When the user successfully logs in with Remember Me checked, a login cookie is issued in addition to the standard session management cookie.[2]

ユーザーが Remember Me チェックによるログインに成功したとき、ログインクッキーが通常のセッション管理クッキーに加えて発行される。

> The login cookie contains the user's username, a series identifier, and a token.

ログインクッキーはユーザー名とシリーズの識別子、そしてトークンを含んでいる。

> The series and token are unguessable random numbers from a suitably large space.

シリーズとトークンは推測できない十分に大きなランダムな数字を使用します。

> All three are stored together in a database table.

３つの値は全てデータベースのテーブルに保存します。

> When a non-logged-in user visits the site and presents a login cookie, the username, series, and token are looked up in the database.

ログインしていないユーザがサイトに訪れログインクッキーを提示した場合、データベースからユーザー名、シリーズ、トークンが検索されます。

> If the triplet is present, the user is considered authenticated.

もし３つの組み合わせが存在した場合、ユーザは認証されていると認識される。

> The used token is removed from the database.

使用されたトークンはデータベースから削除される。

> A new token is generated, stored in database with the username and the same series identifier, and a new login cookie containing all three is issued to the user.

新しいトークンが生成され、ユーザー名とシリーズの識別子とともにデータベースに保存される。そして、３つの全ての情報を持った新しいログインクッキーがユーザーに発行される。

> If the username and series are present but the token does not match, a theft is assumed.

もしユーザー名とシリーズが存在しても、トークンが一致しない場合、盗まれたものと推測される。

> The user receives a strongly worded warning and all of the user's remembered sessions are deleted.

ユーザーは強調された警告を受け取り、全てのユーザーの記録されたセッションが削除されます。

> If the username and series are not present, the login cookie is ignored.

もしユーザー名とシリーズが存在しない場合、ログインクッキーが無視されます。

> It is critical that the series identifier be reused for each token in a series.

１つのシリーズで、それぞれのトークンに対してシリーズの識別子を使用することは重要です。

> If the series identifier were instead simply another one time use random number, the system could not differentiate between a series/token pair that had been stolen and one that, for example, had simply expired and been erased from the database.

シリーズ識別子が単なる別の1回の乱数を使用していた場合、システムは盗まれたシリーズとトークンのペアを区別できませんでした。例えば、期限切れでデータベースから消去されたシリーズ/トークンペア。

#### Conclus
結論

> This system has all the advantages of Miller's original approach. Additionally:

このシステムは Miller のオリジナルのアプローチのメリットをすべて持ち、加えて

> An attacker is only able to use a stolen cookie until the victim next accesses the web site instead of for the full lifetime of the remembered session.

攻撃者が盗んだクッキーを使えるのは、記録されたセッションの全ライフサイクル中ではなく、被害者が次にサイトにアクセスするまでです。

> When the victim next accesses the web site, he will be informed that the theft occurred.

被害者が次に Web サイトにアクセスすると、盗難が起こったことを通知します。

> This system is used by the Persistent Login module for the Drupal content management system.

このシステムは、 Drupal 管理システムの永続化ログインモジュールとして使用されている。

---

> To use the this approach with namespace configuration, you would supply a datasource reference:
このアプローチを namespace 設定で使うには、データソースの参照を提供します。

```xml
<http>
...
<remember-me data-source-ref="someDataSource"/>
</http>
```

> The database should contain a persistent_logins table, created using the following SQL (or equivalent):
データベースは、次のような SQL を使って作られる `persistent_logins` テーブルを持たなければなりません。

```sql
create table persistent_logins (
    username varchar(64) not null,
    series varchar(64) primary key,
    token varchar(64) not null,
    last_used timestamp not null)
```

### 17.4 Remember-Me Interfaces and Implementations
Remember-Me インターフェースと実装

> Remember-me is used with UsernamePasswordAuthenticationFilter, and is implemented via hooks in the AbstractAuthenticationProcessingFilter superclass.
Remember-me は `UsernamePasswordAuthenticationFilter` で使用されます。
親クラスの `AbstractAuthenticationProcessingFilter` のフックを通じて実装されます。

> It is also used within BasicAuthenticationFilter.
`BasicAuthenticationFilter` でも使用されています。

> The hooks will invoke a concrete RememberMeServices at the appropriate times.
フックは、 `RememberMeServices` によって適切なタイミングで実行されます。

> The interface looks like this:
インターフェースは次のようになっています。

```java
Authentication autoLogin(HttpServletRequest request, HttpServletResponse response);

void loginFail(HttpServletRequest request, HttpServletResponse response);

void loginSuccess(HttpServletRequest request, HttpServletResponse response,
    Authentication successfulAuthentication);
```

> Please refer to the Javadoc for a fuller discussion on what the methods do, although note at this stage that AbstractAuthenticationProcessingFilter only calls the loginFail() and loginSuccess() methods.
メソッドが何をするかについての完全な情報は Javadoc を参照してください。
ただし、このステージでは `AbstractAuthenticationProcessingFilter` は `loginFail()` と `loginSuccess()` メソッドだけを呼びます。

> The autoLogin() method is called by RememberMeAuthenticationFilter whenever the SecurityContextHolder does not contain an Authentication.
`SecurityContextHolder` が `Authentication` を持っていなかったとしても、 `autoLogin()` メソッドは `RememberMeAuthenticationFilter` によって呼ばれます。

> This interface therefore provides the underlying remember-me implementation with sufficient notification of authentication-related events, and delegates to the implementation whenever a candidate web request might contain a cookie and wish to be remembered.
このインターフェースは、認証に関係した十分なイベントの通知とともに Remember-me の実装の根底を提供します。
そして、クッキーを含み、記憶してほしい候補となる Web リクエストの場合は常に実装を委譲します。

> This design allows any number of remember-me implementation strategies.
この設計は、複数の Remember-Me の戦略実装を許します。

> We’ve seen above that Spring Security provides two implementations.
Spring Security が提供する２つの実装を見ることができます。

> We’ll look at these in turn.
これらについてみていきましょう。

### 17.4.1 TokenBasedRememberMeServices
> This implementation supports the simpler approach described in Section 17.2, “Simple Hash-Based Token Approach”.
この実装は、セクション17.2 "Simple Hash-Based Token Approach" で説明したシンプルなアプローチをサポートします。

> TokenBasedRememberMeServices generates a RememberMeAuthenticationToken, which is processed by RememberMeAuthenticationProvider.
`TokenBasedRememberMeServices` は、 `RememberMeAuthenticationProvider` が処理する `RememberMeAuthenticationToken` を生成します。

> A key is shared between this authentication provider and the TokenBasedRememberMeServices.
キーはこの認証プロバイダと `TokenBasedRememberMeServices` の間で共有されます。

> In addition, TokenBasedRememberMeServices requires A UserDetailsService from which it can retrieve the username and password for signature comparison purposes, and generate the RememberMeAuthenticationToken to contain the correct GrantedAuthority s.
加えて、 `TokenBasedRememberMeServices` は署名を比較するためにユーザー名とパスワードで検索が可能な `UserDetailsService` を必要とします。
そして、正しい `GrantedAuthority` を持った `RememberMeAuthenticationToken` を生成します。

> Some sort of logout command should be provided by the application that invalidates the cookie if the user requests this.
ユーザがクッキーの無効化を要求した場合は、アプリケーションはある種のログアウトのコマンドを提供すべきです。

> TokenBasedRememberMeServices also implements Spring Security’s LogoutHandler interface so can be used with LogoutFilter to have the cookie cleared automatically.
`TokenBasedRememberMeServices` は、 Spring Security の `LogoutHandler` インターフェースも実装していて、 `LogoutFilter` とともに使うことができ、自動的にクッキーをクリアできます。

> The beans required in an application context to enable remember-me services are as follows:
Remember-Me サービスを有効にする場合にアプリケーションコンテキストで必要となる Bean は次のようになります。

```xml
<bean id="rememberMeFilter" class=
"org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter">
    <property name="rememberMeServices" ref="rememberMeServices"/>
    <property name="authenticationManager" ref="theAuthenticationManager" />
</bean>

<bean id="rememberMeServices" class=
"org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices">
    <property name="userDetailsService" ref="myUserDetailsService"/>
    <property name="key" value="springRocks"/>
</bean>

<bean id="rememberMeAuthenticationProvider" class=
"org.springframework.security.authentication.RememberMeAuthenticationProvider">
    <property name="key" value="springRocks"/>
</bean>
```

> Don’t forget to add your RememberMeServices implementation to your UsernamePasswordAuthenticationFilter.setRememberMeServices() property, include the RememberMeAuthenticationProvider in your AuthenticationManager.setProviders() list, and add RememberMeAuthenticationFilter into your FilterChainProxy (typically immediately after your UsernamePasswordAuthenticationFilter).
次のことを忘れないでください。

- `RememberMeServices` の実装を `UsernamePasswordAuthenticationFilter` の `rememberMeServices` プロパティにセットすること
- `RememberMeAuthenticationProvider` を `AuthenticationManager` の `providers` リストに含めること
- `RememberMeAuthenticationFilter` を `FilterChainProxy` に追加すること（普通は `UsernamePasswordAuthenticationFilter` の直後）

### 17.4.2 PersistentTokenBasedRememberMeServices
> This class can be used in the same way as TokenBasedRememberMeServices, but it additionally needs to be configured with a PersistentTokenRepository to store the tokens.
このクラスは `TokenBasedRememberMeServices` と同じ手段で動くことができます。
しかし、トークンを保存するために `PersistentTokenRepository` を追加で設定する必要があります。

> There are two standard implementations.
２つの標準的な実装があります。

- InMemoryTokenRepositoryImpl which is intended for testing only.
    - `InMemoryTokenRepositoryImpl` はテストのためだけのものです
- JdbcTokenRepositoryImpl which stores the tokens in a database.
    - `JdbcTokenRepositoryImpl` はデータベースにトークンを保存します

> The database schema is described above in Section 17.3, “Persistent Token Approach”.
データベーススキーマはセクション 17.3 "Persistent Token Approach" で説明したものです。

