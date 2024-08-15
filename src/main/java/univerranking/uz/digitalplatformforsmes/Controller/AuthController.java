package univerranking.uz.digitalplatformforsmes.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import univerranking.uz.digitalplatformforsmes.Dto.JwtToken;
import univerranking.uz.digitalplatformforsmes.Dto.LoginRequest;
import univerranking.uz.digitalplatformforsmes.Utils.RestConstants;

@RestController
@RequestMapping(RestConstants.PATH+"/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<JwtToken> login(@RequestBody LoginRequest request) {
        if (request.getUsername() == null || request.getPassword() == null
                || request.getUsername().isEmpty() || request.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            );
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            //String token =

                return ResponseEntity.ok(new JwtToken("xcsdcascd"));
//                        .header("Authorization", "Bearer " + token)
//                        .body(new JwtToken(token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}