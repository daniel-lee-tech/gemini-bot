package com.geminibot.geminibot.services;

import com.geminibot.geminibot.entities.constants.ApiKeyType;
import com.geminibot.geminibot.entities.postgres.ApiKey;
import com.geminibot.geminibot.entities.postgres.User;
import com.geminibot.geminibot.repositories.ApiKeyRepository;
import com.geminibot.geminibot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GeminiKeyPermissionsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ApiKeyRepository apiKeyRepository;

    public ApiKey checkIfUserHasProperKeys(ApiKeyType[] keysNeeded, User user) {

        for (ApiKeyType key : keysNeeded) {
            var apiKey = apiKeyRepository.findByUserAndType(user, key);
            if (apiKey.isPresent()) {
                return apiKey.get();
            }

        }

        return null;
    }
}
