package com.geminibot.geminibot.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.geminibot.geminibot.datatransferobjects.RegisterDTO;
import com.geminibot.geminibot.entities.postgres.User;
import com.geminibot.geminibot.exceptions.UserNotFoundException;
import com.geminibot.geminibot.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Service
public class PasswordEncoderService {
    private final String seed = "some seed to render Secure Random";
    private final byte[] seedBytes = seed.getBytes(StandardCharsets.UTF_8);
    private final String salt = "1234567812345678";
    private final byte[] saltBytes = salt.getBytes(StandardCharsets.UTF_8);
    private final int cost = 5;
    private final BCrypt.Hasher bCryptHasher;
    private final UserRepository userRepository;

    public PasswordEncoderService( UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptHasher = BCrypt.with(new SecureRandom(seedBytes));
    }

    public User createNewUserAndEncodePasswordAndSave(RegisterDTO registerDTO) {
        User newUser = new User();

        assert registerDTO.getPlainTextPassword().equals(registerDTO.getPlainTextPasswordConfirmation());

        String passwordDigest = generatePasswordDigest(registerDTO.getPlainTextPassword());

        newUser.setPasswordDigest(passwordDigest);

        newUser.setEmail(registerDTO.getEmail());

        return userRepository.save(newUser);
    }

    public User findUserByEmailAndVerifyPassword(String email, String plainTextPassword) throws UserNotFoundException, AssertionError {
        User possibleUser = userRepository.findByEmail(email);

        if (possibleUser == null) {
            throw new UserNotFoundException();
        }

        String passwordDigestFromDatabase = possibleUser.getPasswordDigest();
        String passwordDigestFromInput = generatePasswordDigest(plainTextPassword);

        assert passwordDigestFromInput.equals(passwordDigestFromDatabase);

        return possibleUser;
    }

    private String generatePasswordDigest(String plainTextPassword) {
        var password_byte_digest = bCryptHasher.hash(cost, saltBytes, plainTextPassword.getBytes(StandardCharsets.UTF_8));
        return new String(password_byte_digest);
    }
}
