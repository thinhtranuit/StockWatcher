package com.thinhtranuit.example.stockWatcher.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created by thinh on 06/01/2017.
 */
public class StockPrice implements IsSerializable {
    private String symbol;
    private double price;
    private double change;

    public StockPrice(String symbol, double price, double change) {
        this.symbol = symbol;
        this.price = price;
        this.change = change;
    }

    public StockPrice() {
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public double getChange() {
        return change;
    }

    public double getChangePercent() {
        return 100.0 * this.change / this.price;
    }
}
