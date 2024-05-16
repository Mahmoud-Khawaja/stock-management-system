package org.example.stockmanagementsystem.searchingModels;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TransactionSearchModel {
    private final SimpleIntegerProperty transactionId;
    private final SimpleDoubleProperty amount;
    private final SimpleStringProperty type;
    private final SimpleStringProperty status;
    private final SimpleStringProperty createdAt;
    private final SimpleStringProperty updatedAt;

    public TransactionSearchModel(int transactionId, double amount, String type, String status, String createdAt, String updatedAt) {
        this.transactionId = new SimpleIntegerProperty(transactionId);
        this.amount = new SimpleDoubleProperty(amount);
        this.type = new SimpleStringProperty(type);
        this.status = new SimpleStringProperty(status);
        this.createdAt = new SimpleStringProperty(createdAt);
        this.updatedAt = new SimpleStringProperty(updatedAt);
    }

    public int getTransactionId() {
        return transactionId.get();
    }

    public double getAmount() {
        return amount.get();
    }

    public String getType() {
        return type.get();
    }

    public String getStatus() {
        return status.get();
    }

    public String getCreatedAt() {
        return createdAt.get();
    }

    public String getUpdatedAt() {
        return updatedAt.get();
    }
}