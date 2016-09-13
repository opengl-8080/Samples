package sample.jsf;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

@Model
public class BackingBean {
    
    private String value;

    public String submit() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        flash.put("value", this.value);
        return "/page2.xhtml?faces-redirect=true";
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
