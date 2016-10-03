package sample.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name="members")
public class Member implements Serializable {
    @javax.persistence.Id
    @Column(name="login_id")
    private String loginId;
}
