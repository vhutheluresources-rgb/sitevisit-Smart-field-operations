package com.sitevisit.smartfieldoperations.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // ✅ CORS configuration (allows frontend to call API)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // ⚠️ CSRF disabled for API testing (enable with CookieCsrfTokenRepository for production)
            .csrf(csrf -> csrf.disable())
            
            // ✅ Authorization rules
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                    "/login",
                    "/forgot-password",
                    "/reset-password",
                    "/dashboard",
                    "/members",
                    "/companies",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/fonts/**",
                    "/api/auth/**",
                    "/api/companies/**",  // ✅ ADD THIS: Allow company API endpoints
                    "/api/companies"       // ✅ ADD THIS: Allow company list endpoint
                ).permitAll()
                .anyRequest().authenticated()
            )
            
            // ✅ Keep form login disabled (as per your setup)
            .formLogin(form -> form.disable())
            
            // ✅ Keep HTTP Basic disabled (as per your setup)
            .httpBasic(basic -> basic.disable());

        return http.build();
    }

    // ✅ CORS Configuration Source (allows frontend to call backend)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Allow your frontend origins (adjust for production)
        configuration.setAllowedOrigins(List.of(
            "http://localhost:8080",
            "http://127.0.0.1:8080"
        ));
        
        // Allow methods your frontend uses
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        // Allow headers your frontend sends
        configuration.setAllowedHeaders(List.of("*"));
        
        // Allow credentials (cookies, auth headers)
        configuration.setAllowCredentials(true);
        
        // Cache preflight requests for 1 hour
        configuration.setMaxAge(3600L);
        
        // Apply to all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}