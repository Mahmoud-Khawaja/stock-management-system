package org.example.stockmanagementsystem.searchingModels;

public class ApprovalSystemTransactionSearchModel {
    private final int transactionId;
    private final int userId;
    private final double transactionAmount;
    private final String transactionType;
    private final String transactionStatus;
    private final String transactionCreatedAt;
    private final String transactionUpdatedAt;
    private final String username;

    public ApprovalSystemTransactionSearchModel(int transactionId, int userId, double transactionAmount, String transactionType, String transactionStatus, String transactionCreatedAt, String transactionUpdatedAt, String username) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
        this.transactionCreatedAt = transactionCreatedAt;
        this.transactionUpdatedAt = transactionUpdatedAt;
        this.username = username;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public String getTransactionCreatedAt() {
        return transactionCreatedAt;
    }

    public String getTransactionUpdatedAt() {
        return transactionUpdatedAt;
    }

    public String getUsername() {
        return username;
    }
}
