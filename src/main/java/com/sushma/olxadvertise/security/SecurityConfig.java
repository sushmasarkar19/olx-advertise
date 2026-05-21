package com.sushma.olxadvertise.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration for olx-advertise microservice.
 * 
 * This service does NOT manage authentication/JWT directly.
 * Instead, it relies on the API Gateway to validate JWT tokens.
 * 
 * Endpoints requiring authentication are protected by:
 * 1. API Gateway validates JWT before routing to this service
 * 2. X-Auth-User header is added by the gateway (contains username)
 * 3. This service trusts the X-Auth-User header is present and valid
 * 
 * Public endpoints (no auth needed):
 * - GET /advertise/search
 * - GET /advertise/search/filtercriteria
 * - GET /advertise/{advertiseId}
 * 
 * Protected endpoints (require JWT via Gateway):
 * - POST /advertise
 * - PUT /advertise/{id}
 * - GET /user/advertise/**
 * - DELETE /user/advertise/**
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] SWAGGER_PATHS = {
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/webjars/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            .authorizeHttpRequests(auth -> auth
                
                // ── Swagger UI (always public) ────────────────────────────
                .requestMatchers(SWAGGER_PATHS).permitAll()
                
                // ── Public search endpoints (no JWT needed) ───────────────
                .requestMatchers(HttpMethod.GET, "/advertise/search").permitAll()
                .requestMatchers(HttpMethod.GET, "/advertise/search/filtercriteria").permitAll()
                .requestMatchers(HttpMethod.GET, "/advertise/{advertiseId}").permitAll()
                
                // ── Everything else requires the X-Auth-User header ───────
                // (Added by API Gateway after JWT validation)
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
