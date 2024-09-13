package univerranking.uz.digitalplatformforsmes.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Long companyId;
}
