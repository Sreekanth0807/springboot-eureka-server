package com.example.UserAuthenticationService.security;





import com.example.UserAuthenticationService.domain.User;

import java.util.Map;


public interface SecurityTokenGenerator {
    String createToken(User user);

    String generateToken(Map<String, Object> claims, String subject);
}
