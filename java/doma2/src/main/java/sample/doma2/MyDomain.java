package sample.doma2;

import org.seasar.doma.Domain;

@Domain(valueType=String.class)
public class MyDomain {
    
    private String value;

    public MyDomain(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "MyDomain [value=" + value + "]";
    }
}
