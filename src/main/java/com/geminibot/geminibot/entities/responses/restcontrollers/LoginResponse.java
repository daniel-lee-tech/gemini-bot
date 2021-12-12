package com.geminibot.geminibot.entities.responses.restcontrollers;

import com.geminibot.geminibot.datatransferobjects.LoginDTO;

public class LoginResponse extends RestControllerResponse<LoginResponseEntity>{


    public LoginResponse(String message, boolean error, LoginResponseEntity entity) {
        super();
        this.setMessage(message);
        this.setError(error);
        this.setEntity(entity);
    }

    public LoginResponse(String message, boolean error, LoginDTO loginDTO) {
        this.message = message;
        this.error = error;
        this.setEntity(new LoginResponseEntity(loginDTO));
    }

    @Override
    public void setEntity(LoginResponseEntity entity) {
        this.entity = entity;
    }
}
