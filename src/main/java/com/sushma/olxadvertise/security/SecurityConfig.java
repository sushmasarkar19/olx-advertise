package com.sushma.olxadvertise.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security Configuration for olx-advertise microservice.
 * 
 * This service does NOT manage authentication/JWT directly.
 * Instead, it relies on the API Gateway to validate JWT tokens.
 * 
 * Endpoints requiring authentication are protected by:
 * 1. API Gateway validates JWT before routing to this service
 * 2. X-Auth-User header is added by the gateway (contains username)
 * 3. GatewayAuthFilter extracts X-Auth-User and creates Spring Security
 *    Authentication in the SecurityContext
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

    @Autowired
    private GatewayAuthFilter gatewayAuthFilter;

    private static final String[] PUBLIC_PATHS = {
        "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs",
        "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**",
        "/webjars/**", "/actuator/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            .authorizeHttpRequests(auth -> auth
                
                // ── Swagger UI (always public) ────────────────────────────
                .requestMatchers(PUBLIC_PATHS).permitAll()
                
                // ── Public search endpoints (no JWT needed) ───────────────
                .requestMatchers(HttpMethod.GET, "/advertise/search").permitAll()
                .requestMatchers(HttpMethod.GET, "/advertise/search/filtercriteria").permitAll()
                .requestMatchers(HttpMethod.GET, "/advertise/{advertiseId}").permitAll()
                
                // ── Everything else requires the X-Auth-User header ───────
                // (Added by API Gateway after JWT validation)
                .anyRequest().authenticated()
            )
            
            // ── Register GatewayAuthFilter ────────────────────────────────
            // This filter reads the X-Auth-User header (set by the API Gateway
            // after JWT validation) and creates a UsernamePasswordAuthenticationToken
            // in the SecurityContext, satisfying the .authenticated() requirement.
            .addFilterBefore(gatewayAuthFilter,
                             UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
