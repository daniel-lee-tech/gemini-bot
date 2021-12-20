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
import java.util.Objects;

import static java.lang.Thread.sleep;

public class TradesGeminiConsumer extends GeminiConsumer {
    public TradesGeminiConsumer(String publicKey, String privateKey) {
        super(publicKey, privateKey);
    }

    public GeminiTradesResponse getAllTradesSinceTimestamp(BigInteger ts) throws InvalidKeyException, InterruptedException {
        GeminiTradesResponse tradesResponse = this.get500TradesFromTimestamp(ts);

        var tradesFromQuery = tradesResponse.getTrades();

        var timestamps = new ArrayList<BigInteger>();
        timestamps.add(null);
        timestamps.add(BigInteger.valueOf(0));
        BigInteger latestTimestamp = null;


        while (tradesFromQuery.size() != 0 && !Objects.equals(timestamps.get(timestamps.size() - 1), latestTimestamp) && !Objects.equals(timestamps.get(timestamps.size() - 2), latestTimestamp)) {
            System.out.println("sleeping till i fetch more!");
            sleep(5000);

            var latestTrade = (Trade)tradesFromQuery.get(0);

            latestTimestamp = latestTrade.getTimestampms();
            timestamps.add(latestTimestamp);

            System.out.println("LATEST TIMESTAMP=" + latestTimestamp);

            GeminiTradesResponse next500Trades = get500TradesFromTimestamp(latestTimestamp);
            tradesFromQuery = next500Trades.getTrades();
            tradesResponse.appendTrades(tradesFromQuery);
        }

        return tradesResponse;
    }

    public GeminiTradesResponse getAllTradesForAccount() throws InvalidKeyException, HttpClientErrorException, InterruptedException {
        GeminiTradesResponse geminiTradesResponse = getFirst500Trades();

        var tradesFromQuery = geminiTradesResponse.getTrades();

        var timestamps = new ArrayList<BigInteger>();
        timestamps.add(null);
        timestamps.add(BigInteger.valueOf(0));
        BigInteger latestTimestamp = null;

        while (!Objects.equals(timestamps.get(timestamps.size() - 1), latestTimestamp) || !Objects.equals(timestamps.get(timestamps.size() - 2), latestTimestamp)) {
            sleep(5000);
            ObjectMapper objectMapper = new ObjectMapper();
            var latestTrade = (Trade)tradesFromQuery.get(0);

            latestTimestamp = latestTrade.getTimestampms();
            timestamps.add(latestTimestamp);
            System.out.println("LATEST TIMESTAMP=" + latestTimestamp);


            GeminiTradesResponse next500Trades = get500TradesFromTimestamp(latestTimestamp);
            tradesFromQuery = next500Trades.getTrades();
            geminiTradesResponse.appendTrades(tradesFromQuery);
        }

        System.out.println(geminiTradesResponse);
        return geminiTradesResponse;
    }

    public GeminiTradesResponse getFirst500Trades() throws InvalidKeyException, HttpClientErrorException {
        return this.get500TradesFromTimestamp(BigInteger.valueOf(0));
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
