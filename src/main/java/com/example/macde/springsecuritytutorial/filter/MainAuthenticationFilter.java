package com.example.macde.springsecuritytutorial.filter;

import com.example.macde.springsecuritytutorial.config.JwtAuthenticationToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class MainAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private static final List<String> WHITE_LIST = List.of("/api/v1/auth/token");

    public MainAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if (WHITE_LIST.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);

            Authentication jwtAuth = new JwtAuthenticationToken(token);
            Authentication authenticate = authenticationManager.authenticate(jwtAuth);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.clearContext();
        throw new AuthenticationServiceException("Not authenticated");
    }
}
