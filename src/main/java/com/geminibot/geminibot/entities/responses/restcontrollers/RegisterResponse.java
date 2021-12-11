package com.geminibot.geminibot.entities.responses.restcontrollers;

import com.geminibot.geminibot.datatransferobjects.RegisterDTO;
import com.geminibot.geminibot.entities.postgres.User;
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
