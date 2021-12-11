package com.geminibot.geminibot.controllers;

import com.geminibot.geminibot.datatransferobjects.LoginDTO;
import com.geminibot.geminibot.datatransferobjects.RegisterDTO;
import com.geminibot.geminibot.entities.postgres.User;
import com.geminibot.geminibot.entities.responses.restcontrollers.LoginResponse;
import com.geminibot.geminibot.entities.responses.restcontrollers.LoginResponseEntity;
import com.geminibot.geminibot.entities.responses.restcontrollers.RegisterResponse;
import com.geminibot.geminibot.entities.responses.restcontrollers.RegisterResponseEntity;
import com.geminibot.geminibot.exceptions.UserNotFoundException;
import com.geminibot.geminibot.repositories.UserRepository;
import com.geminibot.geminibot.services.JwtService;
import com.geminibot.geminibot.services.MailService;
import com.geminibot.geminibot.services.PasswordEncoderService;
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
    private final PasswordEncoderService passwordEncoderService;
    private final JwtService jwtService;

    public UserController(UserRepository userRepository, MailService mailService, PasswordEncoderService passwordEncoderService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.passwordEncoderService = passwordEncoderService;
        this.jwtService = jwtService;
    }

    @GetMapping("/user/activate/{activationToken}")
    public void getUserActivate(@PathVariable("activationToken") UUID activationToken, HttpServletResponse httpServletResponse) {
        User possibleUser = userRepository.findByEmailVerificationToken(activationToken);

        try {
            if (possibleUser == null) {
                httpServletResponse.sendRedirect("bad token url on react app");
                return;
            }
        } catch (Exception exception) {
            // TODO: handle this error more gracefully
            System.out.println("Something happened");
        }

        assert possibleUser != null; // this should not raise an exception, just making the compiler happy
        possibleUser.setEmailVerified(true);
        User user = userRepository.save(possibleUser);

       try {
            httpServletResponse.sendRedirect("render login page with message saying user has been activated");
       } catch (Exception exception) {
           // TODO: handle this error more gracefully
           System.out.println("Something happened");
       }
    }

    @PostMapping("/user/register")
    @ResponseBody
    public ResponseEntity<RegisterResponse> postUserRegister(@RequestBody RegisterDTO registerDTO) {
        User possibleUser = userRepository.findByEmail(registerDTO.getEmail());

        if (possibleUser != null) {
            RegisterResponse registerResponse = new RegisterResponse("Email is already registered", true, new RegisterResponseEntity(registerDTO));
            System.out.println(registerResponse.getEntity());
            return new ResponseEntity<RegisterResponse>(registerResponse, HttpStatus.valueOf(409));
        }

        try {
            User newUser = passwordEncoderService.createNewUserAndEncodePasswordAndSave(registerDTO);
            System.out.println(newUser);
            RegisterResponse registerResponse = new RegisterResponse("User with email: " + registerDTO.getEmail() + " has been created, Please check your email to activate!", false, new RegisterResponseEntity(newUser));
            mailService.sendActivationEmail(newUser);
            return new ResponseEntity<RegisterResponse>(registerResponse, HttpStatus.valueOf(200));

        } catch (AssertionError e) {
            RegisterResponse registerResponse = new RegisterResponse("Passwords do not match", true, new RegisterResponseEntity(registerDTO));
            return new ResponseEntity<RegisterResponse>(registerResponse, HttpStatus.valueOf(409));

        } catch (MessagingException messagingException) {
            // TODO: integrate something like rollbar here
            System.out.println(messagingException.getMessage());
            System.out.println("For some reason email could not be sent");

            RegisterResponse registerResponse = new RegisterResponse("Internal error, please try again later, and contact an administrator", true, new RegisterResponseEntity(registerDTO));
            return new ResponseEntity<RegisterResponse>(registerResponse, HttpStatus.valueOf(500));

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            // TODO: integrate something like rollbar here
            RegisterResponse registerResponse = new RegisterResponse("Internal error, please try again later, and contact an administrator", true, new RegisterResponseEntity(registerDTO));

            return new ResponseEntity<RegisterResponse>(registerResponse, HttpStatus.valueOf(500));
        }
    }


    @PostMapping("/user/login")
    @ResponseBody
    public ResponseEntity<LoginResponse> postUserLogin(@RequestBody LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String plainTextPassword = loginDTO.getPlainTextPassword();

        try {
            User authenticatedUser = passwordEncoderService.findUserByEmailAndVerifyPassword(email, plainTextPassword);

            String jsonWebToken = jwtService.createToken(authenticatedUser.getEmail(), authenticatedUser.getId());

            LoginResponseEntity loginResponseEntity = new LoginResponseEntity(loginDTO, jsonWebToken);
            LoginResponse loginResponse = new LoginResponse("Success, please add json web token to headers in all subsequent requests", false, loginResponseEntity);

            return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } catch(Exception | UserNotFoundException e) {
            LoginResponse loginResponse = new LoginResponse("Email or password does not exist", true, loginDTO);
            return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.UNAUTHORIZED);
        }
    }
}
