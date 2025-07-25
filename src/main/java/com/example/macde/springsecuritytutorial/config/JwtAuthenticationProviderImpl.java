package com.example.macde.springsecuritytutorial.config;

import com.example.macde.springsecuritytutorial.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.List;

@Component
public class JwtAuthenticationProviderImpl implements AuthenticationProvider {
    private final SecretKey secretKey;

    public JwtAuthenticationProviderImpl(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String token = (String) authentication.getCredentials();
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(secretKey)
                    .clockSkewSeconds(1000L)
                    .requireIssuer("local-server")
                    .build()
                    .parseSignedClaims(token);
            String username = claimsJws.getPayload().get("username", String.class);
            String firstName = claimsJws.getPayload().get("firstName", String.class);
            String lastName = claimsJws.getPayload().get("lastName", String.class);
            String role = claimsJws.getPayload().get("role", String.class);
            List<String> scopes =  claimsJws.getPayload().get("scopes", List.class);
            UserDto userDto = UserDto.builder()
                    .username(username)
                    .firstName(firstName)
                    .lastName(lastName)
                    .build();
            return new JwtAuthenticationToken(userDto,
                    scopes.stream().map(SimpleGrantedAuthority::new).toList());
        } catch (JwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Invalid token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
