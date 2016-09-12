package sample.jsf;

import javax.enterprise.inject.Model;

@Model
public class HelloBean {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void submit() {
        System.out.println("message=" + message);
    }
}
