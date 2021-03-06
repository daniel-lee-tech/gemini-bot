package com.geminibot.geminibot.entities.responses.restcontrollers;

import com.geminibot.geminibot.entities.responses.entities.RegisterResponseEntity;

public class RegisterResponse extends RestControllerResponse<RegisterResponseEntity> {
    public RegisterResponse(String message, boolean error, RegisterResponseEntity entity) {
        this.setMessage(message);
        this.setError(error);
        this.setEntity(entity);
    }

    @Override
    public void setEntity(RegisterResponseEntity entity) {
        this.entity = entity;
    }
}
