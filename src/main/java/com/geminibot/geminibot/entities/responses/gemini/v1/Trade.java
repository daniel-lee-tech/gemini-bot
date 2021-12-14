package com.geminibot.geminibot.entities.responses.gemini.v1;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Trade {
    private BigDecimal price;
    private BigDecimal amount;
    private BigInteger timestamp;
    private BigInteger timestampms;
    private String type;
    private boolean aggressor;
    private String fee_currency;
    private BigDecimal fee_amount;
    private BigInteger tid;
    private BigInteger order_id;
    private String exchange;
    private boolean is_auction_fill;
    private boolean is_clearing_fill;
    private String symbol;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigInteger getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(BigInteger timestamp) {
        this.timestamp = timestamp;
    }

    public BigInteger getTimestampms() {
        return timestampms;
    }

    public void setTimestampms(BigInteger timestampms) {
        this.timestampms = timestampms;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isAggressor() {
        return aggressor;
    }

    public void setAggressor(boolean aggressor) {
        this.aggressor = aggressor;
    }

    public String getFee_currency() {
        return fee_currency;
    }

    public void setFee_currency(String fee_currency) {
        this.fee_currency = fee_currency;
    }

    public BigDecimal getFee_amount() {
        return fee_amount;
    }

    public void setFee_amount(BigDecimal fee_amount) {
        this.fee_amount = fee_amount;
    }

    public BigInteger getTid() {
        return tid;
    }

    public void setTid(BigInteger tid) {
        this.tid = tid;
    }

    public BigInteger getOrder_id() {
        return order_id;
    }

    public void setOrder_id(BigInteger order_id) {
        this.order_id = order_id;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public boolean isIs_auction_fill() {
        return is_auction_fill;
    }

    public void setIs_auction_fill(boolean is_auction_fill) {
        this.is_auction_fill = is_auction_fill;
    }

    public boolean isIs_clearing_fill() {
        return is_clearing_fill;
    }

    public void setIs_clearing_fill(boolean is_clearing_fill) {
        this.is_clearing_fill = is_clearing_fill;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "price=" + price +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", timestampms=" + timestampms +
                ", type='" + type + '\'' +
                ", aggressor=" + aggressor +
                ", fee_currency='" + fee_currency + '\'' +
                ", fee_amount=" + fee_amount +
                ", tid=" + tid +
                ", order_id=" + order_id +
                ", exchange='" + exchange + '\'' +
                ", is_auction_fill=" + is_auction_fill +
                ", is_clearing_fill=" + is_clearing_fill +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
