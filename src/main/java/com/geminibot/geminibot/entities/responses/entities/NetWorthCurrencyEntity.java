package com.geminibot.geminibot.entities.responses.entities;

import com.geminibot.geminibot.datatransferobjects.LoginDTO;
import com.geminibot.geminibot.entities.postgres.User;

import java.math.BigDecimal;

public class NetWorthCurrencyEntity {
    private String currency;
    private BigDecimal netWorth;

    public NetWorthCurrencyEntity(String currency, BigDecimal netWorth) {
        this.currency = currency;
        this.netWorth = netWorth;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(BigDecimal netWorth) {
        this.netWorth = netWorth;
    }

    @Override
    public String toString() {
        return "NetWorthCurrencyEntity{" +
                "currency='" + currency + '\'' +
                ", netWorth=" + netWorth +
                '}';
    }
}
