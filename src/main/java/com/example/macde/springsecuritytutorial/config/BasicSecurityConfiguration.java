package com.example.macde.springsecuritytutorial.config;

import com.example.macde.springsecuritytutorial.filter.MainAuthenticationFilter;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;

@Configuration
public class BasicSecurityConfiguration {
    private static final Logger log = LoggerFactory.getLogger(BasicSecurityConfiguration.class);
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
//                .exceptionHandling((Customizer.withDefaults()))
//                .build();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
//                .exceptionHandling((Customizer.withDefaults()))
//                .formLogin(formLogin -> formLogin.defaultSuccessUrl("/api/v1/hello",true))
//                .build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, MainAuthenticationFilter mainAuthenticationFilter) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"error\": \"Unauthorized\"}");
                        })
                )
                .addFilterBefore(mainAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public SecretKey secretKey() {
        log.info("Creating SecretKey.");
        return Keys.hmacShaKeyFor("hello-this-is-a-key-for-test-some-spring-security-tips-in-sadad-informatics".getBytes(StandardCharsets.UTF_8));
    }
}