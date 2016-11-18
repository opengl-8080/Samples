package common;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Embeddable
@ToString
@EqualsAndHashCode
public class Id<T> implements Serializable {
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
}
