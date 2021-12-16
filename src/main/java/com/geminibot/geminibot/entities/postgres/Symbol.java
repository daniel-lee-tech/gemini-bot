package com.geminibot.geminibot.entities.postgres;

import com.geminibot.geminibot.entities.responses.gemini.v1.SymbolResponse;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Symbol {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    public String symbol;
    public String baseCurrency;
    public String quoteCurrency;
    public int tickSize;
    public int quoteIncrement;
    public BigDecimal minOrderSize;
    public String status;
    public boolean wrapEnabled;

    public Symbol(SymbolResponse symbolResponse) {
        this.symbol = symbolResponse.getSymbol();
        this.baseCurrency = symbolResponse.getBase_currency();
        this.quoteCurrency = symbolResponse.getQuote_currency();
        this.tickSize = symbolResponse.getTick_size();
        this.quoteIncrement = symbolResponse.getQuote_increment();
        this.minOrderSize = symbolResponse.getMin_order_size();
        this.status = symbolResponse.getStatus();
        this.wrapEnabled = symbolResponse.isWrap_enabled();
    }


    public Symbol() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public int getTickSize() {
        return tickSize;
    }

    public void setTickSize(int tickSize) {
        this.tickSize = tickSize;
    }

    public int getQuoteIncrement() {
        return quoteIncrement;
    }

    public void setQuoteIncrement(int quoteIncrement) {
        this.quoteIncrement = quoteIncrement;
    }

    public BigDecimal getMinOrderSize() {
        return minOrderSize;
    }

    public void setMinOrderSize(BigDecimal minOrderSize) {
        this.minOrderSize = minOrderSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isWrapEnabled() {
        return wrapEnabled;
    }

    public void setWrapEnabled(boolean wrapEnabled) {
        this.wrapEnabled = wrapEnabled;
    }
}
