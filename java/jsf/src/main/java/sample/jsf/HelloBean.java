package sample.jsf;

import javax.enterprise.inject.Model;

@Model
public class HelloBean {
    
    private String value;
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }

    public String getMessage() {
        return "helloBean.value = " + this.value;
    }
}
