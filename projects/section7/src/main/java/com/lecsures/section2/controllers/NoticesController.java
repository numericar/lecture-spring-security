package com.lecsures.section2.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notices")
public class NoticesController {

    @GetMapping("")
    public ResponseEntity<String> getNoticeDetails() {
        return ResponseEntity.status(HttpStatus.OK).body("Here are the notice details from database");
    }

}
