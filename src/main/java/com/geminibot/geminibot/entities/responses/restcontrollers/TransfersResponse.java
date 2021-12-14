package com.geminibot.geminibot.entities.responses.restcontrollers;

import com.geminibot.geminibot.entities.postgres.Transfer;
import com.geminibot.geminibot.entities.responses.entities.RegisterResponseEntity;

import java.util.ArrayList;

public class TransfersResponse extends RestControllerResponse<ArrayList<Transfer>> {
    public TransfersResponse() {
        this.entity = new ArrayList<Transfer>();
    }

    @Override
    public void setEntity(ArrayList<Transfer> entity) {
        this.entity = entity;
    }
}
