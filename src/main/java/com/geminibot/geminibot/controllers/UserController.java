package com.geminibot.geminibot.controllers;

import com.geminibot.geminibot.datatransferobjects.RegisterDTO;
import com.geminibot.geminibot.entities.postgres.User;
import com.geminibot.geminibot.entities.responses.restcontrollers.LoginResponse;
import com.geminibot.geminibot.repositories.UserRepository;
import com.geminibot.geminibot.services.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final MailService mailService;

    public UserController(UserRepository userRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    @GetMapping("/user/activate/{activationToken}")
    public void getUserActivate(@PathVariable("activationToken") UUID activationToken, HttpServletResponse httpServletResponse) {
        User possibleUser = userRepository.findByActivationToken(activationToken);

//        try {
//            if (possibleUser == null) {
//                httpServletResponse.sendRedirect("bad token url on react app");
//                return;
//            }
//        } catch (Exception exception) {
//            // TODO: handle this error more gracefully
//            System.out.println("Something happened");
//        }

        assert possibleUser != null; // this should not raise an exception, just making the compiler happy
        possibleUser.setActivated(true);
        User user = userRepository.save(possibleUser);

//        try {
//            httpServletResponse.sendRedirect("render login page with message saying user has been activated");
//        } catch (Exception exception) {
//            // TODO: handle this error more gracefully
//            System.out.println("Something happened");
//        }
    }

    @PostMapping("/user/register")
    @ResponseBody
    public ResponseEntity<LoginResponse> postTestLogin(@RequestBody RegisterDTO registerDTO) {
        User possibleUser = userRepository.findByEmail(registerDTO.getEmail());

        if (possibleUser != null) {
            LoginResponse loginResponse = new LoginResponse("Email is already registered", true, new User(registerDTO));
            System.out.println(loginResponse.getEntity());
            return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.valueOf(409));
        }

        try {
            User user = userRepository.save(new User(registerDTO));
            LoginResponse loginResponse = new LoginResponse("User with email=" + registerDTO.getEmail() + " has been created", false, user);
            mailService.sendActivationEmail(user);
            return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.valueOf(200));

        } catch (AssertionError e) {
            LoginResponse loginResponse = new LoginResponse("Passwords do not match", true, new User(registerDTO));
            return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.valueOf(409));

        } catch (MessagingException messagingException) {
            // TODO: integrate something like rollbar here
            System.out.println(messagingException.getMessage());
            System.out.println("For some reason email could not be sent");

            LoginResponse loginResponse = new LoginResponse("Internal error, please try again later, and contact an administrator", true, new User(registerDTO));
            return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.valueOf(500));

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            // TODO: integrate something like rollbar here
            LoginResponse loginResponse = new LoginResponse("Internal error, please try again later, and contact an administrator", true, new User(registerDTO));

            return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.valueOf(500));
        }
    }
}
