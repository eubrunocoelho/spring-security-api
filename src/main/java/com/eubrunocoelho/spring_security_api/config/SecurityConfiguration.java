package com.eubrunocoelho.spring_security_api.config;

import com.eubrunocoelho.spring_security_api.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    JwtFilter jwtFilter;
    LoginUtilityService loginUtilityService;

    public SecurityConfiguration(JwtFilter jwtFilter, LoginUtilityService loginUtilityService) {
        // Auto inject dependent beans
        this.jwtFilter = jwtFilter;
        this.loginUtilityService = loginUtilityService;
    }

    // Define SecurityFilterChain for API (Basic Authentication)
    @Bean
    @Order(1)
    public SecurityFilterChain basicAuthSecurityFilterChain(HttpSecurity http) throws Exception {
        // JWT based Token Authentication is enabled
        // To enable Basic Authentication, comment addFilterBefore(jwtFilter, ...) and uncomment httpBasic
        return http
                .csrf(csrf -> csrf.disable())
                .securityMatcher("/api/**")
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/api/open/**").permitAll();
                    request.requestMatchers("/api/users/register").permitAll();
                    request.requestMatchers("/api/users/login").permitAll();
                    request.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ))
//                .httpBasic(Customizer.withDefaults()) // Basic Authentication (Disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Custom User Details Service to manage User instance
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService userDetailsService = (userName) -> {
            return loginUtilityService.findMatch(userName);
        };

        return userDetailsService;
    }

    // Password encoder
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
