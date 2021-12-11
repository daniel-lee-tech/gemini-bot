package com.geminibot.geminibot.entities.responses.restcontrollers;

import com.geminibot.geminibot.datatransferobjects.RegisterDTO;
import com.geminibot.geminibot.entities.postgres.User;

public class RegisterResponseEntity {
    private Long id;

    private String email;
    private boolean emailVerified;

    public RegisterResponseEntity(User newUser) {
        this.setEmail(newUser.getEmail());
        this.setId(newUser.getId());
        this.setEmailVerified(newUser.isEmailVerified());
    }

    public RegisterResponseEntity(RegisterDTO registerDTO) {
        this.setId(0L);
        this.setEmail(registerDTO.getEmail());
        this.setEmailVerified(false);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }


    @Override
    public String toString() {
        return "RegisterResponseEntity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", emailVerified=" + emailVerified +
                '}';
    }
}
