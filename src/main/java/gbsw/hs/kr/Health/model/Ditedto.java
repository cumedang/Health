package gbsw.hs.kr.Health.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "dite")
public class Ditedto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String day;

    @Column
    private String moludi;

    @Column
    private String foodname;

    @Column
    private String username;
}
