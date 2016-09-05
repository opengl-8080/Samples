package sample.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class SampleBean {
    private int id;
    private String buttonLabel;
    private String command;

    public void init() {
        if (this.id % 2 == 0) {
            this.buttonLabel = "偶数";
            this.command = "ぐうすう";
        } else {
            this.buttonLabel = "奇数";
            this.command = "きすう";
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setButtonLabel(String buttonLabel) {
        this.buttonLabel = buttonLabel;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getId() {
        return id;
    }

    public String getButtonLabel() {
        return buttonLabel;
    }

    public String getCommand() {
        return command;
    }
}
