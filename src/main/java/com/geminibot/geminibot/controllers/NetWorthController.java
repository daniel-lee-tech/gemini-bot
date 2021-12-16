package com.geminibot.geminibot.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.geminibot.geminibot.annotations.RequiresAuthorizationToken;
import com.geminibot.geminibot.entities.postgres.User;
import com.geminibot.geminibot.entities.responses.restcontrollers.ErrorResponse;
import com.geminibot.geminibot.entities.responses.restcontrollers.TradesResponse;
import com.geminibot.geminibot.entities.serializers.JwtAuthPayload;
import com.geminibot.geminibot.repositories.SymbolRepository;
import com.geminibot.geminibot.repositories.TradeRepository;
import com.geminibot.geminibot.repositories.TransferRepository;
import com.geminibot.geminibot.repositories.UserRepository;
import com.geminibot.geminibot.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

@Controller
public class NetWorthController {

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
    @GetMapping("/networth")
    public String getNetWorth(HttpServletRequest httpServletRequest) throws JsonProcessingException {
        JwtAuthPayload jwtAuthPayload = jwtService.serializeDecodedJwtPayload(httpServletRequest);
        Optional<User> user = userRepository.findById(jwtAuthPayload.getUserId());

        if (user.isPresent()) {
            TradesResponse tradesResponse = new TradesResponse();
            var trades = tradeRepository.findAllByUser(user.get());
            var transfers = transferRepository.findAllByUser(user.get());

            HashMap<String, BigDecimal> totalAmounts = new HashMap<>();

            for (var transfer: transfers ) {
                var symbol = transfer.getCurrency();
                totalAmounts.putIfAbsent(symbol, BigDecimal.valueOf(0));
                var symbolAmount = totalAmounts.get(symbol);
                if (transfer.getType().equals("Deposit")) {
                    totalAmounts.put(symbol, symbolAmount.add(BigDecimal.valueOf(transfer.getAmount())));
                } else {
                    totalAmounts.put(symbol, symbolAmount.subtract(BigDecimal.valueOf(transfer.getAmount())));
                }
            }

            System.out.println(totalAmounts);

            for (var trade: trades) {
                var fullSymbol = trade.getSymbol();
                var symbol = symbolRepository.findBySymbol(fullSymbol);
                if (symbol.isPresent()) {
                    var base = symbol.get().getBaseCurrency();
                    var quotePrice = trade.getPrice().multiply(trade.getAmount()).add(trade.getFeeAmount());
                    System.out.println(quotePrice);
                    var quote = symbol.get().getQuoteCurrency();
                    totalAmounts.putIfAbsent(base, BigDecimal.valueOf(0));
                    totalAmounts.putIfAbsent(quote, BigDecimal.valueOf(0));

                    if (trade.getType().equals("Buy")) {
                        totalAmounts.put(quote, totalAmounts.get(quote).subtract(quotePrice));
                        totalAmounts.put(base, totalAmounts.get(base).add(totalAmounts.get(base)));
                    } else {
                        totalAmounts.put(quote, totalAmounts.get(quote).add(quotePrice));
                        totalAmounts.put(base, totalAmounts.get(base).subtract(totalAmounts.get(base)));
                    }
                }
            }

            System.out.println(totalAmounts);



//            return new ResponseEntity<>(tradesResponse, HttpStatus.OK);
        } else {
            var error = new ErrorResponse("User does not exist ", true, null);
//            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }

        return "hello";
    }
}
