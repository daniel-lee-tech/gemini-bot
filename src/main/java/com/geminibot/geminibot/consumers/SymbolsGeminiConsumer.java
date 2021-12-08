package com.geminibot.geminibot.consumers;

import com.geminibot.geminibot.consumers.constants.GeminiUrlsEnum;
import com.geminibot.geminibot.entities.responses.gemini.v1.SymbolResponse;

import java.util.ArrayList;

public class SymbolsGeminiConsumer extends GeminiConsumer {

    public SymbolsGeminiConsumer(String publicKey, String privateKey) {
        super("NO KEYS NEEDED", "NO KEYS NEEDED");
    }

    public String[] getSymbolsList() {
        var fullUrl = geminiUrls.get(GeminiUrlsEnum.URL_PREFIX) + geminiUrls.get(GeminiUrlsEnum.SYMBOLS_URL);
        return restTemplate.getForObject(fullUrl, String[].class);
    }

    public ArrayList<SymbolResponse> getSymbolsListWithDetails() {
        ArrayList<SymbolResponse> symbolsList = new ArrayList<>();

        for (String symbol: this.getSymbolsList()) {

            var fullUrl = geminiUrls.get(GeminiUrlsEnum.URL_PREFIX) + geminiUrls.get(GeminiUrlsEnum.SYMBOL_DETAILS_URL) + "/" + symbol;

            symbolsList.add(restTemplate.getForObject(fullUrl, SymbolResponse.class));
        }

        return symbolsList;
    }
}
