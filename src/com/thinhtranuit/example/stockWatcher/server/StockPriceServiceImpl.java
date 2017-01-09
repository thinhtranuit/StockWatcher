package com.thinhtranuit.example.stockWatcher.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.thinhtranuit.example.stockWatcher.client.DelistedException;
import com.thinhtranuit.example.stockWatcher.client.StockPrice;

import java.util.Random;

/**
 * Created by thinh on 08/01/2017.
 */
public class StockPriceServiceImpl extends RemoteServiceServlet implements StockPriceService {

    public static final double MAX_PRICE = 100.0;
    public static final double MAX_PRICE_CHANGE = 0.02;

    @Override
    public StockPrice[] getPrices(String[] symbols) throws DelistedException {
        Random rnd = new Random();

        StockPrice[] prices = new StockPrice[symbols.length];
        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i].toUpperCase().equals("FUCK")){
                throw new DelistedException("FUCK");
            }
            double price = rnd.nextDouble() * MAX_PRICE;
            double change = price * MAX_PRICE_CHANGE * (rnd.nextDouble() * 2f - 1f);

            prices[i] = new StockPrice(symbols[i], price, change);
        }

        return prices;
    }
}
