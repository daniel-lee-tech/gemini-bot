package com.geminibot.geminibot.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geminibot.geminibot.entities.serializers.JwtAuthPayload;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Base64;

@Service
public class JwtService {
    //HMAC
    Algorithm algorithm = Algorithm.HMAC256("secret");

    public String createToken(String email, long userId) {
            int oneHour = 1000 * 60 * 60;
            String token = JWT.create()
                    .withClaim("email", email)
                    .withClaim("userId", userId)
                    .withClaim("exp", Instant.now().getEpochSecond() + oneHour) // expiration date
                    .withIssuer("gemini-bot")
                    .sign(algorithm);

        return token;
    }

    public boolean verifyToken(String token)  {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("gemini-bot")
                    .build(); //Reusable verifier instance
            DecodedJWT decodedJwt = verifier.verify(token);

            JwtAuthPayload jwtAuthPayload = serializeDecodedJwtPayload(decodedJwt);
            return !isExpiredToken(jwtAuthPayload);
        } catch (Exception exception) {
            return false;
        }
    }

    private JwtAuthPayload serializeDecodedJwtPayload(DecodedJWT decodedJwt) throws JsonProcessingException {
        String payload = new String(Base64.getDecoder().decode(decodedJwt.getPayload()));
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(payload, JwtAuthPayload.class);
    }

    public boolean isExpiredToken(JwtAuthPayload jwtAuthPayload) {
        return Instant.now().getEpochSecond() > jwtAuthPayload.getExp();
    }
}
