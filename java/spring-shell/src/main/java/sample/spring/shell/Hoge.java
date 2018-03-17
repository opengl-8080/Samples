package sample.spring.shell;

import javax.validation.constraints.Size;

public class Hoge {
    
    private final String value;

    public Hoge(@Size(min=1, max=5) String value) {
        this.value = value;
    }
    
    public void hello() {
        System.out.println("Hoge(" + this.value + ")");
    }
}
