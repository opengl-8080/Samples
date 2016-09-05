package sample.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named
public class NextBean {

    private String message;

    public String execute(int id, String command) {
        this.message = "id=" + id + ", command=" + command;
        return "next.xhtml";
    }

    public String getMessage() {
        return message;
    }
}
