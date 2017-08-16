package sample.thymeleaf.web;

import sample.thymeleaf.MyText;

import javax.validation.Valid;
import javax.validation.constraints.Min;

public class MyForm {
    @Valid
    private MyText text;
    @Min(100)
    private Integer number;

    @Override
    public String toString() {
        return "MyForm{" +
                "text='" + text + '\'' +
                ", number=" + number +
                '}';
    }
}
