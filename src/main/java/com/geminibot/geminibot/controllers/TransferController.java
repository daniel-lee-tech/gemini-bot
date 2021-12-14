package com.geminibot.geminibot.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geminibot.geminibot.annotations.RequiresAuthorizationToken;
import com.geminibot.geminibot.consumers.TransfersGeminiConsumer;
import com.geminibot.geminibot.entities.postgres.Transfer;
import com.geminibot.geminibot.entities.postgres.User;
import com.geminibot.geminibot.entities.responses.gemini.v1.GeminiTransfersResponse;
import com.geminibot.geminibot.entities.responses.restcontrollers.ErrorResponse;
import com.geminibot.geminibot.entities.responses.restcontrollers.TransfersResponse;
import com.geminibot.geminibot.entities.serializers.JwtAuthPayload;
import com.geminibot.geminibot.entities.utils.AllKeyTypes;
import com.geminibot.geminibot.repositories.TransferRepository;
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
public class TransferController {
    @Autowired
    GeminiKeyPermissionsService geminiKeyPermissionsService;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TransferRepository transferRepository;

    @RequiresAuthorizationToken
    @GetMapping("/transfers/import")
    public ResponseEntity<Object> getTransfersImport(HttpServletRequest httpServletRequest) throws JsonProcessingException, InvalidKeyException, InterruptedException {
        JwtAuthPayload jwtAuthPayload = jwtService.serializeDecodedJwtPayload(httpServletRequest);
        Optional<User> user = userRepository.findById(jwtAuthPayload.getUserId());

        var neededKeys = AllKeyTypes.get();

        if (user.isPresent()) {
            var apiKey = geminiKeyPermissionsService.checkIfUserHasProperKeys(AllKeyTypes.get(), user.get());
            if (apiKey != null) {
                TransfersResponse transfersResponse = new TransfersResponse();
                transfersResponse.setEntity(new ArrayList<>());

                Optional<Transfer> latestTransfer = transferRepository.findFirstByUserOrderByTimestampmsDesc(user.get());

                var consumer = new TransfersGeminiConsumer(apiKey.getPublicKey(), apiKey.getSecretKey());

                GeminiTransfersResponse response;

                if (latestTransfer.isPresent()) {
                    var latestTimestamp = latestTransfer.get().getTimestampms();
                    response = consumer.getAllTransfersSinceTimestamp(latestTimestamp.add(BigInteger.valueOf(1)));
                } else {
                    response = consumer.getAllTransfersForAccount();
                }

                for (var transfer : response.getTransfers()) {
                    var savedTransfer = transferRepository.save(new Transfer(transfer, user.get()));
                    transfersResponse.getEntity().add(savedTransfer);
                }

                return new ResponseEntity<>(transfersResponse, HttpStatus.OK);
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


    @GetMapping("/transfers")
    @RequiresAuthorizationToken
    public ResponseEntity<Object> getTransfers(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        JwtAuthPayload jwtAuthPayload = jwtService.serializeDecodedJwtPayload(httpServletRequest);
        Optional<User> user = userRepository.findById(jwtAuthPayload.getUserId());

        if (user.isPresent()) {
            TransfersResponse transfersResponse = new TransfersResponse();
            transfersResponse.setEntity(transferRepository.findAllByUser(user.get()));
            return new ResponseEntity<>(transfersResponse, HttpStatus.OK);
        } else {
            var error = new ErrorResponse("User does not exist ", true, null);
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
    }
}
