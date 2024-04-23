package gbsw.hs.kr.Health.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "member")
public class Signdto {
    @Id
    private String id;

    @Column(name = "pw")
    private String pw;

    @Column(name = "name")
    private String name;
}
