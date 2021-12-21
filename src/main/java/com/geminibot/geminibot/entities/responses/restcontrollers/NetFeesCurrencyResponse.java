package com.geminibot.geminibot.entities.responses.restcontrollers;

import com.geminibot.geminibot.entities.responses.entities.NetFeesCurrencyEntity;
import com.geminibot.geminibot.entities.responses.entities.NetWorthCurrencyEntity;

import java.util.ArrayList;

public class NetFeesCurrencyResponse extends RestControllerResponse<ArrayList<NetFeesCurrencyEntity>>{
    public NetFeesCurrencyResponse() {
        this.entity = new ArrayList<>();
    }
}
