package sample.jpa;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MultipleValues implements Serializable {
    @Column(name="number_value")
    private int numberValue;
    @Column(name="text_value")
    private String textValue;
    @Temporal(TemporalType.DATE)
    @Column(name="date_value")
    private Date dateValue;
}
