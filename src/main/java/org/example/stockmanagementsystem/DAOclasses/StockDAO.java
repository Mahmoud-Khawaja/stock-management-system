package org.example.stockmanagementsystem.DAOclasses;

import java.util.Map;

public interface StockDAO {
    int getStockAmount(int stockId);
    Map<Integer, Map<String, Object>> getAllStocksWithLastPrice();
    String getStockName(int stockId);
    int getMostRecentStockId();
    Map<Integer, Map<String, Object>> getAllStocks();
    void removeStock(int stockId);
    void updateStockQuantity(int stockId, int newQuantity);
    int getAvailableStocks(int stockId);
    void addStock(String label, String companyName, double initialPrice, double tradingPrice, double dividends, int availableStocks, double profitPercentage);
}

