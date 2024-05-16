package org.example.stockmanagementsystem.DAOclasses;

import java.util.List;

public interface TransactionDAO  {
    void deposit(int userId, double amount);
    void withdraw(int userId, double amount);
    List<List<String>> getUserTransactionHistory(int userId);
    List<List<String>> getAdminTransactionHistory();
    void completeTransaction(int transactionId);
    void cancelTransaction(int transactionId);
}
