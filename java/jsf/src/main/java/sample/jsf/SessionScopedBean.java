package sample.jsf;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.UUID;

@Named
@SessionScoped
public class SessionScopedBean implements Serializable {
    private final UUID uuid = UUID.randomUUID();
    private String value;

    @Inject
    transient private HttpSession session;

    @PostConstruct
    public void construct() {
        System.out.println("construct : " + this.uuid);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("destroy : " + this.uuid);
    }

    public void method() {
        System.out.println("method : " + this.uuid);
    }

    public void killSession() {
        System.out.println("killSession : " + this.uuid);
        this.session.invalidate();
    }

    public String getValue() {
        System.out.println("getValue : " + this.uuid);
        return value;
    }

    public void setValue(String value) {
        System.out.println("setValue : " + this.uuid);
        this.value = value;
    }
}
