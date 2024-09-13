package univerranking.uz.digitalplatformforsmes.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import univerranking.uz.digitalplatformforsmes.Jwt.JwtConfig;
import univerranking.uz.digitalplatformforsmes.Jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtTokenProvider jwtTokenProvider;
    private final String[] SWAGGER_URLS = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**"
    };

    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(SWAGGER_URLS).permitAll()
                        .requestMatchers("/api/v1/auth",
                                "/api/v1/company/save",
                                "/api/v1/user/save",
                                "/api/analytics/**",
                                "/api/forecast/**",
                                "/api/v1/data",
                                "/api/v1/data/upload",
                                "/api/v1/user/list","/api/v1/user/profile",
                                "/**").permitAll()
                        //.requestMatchers().hasRole("USER")
//                        .requestMatchers(
//                                "/api/v1/user/get/**").hasAnyRole("MANAGER", "ADMIN")
                        .anyRequest().authenticated()
                );
        JwtConfig jwtConfig = new JwtConfig(jwtTokenProvider);
        jwtConfig.configure(http);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}