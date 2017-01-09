package com.thinhtranuit.example.stockWatcher.client;

import java.io.Serializable;

/**
 * Created by thinh on 09/01/2017.
 */
public class DelistedException extends Exception implements Serializable {
    private String symbol;

    public DelistedException() {
    }

    public DelistedException(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }
}
