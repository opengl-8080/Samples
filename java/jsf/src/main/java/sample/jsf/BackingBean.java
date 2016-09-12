package sample.jsf;

import javax.enterprise.inject.Model;

@Model
public class BackingBean {
    
    public String page2() {
        return "page2.xhtml";
    }
}
