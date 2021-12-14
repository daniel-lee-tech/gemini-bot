package com.geminibot.geminibot.entities.responses.restcontrollers;

import com.geminibot.geminibot.entities.postgres.Trade;
import com.geminibot.geminibot.entities.postgres.Transfer;

import java.util.ArrayList;

public class TradesResponse extends RestControllerResponse<ArrayList<com.geminibot.geminibot.entities.postgres.Trade>> {
    public TradesResponse() {
        this.entity = new ArrayList<Trade>();
    }

    @Override
    public void setEntity(ArrayList<Trade> entity) {
        this.entity = entity;
    }
}
