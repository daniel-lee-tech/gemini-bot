package com.geminibot.geminibot.entities.responses.gemini.v1;

import java.math.BigInteger;

public class Account {
    private String accountName;
    private String shortName;
    private String type;
    private BigInteger created;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigInteger getCreated() {
        return created;
    }

    public void setCreated(BigInteger created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "accountName='" + accountName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", type='" + type + '\'' +
                ", created=" + created +
                '}';
    }
}
