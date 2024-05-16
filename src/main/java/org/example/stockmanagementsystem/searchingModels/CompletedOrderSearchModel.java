package org.example.stockmanagementsystem.searchingModels;

import javafx.beans.property.*;

public class CompletedOrderSearchModel {
    private final IntegerProperty orderId;
    private final StringProperty stockLabel;
    private final StringProperty companyName;
    private final DoubleProperty stockPrice;
    private final IntegerProperty amount;
    private final StringProperty orderedAt;

    public CompletedOrderSearchModel(int orderId, String stockLabel, String companyName, double stockPrice, int amount,
            String orderedAt) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.stockLabel = new SimpleStringProperty(stockLabel);
        this.companyName = new SimpleStringProperty(companyName);
        this.stockPrice = new SimpleDoubleProperty(stockPrice);
        this.amount = new SimpleIntegerProperty(amount);
        this.orderedAt = new SimpleStringProperty(orderedAt);
    }

    public int getOrderId() {
        return orderId.get();
    }

    public IntegerProperty orderIdProperty() {
        return orderId;
    }

    public String getStockLabel() {
        return stockLabel.get();
    }

    public StringProperty stockLabelProperty() {
        return stockLabel;
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public double getStockPrice() {
        return stockPrice.get();
    }

    public DoubleProperty stockPriceProperty() {
        return stockPrice;
    }

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public String getOrderedAt() {
        return orderedAt.get();
    }

    public StringProperty orderedAtProperty() {
        return orderedAt;
    }
}
