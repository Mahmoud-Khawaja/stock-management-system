package org.example.stockmanagementsystem.DAOclassesImplementation;

import org.example.stockmanagementsystem.DAOclasses.TransactionDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/stock_management_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "your_password";

    @Override
    public void deposit(int userId, double amount) {
        performTransaction(userId, amount, "DEPOSIT");
    }

    @Override
    public void withdraw(int userId, double amount) {
        performTransaction(userId, amount, "WITHDRAWAL");
    }

    @Override
    public List<List<String>> getAdminTransactionHistory() {
        List<List<String>> transactionHistory = new ArrayList<>();

        String query = "SELECT d.transaction_id, d.user_id, d.amount, d.transaction_type, d.status, d.created_at, d.updated_at, u.username " +
                "FROM deposit_withdrawal d " +
                "INNER JOIN users u ON d.user_id = u.user_id " +
                "WHERE d.status = 'PENDING' " +
                "ORDER BY d.created_at DESC";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                List<String> transaction = new ArrayList<>();
                transaction.add(String.valueOf(resultSet.getInt("transaction_id")));
                transaction.add(String.valueOf(resultSet.getInt("user_id")));
                transaction.add(String.valueOf(resultSet.getDouble("amount")));
                transaction.add(resultSet.getString("transaction_type"));
                transaction.add(resultSet.getString("status"));
                transaction.add(resultSet.getString("created_at"));
                transaction.add(resultSet.getString("updated_at"));
                transaction.add(resultSet.getString("username")); // Include username
                transactionHistory.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching approval system transaction history: " + e.getMessage());
        }

        return transactionHistory;
    }
    @Override
    public List<List<String>> getUserTransactionHistory(int userId) {
        List<List<String>> transactionHistory = new ArrayList<>();

        String query = "SELECT transaction_id, amount, transaction_type, status, created_at, updated_at " +
                "FROM deposit_withdrawal WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                List<String> transaction = new ArrayList<>();
                transaction.add(String.valueOf(resultSet.getInt("transaction_id")));
                transaction.add(String.valueOf(resultSet.getDouble("amount")));
                transaction.add(resultSet.getString("transaction_type"));
                transaction.add(resultSet.getString("status"));
                transaction.add(resultSet.getString("created_at"));
                transaction.add(resultSet.getString("updated_at"));
                transactionHistory.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching transaction history: " + e.getMessage());
        }

        return transactionHistory;
    }

    private void performTransaction(int userId, double amount, String transactionType) {
        String query = "INSERT INTO deposit_withdrawal (user_id, amount, transaction_type, status, created_at, updated_at) VALUES (?, ?, ?, 'PENDING', NOW(), NOW())";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setDouble(2, amount);
            preparedStatement.setString(3, transactionType);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Transaction type: " + transactionType.toLowerCase() + " successful.");
            } else {
                System.out.println("Failed to perform " + transactionType.toLowerCase() + " transaction.");
            }
        } catch (SQLException e) {
            System.out.println("Error performing " + transactionType.toLowerCase() + " transaction: " + e.getMessage());
        }
    }

    @Override
    public void completeTransaction(int transactionId) {
        updateTransactionStatus(transactionId, "completed");
    }

    @Override
    public void cancelTransaction(int transactionId) {
        updateTransactionStatus(transactionId, "cancelled");
    }

    private void updateTransactionStatus(int transactionId, String status) {
        String query = "UPDATE deposit_withdrawal SET status = ? WHERE transaction_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, transactionId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Transaction status updated successfully.");
            } else {
                System.out.println("Failed to update transaction status.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating transaction status: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
