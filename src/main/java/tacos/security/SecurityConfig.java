package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tacos.data.UserRepository;
import tacos.User;

import java.util.Collections;

/**
 * Spring Security 설정 클래스.
 * - PasswordEncoder 빈을 등록해서 RegistrationController에서 주입받도록 한다.
 * - 기본적인 formLogin/login, logout 설정 예시를 포함한다.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserRepository userRepo;

    public SecurityConfig(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * PasswordEncoder 빈 등록 (@Autowired로 RegistrationController, UserDetailsService 등에서 사용)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * UserDetailsService 빈 등록
     * - UserRepository를 사용해 MongoDB에서 User 찾아내도록 구현
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null) {
                return user;
            }
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }

    /**
     * SecurityFilterChain 정의
     * - 모든 요청은 로그인 필요
     * - "/register", "/login", "/css/**", "/js/**", "/images/**" 등은 permitAll
     * - 기본적인 formLogin, logout 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // 회원가입, 로그인 페이지 접근은 모두 허용
                        .requestMatchers("/register", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                        // 그 외 모든 요청은 인증된 사용자만
                        .anyRequest().authenticated()
                )
                // 기본 formLogin 설정
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                )
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                )
                // CSRF는 기본 설정 유지
                .csrf(Customizer.withDefaults());

        return http.build();
    }
}
