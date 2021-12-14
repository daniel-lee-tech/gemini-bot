package com.geminibot.geminibot.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geminibot.geminibot.consumers.constants.GeminiUrlsEnum;
import com.geminibot.geminibot.entities.responses.gemini.v1.Trade;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import com.geminibot.geminibot.entities.responses.gemini.v1.GeminiTradesResponse;
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

    public GeminiTradesResponse getAllTradesSinceTimestamp(BigInteger ts) throws InvalidKeyException, InterruptedException {
        GeminiTradesResponse tradesResponse = this.get500TradesFromTimestamp(ts);

        var tradesFromQuery = tradesResponse.getTrades();
        System.out.println(tradesFromQuery);

        // reasoning: 500 trades is the max per call
        // so if call has 500 trades,
        // there is a high chance there is more trades to query
        while (tradesFromQuery.size() == 500) {
            System.out.println("sleeping till i fetch more!");
            sleep(5000);
            var latestTrade = (Trade)tradesFromQuery.get(0);
            BigInteger latestTimeStamp = latestTrade.getTimestampms();

            GeminiTradesResponse next500Trades = get500TradesFromTimestamp(latestTimeStamp.add(BigInteger.ONE));
            tradesFromQuery = next500Trades.getTrades();
            tradesResponse.appendTrades(tradesFromQuery);
        }

        return tradesResponse;
    }

    public GeminiTradesResponse getAllTradesForAccount() throws InvalidKeyException, HttpClientErrorException, InterruptedException {
        GeminiTradesResponse geminiTradesResponse = getFirst500Trades();

        var tradesFromQuery = geminiTradesResponse.getTrades();
        System.out.println(tradesFromQuery);

        // reasoning: 500 trades is a the max per call
        // so if call has 500 trades,
        // there is a high chance there is more trades to query
        while (tradesFromQuery.size() == 500) {
            sleep(5000);
            ObjectMapper objectMapper = new ObjectMapper();
            var latestTrade = (Trade)tradesFromQuery.get(0);

            BigInteger latestTimestamp = latestTrade.getTimestampms();

            GeminiTradesResponse next500Trades = get500TradesFromTimestamp(latestTimestamp.add(BigInteger.ONE));
            tradesFromQuery = next500Trades.getTrades();
            geminiTradesResponse.appendTrades(tradesFromQuery);
        }

        System.out.println(geminiTradesResponse);
        return geminiTradesResponse;
    }

    public GeminiTradesResponse getFirst500Trades() throws InvalidKeyException, HttpClientErrorException {
        BigInteger createdDate = new AccountGeminiConsumer(this).getAccountCreationDate();
        return this.get500TradesFromTimestamp(createdDate);
    }

    public GeminiTradesResponse getMostRecent500Trades() throws InvalidKeyException, HttpClientErrorException {
        var payload = new HashMap<String, Object>();
        payload.put("limit_trades", 500);
        return this.call(payload, new GeminiTradesResponse());
    }

    public GeminiTradesResponse get500TradesFromTimestamp(BigInteger timestamp) throws InvalidKeyException, HttpClientErrorException {
        var payload = new HashMap<String, Object>();
        payload.put("limit_trades", 500);
        payload.put("timestamp", timestamp);
        return this.call(payload, new GeminiTradesResponse());
    }

    private GeminiTradesResponse call(HashMap<String, Object> payload, GeminiTradesResponse geminiTradesResponse) throws InvalidKeyException {
        var payloadInitializer = this.initiatePayload(payload, GeminiUrlsEnum.MY_TRADES_URL);
        var encoded_payload_map = encode_payload(payloadInitializer.getPayload());

        assert encoded_payload_map != null;
        var header = createHeader(encoded_payload_map);

        ResponseEntity<ArrayList<Trade>> trades = restTemplate.exchange(payloadInitializer.getFullUrl(), HttpMethod.POST, new HttpEntity<Object>(header), new ParameterizedTypeReference<ArrayList<Trade>>() {
        });
        geminiTradesResponse.setTrades(trades.getBody());
        return geminiTradesResponse;
    }
}
