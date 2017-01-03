package com.thinhtranuit.example.stockWatcher.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class StockWatcher implements EntryPoint {
    private VerticalPanel mainPanel = new VerticalPanel();
    private FlexTable stockTable = new FlexTable();
    private HorizontalPanel addPanel = new HorizontalPanel();
    private TextBox textBox = new TextBox();
    private Button addButton = new Button("ADD");
    private Label timeUpdated = new Label();

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        stockTable.setText(0 , 0, "Symbol");
        stockTable.setText(0 , 1, "Price");
        stockTable.setText(0 , 2, "Change");
        stockTable.setText(0 , 3, "Remove");
        stockTable.setBorderWidth(1);

        addPanel.add(textBox);
        addPanel.add(addButton);

        mainPanel.add(stockTable);
        mainPanel.add(addPanel);
        mainPanel.add(timeUpdated);

        RootPanel.get("stockList").add(mainPanel);

        textBox.setFocus(true);
    }
}
