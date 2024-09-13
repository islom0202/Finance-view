package univerranking.uz.digitalplatformforsmes.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtToken {
    private String accessToken;
    private String refreshToken;
    public JwtToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
