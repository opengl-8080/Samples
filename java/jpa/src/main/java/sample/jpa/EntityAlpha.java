package sample.jpa;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name="table_alpha")
@ToString
@NoArgsConstructor
public class EntityAlpha implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="string_value")
    private String stringValue;

    @Column(name="boolean_value")
    private boolean booleanValue;

    @Column(name="int_value")
    private int intValue;

    @Column(name="double_value")
    private double doubleValue;

    @Column(name="big_integer_value")
    private BigInteger bigIntegerValue;

    @Column(name="big_decimal_value")
    private BigDecimal bigDecimalValue;

    @Column(name="date_value")
    @Temporal(TemporalType.DATE)
    private Date dateValue;

    @Column(name="datetime_value")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTimeValue;

    public EntityAlpha(String stringValue, boolean booleanValue, int intValue, double doubleValue, BigInteger bigIntegerValue, BigDecimal bigDecimalValue, Date dateValue, Date dateTimeValue) {
        this.stringValue = stringValue;
        this.booleanValue = booleanValue;
        this.intValue = intValue;
        this.doubleValue = doubleValue;
        this.bigIntegerValue = bigIntegerValue;
        this.bigDecimalValue = bigDecimalValue;
        this.dateValue = dateValue;
        this.dateTimeValue = dateTimeValue;
    }
}
