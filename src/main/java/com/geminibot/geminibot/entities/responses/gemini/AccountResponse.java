package com.geminibot.geminibot.entities.responses.gemini;

import com.geminibot.geminibot.entities.responses.gemini.v1.Account;
import com.geminibot.geminibot.entities.responses.gemini.v1.GeminiResponse;

import java.util.ArrayList;

public class AccountResponse extends GeminiResponse {

    private Account account;
    private ArrayList<User> users;
    private String memo_reference_code;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getMemo_reference_code() {
        return memo_reference_code;
    }

    public void setMemo_reference_code(String memo_reference_code) {
        this.memo_reference_code = memo_reference_code;
    }

    @Override
    public String toString() {
        return "AccountResponse{" +
                "account=" + account +
                ", users=" + users +
                ", memo_reference_code='" + memo_reference_code + '\'' +
                '}';
    }
}


class User {
    private String name;
    private String lastSignIn;
    private String status;
    private String countryCode;
    private boolean isVerified;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastSignIn() {
        return lastSignIn;
    }

    public void setLastSignIn(String lastSignIn) {
        this.lastSignIn = lastSignIn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", lastSignIn='" + lastSignIn + '\'' +
                ", status='" + status + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", isVerified=" + isVerified +
                '}';
    }
}