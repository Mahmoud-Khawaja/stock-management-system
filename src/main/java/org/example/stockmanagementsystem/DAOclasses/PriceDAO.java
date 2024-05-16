package org.example.stockmanagementsystem.DAOclasses;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface PriceDAO {
    Map<Integer, List<Double>> getAllPrices();
    Map<Integer, List<Timestamp>> getAllTimestamps();
    List<String> getPriceHistory(int stockId);
    double getLastPrice(int stockId);
    boolean updatePrice(int stockId, double newPrice);
}