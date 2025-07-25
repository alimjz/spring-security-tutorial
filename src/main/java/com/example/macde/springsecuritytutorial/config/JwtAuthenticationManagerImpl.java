package com.example.macde.springsecuritytutorial.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationManagerImpl implements AuthenticationManager {
    private final AuthenticationProvider jwtProvider;

    public JwtAuthenticationManagerImpl(AuthenticationProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof JwtAuthenticationToken) {
            return jwtProvider.authenticate(authentication);
        }
        throw new UnsupportedOperationException("Unsupported authentication type");
    }
}
