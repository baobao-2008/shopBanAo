package vn.fpoly.assignmentjava202.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.AuthProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf-> csrf.disable())// tạm tời tắt scurity
        .authorizeHttpRequests(auth->auth//Thieets lập các quy tắc
                .requestMatchers("/home/**","/css/**","/images/**","/auth/login","/auth/register", "/auth/activate", "/auth/forgot-password/**","/auth/reset-password" ).permitAll() // cho phép các trang này không cần kiểm tra qua lơp bảo mật để đăng nhập
                .requestMatchers("/admin/**","/adminac/**").hasRole("ADMIN")
                .anyRequest().authenticated()// Để thực hiện mua hàng hay thanh toán cần phải đăng nhập
        )
                .formLogin(login->login
                        .loginPage("/auth/login")//Đường dẫn trang login
                        .loginProcessingUrl("/auth/login-process")// url xư lý khi nhân nút đăng nhập
                        .successHandler((request, response, authentication) -> {
                            // kiểm tra quyền của người  vừa đăng nhập
                            var authorities = authentication.getAuthorities();
                            for(var auth: authorities) {
                                if(auth.getAuthority().equals("ROLE_ADMIN")) {
                                    response.sendRedirect("/admin/index");
                                    return;
                                }
                            }
                            response.sendRedirect("/home/index");
                        })
                        .failureUrl("/auth/login?error=true")// Thât bại
                        .permitAll()
                )
                .logout(logout->logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/home/index")
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
