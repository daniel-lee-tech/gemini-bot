package com.geminibot.geminibot.consumers;

import com.geminibot.geminibot.consumers.constants.GeminiUrlsEnum;
import com.geminibot.geminibot.entities.responses.gemini.AccountResponse;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.util.HashMap;

public class AccountGeminiConsumer extends GeminiConsumer {
    public AccountGeminiConsumer(String publicKey, String privateKey) {
        super(publicKey, privateKey);
    }

    public AccountGeminiConsumer(GeminiConsumer geminiConsumer) {
        super(geminiConsumer.getPublicKey(), geminiConsumer.getPrivateKey());
    }

    private AccountResponse call(HashMap<String, Object> payload, AccountResponse accountResponse) throws InvalidKeyException {
        var payloadInitializer = this.initiatePayload(payload, GeminiUrlsEnum.MY_ACCOUNT_URL);
        var encoded_payload_map = encode_payload(payloadInitializer.getPayload());

        assert encoded_payload_map != null;
        var header = createHeader(encoded_payload_map);

        AccountResponse response;
        response = restTemplate.postForObject(payloadInitializer.getFullUrl(), new HttpEntity<Object>(header), accountResponse.getClass());
        return response;
    }


    public AccountResponse getAccountInfo() throws InvalidKeyException, HttpClientErrorException {
        AccountResponse accountResponse;
        accountResponse = this.call(new HashMap<>(), new AccountResponse());
        return accountResponse;
    }


    public BigInteger getAccountCreationDate() throws InvalidKeyException, HttpClientErrorException {
        AccountResponse accountResponse = getAccountInfo();
        var account = accountResponse.getAccount();
        return account.getCreated();
    }

}
