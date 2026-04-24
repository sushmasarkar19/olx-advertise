package com.sushma.olxadvertise.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.sushma.olxadvertise.client.fallback.LoginServiceFallback;

/**
 * Feign client for olx-login service.
 * Base URL is resolved from application.properties via 'url' attribute.
 * Fallback handles the case when olx-login is unreachable.
 */
//@FeignClient(name = "olx-login", url = "${olx.login.service.url}", fallback = LoginServiceFallback.class)
@FeignClient(
	    name = "login-service",
	    url  = "${olx.login.service.url}"   // externalized — never hardcode
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
