package org.example.stockmanagementsystem.DAOclasses;

import java.sql.Timestamp;
import java.util.List;

public interface TradingSessionDAO {
    List<List<String>> getAllTradingSessions();
    void openTradingSession(int adminUserId);
    void closeTradingSession();
    boolean hasActiveTradingSession();
}