package sample.jsf;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class ViewScopedBean implements Serializable {
    private final UUID uuid = UUID.randomUUID();
    private String value;

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

    public String next() {
        return "next.xhtml";
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
