package com.geminibot.geminibot.entities.responses.gemini.v1;

import java.util.ArrayList;

public class GeminiTransfersResponse extends GeminiResponse {
    private ArrayList<Transfer> transfers;

    public ArrayList<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(ArrayList<Transfer> transfers) {
        this.transfers = transfers;
    }

    public int getTransfersCount() {
        return transfers.size();
    }

    @Override
    public String toString() {
        return "TransfersResponse{" +
                "transferCount=" + getTransfersCount() +
                "transfers=" + transfers +
                '}';
    }

    public void appendTransfers(ArrayList<Transfer> newTransfers) {
        this.transfers.addAll(newTransfers);
    }
}
