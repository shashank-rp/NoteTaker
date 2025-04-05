package com.rith.notetaker2.model;

import androidx.annotation.NonNull;

public class TokenAdvanceModel {
    private int id;
    private int tokenAmt;
    private String tokenSymbol;

    @NonNull
    @Override
    public String toString() {
        return tokenSymbol;
    }

    public TokenAdvanceModel(int tokenAmt, String tokenSymbol) {
        this.tokenAmt = tokenAmt;
        this.tokenSymbol = tokenSymbol;
    }

    public TokenAdvanceModel() {
    }

    public int getId() {
        return id;
    }

    public TokenAdvanceModel setId(int id) {
        this.id = id;
        return this;
    }

    public int getTokenAmt() {
        return tokenAmt;
    }

    public TokenAdvanceModel setTokenAmt(int tokenAmt) {
        this.tokenAmt = tokenAmt;
        return this;
    }

    public String getTokenSymbol() {
        return tokenSymbol;
    }

    public TokenAdvanceModel setTokenSymbol(String tokenSymbol) {
        this.tokenSymbol = tokenSymbol;
        return this;
    }
}
