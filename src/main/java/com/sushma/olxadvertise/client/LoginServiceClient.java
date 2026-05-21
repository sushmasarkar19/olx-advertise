package com.sushma.olxadvertise.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.sushma.olxadvertise.client.fallback.LoginServiceFallback;

/**
 * Feign client for olx-login service.
 * 
 * Uses Eureka service discovery instead of hardcoded URLs.
 * The service name MUST match the spring.application.name in olx-login's application.yml
 * 
 * Service Discovery Flow:
 * 1. Feign queries Eureka for service: "olx-login"
 * 2. Eureka returns available instances (e.g., http://192.168.1.100:8081)
 * 3. Feign automatically load-balances between instances
 * 4. Base URL is constructed: http://<instance>:8081/olx
 * 5. Requests are routed to endpoints like: GET /user/token/validate
 * 
 * If olx-login is unreachable, fallback is used (returns default values).
 */
@FeignClient(
	name = "olx-login",
	path = "/olx",
	fallback = LoginServiceFallback.class
)
public interface LoginServiceClient {

    /**
     * Validates the Authorization token.
     * Maps to: GET /user/token/validate in olx-login
     */
    @GetMapping("/user/token/validate")
    Boolean validateToken(@RequestHeader("Authorization") String authToken);

    /**
     * Fetches the logged-in user's details using the Authorization token.
     * Maps to: GET /user in olx-login
     */
    @GetMapping("/user/info")
    Map<String, Object> getUserDetails(@RequestHeader("Authorization") String authToken);
}
