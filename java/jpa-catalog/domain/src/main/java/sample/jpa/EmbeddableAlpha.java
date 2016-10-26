package sample.jpa;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddableAlpha implements Serializable {
    @Column(name="map_key")
    private String value;
}
