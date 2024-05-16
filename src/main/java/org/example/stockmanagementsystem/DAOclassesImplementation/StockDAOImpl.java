package org.example.stockmanagementsystem.DAOclassesImplementation;

import org.example.stockmanagementsystem.DAOclasses.StockDAO;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class StockDAOImpl implements StockDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/stock_management_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "your_password";


    @Override
    public int getStockAmount(int stockId) {
        int stockAmount = 0;

        String query = "SELECT available_stocks FROM stock WHERE stock_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, stockId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    stockAmount = resultSet.getInt("available_stocks");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching stock amount: " + e.getMessage());
        }

        return stockAmount;
    }
    @Override
    public void updateStockQuantity(int stockId, int newQuantity) {
        String query = "UPDATE stock SET available_stocks = ? WHERE stock_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, newQuantity);
            preparedStatement.setInt(2, stockId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Stock quantity updated successfully.");
            } else {
                System.out.println("Failed to update stock quantity.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating stock quantity: " + e.getMessage());
        }
    }
    @Override
    public String getStockName(int stockId) {
        String stockLabel = null;
        String query = "SELECT label FROM stock WHERE stock_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, stockId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                stockLabel = resultSet.getString("label");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching stock label: " + e.getMessage());
        }

        return stockLabel;
    }

    @Override
    public Map<Integer, Map<String, Object>> getAllStocks() {
        Map<Integer, Map<String, Object>> stocks = new HashMap<>();
        String query = "SELECT * FROM stock";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int stockId = resultSet.getInt("stock_id");
                String label = resultSet.getString("label");
                String companyName = resultSet.getString("company_name");
                double initialPrice = resultSet.getDouble("initial_price");
                double tradingPrice = resultSet.getDouble("trading_price");
                double dividends = resultSet.getDouble("dividends");
                int availableStocks = resultSet.getInt("available_stocks");
                double profitPercentage = resultSet.getDouble("profit_percentage");
                String createdAt = resultSet.getString("created_at");

                Map<String, Object> stockInfo = new HashMap<>();
                stockInfo.put("label", label);
                stockInfo.put("companyName", companyName);
                stockInfo.put("initialPrice", initialPrice);
                stockInfo.put("tradingPrice", tradingPrice);
                stockInfo.put("dividends", dividends);
                stockInfo.put("availableStocks", availableStocks);
                stockInfo.put("profitPercentage", profitPercentage);
                stockInfo.put("createdAt", createdAt);

                stocks.put(stockId, stockInfo);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching stocks: " + e.getMessage());
        }

        return stocks;
    }

    public Map<Integer, Map<String, Object>> getAllStocksWithLastPrice() {
        Map<Integer, Map<String, Object>> stocks = new HashMap<>();
        String query = "SELECT stock_id, company_name, updated_at FROM stock";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int stockId = resultSet.getInt("stock_id");
                String companyName = resultSet.getString("company_name");
                Timestamp updatedAt = resultSet.getTimestamp("updated_at");

                Map<String, Object> stockInfo = new HashMap<>();
                stockInfo.put("stockId", stockId);
                stockInfo.put("companyName", companyName);
                stockInfo.put("lastPriceDate", updatedAt); // Change key here

                stocks.put(stockId, stockInfo);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching stocks: " + e.getMessage());
        }

        return stocks;
    }
    @Override
    public int getAvailableStocks(int stockId) {
        int availableStocks = 0;

        String query = "SELECT available_stocks FROM stock WHERE stock_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, stockId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                availableStocks = resultSet.getInt("available_stocks");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching available stocks: " + e.getMessage());
        }

        return availableStocks;
    }

    @Override
    public void addStock(String label, String companyName, double initialPrice, double tradingPrice, double dividends, int availableStocks, double profitPercentage) {
        String query = "INSERT INTO stock (label, company_name, initial_price, trading_price, dividends, available_stocks, profit_percentage, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, label);
            preparedStatement.setString(2, companyName);
            preparedStatement.setDouble(3, initialPrice);
            preparedStatement.setDouble(4, tradingPrice);
            preparedStatement.setDouble(5, dividends);
            preparedStatement.setInt(6, availableStocks);
            preparedStatement.setDouble(7, profitPercentage);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                //debug
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    System.out.println("Stock added successfully with ID: " + generatedId);
                } else {
                    System.out.println("Failed to retrieve generated ID.");
                }
            } else {
                System.out.println("Failed to add stock.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding stock: " + e.getMessage());
        }
    }

    @Override
    public void removeStock(int stockId) {
        String query = "DELETE FROM stock WHERE stock_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, stockId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Stock removed successfully.");
            } else {
                System.out.println("No stock found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error removing stock: " + e.getMessage());
        }
    }
    @Override
    public int getMostRecentStockId() {
        int mostRecentStockId = -1; // Initialize with a default value

        String query = "SELECT stock_id FROM stock ORDER BY stock_id DESC LIMIT 1";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                mostRecentStockId = resultSet.getInt("stock_id");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching most recent stock ID: " + e.getMessage());
        }

        return mostRecentStockId;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
