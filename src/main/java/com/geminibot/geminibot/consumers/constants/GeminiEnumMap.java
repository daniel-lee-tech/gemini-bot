package com.geminibot.geminibot.consumers.constants;

import java.util.EnumMap;

public final class GeminiEnumMap {
    private final EnumMap<GeminiUrlsEnum, String> gemininEnumMap;

    public GeminiEnumMap() {
        EnumMap<GeminiUrlsEnum, String> gemininEnumMap = new EnumMap<GeminiUrlsEnum, String>(GeminiUrlsEnum.class);

        gemininEnumMap.put(GeminiUrlsEnum.URL_PREFIX,"https://api.gemini.com");
        gemininEnumMap.put(GeminiUrlsEnum.MY_TRADES_URL, "/v1/mytrades");
        gemininEnumMap.put(GeminiUrlsEnum.MY_ACCOUNT_URL,"/v1/account");
        gemininEnumMap.put(GeminiUrlsEnum.TRANSFERS_URL, "/v1/transfers");
        gemininEnumMap.put(GeminiUrlsEnum.SYMBOLS_URL, "/v1/symbols");
        gemininEnumMap.put(GeminiUrlsEnum.SYMBOL_DETAILS_URL, "/v1/symbols/details");

        this.gemininEnumMap = gemininEnumMap;
    }

    public EnumMap<GeminiUrlsEnum, String> getInstance() {
        return this.gemininEnumMap;
    }
}
