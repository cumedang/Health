package gbsw.hs.kr.Health.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TextDto {
    private String foodName;

    private String userid;

    private String username;

    private String moludi;

    private String date;

    private String Calories;

    private String tan;

    private String prot;

    private String prov;

    private String vita;

    private List<CommentDto> commentList;

}
