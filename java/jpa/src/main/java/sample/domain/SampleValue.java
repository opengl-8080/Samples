package sample.domain;

import javax.persistence.Embeddable;

@Embeddable
public class SampleValue {
    
    private final String value;
    
    public SampleValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SampleValue [value=" + value + "]";
    }
    
    @SuppressWarnings("unused")
    private SampleValue() {
        this(null);
    }
}
