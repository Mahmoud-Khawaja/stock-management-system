// StockOrderDAO.java
package org.example.stockmanagementsystem.DAOclasses;

import java.util.List;

public interface StockOrderDAO {
    void placeOrder(int userId, int stockId, String orderType, double orderPrice, int orderQuantity);
    List<List<String>> getOrdersForUser(int userId);
    List<List<String>> getCompletedOrders(int userId);
    int getOrderAmount(int orderId);
    void updateOrderAmount(int orderId, int newAmount);
    int getStockId(int orderId);
}