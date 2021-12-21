package com.geminibot.geminibot.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geminibot.geminibot.annotations.RequiresAuthorizationToken;
import com.geminibot.geminibot.entities.postgres.User;
import com.geminibot.geminibot.entities.responses.entities.NetFeesCurrencyEntity;
import com.geminibot.geminibot.entities.responses.entities.NetTransfersCurrencyEntity;
import com.geminibot.geminibot.entities.responses.entities.NetWorthCurrencyEntity;
import com.geminibot.geminibot.entities.responses.restcontrollers.*;
import com.geminibot.geminibot.entities.serializers.JwtAuthPayload;
import com.geminibot.geminibot.repositories.SymbolRepository;
import com.geminibot.geminibot.repositories.TradeRepository;
import com.geminibot.geminibot.repositories.TransferRepository;
import com.geminibot.geminibot.repositories.UserRepository;
import com.geminibot.geminibot.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.TreeMap;

@Controller
public class AnalysisController {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TradeRepository tradeRepository;
    @Autowired
    TransferRepository transferRepository;
    @Autowired
    SymbolRepository symbolRepository;

    @RequiresAuthorizationToken
    @GetMapping("/analysis/currencies")
    public ResponseEntity<Object> getAnalysisCurrencies(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        JwtAuthPayload jwtAuthPayload = jwtService.serializeDecodedJwtPayload(httpServletRequest);
        Optional<User> user = userRepository.findById(jwtAuthPayload.getUserId());

        if (user.isPresent()) {
            var userId = user.get().getId();
            TradesResponse tradesResponse = new TradesResponse();

            TreeMap<String, BigDecimal> totalAmounts = new TreeMap<>();

            var transferSumsByCurrency = transferRepository.getTransferRollupPerSymbol(userId);

            for (var transfer : transferSumsByCurrency) {
                var symbol = transfer.getCurrency();
                totalAmounts.put(symbol, transfer.getAmount());
            }

            var fees = tradeRepository.getAllFeesBySymbol(userId);

            for (var fee : fees) {
                var feeSymbol = fee.getFeeCurrency();
                var feeAmount = fee.getFeeAmount();
                totalAmounts.putIfAbsent(feeSymbol, feeAmount.multiply(BigDecimal.ZERO));
                // subtract all the fees paid for this symbol
                totalAmounts.put(feeSymbol, totalAmounts.get(feeSymbol).subtract(feeAmount));
            }



            var tradeSumsByCurrency = tradeRepository.getTradeAggregatePerSymbol(userId);

            for (var trade : tradeSumsByCurrency) {
                var base = trade.getBaseCurrency();
                var quote = trade.getQuoteCurrency();

                totalAmounts.putIfAbsent(base, BigDecimal.ZERO);
                totalAmounts.putIfAbsent(quote, BigDecimal.ZERO);

                totalAmounts.put(base, totalAmounts.get(base).add(trade.getBaseAmount()));
                totalAmounts.put(quote, totalAmounts.get(quote).add(trade.getQuoteAmount()));

            }

            var netWorthResponse = new NetWorthCurrencyResponse();

            totalAmounts.forEach((String key, BigDecimal value) -> netWorthResponse.getEntity().add(new NetWorthCurrencyEntity(key, value)));

            return new ResponseEntity<>(netWorthResponse, HttpStatus.OK);
        } else {
            var error = new ErrorResponse("User does not exist ", true, null);
//            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("Unauthorized, please login in", HttpStatus.UNAUTHORIZED);

    }


    @RequiresAuthorizationToken
    @GetMapping("/analysis/fees")
    public ResponseEntity<Object> getAnalysisFees(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        JwtAuthPayload jwtAuthPayload = jwtService.serializeDecodedJwtPayload(httpServletRequest);
        Optional<User> user = userRepository.findById(jwtAuthPayload.getUserId());

        if (user.isPresent()) {
            var userId = user.get().getId();
            TradesResponse tradesResponse = new TradesResponse();

            TreeMap<String, BigDecimal> totalFees = new TreeMap<>();


            var fees = tradeRepository.getAllFeesBySymbol(userId);

            for (var fee : fees) {
                var feeSymbol = fee.getFeeCurrency();
                var feeAmount = fee.getFeeAmount();
                totalFees.putIfAbsent(feeSymbol, BigDecimal.ZERO);
                // subtract all the fees paid for this symbol
                totalFees.put(feeSymbol, totalFees.get(feeSymbol).add(feeAmount));
            }

            var newFeesResponse = new NetFeesCurrencyResponse();

            totalFees.forEach((String key, BigDecimal value) -> newFeesResponse.getEntity().add(new NetFeesCurrencyEntity(key, value)));

            return new ResponseEntity<>(newFeesResponse, HttpStatus.OK);
        } else {
            var error = new ErrorResponse("User does not exist ", true, null);
//            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("Unauthorized, please login in", HttpStatus.UNAUTHORIZED);

    }


    @RequiresAuthorizationToken
    @GetMapping("/analysis/transfers")
    public ResponseEntity<Object> getAnalysisTransfers(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        JwtAuthPayload jwtAuthPayload = jwtService.serializeDecodedJwtPayload(httpServletRequest);
        Optional<User> user = userRepository.findById(jwtAuthPayload.getUserId());

        if (user.isPresent()) {
            var userId = user.get().getId();
            TradesResponse tradesResponse = new TradesResponse();

            TreeMap<String, BigDecimal> allTransfersMap = new TreeMap<>();

            var transfers = transferRepository.getTransferRollupPerSymbol(userId);

            for (var transfer : transfers) {
                var symbol = transfer.getCurrency();
                var amount = transfer.getAmount();
                allTransfersMap.putIfAbsent(symbol, BigDecimal.ZERO);
                // subtract all the fees paid for this symbol
                allTransfersMap.put(symbol, allTransfersMap.get(symbol).add(amount));
            }

            var newTransfersResponse = new NetTransfersCurrencyResponse();

            allTransfersMap.forEach((String key, BigDecimal value) -> newTransfersResponse.getEntity().add(new NetTransfersCurrencyEntity(key, value)));
            return new ResponseEntity<>(newTransfersResponse, HttpStatus.OK);
        } else {
            var error = new ErrorResponse("User does not exist ", true, null);
//            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("Unauthorized, please login in", HttpStatus.UNAUTHORIZED);

    }

}
