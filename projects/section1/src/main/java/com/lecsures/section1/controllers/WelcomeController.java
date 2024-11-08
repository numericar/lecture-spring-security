package com.lecsures.section1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/welcomes")
public class WelcomeController {

    @GetMapping("")
    public ResponseEntity<String> getWelcome() {
        String message = "Hello World";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

}
