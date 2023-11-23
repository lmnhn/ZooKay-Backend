package com.thezookaycompany.zookayproject.services.impl;

import com.thezookaycompany.zookayproject.model.entity.Account;
import com.thezookaycompany.zookayproject.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public String generateJwt(Authentication auth) {
        Instant now = Instant.now();

        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Account account = (Account) auth.getPrincipal();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(auth.getName()) //username or email
                .claim("roles", scope)
                .claim("email", account.getEmail()) // Add the email as a custom claim
                .build();
        //pass token value back to front end
        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }

    // decode email từ jwt
    @Override
    public Map<String, Object> decodeJwt(String jwtToken) {
        Jwt jwt = jwtDecoder.decode(jwtToken);
        Map<String, Object> claimData = jwt.getClaims();
        return claimData;
    }
}
