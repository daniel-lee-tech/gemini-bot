package com.geminibot.geminibot.entities.responses.entities;

import java.math.BigDecimal;

public class NetTransfersCurrencyEntity {
    private String currency;
    private BigDecimal netTransfers;

    public NetTransfersCurrencyEntity(String currency, BigDecimal transfers) {
        this.currency = currency;
        this.netTransfers = transfers;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getNetTransfers() {
        return netTransfers;
    }

    public void setNetTransfers(BigDecimal transfers) {
        this.netTransfers = transfers;
    }

    @Override
    public String toString() {
        return "NetWorthCurrencyEntity{" +
                "currency='" + currency + '\'' +
                ", transfers=" + netTransfers +
                '}';
    }
}
