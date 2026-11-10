package com.gnaneshwari.jobportal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())   // not needed for stateless token APIs
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
            	    .requestMatchers("/", "/index.html","/error").permitAll()
            	    .requestMatchers("/api/auth/**").permitAll()
            	    .requestMatchers(HttpMethod.POST, "/api/jobs/*/apply").hasRole("JOB_SEEKER")
            	    .requestMatchers(HttpMethod.GET, "/api/jobs/*/applicants").hasRole("RECRUITER")
            	    .requestMatchers(HttpMethod.GET, "/api/jobs/**").permitAll()
            	    .requestMatchers(HttpMethod.POST, "/api/jobs/**").hasRole("RECRUITER")
            	    .requestMatchers(HttpMethod.PUT, "/api/jobs/**").hasRole("RECRUITER")
            	    .requestMatchers(HttpMethod.DELETE, "/api/jobs/**").hasRole("RECRUITER")
            	    .anyRequest().authenticated())
            .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}