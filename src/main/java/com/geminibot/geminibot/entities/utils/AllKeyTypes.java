package com.geminibot.geminibot.entities.utils;

import com.geminibot.geminibot.entities.constants.ApiKeyType;

public class AllKeyTypes {
    public static ApiKeyType[] get() {
        var allKeys = new ApiKeyType[4];
        allKeys[0] = ApiKeyType.ADMINISTRATOR;
        allKeys[1] = ApiKeyType.AUDITOR;
        allKeys[2] = ApiKeyType.MANAGER;
        allKeys[3] =  ApiKeyType.TRADER;
        return allKeys;
    }
}
