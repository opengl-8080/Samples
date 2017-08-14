package sample.thymeleaf.web;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class MyForm {
    @Size(min=3)
    private String text;
    @Min(100)
    private Integer number;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
