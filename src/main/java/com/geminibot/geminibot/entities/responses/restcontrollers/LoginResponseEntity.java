package com.geminibot.geminibot.entities.responses.restcontrollers;

import com.geminibot.geminibot.datatransferobjects.LoginDTO;

public class LoginResponseEntity {
    private String email;
    private String plainTextPassword;
    private String jsonWebToken;

    public LoginResponseEntity(String email, String plainTextPassword, String jsonWebToken) {
        this.email = email;
        this.plainTextPassword = plainTextPassword;
        this.jsonWebToken = jsonWebToken;
    }

    public LoginResponseEntity(String email, String plainTextPassword) {
        this.email = email;
        this.plainTextPassword = plainTextPassword;
    }

    public LoginResponseEntity(LoginDTO loginDTO) {
        this.email = loginDTO.getEmail();
        this.plainTextPassword = loginDTO.getPlainTextPassword();
    }


    public LoginResponseEntity(LoginDTO loginDTO, String jsonWebToken) {
        this.email = loginDTO.getEmail();
        this.plainTextPassword = loginDTO.getPlainTextPassword();
        this.jsonWebToken = jsonWebToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlainTextPassword() {
        return plainTextPassword;
    }

    public void setPlainTextPassword(String plainTextPassword) {
        this.plainTextPassword = plainTextPassword;
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
                "email='" + email + '\'' +
                ", plainTextPassword='" + plainTextPassword + '\'' +
                ", jsonWebToken='" + jsonWebToken + '\'' +
                '}';
    }
}
