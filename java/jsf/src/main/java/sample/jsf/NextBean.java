package sample.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named
public class NextBean {

    private String message;

    public String gusu() {
        this.message = "Gusu!!";
        return "next.xhtml";
    }

    public String kisu() {
        this.message = "Kisu!!";
        return "next.xhtml";
    }

    public String getMessage() {
        return message;
    }
}
