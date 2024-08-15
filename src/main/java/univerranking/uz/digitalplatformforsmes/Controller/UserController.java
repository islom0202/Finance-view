package univerranking.uz.digitalplatformforsmes.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univerranking.uz.digitalplatformforsmes.Dto.UserRequestDto;
import univerranking.uz.digitalplatformforsmes.Utils.RestConstants;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(RestConstants.PATH+"/api/user")
public class UserController {

    @PostMapping("/save")
    public ResponseEntity<UserRequestDto> saveUser(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userRequestDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserRequestDto> getUserById(@PathVariable("id") String id) {
        return ResponseEntity.ok(new UserRequestDto());
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserRequestDto>> getAllUsers() {
        return ResponseEntity.ok(new ArrayList<>());
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
