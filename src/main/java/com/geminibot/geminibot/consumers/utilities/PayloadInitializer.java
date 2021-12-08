package com.geminibot.geminibot.consumers.utilities;

import java.util.HashMap;

public class PayloadInitializer {
    private String fullUrl;
    private HashMap<String, Object> payload;

    public PayloadInitializer(String fullUrl, HashMap<String, Object> payload) {
        this.fullUrl = fullUrl;
        this.payload = payload;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public HashMap<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(HashMap<String, Object> payload) {
        this.payload = payload;
    }
}
