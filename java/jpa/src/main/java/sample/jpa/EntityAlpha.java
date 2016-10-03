package sample.jpa;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="table_alpha")
@ToString
@NoArgsConstructor
public class EntityAlpha implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="ordinal_enum_value")
    private EnumAlpha ordinalEnumValue;

    @Column(name="string_enum_value")
    @Enumerated(EnumType.STRING)
    private EnumAlpha stringEnumValue;

    public EntityAlpha(EnumAlpha ordinalEnumValue, EnumAlpha stringEnumValue) {
        this.ordinalEnumValue = ordinalEnumValue;
        this.stringEnumValue = stringEnumValue;
    }
}
