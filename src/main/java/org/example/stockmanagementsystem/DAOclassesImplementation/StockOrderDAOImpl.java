package org.example.stockmanagementsystem.DAOclassesImplementation;

import org.example.stockmanagementsystem.DAOclasses.StockDAO;
import org.example.stockmanagementsystem.DAOclasses.StockOrderDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockOrderDAOImpl implements StockOrderDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/stock_management_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "your_password";

    @Override
    public void placeOrder(int userId, int stockId, String orderType, double orderPrice, int orderQuantity) {
        String query = "INSERT INTO stock_order (user_id, stock_id, order_type, order_price, order_quantity, order_date, status) VALUES (?, ?, ?, ?, ?, NOW(), 'completed')";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, stockId);
            preparedStatement.setString(3, orderType);
            preparedStatement.setDouble(4, orderPrice);
            preparedStatement.setInt(5, orderQuantity);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order placed successfully.");
            } else {
                System.out.println("Failed to place order.");
            }
        } catch (SQLException e) {
            System.out.println("Error placing order: " + e.getMessage());
        }
    }
    @Override
    public List<List<String>> getCompletedOrders(int userId) {
        List<List<String>> completedOrdersList = new ArrayList<>();
        String query = "SELECT so.order_id, s.label AS stock_label, s.company_name, so.order_price, so.order_quantity, so.order_date " +
                "FROM stock_order so " +
                "JOIN stock s ON so.stock_id = s.stock_id " +
                "WHERE so.user_id = ? AND so.status = 'completed' AND so.order_quantity > 0 AND so.order_type = 'buy'";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    List<String> orderDetails = new ArrayList<>();
                    orderDetails.add(String.valueOf(resultSet.getInt("order_id")));
                    orderDetails.add(resultSet.getString("stock_label"));
                    orderDetails.add(resultSet.getString("company_name"));
                    orderDetails.add(String.valueOf(resultSet.getDouble("order_price")));
                    orderDetails.add(String.valueOf(resultSet.getInt("order_quantity")));
                    orderDetails.add(resultSet.getTimestamp("order_date").toString());
                    completedOrdersList.add(orderDetails);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching completed buy orders for user: " + e.getMessage());
        }
        return completedOrdersList;
    }

    @Override
    public void updateOrderAmount(int orderId, int newAmount) {
        String updateQuery = "UPDATE stock_order SET order_quantity = ? WHERE order_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, newAmount);
            preparedStatement.setInt(2, orderId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order " + orderId + " amount updated successfully.");
            } else {
                System.out.println("Failed to update order amount or order not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating order amount: " + e.getMessage());
        }
    }

    @Override
    public int getOrderAmount(int orderId) {
        int orderAmount = 0;
        String query = "SELECT order_quantity FROM stock_order WHERE order_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    orderAmount = resultSet.getInt("order_quantity");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching order amount: " + e.getMessage());
        }
        return orderAmount;
    }

    @Override
    public List<List<String>> getOrdersForUser(int userId) {
        List<List<String>> ordersList = new ArrayList<>();
        String query = "SELECT order_id, order_type, order_price, order_quantity, order_date, status FROM stock_order WHERE user_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    List<String> orderDetails = new ArrayList<>();
                    orderDetails.add(String.valueOf(resultSet.getInt("order_id")));
                    orderDetails.add(resultSet.getString("order_type"));
                    orderDetails.add(String.valueOf(resultSet.getDouble("order_price")));
                    orderDetails.add(String.valueOf(resultSet.getInt("order_quantity")));
                    orderDetails.add(resultSet.getTimestamp("order_date").toString());
                    orderDetails.add(resultSet.getString("status"));
                    ordersList.add(orderDetails);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching orders for user: " + e.getMessage());
        }
        return ordersList;
    }
    @Override
    public int getStockId(int orderId) {
        int stockId = -1;
        String query = "SELECT stock_id FROM stock_order WHERE order_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    stockId = resultSet.getInt("stock_id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching stock ID by order ID: " + e.getMessage());
        }
        return stockId;
    }




    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
