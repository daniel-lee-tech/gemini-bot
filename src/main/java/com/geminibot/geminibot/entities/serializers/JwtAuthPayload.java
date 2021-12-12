package com.geminibot.geminibot.entities.serializers;

public class JwtAuthPayload {
    private String iss;
    private long exp;
    private long userId;
    private String email;

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "JwtAuthPayload{" +
                "iss='" + iss + '\'' +
                ", exp=" + exp +
                ", userId=" + userId +
                ", email='" + email + '\'' +
                '}';
    }
}
