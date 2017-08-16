package sample.thymeleaf;

import javax.validation.constraints.Size;

public class MyText {
    @Size(min=3)
    private String value;

    public MyText() {
    }

    public MyText(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MyText{" +
                "value='" + value + '\'' +
                '}';
    }
}
