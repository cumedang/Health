package gbsw.hs.kr.Health.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "foods")
public class FoodDto {
    @Id
    @Column(name = "food_name")
    private String foodname;

    @Column
    private String calories;

    @Column
    private String photo_link;

    @Column
    private String tan;

    @Column
    private String prot;

    @Column
    private String prov;

    @Column
    private String vita;
}
