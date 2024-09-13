package univerranking.uz.digitalplatformforsmes.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    public String generateToken(UserDetails userDetails) {
        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 minutes expiration
                .claim("role", roles)
                .signWith(getKey()).compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // one weeks expiration
                .claim("role", roles)
                .signWith(getKey()).compact();
    }

//    public boolean isValid(String token) {
//        Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).getBody();
////                verifyWith(getKey())
////                .build()
////                .parseSignedClaims(token)
////                .getPayload();
//        return true;
//    }
public boolean isValid(String token) {
    try {
        Key key = getKey(); // Method to retrieve your signing key
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims != null; // Token is valid if claims are successfully parsed
    } catch (Exception e) {
        // Handle exceptions (e.g., ExpiredJwtException, MalformedJwtException)
        return false;
    }
}

    public SecretKey getKey() {
        byte[] bytes = Decoders.BASE64.decode("1234567891234567891234567891234567891234567891234567891234567890");
        return Keys.hmacShaKeyFor(bytes);
    }
    public Authentication getUser(String token) {
        Key key = getKey(); // Method to retrieve your signing key
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();

        Claims claims = parser.parseClaimsJws(token).getBody();
//        Claims claims = Jwts.parser().
//                verifyWith(getKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
        String roles = claims.get("role", String.class);
        var role = Arrays.stream(roles.split(",")).map(SimpleGrantedAuthority::new).toList();
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
