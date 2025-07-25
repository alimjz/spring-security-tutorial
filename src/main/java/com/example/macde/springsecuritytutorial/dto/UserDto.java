package com.example.macde.springsecuritytutorial.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private  String username;
    private String firstName;
    private String lastName;
    private String role;
    private List<GrantedAuthority> authorities;

}
