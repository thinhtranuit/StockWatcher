package com.thinhtranuit.example.stockWatcher.server;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.thinhtranuit.example.stockWatcher.client.DelistedException;
import com.thinhtranuit.example.stockWatcher.client.StockPrice;


/**
 * Created by thinh on 08/01/2017.
 */
@RemoteServiceRelativePath("stockPrices")
public interface StockPriceService extends RemoteService {
    StockPrice[] getPrices(String[] symbols) throws DelistedException;
}
