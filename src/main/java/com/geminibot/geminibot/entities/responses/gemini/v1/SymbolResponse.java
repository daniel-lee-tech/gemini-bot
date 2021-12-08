package com.geminibot.geminibot.entities.responses.gemini.v1;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

public class SymbolResponse {
    public String symbol;
    public String base_currency;
    public String quote_currency;
    public int tick_size;
    public int quote_increment;
    public BigDecimal min_order_size;
    public String status;
    public boolean wrap_enabled;


    @JsonCreator
    public SymbolResponse(
            @JsonProperty("symbol") String symbol,
            @JsonProperty("base_currency") String base_currency,
            @JsonProperty("quote_currency") String quote_currency,
            @JsonProperty("tick_size") int tick_size,
            @JsonProperty("quote_increment") int quote_increment,
            @JsonProperty("min_order_size") BigDecimal min_order_size,
            @JsonProperty("status") String status,
            @JsonProperty("wrap_enabled") boolean wrap_enabled
    ) {
        this.symbol = symbol;
        this.base_currency = base_currency;
        this.quote_currency = quote_currency;
        this.tick_size = tick_size;
        this.quote_increment = quote_increment;
        this.min_order_size = min_order_size;
        this.status = status;
        this.wrap_enabled = wrap_enabled;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBase_currency() {
        return base_currency;
    }

    public void setBase_currency(String base_currency) {
        this.base_currency = base_currency;
    }

    public String getQuote_currency() {
        return quote_currency;
    }

    public void setQuote_currency(String quote_currency) {
        this.quote_currency = quote_currency;
    }

    public int getTick_size() {
        return tick_size;
    }

    public void setTick_size(int tick_size) {
        this.tick_size = tick_size;
    }

    public int getQuote_increment() {
        return quote_increment;
    }

    public void setQuote_increment(int quote_increment) {
        this.quote_increment = quote_increment;
    }

    public BigDecimal getMin_order_size() {
        return min_order_size;
    }

    public void setMin_order_size(BigDecimal min_order_size) {
        this.min_order_size = min_order_size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isWrap_enabled() {
        return wrap_enabled;
    }

    public void setWrap_enabled(boolean wrap_enabled) {
        this.wrap_enabled = wrap_enabled;
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "symbol='" + symbol + '\'' +
                ", base_currency='" + base_currency + '\'' +
                ", quote_currency='" + quote_currency + '\'' +
                ", tick_size=" + tick_size +
                ", quote_increment=" + quote_increment +
                ", min_order_size=" + min_order_size +
                ", status='" + status + '\'' +
                ", wrap_enabled=" + wrap_enabled +
                '}';
    }
}
