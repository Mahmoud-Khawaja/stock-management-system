package org.example.stockmanagementsystem.AdminManagerClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.stockmanagementsystem.DAOclasses.TransactionDAO;
import org.example.stockmanagementsystem.DAOclassesImplementation.TransactionDAOImpl;
import org.example.stockmanagementsystem.searchingModels.ApprovalSystemTransactionSearchModel;

import java.util.List;

public class ApprovalSystemTransactionManager {
    private final TableView<ApprovalSystemTransactionSearchModel> approvalSystemTableView;
    private final ObservableList<ApprovalSystemTransactionSearchModel> approvalSystemTransactionObservableList;

    public ApprovalSystemTransactionManager(TableView<ApprovalSystemTransactionSearchModel> approvalSystemTableView,
                                            TableColumn<ApprovalSystemTransactionSearchModel, Integer> transactionIdColumn,
                                            TableColumn<ApprovalSystemTransactionSearchModel, Integer> userIdColumn,
                                            TableColumn<ApprovalSystemTransactionSearchModel, Double> transactionAmountColumn,
                                            TableColumn<ApprovalSystemTransactionSearchModel, String> transactionTypeColumn,
                                            TableColumn<ApprovalSystemTransactionSearchModel, String> transactionStatusColumn,
                                            TableColumn<ApprovalSystemTransactionSearchModel, String> transactionCreatedAtColumn,
                                            TableColumn<ApprovalSystemTransactionSearchModel, String> transactionUpdatedAtColumn,
                                            TableColumn<ApprovalSystemTransactionSearchModel, String> usernameColumn) {
        this.approvalSystemTableView = approvalSystemTableView;

        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        transactionAmountColumn.setCellValueFactory(new PropertyValueFactory<>("transactionAmount"));
        transactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        transactionStatusColumn.setCellValueFactory(new PropertyValueFactory<>("transactionStatus"));
        transactionCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("transactionCreatedAt"));
        transactionUpdatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("transactionUpdatedAt"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        styleTableColumns(transactionIdColumn, userIdColumn, transactionAmountColumn,
                transactionTypeColumn, transactionStatusColumn, transactionCreatedAtColumn,
                transactionUpdatedAtColumn, usernameColumn);

        this.approvalSystemTransactionObservableList = FXCollections.observableArrayList();
        this.approvalSystemTableView.setItems(approvalSystemTransactionObservableList);
    }

    public void initialize(TextField approvalSystemSearchBar) {
        setupApprovalSystemTransactionSearchFilter(approvalSystemSearchBar);
        populateTransactionTableView();
    }

    private void styleTableColumns(TableColumn<?, ?>... columns) {
        for (TableColumn<?, ?> column : columns) {
            column.setStyle("-fx-alignment: CENTER;");
        }
    }

    private void setupApprovalSystemTransactionSearchFilter(TextField approvalSystemSearchBar) {
        FilteredList<ApprovalSystemTransactionSearchModel> filteredData = new FilteredList<>(approvalSystemTransactionObservableList, b -> true);
        approvalSystemSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(transactionModel -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return transactionModelMatches(transactionModel, lowerCaseFilter);
            });
        });
        SortedList<ApprovalSystemTransactionSearchModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(approvalSystemTableView.comparatorProperty());
        approvalSystemTableView.setItems(sortedData);
    }

    private boolean transactionModelMatches(ApprovalSystemTransactionSearchModel transactionModel, String filter) {
        return String.valueOf(transactionModel.getTransactionId()).contains(filter)
                || String.valueOf(transactionModel.getUserId()).contains(filter)
                || String.valueOf(transactionModel.getTransactionAmount()).contains(filter)
                || transactionModel.getTransactionType().toLowerCase().contains(filter)
                || transactionModel.getTransactionStatus().toLowerCase().contains(filter)
                || transactionModel.getTransactionCreatedAt().toLowerCase().contains(filter)
                || transactionModel.getTransactionUpdatedAt().toLowerCase().contains(filter)
                || transactionModel.getUsername().toLowerCase().contains(filter);
    }

    private void populateTransactionTableView() {
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        List<List<String>> allTransactionHistory = transactionDAO.getAdminTransactionHistory();
        approvalSystemTransactionObservableList.clear();

        for (List<String> transaction : allTransactionHistory) {
            ApprovalSystemTransactionSearchModel transactionModel = new ApprovalSystemTransactionSearchModel(
                    Integer.parseInt(transaction.get(0)), // Transaction ID
                    Integer.parseInt(transaction.get(1)), // User ID
                    Double.parseDouble(transaction.get(2)), // Amount
                    transaction.get(3), // Transaction type
                    transaction.get(4), // Status
                    transaction.get(5), // Created at
                    transaction.get(6), // Updated at
                    transaction.get(7) // Username
            );
            approvalSystemTransactionObservableList.add(transactionModel);
        }
    }
}
