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
public class NumberValue implements Serializable {
    @Column(name="number_value")
    public int value;

    @Override
    public boolean equals(Object obj) {
        System.out.println("NumberValue.equals(" + obj + ")");
        return (obj instanceof NumberValue)
                && ((NumberValue) obj).value == this.value;
    }

    @Override
    public int hashCode() {
        return this.value;
    }
}
