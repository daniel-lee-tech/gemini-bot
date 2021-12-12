package com.geminibot.geminibot.entities.responses.restcontrollers;

import com.geminibot.geminibot.datatransferobjects.LoginDTO;
import com.geminibot.geminibot.entities.postgres.User;

public class LoginResponseEntity {
    private long id;
    private String email;
    private String jsonWebToken;

    public LoginResponseEntity(LoginDTO loginDTO) {
        this.email = loginDTO.getEmail();
    }

    public LoginResponseEntity(User authenticatedUser, String jsonWebToken) {
        this.id = authenticatedUser.getId();
        this.email = authenticatedUser.getEmail();
        this.jsonWebToken = jsonWebToken;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJsonWebToken() {
        return jsonWebToken;
    }

    public void setJsonWebToken(String jsonWebToken) {
        this.jsonWebToken = jsonWebToken;
    }

    @Override
    public String toString() {
        return "LoginResponseEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", jsonWebToken='" + jsonWebToken + '\'' +
                '}';
    }
}
