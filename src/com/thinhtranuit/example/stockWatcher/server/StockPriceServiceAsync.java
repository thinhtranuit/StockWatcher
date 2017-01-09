package com.thinhtranuit.example.stockWatcher.server;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.thinhtranuit.example.stockWatcher.client.StockPrice;

public interface StockPriceServiceAsync {
    void getPrices(String[] symbols, AsyncCallback<StockPrice[]> async);
}
