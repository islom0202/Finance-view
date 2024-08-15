package univerranking.uz.digitalplatformforsmes.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserRequestDto {
    private String username;
    private String password;
    private String role;
    private Date createdAt;
}
