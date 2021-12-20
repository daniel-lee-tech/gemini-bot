package com.geminibot.geminibot.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geminibot.geminibot.consumers.constants.GeminiUrlsEnum;
import com.geminibot.geminibot.entities.responses.gemini.v1.Trade;
import com.geminibot.geminibot.entities.responses.gemini.v1.Transfer;
import com.geminibot.geminibot.entities.responses.gemini.v1.GeminiTransfersResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class TransfersGeminiConsumer extends GeminiConsumer{
    public TransfersGeminiConsumer(String publicKey, String privateKey) {
        super(publicKey, privateKey);
    }

    public GeminiTransfersResponse getAllTransfersSinceTimestamp(BigInteger ts) throws InvalidKeyException, InterruptedException {
        GeminiTransfersResponse transfersResponse = this.get50TradesFromTimeStamp(ts);

        var transfersFromQuery = transfersResponse.getTransfers();

        var timestamps = new ArrayList<BigInteger>();
        timestamps.add(null);
        timestamps.add(BigInteger.valueOf(0));
        BigInteger latestTimestamp = null;

        while (!Objects.equals(timestamps.get(timestamps.size() - 1), latestTimestamp) || !Objects.equals(timestamps.get(timestamps.size() - 2), latestTimestamp)) {
            sleep(5000);
            var latestTransfer = (Transfer)transfersFromQuery.get(0);

            latestTimestamp = latestTransfer.getTimestampms();
            timestamps.add(latestTimestamp);
            System.out.println("LATEST TIMESTAMP=" + latestTimestamp);

            GeminiTransfersResponse next50Transfers = get50TradesFromTimeStamp(latestTimestamp);
            transfersFromQuery = next50Transfers.getTransfers();
            transfersResponse.appendTransfers(transfersFromQuery);
        }

        return transfersResponse;
    }


    public GeminiTransfersResponse getAllTransfersForAccount() throws InvalidKeyException, InterruptedException {
        GeminiTransfersResponse transfersResponse = this.getFirst50Transfers();

        var transfersFromQuery = transfersResponse.getTransfers();

        var timestamps = new ArrayList<BigInteger>();
        timestamps.add(null);
        timestamps.add(BigInteger.valueOf(0));
        BigInteger latestTimestamp = null;

        while (!Objects.equals(timestamps.get(timestamps.size() - 1), latestTimestamp) || !Objects.equals(timestamps.get(timestamps.size() - 2), latestTimestamp)) {
            sleep(5000);
            ObjectMapper objectMapper = new ObjectMapper();
            var latestTransfer = (Transfer)transfersFromQuery.get(0);

            latestTimestamp = latestTransfer.getTimestampms();
            timestamps.add(latestTimestamp);
            System.out.println("LATEST TIMESTAMP=" + latestTimestamp);


            GeminiTransfersResponse next50Transfers = get50TradesFromTimeStamp(latestTimestamp);
            transfersFromQuery = next50Transfers.getTransfers();
            transfersResponse.appendTransfers(transfersFromQuery);
        }

        System.out.println(transfersResponse);
        return transfersResponse;
    }

    public GeminiTransfersResponse getFirst50Transfers() throws InvalidKeyException {
        return this.get50TradesFromTimeStamp(BigInteger.valueOf(0));

    }

    private GeminiTransfersResponse get50TradesFromTimeStamp(BigInteger timestamp) throws InvalidKeyException {
        var payload = new HashMap<String, Object>();
        payload.put("limit_transfers", 50);
        payload.put("timestamp", timestamp);
        return this.call(payload, new GeminiTransfersResponse());
    }

    private GeminiTransfersResponse call(HashMap<String, Object> payload, GeminiTransfersResponse transfersResponse) throws InvalidKeyException {
        var payloadInitializer = this.initiatePayload(payload, GeminiUrlsEnum.TRANSFERS_URL);
        var encoded_payload_map = encode_payload(payloadInitializer.getPayload());

        assert encoded_payload_map != null;
        var header = createHeader(encoded_payload_map);

        ResponseEntity<ArrayList<Transfer>> transfers = restTemplate.exchange(payloadInitializer.getFullUrl(), HttpMethod.POST, new HttpEntity<Object>(header), new ParameterizedTypeReference<ArrayList<Transfer>>() {
        });
        transfersResponse.setTransfers(transfers.getBody());
        return transfersResponse;
    }
}
