//package com.investhoodit.gr12webapp.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    // Configure the SecurityFilterChain to define custom security settings
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(requests -> requests
//                        .requestMatchers("/", "/login","/signup", "/css/**", "/js/**").permitAll()  // Allow public access to home and static resources
//                        .requestMatchers("/dashboard").authenticated()  // Protect the dashboard route
//                        .anyRequest().authenticated())  // Require authentication for other routes
//                .formLogin(login -> login
//                        .loginPage("/dashboard")  // Custom login page
//                        .permitAll())  // Allow everyone to access login page
//                .logout(logout -> logout
//                        .permitAll());  // Allow everyone to logout
//
//        return http.build();  // Ensure the http configuration is built
//    }
//
//    // Define in-memory user details for authentication
//    @Bean
//    public UserDetailsService userDetailsService() {
//        var user = User.withUsername("user")
//            .password(passwordEncoder().encode("password"))  // Encode password using BCrypt
//            .roles("USER")  // Assign the "USER" role to this user
//            .build();
//
//        var admin = User.withUsername("admin")
//            .password(passwordEncoder().encode("admin"))  // Encode password using BCrypt
//            .roles("ADMIN")  // Assign the "ADMIN" role to this admin
//            .build();
//
//        // Return an in-memory user details manager with the created users
//        return new InMemoryUserDetailsManager(user, admin);  
//    }
//
//    // Password encoder bean to securely hash passwords
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();  // BCrypt for password encoding
//    }
//
//   
//}
/*/
package com.investhoodit.gr12webapp.config;

import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login", "/signup").permitAll() // Allow access to login and signup
				.anyRequest().authenticated() // Secure all other endpoints
				.and().formLogin().loginPage("/login") // Customize login page if needed
				.and().logout().permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Authentication manager configuration (e.g., with user details service)
		auth.userDetailsService(userDetailsService());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}*/
