package com.sitevisit.smartfieldoperations.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/login",
                                "/forgot-password",
                                "/reset-password",
                                "/dashboard",
                                "/members",
                                "/companies",
                                "/site-visits",
                                "/reports",
                                "/reminders-notifications",
                                "/site-visits/save",
                                "/site-visits/update-status/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/api/**"   // 🔥 IMPORTANT FIX FOR YOUR NOTIFICATIONS API
                        ).permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}