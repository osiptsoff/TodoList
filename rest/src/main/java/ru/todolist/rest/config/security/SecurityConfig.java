package ru.todolist.rest.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import ru.todolist.rest.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
        		.csrf(csrf -> csrf.disable())
        		.httpBasic(httpBasic -> httpBasic.disable())
        		.formLogin(formLogin -> formLogin.disable())
        		.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        		.authorizeHttpRequests(auth -> {
        			auth.requestMatchers("/rest/auth/**", "/rest/users").permitAll();
        			auth.anyRequest().authenticated();
        		})
        		.addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        		.build();

    }
    
    @Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/rest/**", configuration);
		return source;
	}
	
}
