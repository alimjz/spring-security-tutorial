package com.example.macde.springsecuritytutorial.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloWorldController {

    @GetMapping
    public ResponseEntity<String> helloWorld() {
        return  ResponseEntity.ok("Hello World");
    }
}
