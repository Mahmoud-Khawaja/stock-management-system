package org.example.stockmanagementsystem.DAOclassesImplementation;

import org.example.stockmanagementsystem.DAOclasses.TradingSessionDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TradingSessionDAOImpl implements TradingSessionDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/stock_management_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "your_password";

    @Override
    public void openTradingSession(int adminUserId) {
        if (hasActiveTradingSession()) {
            System.out.println("There is already an active trading session.");
            return;
        }

        Timestamp startTime = new Timestamp(System.currentTimeMillis());

        String query = "INSERT INTO trading_session (start_time, end_time, admin_user_id) VALUES (?, NULL, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setTimestamp(1, startTime);
            preparedStatement.setInt(2, adminUserId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Trading session created successfully.");
            } else {
                System.out.println("Failed to create trading session.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating trading session: " + e.getMessage());
        }
    }

    @Override
    public void closeTradingSession() {
        if (!hasActiveTradingSession()) {
            System.out.println("There is no active trading session to close.");
            return;
        }

        Timestamp endTime = new Timestamp(System.currentTimeMillis());

        updateTradingSessionEndTime(getActiveTradingSessionId(), endTime);

        updateTradingSessionStatus(getActiveTradingSessionId(), "closed");

        System.out.println("Trading session closed successfully.");
    }

    @Override
    public List<List<String>> getAllTradingSessions() {
        List<List<String>> tradingSessions = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM trading_session")) {

            while (resultSet.next()) {
                List<String> sessionData = new ArrayList<>();
                sessionData.add(String.valueOf(resultSet.getInt("session_id")));
                Timestamp startTime = resultSet.getTimestamp("start_time");
                Timestamp endTime = resultSet.getTimestamp("end_time");

                sessionData.add(startTime != null ? startTime.toString() : "");
                sessionData.add(endTime != null ? endTime.toString() : "");

                sessionData.add(resultSet.getString("status"));
                sessionData.add(String.valueOf(resultSet.getInt("admin_user_id")));

                tradingSessions.add(sessionData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tradingSessions;
    }

    public boolean hasActiveTradingSession() {
        String query = "SELECT COUNT(*) AS count FROM trading_session WHERE status = 'open'";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt("count") > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error checking active trading session: " + e.getMessage());
        }
        return false;
    }

    private int getActiveTradingSessionId() {
        String query = "SELECT session_id FROM trading_session WHERE status = 'open' LIMIT 1";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt("session_id");
            }
        } catch (SQLException e) {
            System.out.println("Error getting active trading session ID: " + e.getMessage());
        }
        return -1;
    }
    private void updateTradingSessionEndTime(int sessionId, Timestamp endTime) {
        String query = "UPDATE trading_session SET end_time = ? WHERE session_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, endTime);
            preparedStatement.setInt(2, sessionId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Trading session end time updated successfully.");
            } else {
                System.out.println("Failed to update trading session end time.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating trading session end time: " + e.getMessage());
        }
    }

    private void updateTradingSessionStatus(int sessionId, String newStatus) {
        String query = "UPDATE trading_session SET status = ? WHERE session_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, sessionId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Trading session status updated successfully.");
            } else {
                System.out.println("Failed to update trading session status.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating trading session status: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
