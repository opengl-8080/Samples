package sample.domain;

import lombok.Data;
import sample.other.infrastructure.ormapper.Column;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Embeddable
@Data
public class Id implements Serializable {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
}
