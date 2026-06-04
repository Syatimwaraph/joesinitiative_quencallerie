package dev.syatimwaraph.quencallerie_mngt_v1.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/login").permitAll()

                        .requestMatchers(
                                "/dashboard/**",
                                "/users/**",
                                "/reports/**",
                                "/requisitions/**"
                        ).hasRole("ADMIN")

                        .requestMatchers("/pos/**", "/cart/**")
                        .hasAnyRole("ADMIN", "SALER")

                        .anyRequest()
                        .authenticated()
                )

                .formLogin(login -> login
                        .loginPage("/login")
                        .successHandler(loginSuccessHandler)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/logged-out")
                        .permitAll()
                );
//                .exceptionHandling(exception -> exception
//                        .accessDeniedPage("/access-denied")
//                );

        return http.build();
    }
}

//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http)
//            throws Exception {
//
//        http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
//                        .requestMatchers("/login").permitAll()
//                        .requestMatchers("/users/create", "/users/save").hasRole("ADMIN")
//                        .requestMatchers("/pos", "/cart/**", "/product-requests", "/product-requests/**").hasAnyRole("ADMIN", "SALER")
//                        .requestMatchers("/**").hasRole("ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/dashboard", true)
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                );
//
//        return http.build();
//    }
//}