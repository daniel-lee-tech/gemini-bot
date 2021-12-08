package com.geminibot.geminibot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HealthController {

    public static ResponseEntity<String> getHealth() {
        return new ResponseEntity<>("what about now? work i'm working!", HttpStatus.OK);
    }

    public static ResponseEntity<String> getHealthNested() {
        return new ResponseEntity<>("what about now? work i'm working!", HttpStatus.OK);
    }
}
