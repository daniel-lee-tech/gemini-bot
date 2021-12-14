package com.geminibot.geminibot.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geminibot.geminibot.consumers.constants.GeminiUrlsEnum;
import com.geminibot.geminibot.entities.responses.gemini.v1.Trade;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import com.geminibot.geminibot.entities.responses.gemini.v1.TradesResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class TradesGeminiConsumer extends GeminiConsumer {
    public TradesGeminiConsumer(String publicKey, String privateKey) {
        super(publicKey, privateKey);
    }

    public TradesResponse getAllTrades() throws InvalidKeyException, HttpClientErrorException, InterruptedException {
        TradesResponse tradesResponse = getFirst500Trades();

        var tradesFromQuery = tradesResponse.getTrades();
        System.out.println(tradesFromQuery);

        // reasoning: 500 trades is a the max per call
        // so if call has 500 trades,
        // there is a high chance there is more trades to query
        while (tradesFromQuery.size() == 500) {
            sleep(5000);
            ObjectMapper objectMapper = new ObjectMapper();
            var latestTrade = (Trade)tradesFromQuery.get(0);

            BigInteger latestTimestamp = latestTrade.getTimestampms();

            TradesResponse next500Trades = get500TradesFromTimestamp(latestTimestamp.add(BigInteger.ONE));
            tradesFromQuery = next500Trades.getTrades();
            tradesResponse.appendTrades(tradesFromQuery);
        }

        System.out.println(tradesResponse);
        return tradesResponse;
    }

    public TradesResponse getFirst500Trades() throws InvalidKeyException, HttpClientErrorException {
        BigInteger createdDate = new AccountGeminiConsumer(this).getAccountCreationDate();
        return this.get500TradesFromTimestamp(createdDate);
    }

    public TradesResponse getMostRecent500Trades() throws InvalidKeyException, HttpClientErrorException {
        var payload = new HashMap<String, Object>();
        payload.put("limit_trades", 500);
        return this.call(payload, new TradesResponse());
    }

    public TradesResponse get500TradesFromTimestamp(BigInteger timestamp) throws InvalidKeyException, HttpClientErrorException {
        var payload = new HashMap<String, Object>();
        payload.put("limit_trades", 500);
        payload.put("timestamp", timestamp);
        return this.call(payload, new TradesResponse());
    }

    private TradesResponse call(HashMap<String, Object> payload, TradesResponse tradesResponse) throws InvalidKeyException {
        var payloadInitializer = this.initiatePayload(payload, GeminiUrlsEnum.MY_TRADES_URL);
        var encoded_payload_map = encode_payload(payloadInitializer.getPayload());

        assert encoded_payload_map != null;
        var header = createHeader(encoded_payload_map);

        ResponseEntity<ArrayList<Trade>> trades = restTemplate.exchange(payloadInitializer.getFullUrl(), HttpMethod.POST, new HttpEntity<Object>(header), new ParameterizedTypeReference<ArrayList<Trade>>() {
        });
        tradesResponse.setTrades(trades.getBody());
        return tradesResponse;
    }
}
