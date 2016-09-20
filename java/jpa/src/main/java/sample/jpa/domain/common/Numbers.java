package sample.jpa.domain.common;

import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;
import java.text.DecimalFormat;

@Entity
@ToString
public class Numbers implements Serializable {
    @Id
    @Enumerated(EnumType.STRING)
    private NumbersType type;
    @Column(name="current_value")
    private long currentValue;

    public synchronized void countUp() {
        this.currentValue++;
    }

    public String format(DecimalFormat formatter) {
        return formatter.format(this.currentValue);
    }

    @Deprecated protected Numbers() {}
}
