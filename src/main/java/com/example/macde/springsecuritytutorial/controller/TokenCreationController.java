package com.example.macde.springsecuritytutorial.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/token")
public class TokenCreationController {

    private final SecretKey secretKey;

    public TokenCreationController(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    @PostMapping
    public ResponseEntity<String> createToken() {
        return ResponseEntity.ok(Jwts.builder().subject("ali")
                .issuedAt(Date.from(Instant.now()))
                .issuer("local-server")
                        .expiration(Date.from(Instant.now().plusSeconds(3600)))
                .claim("audience", "fannavari-mahsool")
                .claim("roles", "admin")
                .claim("scopes", List.of("openid", "profile", "email"))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact());
    }

    @PostMapping("/action")
    public ResponseEntity<String> doAction() {
        return ResponseEntity.ok("action completed");
    }
}
