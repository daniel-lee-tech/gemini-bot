package com.geminibot.geminibot.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geminibot.geminibot.annotations.RequiresAuthorizationToken;
import com.geminibot.geminibot.consumers.TradesGeminiConsumer;
import com.geminibot.geminibot.entities.postgres.Trade;
import com.geminibot.geminibot.entities.postgres.User;
import com.geminibot.geminibot.entities.responses.gemini.v1.GeminiTradesResponse;
import com.geminibot.geminibot.entities.responses.restcontrollers.ErrorResponse;
import com.geminibot.geminibot.entities.responses.restcontrollers.TradesResponse;
import com.geminibot.geminibot.entities.serializers.JwtAuthPayload;
import com.geminibot.geminibot.entities.utils.AllKeyTypes;
import com.geminibot.geminibot.repositories.TradeRepository;
import com.geminibot.geminibot.repositories.UserRepository;
import com.geminibot.geminibot.services.GeminiKeyPermissionsService;
import com.geminibot.geminibot.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class TradesController {
    @Autowired
    GeminiKeyPermissionsService geminiKeyPermissionsService;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TradeRepository tradeRepository;

    @RequiresAuthorizationToken
    @GetMapping("/trades/import")
    public ResponseEntity<Object> getTradesImport(HttpServletRequest httpServletRequest) throws JsonProcessingException, InvalidKeyException, InterruptedException {
        JwtAuthPayload jwtAuthPayload = jwtService.serializeDecodedJwtPayload(httpServletRequest);
        Optional<User> user = userRepository.findById(jwtAuthPayload.getUserId());

        var neededKeys = AllKeyTypes.get();

        if (user.isPresent()) {
            var apiKey = geminiKeyPermissionsService.checkIfUserHasProperKeys(AllKeyTypes.get(), user.get());
            if (apiKey != null) {
                TradesResponse tradesResponse = new TradesResponse();
                tradesResponse.setEntity(new ArrayList<>());

                Optional<Trade> latestTrade = tradeRepository.findFirstByUserOrderByTimestampmsDesc(user.get());

                var consumer = new TradesGeminiConsumer(apiKey.getPublicKey(), apiKey.getSecretKey());

                GeminiTradesResponse response;

                if (latestTrade.isPresent()) {
                    var latestTimestamp = latestTrade.get().getTimestampms();
                    response = consumer.getAllTradesSinceTimestamp(latestTimestamp.add(BigInteger.valueOf(1)));
                } else {
                    response = consumer.getAllTradesForAccount();
                }

                for (var trade : response.getTrades()) {
                    var savedTrade = tradeRepository.save(new Trade(trade, user.get()));
                    tradesResponse.getEntity().add(savedTrade);
                }

                return new ResponseEntity<>(tradesResponse, HttpStatus.OK);
            } else {
                // error response
                var error = new ErrorResponse("Account does not have needed API keys, please add these api key(s) " + neededKeys, true, null);

                return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
            }
        } else {
            var error = new ErrorResponse("User does not exist ", true, null);

            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/trades")
    @RequiresAuthorizationToken
    public ResponseEntity<Object> getTrades(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        JwtAuthPayload jwtAuthPayload = jwtService.serializeDecodedJwtPayload(httpServletRequest);
        Optional<User> user = userRepository.findById(jwtAuthPayload.getUserId());

        if (user.isPresent()) {
            TradesResponse tradesResponse = new TradesResponse();
            tradesResponse.setEntity(tradeRepository.findAllByUser(user.get()));
            return new ResponseEntity<>(tradesResponse, HttpStatus.OK);
        } else {
            var error = new ErrorResponse("User does not exist ", true, null);
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
    }
}
