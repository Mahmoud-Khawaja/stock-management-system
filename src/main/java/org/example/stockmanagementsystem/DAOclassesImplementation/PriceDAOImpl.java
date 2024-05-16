package org.example.stockmanagementsystem.DAOclassesImplementation;

import org.example.stockmanagementsystem.DAOclasses.PriceDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceDAOImpl implements PriceDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/stock_management_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "your_password";

    @Override
    public Map<Integer, List<Double>> getAllPrices() {
        Map<Integer, List<Double>> pricesMap = new HashMap<>();
        String query = "SELECT stock_id, price FROM price_history ORDER BY stock_id, date_time";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int stockId = resultSet.getInt("stock_id");
                double price = resultSet.getDouble("price");

                pricesMap.computeIfAbsent(stockId, k -> new ArrayList<>()).add(price);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching prices: " + e.getMessage());
        }
        return pricesMap;
    }
    @Override
    public boolean updatePrice(int stockId, double newPrice) {
        String query = "INSERT INTO price_history (stock_id, price, date_time) VALUES (?, ?, NOW())";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, stockId);
            preparedStatement.setDouble(2, newPrice);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating price for stock ID " + stockId + ": " + e.getMessage());
            return false;
        }
    }
    @Override
    public double getLastPrice(int stockId) {
        double lastPrice = 0;

        String query = "SELECT price FROM price_history WHERE stock_id = ? ORDER BY date_time DESC LIMIT 1";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, stockId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                lastPrice = resultSet.getDouble("price");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching last price: " + e.getMessage());
        }
        return lastPrice;
    }

    @Override
    public List<String> getPriceHistory(int stockId) {
        List<String> priceHistory = new ArrayList<>();
        String query = "SELECT date_time, price FROM price_history WHERE stock_id = ? ORDER BY date_time";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, stockId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Timestamp timestamp = resultSet.getTimestamp("date_time");
                    double price = resultSet.getDouble("price");
                    String entry = timestamp.toString() + ", " + price;
                    priceHistory.add(entry);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching price history for stock ID " + stockId + ": " + e.getMessage());
        }
        return priceHistory;
    }

    @Override
    public Map<Integer, List<Timestamp>> getAllTimestamps() {
        Map<Integer, List<Timestamp>> timestampsMap = new HashMap<>();
        String query = "SELECT stock_id, date_time FROM price_history ORDER BY stock_id, date_time";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int stockId = resultSet.getInt("stock_id");
                Timestamp timestamp = resultSet.getTimestamp("date_time");

                timestampsMap.computeIfAbsent(stockId, k -> new ArrayList<>()).add(timestamp);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching timestamps: " + e.getMessage());
        }
        return timestampsMap;
    }



    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
