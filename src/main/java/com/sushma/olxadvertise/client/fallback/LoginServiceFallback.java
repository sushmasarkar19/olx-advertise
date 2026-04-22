package com.sushma.olxadvertise.client.fallback;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.sushma.olxadvertise.client.LoginServiceClient;
import com.sushma.olxadvertise.exception.InvalidTokenException;

/**
 * Feign fallback for LoginServiceClient.
 * Invoked when olx-login is unreachable or returns an error.
 */
@Component
public class LoginServiceFallback implements LoginServiceClient {

    @Override
    public Boolean validateToken(String authToken) {
        throw new InvalidTokenException("olx-login service is unavailable. Cannot validate token.");
    }

    @Override
    public Map<String, Object> getUserDetails(String authToken) {
        throw new InvalidTokenException("olx-login service is unavailable. Cannot fetch user details.");
    }
}
