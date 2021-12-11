package com.geminibot.geminibot.controllers;

import com.geminibot.geminibot.annotations.RequiresAuthorizationToken;
import com.geminibot.geminibot.entities.postgres.User;
import com.geminibot.geminibot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/test")
    public ResponseEntity<User> createUserAndShow() {
        User user = userRepository.save(new User("test", "password"));
        return new ResponseEntity<User>(user, HttpStatus.valueOf(200));
    }

    @GetMapping("/test/protected")
    @ResponseBody
    @RequiresAuthorizationToken
    public ResponseEntity<Object> getTestProtected(HttpServletRequest httpServletRequest) {
        return new ResponseEntity<Object>("Test protected", HttpStatus.OK);
    }
}
