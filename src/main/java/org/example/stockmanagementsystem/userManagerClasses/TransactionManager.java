package org.example.stockmanagementsystem.userManagerClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.stockmanagementsystem.searchingModels.TransactionSearchModel;

import java.util.List;

public class TransactionManager {
    private final TableView<TransactionSearchModel> transactionTableView;
    private final ObservableList<TransactionSearchModel> transactionSearchModelObservableList;

    public TransactionManager(TableView<TransactionSearchModel> transactionTableView,
                              TableColumn<TransactionSearchModel, Integer> transactionIdColumn,
                              TableColumn<TransactionSearchModel, String> transactionTypeColumn,
                              TableColumn<TransactionSearchModel, String> transactionStatusColumn,
                              TableColumn<TransactionSearchModel, Double> transactionAmountColumn,
                              TableColumn<TransactionSearchModel, String> transactionCreatedAtColumn,
                              TableColumn<TransactionSearchModel, String> transactionUpdatedAtColumn) {
        this.transactionTableView = transactionTableView;

        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        transactionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        transactionStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        transactionAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        transactionCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        transactionUpdatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        styleTableColumns(transactionIdColumn, transactionTypeColumn, transactionStatusColumn,
                transactionAmountColumn, transactionCreatedAtColumn, transactionUpdatedAtColumn);

        this.transactionSearchModelObservableList = FXCollections.observableArrayList();
        this.transactionTableView.setItems(transactionSearchModelObservableList);
    }

    public void initialize(TextField transactionSearchBar, List<List<String>> transactionHistory) {
        setupTransactionSearchFilter(transactionSearchBar);
        populateTransactionTableView(transactionHistory);
    }

    private void styleTableColumns(TableColumn<?, ?>... columns) {
        for (TableColumn<?, ?> column : columns) {
            column.setStyle("-fx-alignment: CENTER;");
        }
    }

    private void setupTransactionSearchFilter(TextField transactionSearchBar) {
        FilteredList<TransactionSearchModel> filteredData = new FilteredList<>(transactionSearchModelObservableList, b -> true);
        transactionSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(transactionModel -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return transactionModelMatches(transactionModel, lowerCaseFilter);
            });
        });
        SortedList<TransactionSearchModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(transactionTableView.comparatorProperty());
        transactionTableView.setItems(sortedData);
    }

    private boolean transactionModelMatches(TransactionSearchModel transactionModel, String filter) {
        return String.valueOf(transactionModel.getTransactionId()).contains(filter)
                || transactionModel.getType().toLowerCase().contains(filter)
                || transactionModel.getStatus().toLowerCase().contains(filter)
                || String.valueOf(transactionModel.getAmount()).contains(filter)
                || transactionModel.getCreatedAt().toLowerCase().contains(filter)
                || transactionModel.getUpdatedAt().toLowerCase().contains(filter);
    }

    private void populateTransactionTableView(List<List<String>> transactionHistory) {
        transactionSearchModelObservableList.clear();
        for (List<String> transaction : transactionHistory) {
            TransactionSearchModel transactionModel = new TransactionSearchModel(
                    Integer.parseInt(transaction.get(0)),
                    Double.parseDouble(transaction.get(1)),
                    transaction.get(2),
                    String.valueOf(transaction.get(3)),
                    transaction.get(4),
                    transaction.get(5)
            );
            transactionSearchModelObservableList.add(transactionModel);
        }
    }
}
