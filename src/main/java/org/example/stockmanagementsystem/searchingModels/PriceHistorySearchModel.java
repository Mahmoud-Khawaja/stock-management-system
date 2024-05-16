package org.example.stockmanagementsystem.searchingModels;

import javafx.beans.property.*;

public class PriceHistorySearchModel {

    private final IntegerProperty stockId;
    private final StringProperty companyName;
    private final DoubleProperty price;
    private final StringProperty date;

    public PriceHistorySearchModel(int stockId, String companyName, double price, String date) {
        this.stockId = new SimpleIntegerProperty(stockId);
        this.companyName = new SimpleStringProperty(companyName);
        this.price = new SimpleDoubleProperty(price);
        this.date = new SimpleStringProperty(date);
    }

    public int getStockId() {
        return stockId.get();
    }

    public IntegerProperty stockIdProperty() {
        return stockId;
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }
}
