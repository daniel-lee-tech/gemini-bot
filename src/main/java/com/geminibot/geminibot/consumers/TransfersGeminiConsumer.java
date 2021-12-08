package com.geminibot.geminibot.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geminibot.geminibot.consumers.constants.GeminiUrlsEnum;
import com.geminibot.geminibot.entities.responses.gemini.v1.Transfer;
import com.geminibot.geminibot.entities.responses.gemini.v1.TransfersResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;

public class TransfersGeminiConsumer extends GeminiConsumer{
    public TransfersGeminiConsumer(String publicKey, String privateKey) {
        super(publicKey, privateKey);
    }

    public TransfersResponse getAllTransfersForAccount() throws InvalidKeyException {
        TransfersResponse transfersResponse = this.getFirst50Transfers();

        var transfersFromQuery = transfersResponse.getTransfers();

        // reasoning: 50 trades is a the max per call
        // so if call has 50 trades,
        // there is a high chance there is more trades to query
        while (transfersFromQuery.size() == 50) {
            ObjectMapper objectMapper = new ObjectMapper();
            var latestTransfer = (Transfer)transfersFromQuery.get(0);
            BigInteger latestTimeStamp = latestTransfer.getTimestampms();

            TransfersResponse next50Transfers = get50TradesFromTimeStamp(latestTimeStamp.add(BigInteger.ONE));
            transfersFromQuery = next50Transfers.getTransfers();
            transfersResponse.appendTransfers(transfersFromQuery);
        }

        return transfersResponse;
    }

    public TransfersResponse getFirst50Transfers() throws InvalidKeyException {
        BigInteger createdDate = new AccountGeminiConsumer(this).getAccountCreationDate();
        return this.get50TradesFromTimeStamp(createdDate);

    }

    private TransfersResponse get50TradesFromTimeStamp(BigInteger timestamp) throws InvalidKeyException {
        var payload = new HashMap<String, Object>();
        payload.put("limit_transfers", 50);
        payload.put("timestamp", timestamp);
        return this.call(payload, new TransfersResponse());
    }

    private TransfersResponse call(HashMap<String, Object> payload, TransfersResponse transfersResponse) throws InvalidKeyException {
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
