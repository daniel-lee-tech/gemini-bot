package com.geminibot.geminibot.entities.responses.restcontrollers;

import com.geminibot.geminibot.entities.responses.entities.NetWorthCurrencyEntity;

import java.math.BigDecimal;
import java.util.ArrayList;

public class NetWorthCurrencyResponse extends RestControllerResponse<ArrayList<NetWorthCurrencyEntity>>{
    public NetWorthCurrencyResponse() {
        this.entity = new ArrayList<>();
    }
}
