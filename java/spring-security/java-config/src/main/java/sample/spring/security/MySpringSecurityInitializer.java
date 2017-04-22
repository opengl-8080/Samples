package sample.spring.security;

        import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class MySpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
    public MySpringSecurityInitializer() {
        super(MySpringSecurityConfig.class);
    }

    @Override
    protected boolean enableHttpSessionEventPublisher() {
        return true;
    }
}
