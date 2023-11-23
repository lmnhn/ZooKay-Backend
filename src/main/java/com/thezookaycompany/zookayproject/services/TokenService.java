package com.thezookaycompany.zookayproject.services;

import org.springframework.security.core.Authentication;

import java.util.Map;

public interface TokenService {
    String generateJwt(Authentication auth);
    Map<String, Object> decodeJwt(String jwt);
}
