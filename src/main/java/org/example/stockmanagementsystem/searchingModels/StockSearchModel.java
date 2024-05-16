package org.example.stockmanagementsystem.searchingModels;

import javafx.beans.property.*;

public class StockSearchModel {

    private final IntegerProperty stockId;
    private final StringProperty label;
    private final StringProperty companyName;
    private final DoubleProperty initialPrice;
    private final DoubleProperty tradingPrice;
    private final DoubleProperty dividends;
    private final IntegerProperty availableStocks;
    private final DoubleProperty profitPercentage;
    private final StringProperty createdAt;

    public StockSearchModel(int stockId, String label, String companyName, double initialPrice, double tradingPrice, double dividends, int availableStocks, double profitPercentage, String createdAt) {
        this.stockId = new SimpleIntegerProperty(stockId);
        this.label = new SimpleStringProperty(label);
        this.companyName = new SimpleStringProperty(companyName);
        this.initialPrice = new SimpleDoubleProperty(initialPrice);
        this.tradingPrice = new SimpleDoubleProperty(tradingPrice);
        this.dividends = new SimpleDoubleProperty(dividends);
        this.availableStocks = new SimpleIntegerProperty(availableStocks);
        this.profitPercentage = new SimpleDoubleProperty(profitPercentage);
        this.createdAt = new SimpleStringProperty(createdAt);
    }

    public int getStockId() {
        return stockId.get();
    }

    public IntegerProperty stockIdProperty() {
        return stockId;
    }

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public double getInitialPrice() {
        return initialPrice.get();
    }

    public DoubleProperty initialPriceProperty() {
        return initialPrice;
    }

    public double getTradingPrice() {
        return tradingPrice.get();
    }

    public DoubleProperty tradingPriceProperty() {
        return tradingPrice;
    }

    public int getAvailableStocks() {
        return availableStocks.get();
    }

    public IntegerProperty availableStocksProperty() {
        return availableStocks;
    }

    public double getDividends() {
        return dividends.get();
    }

    public DoubleProperty dividendsProperty() {
        return dividends;
    }

    public double getProfitPercentage() {
        return profitPercentage.get();
    }

    public DoubleProperty profitPercentageProperty() {
        return profitPercentage;
    }

    public String getCreatedAt() {
        return createdAt.get();
    }

    public StringProperty createdAtProperty() {
        return createdAt;
    }
}
