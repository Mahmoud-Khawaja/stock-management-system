package org.example.stockmanagementsystem.searchingModels;

import javafx.beans.property.*;

public class UserSearchModel {

    private final IntegerProperty userId;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty username;
    private final StringProperty createdAt;
    private final StringProperty isAdmin;
    private final DoubleProperty balance;

    public UserSearchModel(int userId, String firstName, String lastName, String createdAt, String username, String isAdmin, double balance) {
        this.userId = new SimpleIntegerProperty(userId);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.username = new SimpleStringProperty(username);
        this.createdAt = new SimpleStringProperty(createdAt);
        this.isAdmin = new SimpleStringProperty(isAdmin);
        this.balance = new SimpleDoubleProperty(balance);
    }

    public int getUserId() {
        return userId.get();
    }


    public String getFirstName() {
        return firstName.get();
    }


    public String getLastName() {
        return lastName.get();
    }


    public String getUsername() {
        return username.get();
    }


    public String getCreatedAt() {
        return createdAt.get();
    }


    public String getIsAdmin() {
        return isAdmin.get();
    }


    public double getBalance() {
        return balance.get();
    }

}
