package com.geminibot.geminibot.entities.postgres;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity
@Table(name = "trades")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal price;
    private BigDecimal amount;
    private BigInteger timestamp;
    private BigInteger timestampms;
    private String type;
    private boolean aggressor;
    private String feeCurrency;
    private BigDecimal feeAmount;
    @Column(unique = true)
    private BigInteger tid;
    private BigInteger orderId;
    private String exchange;
    private boolean isAuctionFill;
    private boolean isClearingFill;
    private String symbol;

    public Trade(com.geminibot.geminibot.entities.responses.gemini.v1.Trade trade, User user) {
        this.type = trade.getType();
        this.price = trade.getPrice();
        this.timestampms = trade.getTimestampms();
        this.timestamp = trade.getTimestamp();
        this.amount = trade.getAmount();
        this.aggressor = trade.isAggressor();
        this.feeCurrency = trade.getFee_currency();
        this.feeAmount = trade.getFee_amount();
        this.tid = trade.getTid();
        this.orderId = trade.getOrder_id();
        this.exchange = trade.getExchange();
        this.isAuctionFill = trade.isIs_auction_fill();
        this.isClearingFill = trade.isIs_clearing_fill();
        this.symbol = trade.getSymbol();

        this.user = user;
    }

    public Trade() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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

    public String getFeeCurrency() {
        return feeCurrency;
    }

    public void setFeeCurrency(String feeCurrency) {
        this.feeCurrency = feeCurrency;
    }

    public BigDecimal getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(BigDecimal feeAmount) {
        this.feeAmount = feeAmount;
    }

    public BigInteger getTid() {
        return tid;
    }

    public void setTid(BigInteger tid) {
        this.tid = tid;
    }

    public BigInteger getOrderId() {
        return orderId;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public boolean isAuctionFill() {
        return isAuctionFill;
    }

    public void setAuctionFill(boolean auctionFill) {
        isAuctionFill = auctionFill;
    }

    public boolean isClearingFill() {
        return isClearingFill;
    }

    public void setClearingFill(boolean clearingFill) {
        isClearingFill = clearingFill;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
