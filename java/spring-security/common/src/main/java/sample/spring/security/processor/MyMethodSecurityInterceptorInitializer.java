package sample.spring.security.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;

public class MyMethodSecurityInterceptorInitializer implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!(bean instanceof MethodSecurityInterceptor))  {
            return bean;
        }

        System.out.println("postProcessAfterInitialization");
        MethodSecurityInterceptor methodSecurityInterceptor = (MethodSecurityInterceptor) bean;
        methodSecurityInterceptor.setAlwaysReauthenticate(true);
        return methodSecurityInterceptor;
    }
}
