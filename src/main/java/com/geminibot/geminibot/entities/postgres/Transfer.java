package com.geminibot.geminibot.entities.postgres;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String type;
    private String advanced;
    private BigInteger timestampms;
    private BigInteger eid;
    private String currency;
    private double amount;
    private String method;

    public Transfer(com.geminibot.geminibot.entities.responses.gemini.v1.Transfer transfer, User user) {
        this.type = transfer.getType();
        this.advanced = transfer.getAdvanced();
        this.timestampms = transfer.getTimestampms();
        this.eid = transfer.getEid();
        this.currency = transfer.getCurrency();
        this.amount = transfer.getAmount();
        this.method = transfer.getMethod();
        this.user = user;
    }

    public Transfer() {

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
                "id=" + id +
                ", user=" + user +
                ", type='" + type + '\'' +
                ", advanced='" + advanced + '\'' +
                ", timestampms=" + timestampms +
                ", eid=" + eid +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", method='" + method + '\'' +
                '}';
    }
}
