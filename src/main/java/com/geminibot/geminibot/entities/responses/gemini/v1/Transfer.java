package com.geminibot.geminibot.entities.responses.gemini.v1;

import java.math.BigInteger;

public class Transfer {
    private String type;
    private String advanced;
    private BigInteger timestampms;
    private BigInteger eid;
    private String currency;
    private double amount;
    private String method;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdvanced() {
        return advanced;
    }

    public void setAdvanced(String advanced) {
        this.advanced = advanced;
    }

    public BigInteger getTimestampms() {
        return timestampms;
    }

    public void setTimestampms(BigInteger timestampms) {
        this.timestampms = timestampms;
    }

    public BigInteger getEid() {
        return eid;
    }

    public void setEid(BigInteger eid) {
        this.eid = eid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    @Override
    public String toString() {
        return "Transfer{" +
                "type='" + type + '\'' +
                ", advanced='" + advanced + '\'' +
                ", timestampms=" + timestampms +
                ", eid=" + eid +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", method='" + method + '\'' +
                '}';
    }
}
