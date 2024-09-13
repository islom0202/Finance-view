package univerranking.uz.digitalplatformforsmes.Service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import univerranking.uz.digitalplatformforsmes.Dto.UserRequestDto;
import univerranking.uz.digitalplatformforsmes.Dto.UserResponseDto;
import univerranking.uz.digitalplatformforsmes.Entity.Companies;
import univerranking.uz.digitalplatformforsmes.Entity.Users;
import univerranking.uz.digitalplatformforsmes.Enum.UserRole;
import univerranking.uz.digitalplatformforsmes.Repository.CompaniesRepository;
import univerranking.uz.digitalplatformforsmes.Repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CompaniesRepository companiesRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        Users user = new Users();
        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        user.setFirstName(userRequestDto.getFirstName());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setLastName(userRequestDto.getLastName());
        user.setRole(UserRole.ROLE_USER);
        user.setCreatedAt(LocalDateTime.now());
        Optional<Companies> companies = getCompanyById(userRequestDto.getCompanyId());
        user.setCompanies(companies.get());
        userRepository.save(user);
        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                user.getCompanies().getName()
        );
    }

    public Optional<Companies> getCompanyById(Long companyId) {
        return companiesRepository.findById(companyId);
    }

    public UserResponseDto getUserProfile(String username) {
        Users user = userRepository.findByUsername(username);
        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getUsername(),
                user.getRole(),
                user.getCompanies().getName()
        );
    }
}
