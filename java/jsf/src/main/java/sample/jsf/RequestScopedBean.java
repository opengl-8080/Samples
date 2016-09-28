package sample.jsf;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class RequestScopedBean {

    private String value;

    @PostConstruct
    public void construct() {
        System.out.println("construct : " + this.hashCode());
    }

    @PreDestroy
    public void destroy() {
        System.out.println("destroy : " + this.hashCode());
    }

    public void method() {
        System.out.println("method : " + this.hashCode());
    }

    public String getValue() {
        System.out.println("getValue : " + this.hashCode());
        return value;
    }

    public void setValue(String value) {
        System.out.println("setValue : " + this.hashCode());
        this.value = value;
    }
}
