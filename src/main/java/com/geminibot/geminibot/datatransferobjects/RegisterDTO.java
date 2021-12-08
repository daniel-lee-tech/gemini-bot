package com.geminibot.geminibot.datatransferobjects;

public class RegisterDTO {
    private String email;
    private String plainTextPassword;
    private String plainTextPasswordConfirmation;

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

    public String getPlainTextPasswordConfirmation() {
        return plainTextPasswordConfirmation;
    }

    public void setPlainTextPasswordConfirmation(String plainTextPasswordConfirmation) {
        this.plainTextPasswordConfirmation = plainTextPasswordConfirmation;
    }


}
