package com.geminibot.geminibot.entities.responses.restcontrollers;

import com.geminibot.geminibot.entities.responses.entities.NetFeesCurrencyEntity;
import com.geminibot.geminibot.entities.responses.entities.NetTransfersCurrencyEntity;

import java.util.ArrayList;

public class NetTransfersCurrencyResponse extends RestControllerResponse<ArrayList<NetTransfersCurrencyEntity>>{
    public NetTransfersCurrencyResponse() {
        this.entity = new ArrayList<>();
    }
}
