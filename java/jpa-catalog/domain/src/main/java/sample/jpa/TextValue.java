package sample.jpa;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TextValue implements Serializable {
    @Column(name="text_value")
    private String value;
    
    @Override
    public boolean equals(Object obj) {
        System.out.println("TextValue.equals(" + obj + ")");
        return (obj instanceof TextValue)
                && ((TextValue) obj).value.equals(this.value);
    }
    
    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
}
