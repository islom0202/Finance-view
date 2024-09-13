package univerranking.uz.digitalplatformforsmes.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import univerranking.uz.digitalplatformforsmes.Dto.UserMapper;
import univerranking.uz.digitalplatformforsmes.Dto.UserRequestDto;
import univerranking.uz.digitalplatformforsmes.Dto.UserResponseDto;
import univerranking.uz.digitalplatformforsmes.Entity.Users;
import univerranking.uz.digitalplatformforsmes.Repository.UserRepository;
import univerranking.uz.digitalplatformforsmes.Service.UserService;
import univerranking.uz.digitalplatformforsmes.Utils.RestConstants;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(RestConstants.PATH+"/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/save")
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.saveUser(userRequestDto));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getProfile() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserResponseDto userResponseDto = userService.getUserProfile(userDetails.getUsername());
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserRequestDto> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new UserRequestDto());
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<Users> usersList = userRepository.findAll();
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for (Users user : usersList) {
            UserResponseDto userResponseDto = UserMapper.toDto(user);
            userResponseDtoList.add(userResponseDto);
        }
        return ResponseEntity.ok(userResponseDtoList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        // Logic to delete a user
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<String> updateUserRole(@PathVariable Long id, @RequestParam String role) {
        // Logic to update user's role
        return ResponseEntity.ok("User role updated successfully");
    }
}
