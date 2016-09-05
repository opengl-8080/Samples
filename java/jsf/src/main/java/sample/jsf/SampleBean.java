package sample.jsf;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("sampleBean")
@ViewScoped
public class SampleBean implements Serializable {
    private int id;
    private boolean flag;

    public void init() {
        this.flag = (this.id % 2 == 0);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String gusu() {
        return "next.xhtml";
    }

    public String kisu() {
        return "next.xhtml";
    }

    @Override
    public String toString() {
        return "SampleBean{" +
                "id=" + id +
                ", flag=" + flag +
                '}';
    }

    public boolean isFlag() {
        return flag;
    }
}
