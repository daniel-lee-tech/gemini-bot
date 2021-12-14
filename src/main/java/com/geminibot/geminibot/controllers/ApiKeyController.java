package com.geminibot.geminibot.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geminibot.geminibot.annotations.RequiresAuthorizationToken;
import com.geminibot.geminibot.entities.postgres.ApiKey;
import com.geminibot.geminibot.entities.postgres.User;
import com.geminibot.geminibot.entities.responses.restcontrollers.ApiKeyResponse;
import com.geminibot.geminibot.entities.serializers.JwtAuthPayload;
import com.geminibot.geminibot.repositories.ApiKeyRepository;
import com.geminibot.geminibot.repositories.UserRepository;
import com.geminibot.geminibot.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class ApiKeyController {
    private final UserRepository userRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final JwtService jwtService;

    public ApiKeyController(UserRepository userRepository, JwtService jwtService, ApiKeyRepository apiKeyRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.apiKeyRepository = apiKeyRepository;
    }

    @GetMapping("/apikeys")
    @ResponseBody
    @RequiresAuthorizationToken
    public ResponseEntity<ApiKeyResponse> getUsersApiKeys(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        ApiKeyResponse apiKeyResponse = new ApiKeyResponse();

        JwtAuthPayload jwtAuthPayload = jwtService.serializeDecodedJwtPayload(httpServletRequest);
        Optional<User> user = userRepository.findById(jwtAuthPayload.getUserId());

        if (user.isPresent()) {
            var keys = apiKeyRepository.findByUser(user.get());
            apiKeyResponse.setEntity(keys);
            return new ResponseEntity<ApiKeyResponse>(apiKeyResponse, HttpStatus.OK);
        } else {
            apiKeyResponse.setError(true);
            apiKeyResponse.setMessage("User not found for this token.");
            return new ResponseEntity<ApiKeyResponse>(apiKeyResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/apikeys")
    @ResponseBody
    @RequiresAuthorizationToken
    public ResponseEntity<ApiKeyResponse> postUsersApiKeys(HttpServletRequest httpServletRequest, @RequestBody ApiKey apiKey) throws JsonProcessingException {
        ApiKeyResponse apiKeyResponse = new ApiKeyResponse();

        JwtAuthPayload jwtAuthPayload = jwtService.serializeDecodedJwtPayload(httpServletRequest);
        Optional<User> user = userRepository.findById(jwtAuthPayload.getUserId());

        if (user.isPresent()) {
            apiKey.setUser(user.get());
            var savedKey = apiKeyRepository.findByUserAndType(user.get(), apiKey.getType());

            try {
                savedKey.ifPresent(apiKeyRepository::delete);
                System.out.println(apiKey.getSecretKey());
                apiKeyRepository.save(apiKey);
            } catch (Exception exception) {
                apiKeyResponse.setError(true);
                apiKeyResponse.setMessage(apiKey.getType() + " Key is already present. Something went wrong, please contact support to get this issue fixed!");
                return new ResponseEntity<ApiKeyResponse>(apiKeyResponse, HttpStatus.UNPROCESSABLE_ENTITY);
            }
            var keys = apiKeyRepository.findByUser(user.get());
            apiKeyResponse.setEntity(keys);
            return new ResponseEntity<ApiKeyResponse>(apiKeyResponse, HttpStatus.OK);
        } else {
            apiKeyResponse.setError(true);
            apiKeyResponse.setMessage("User not found for this token.");
            return new ResponseEntity<ApiKeyResponse>(apiKeyResponse, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}
