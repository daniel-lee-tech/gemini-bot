package com.geminibot.geminibot.entities.responses.gemini.v1;

import java.util.ArrayList;

public class GeminiTradesResponse extends GeminiResponse {
    private ArrayList<Trade> trades = new ArrayList<>();

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public void setTrades(ArrayList<Trade> trades) {
        this.trades = trades;
    }

    public void appendTrades(ArrayList<Trade> newTrades) {
        this.trades.addAll(newTrades);
    }

    @Override
    public String toString() {
        return "TradesResponse{" +
                "trades=" + trades +
                "tradesTotal=" + trades.size() +
                '}';
    }
}


