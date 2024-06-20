package gbsw.hs.kr.Health.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeDto {
    public String password;

    public String newPassword;
}
