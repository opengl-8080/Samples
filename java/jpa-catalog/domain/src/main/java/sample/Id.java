package sample;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Embeddable
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Id<T> implements Serializable {
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    public Id(Long id) {
        this.id = id;
    }
}
