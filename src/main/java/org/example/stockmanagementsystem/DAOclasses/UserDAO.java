package org.example.stockmanagementsystem.DAOclasses;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    boolean isValidUser(String username, String password);
    List<String[]> getAllUsers();
    String[] getUserInfo(int userId);
    String getUserId(String username, String password);
    double getUserBalance(int userId);
    boolean addUser(String username, String password, String firstName, String lastName);
    boolean removeUser(int userId) throws SQLException;
    void updateUserBalance(int userId, double newBalance);
}