package gbsw.hs.kr.Health.model;

import jakarta.persistence.*;
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
    private String pwd;

    @Column(name = "name")
    private String name;

    @Enumerated(value = EnumType.STRING) //저장될때는 string으로 저장되도록
    private Role role;
}
