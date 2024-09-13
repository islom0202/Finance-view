package univerranking.uz.digitalplatformforsmes.Dto;

import lombok.Data;
import univerranking.uz.digitalplatformforsmes.Enum.UserRole;

@Data
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private UserRole role;
    private String companyName;

    public UserResponseDto(Long id, String firstName, String lastName, String email, String username, UserRole role, String companyName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.role = role;
        this.companyName = companyName;
    }
}
