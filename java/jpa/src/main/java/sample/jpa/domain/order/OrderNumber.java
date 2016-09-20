package sample.jpa.domain.order;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import sample.jpa.domain.common.Numbers;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.text.DecimalFormat;

@Embeddable
@ToString
@EqualsAndHashCode
public class OrderNumber implements Serializable {
    @Column(name="order_number")
    private String value;

    public static OrderNumber create(Numbers numbers) {
        DecimalFormat formatter = new DecimalFormat("O000000000");
        String textOrderNumber = numbers.format(formatter);
        return new OrderNumber(textOrderNumber);
    }

    public OrderNumber(String value) {
        this.value = value;
    }

    @Deprecated protected OrderNumber() {}

    public String getValue() {
        return value;
    }
}
