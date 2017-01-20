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

> It doesn’t use servlets or any other servlet-based frameworks (such as Spring MVC) internally, so it has no strong links to any particular web technology.

> It deals in HttpServletRequest s and HttpServletResponse s and doesn’t care whether the requests come from a browser, a web service client, an HttpInvoker or an AJAX application.

Spring Security maintains a filter chain internally where each of the filters has a particular responsibility and filters are added or removed from the configuration depending on which services are required.

> The ordering of the filters is important as there are dependencies between them.

> If you have been using namespace configuration, then the filters are automatically configured for you and you don’t have to define any Spring beans explicitly but here may be times when you want full control over the security filter chain, either because you are using features which aren’t supported in the namespace, or you are using your own customized versions of classes.
