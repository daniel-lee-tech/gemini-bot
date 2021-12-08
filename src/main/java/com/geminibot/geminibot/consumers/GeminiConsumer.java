package com.geminibot.geminibot.consumers;

import com.geminibot.geminibot.consumers.constants.GeminiEnumMap;
import com.geminibot.geminibot.consumers.constants.GeminiUrlsEnum;
import com.geminibot.geminibot.consumers.utilities.PayloadInitializer;
import net.minidev.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.EnumMap;
import java.util.HashMap;


public abstract class GeminiConsumer {
    protected final EnumMap<GeminiUrlsEnum, String> geminiUrls = new GeminiEnumMap().getInstance();

    protected final String publicKey;
    protected final String privateKey;

    protected final RestTemplate restTemplate = new RestTemplateBuilder().build();

    public GeminiConsumer(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    protected String getPrivateKey() {
        return privateKey;
    }

    protected HttpHeaders createHeader(HashMap<String, String> encoded_payload_map) {
        HttpHeaders header = new HttpHeaders();

        header.set("Content-Type", "text/plain");
        header.set("Content-Length", "0");
        header.set("X-GEMINI-APIKEY", this.getPublicKey());
        header.set("X-GEMINI-PAYLOAD", encoded_payload_map.get("payload"));
        header.set("X-GEMINI-SIGNATURE", encoded_payload_map.get("signature"));
        header.set("Cache-Control", "no-cache");

        return header;
    }

    protected HashMap<String, String> encode_payload(HashMap<String, ?> payload) throws InvalidKeyException {
        //     # change secret key encoding to utf-8
        byte[] utf8_encoded_secret = this.getPrivateKey().getBytes(StandardCharsets.UTF_8);

        //     # convert payload to json and encode to utf-8
        String jsonPayload = new JSONObject(payload).toJSONString();
        byte[] utf8_encoded_json_payload = jsonPayload.getBytes(StandardCharsets.UTF_8);

        //     # convert encoded payload to base 64
        byte[] base64_encoded_payload = Base64.getEncoder().encode(utf8_encoded_json_payload);

        try {
            //    # sign b64_encoded_payload with your utf_8_encoded secret with the sha384 algorithm and convert it to a hexadecimal hash
            Mac mac = Mac.getInstance("HmacSHA384");
            mac.init(new SecretKeySpec(utf8_encoded_secret, "HmacSHA384"));
            var mac_encoded_payload = mac.doFinal(base64_encoded_payload);
            var signature = bytesToHex(mac_encoded_payload);

            var encoded_payload_map = new HashMap<String, String>();
            encoded_payload_map.put("signature", signature);
            encoded_payload_map.put("payload", Base64.getEncoder().encodeToString(utf8_encoded_json_payload));


            return encoded_payload_map;

        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            System.out.println("I shouldn't run because I made sure this algorithm exists, if i do run, make sure the algorithm argument is: HmacSHA384 ");
        }
        return null;
    }

    String create_nonce() {
        long unixTime = Instant.now().getEpochSecond();
        return Long.toString(unixTime * 1000);
    }

    protected String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0, v; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    protected PayloadInitializer initiatePayload(HashMap<String, Object> payload, GeminiUrlsEnum urlEnum) {
        var nonce = create_nonce();
        var urlPrefix = geminiUrls.get(GeminiUrlsEnum.URL_PREFIX);
        var urlSuffix = geminiUrls.get(urlEnum);
        var fullUrl = urlPrefix + urlSuffix;

        payload.put("request", urlSuffix);
        payload.put("nonce", nonce);

        return new PayloadInitializer(fullUrl, payload);
    }
}
