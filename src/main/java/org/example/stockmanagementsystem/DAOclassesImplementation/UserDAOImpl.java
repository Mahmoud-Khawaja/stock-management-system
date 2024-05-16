package org.example.stockmanagementsystem.DAOclassesImplementation;

import org.example.stockmanagementsystem.DAOclasses.UserDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/stock_management_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "your_password";


    @Override
    public void updateUserBalance(int userId, double newBalance) {
        String query = "UPDATE users SET balance = ? WHERE user_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, userId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating user balance: " + e.getMessage());
        }
    }

    @Override
    public boolean isValidUser(String username, String password) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        boolean isValid = false;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    isValid = count > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error validating user: " + e.getMessage());
        }
        return isValid;
    }

    @Override
    public String getUserId(String username, String password) {
        String query = "SELECT user_id FROM users WHERE username = ? AND password = ?";
        String userId = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getString("user_id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getting user ID: " + e.getMessage());
        }
        return userId;
    }

    @Override
    public List<String[]> getAllUsers() {
        List<String[]> users = new ArrayList<>();

        String sql = "SELECT user_id, firstname, lastname, username, created_at, is_admin, balance FROM users";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String username = resultSet.getString("username");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                boolean isAdmin = resultSet.getBoolean("is_admin");
                double balance = resultSet.getDouble("balance");

                String[] userInfo = {String.valueOf(userId), firstName, lastName, username, String.valueOf(createdAt), String.valueOf(isAdmin), String.valueOf(balance)};

                users.add(userInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public String[] getUserInfo(int userId) {
        String[] userInfo = new String[3];

        String sql = "SELECT firstname, lastname, is_admin FROM users WHERE user_id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userInfo[0] = resultSet.getString("firstname");
                userInfo[1] = resultSet.getString("lastname");
                boolean isAdmin = resultSet.getBoolean("is_admin");
                userInfo[2] = isAdmin ? "admin" : "normal";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    @Override
    public boolean addUser(String username, String password, String firstName, String lastName) {
        String query = "INSERT INTO users (username, password, firstname, lastname,is_admin) VALUES (?, ?, ?, ?,0)";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }
    @Override
    public double getUserBalance(int userId) {
        String sql = "SELECT balance FROM users WHERE user_id = ?";
        double balance = 0;

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                balance = resultSet.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }
}