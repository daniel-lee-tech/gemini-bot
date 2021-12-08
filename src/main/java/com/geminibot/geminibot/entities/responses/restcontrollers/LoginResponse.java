package com.geminibot.geminibot.entities.responses.restcontrollers;

import com.geminibot.geminibot.datatransferobjects.RegisterDTO;
import com.geminibot.geminibot.entities.postgres.User;

public class LoginResponse extends RestControllerResponse<User> {
    public LoginResponse(String message, boolean error, User entity) {
        this.setMessage(message);
        this.setError(error);
        this.setEntity(entity);
    }

    @Override
    public void setEntity(User entity) {
        entity.redactPassword();
        this.entity = entity;
    }

    public void setEntity(RegisterDTO entity) {
        entity.setPlainTextPassword("<REDACTED>");
        entity.setPlainTextPasswordConfirmation("<REDACTED>");
        this.entity = new User(entity);
    }
}
