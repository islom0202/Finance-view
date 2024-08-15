package univerranking.uz.digitalplatformforsmes.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtToken {
    private String token;
    public JwtToken(String token) {
        this.token = token;
    }
}
