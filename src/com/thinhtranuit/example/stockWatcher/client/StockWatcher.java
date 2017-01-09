package com.thinhtranuit.example.stockWatcher.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.thinhtranuit.example.stockWatcher.server.StockPriceService;
import com.thinhtranuit.example.stockWatcher.server.StockPriceServiceAsync;

import java.util.ArrayList;
import java.util.Date;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class StockWatcher implements EntryPoint {
    public static final int PERIOD_MILLIS = 1000;
    private Image googleIcon;
    private HorizontalPanel logoPanel = new HorizontalPanel();
    private VerticalPanel mainPanel = new VerticalPanel();
    private FlexTable stockTable = new FlexTable();
    private HorizontalPanel addPanel = new HorizontalPanel();
    private TextBox textBox = new TextBox();
    private Button addButton = new Button("ADD");
    private Label timeUpdated = new Label();
    private ArrayList<String> stocks = new ArrayList<>();
    private StockPriceServiceAsync stokServiceAsync = GWT.create(StockPriceService.class);
    private Label errorMsg = new Label();
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        //set icon header
        MyResource myResource = GWT.create(MyResource.class);
        googleIcon = new Image(myResource.logo());
        logoPanel.add(googleIcon);
        RootPanel.get("googleIcon").add(logoPanel);
        //set header for stock table
        stockTable.setText(0 , 0, "Symbol");
        stockTable.setText(0 , 1, "Price");
        stockTable.setText(0 , 2, "Change");
        stockTable.setText(0 , 3, "Remove");
        stockTable.setBorderWidth(1);

        addPanel.add(textBox);
        addPanel.add(addButton);
        errorMsg.setStyleName("errorMessage");
        errorMsg.setVisible(false);
        mainPanel.add(errorMsg);
        mainPanel.add(stockTable);
        mainPanel.add(addPanel);
        mainPanel.add(timeUpdated);
        RootPanel.get("stockList").add(mainPanel);

        textBox.setFocus(true);

        //add a new stock to table if user press on "Add" button
        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addStock();
            }
        });

        //add a new stock to table if user press Enter
        textBox.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
                    addStock();
                }
            }
        });

        //Set a timer to refresh data every 5 minutes
        Timer timer = new Timer() {
            @Override
            public void run() {
                refreshStockList();
            }
        };
        timer.scheduleRepeating(PERIOD_MILLIS);

        setStyle();
    }

    private void setStyle() {
        stockTable.getRowFormatter().addStyleName(0, "tableHeader");
        stockTable.addStyleName("text-center");
        stockTable.addStyleName("watchList");
        stockTable.getCellFormatter().addStyleName(0, 0, "watchListSymbolColumn");
        stockTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
        stockTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
        stockTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");
        addPanel.addStyleName("addPanel");
        errorMsg.addStyleName("errorMessage");
    }

    private void refreshStockList() {
        AsyncCallback<StockPrice[]> asyncCallback =  new AsyncCallback<StockPrice[]>() {
            @Override
            public void onFailure(Throwable caught) {
                if (caught instanceof DelistedException){
                    errorMsg.setVisible(true);
                    String str = "Error: Company " + ((DelistedException) caught).getSymbol() + " have been deleted";
                    errorMsg.setText(str);
                    errorMsg.setVisible(true);
                }
            }

            @Override
            public void onSuccess(StockPrice[] result) {
                updateTable(result);
            }
        };
        stokServiceAsync.getPrices(stocks.toArray(new String[0]), asyncCallback);
    }

    private void updateTable(StockPrice[] stockPrices) {
        for(StockPrice stockPrice : stockPrices){
            if (!stocks.contains(stockPrice.getSymbol())){
                return;
            }
            int row = stocks.indexOf(stockPrice.getSymbol()) + 1;
            String priceText = NumberFormat.getFormat("#,##0.00").format(
                    stockPrice.getPrice());
            NumberFormat changeFormat = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
            String changeText = changeFormat.format(stockPrice.getChange());
            String changePercentText = changeFormat.format(stockPrice.getChangePercent());

            // Populate the Price and Change fields with new data.
            stockTable.setText(row, 1, priceText);
            String changeStyleName = "noChange";
            if (stockPrice.getChangePercent() < -0.1) {
                changeStyleName = "negativeChange";
            }
            else if (stockPrice.getChangePercent() > 0.1) {
                changeStyleName = "positiveChange";
            }
            Label changeWidget = new Label();
            changeWidget.setText(changeText + " (" + changePercentText
                    + "%)");
            changeWidget.addStyleName(changeStyleName);
            stockTable.setWidget(row, 2, changeWidget);
        }
        DateTimeFormat dateFormat = DateTimeFormat.getFormat(
                DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM);
        timeUpdated.setText("Last update : "
                + dateFormat.format(new Date()));

        //clear any error
        errorMsg.setVisible(false);
    }

    /**
     * This method execute when you press "Add" button or enter.
     * Add new Stock to the Stock Table
     */
    private void addStock(){
        final String symbol = textBox.getText().trim();
        textBox.setFocus(true);
        // Stock code must be between 1 and 10 chars that are numbers, letters, or dots.
        if (!symbol.toUpperCase().matches("^[0-9A-Z\\.\\ ]{1,10}$")) {
            Window.alert("'" + symbol + "' is not a valid symbol.");
            textBox.selectAll();
            return;
        }
        textBox.setText("");
        if (stocks.contains(symbol)){
            return;
        } else {
            int row = stockTable.getRowCount();
            stocks.add(symbol);
            stockTable.setText(row, 0, symbol);
            stockTable.getCellFormatter().addStyleName(row, 0, "watchListSymbolColumn");
            stockTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
            stockTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
            stockTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");
            Button removeButton = new Button("X");
            removeButton.addStyleDependentName("remove");
            removeButton.addStyleName("btn-warning");
            removeButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    int index = stocks.indexOf(symbol);
                    stocks.remove(index);
                    stockTable.removeRow(index + 1);
                }
            });
            stockTable.setWidget(row, 3, removeButton);
        }
    }
}
