package com.geminibot.geminibot.entities.responses.entities;

import java.math.BigDecimal;

public class NetFeesCurrencyEntity {
    private String currency;
    private BigDecimal fees;

    public NetFeesCurrencyEntity(String currency, BigDecimal fees) {
        this.currency = currency;
        this.fees = fees;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getNetFees() {
        return fees;
    }

    public void setNetFees(BigDecimal fees) {
        this.fees = fees;
    }

    @Override
    public String toString() {
        return "NetWorthCurrencyEntity{" +
                "currency='" + currency + '\'' +
                ", fees=" + fees +
                '}';
    }
}
