package univerranking.uz.digitalplatformforsmes.Dto;

import univerranking.uz.digitalplatformforsmes.Entity.Users;

public class UserMapper {
    public static UserResponseDto toDto(Users user) {
        String companyName = (user.getCompanies() != null) ? user.getCompanies().getName() : null;

        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                companyName
        );
    }
}
