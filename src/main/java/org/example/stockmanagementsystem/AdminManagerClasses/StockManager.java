package org.example.stockmanagementsystem.AdminManagerClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.stockmanagementsystem.ConfirmationDialog;
import org.example.stockmanagementsystem.DAOclasses.StockDAO;
import org.example.stockmanagementsystem.helperClasses.InputValidator;
import org.example.stockmanagementsystem.searchingModels.StockSearchModel;

import java.util.Map;
import java.util.Optional;

public class StockManager {
    private final StockDAO stockDAO;
    private final TableView<StockSearchModel> stockTableView;
    private final ObservableList<StockSearchModel> stockSearchModelObservableList;

    public StockManager(StockDAO stockDAO, TableView<StockSearchModel> stockTableView,
                        TableColumn<StockSearchModel, Integer> stockIdColumn,
                        TableColumn<StockSearchModel, String> stockLabelColumn,
                        TableColumn<StockSearchModel, String> stockCompanyNameColumn,
                        TableColumn<StockSearchModel, Double> stockInitialPriceColumn,
                        TableColumn<StockSearchModel, Double> stockTradingPriceColumn,
                        TableColumn<StockSearchModel, Double> stockDividendsColumn,
                        TableColumn<StockSearchModel, Integer> availableStocksColumn,
                        TableColumn<StockSearchModel, Double> stockProfitPercentageColumn,
                        TableColumn<StockSearchModel, String> stockCreatedColumn) {
        this.stockDAO = stockDAO;
        this.stockTableView = stockTableView;

        stockIdColumn.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        stockLabelColumn.setCellValueFactory(new PropertyValueFactory<>("label"));
        stockCompanyNameColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        stockInitialPriceColumn.setCellValueFactory(new PropertyValueFactory<>("initialPrice"));
        stockTradingPriceColumn.setCellValueFactory(new PropertyValueFactory<>("tradingPrice"));
        stockDividendsColumn.setCellValueFactory(new PropertyValueFactory<>("dividends"));
        availableStocksColumn.setCellValueFactory(new PropertyValueFactory<>("availableStocks"));
        stockProfitPercentageColumn.setCellValueFactory(new PropertyValueFactory<>("profitPercentage"));
        stockCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        styleTableColumns(stockIdColumn, stockLabelColumn, stockCompanyNameColumn,
                stockInitialPriceColumn, stockTradingPriceColumn, stockDividendsColumn,
                availableStocksColumn, stockProfitPercentageColumn, stockCreatedColumn);

        this.stockSearchModelObservableList = FXCollections.observableArrayList();
        this.stockTableView.setItems(stockSearchModelObservableList);
    }

    public void initialize(TextField stockSearchBar) {
        setupStockSearchFilter(stockSearchBar);
        populateStockTableView();
    }

    private void styleTableColumns(TableColumn<?, ?>... columns) {
        for (TableColumn<?, ?> column : columns) {
            column.setStyle("-fx-alignment: CENTER;");
        }
    }

    private void setupStockSearchFilter(TextField stockSearchBar) {
        FilteredList<StockSearchModel> filteredData = new FilteredList<>(stockSearchModelObservableList, b -> true);
        stockSearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(stockModel -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return stockModelMatches(stockModel, lowerCaseFilter);
            });
        });
        SortedList<StockSearchModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(stockTableView.comparatorProperty());
        stockTableView.setItems(sortedData);
    }

    private boolean stockModelMatches(StockSearchModel stockModel, String filter) {
        String initialPriceString = String.format("%.2f", stockModel.getInitialPrice());
        String tradingPriceString = String.format("%.2f", stockModel.getTradingPrice());
        String dividendsString = String.format("%.2f", stockModel.getDividends());
        String profitPercentageString = String.format("%.2f", stockModel.getProfitPercentage());

        return String.valueOf(stockModel.getStockId()).contains(filter)
                || stockModel.getLabel().toLowerCase().contains(filter)
                || stockModel.getCompanyName().toLowerCase().contains(filter)
                || initialPriceString.contains(filter)
                || tradingPriceString.contains(filter)
                || dividendsString.contains(filter)
                || String.valueOf(stockModel.getAvailableStocks()).contains(filter)
                || profitPercentageString.contains(filter);
    }

    private void populateStockTableView() {
        try {
            Map<Integer, Map<String, Object>> allStocks = stockDAO.getAllStocks();
            stockSearchModelObservableList.clear();
            allStocks.forEach((stockId, stockInfo) -> {
                StockSearchModel stockModel = createStockModel(stockId, stockInfo);
                stockSearchModelObservableList.add(stockModel);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private StockSearchModel createStockModel(int stockId, Map<String, Object> stockInfo) {
        String label = stockInfo.containsKey("label") ? (String) stockInfo.get("label") : "";
        String companyName = stockInfo.containsKey("companyName") ? (String) stockInfo.get("companyName") : "";
        double initialPrice = stockInfo.containsKey("initialPrice") ? (double) stockInfo.get("initialPrice") : 0.0;
        double tradingPrice = stockInfo.containsKey("tradingPrice") ? (double) stockInfo.get("tradingPrice") : 0.0;
        double dividends = stockInfo.containsKey("dividends") ? (double) stockInfo.get("dividends") : 0.0;
        int availableStocks = stockInfo.containsKey("availableStocks") ? (int) stockInfo.get("availableStocks") : 0;
        double profitPercentage = stockInfo.containsKey("profitPercentage") ? (double) stockInfo.get("profitPercentage") : 0.0;
        String createdAt = stockInfo.containsKey("createdAt") ? (String) stockInfo.get("createdAt") : "";

        return new StockSearchModel(stockId, label, companyName, initialPrice, tradingPrice, dividends, availableStocks, profitPercentage, createdAt);
    }

    public void addStock(TextField stockCompanyLabelField, TextField stockCompanyNameField,
                         TextField stockInitialPriceField, TextField stockTradingPriceField,
                         TextField stockDividendsField, TextField availableStocksField,
                         TextField stockProfitField) {
        boolean isValidLabel = InputValidator.validateTextField(stockCompanyLabelField);
        boolean isValidCompanyName = InputValidator.validateTextField(stockCompanyNameField);
        boolean isValidInitialPrice = InputValidator.validateDoubleField(stockInitialPriceField);
        boolean isValidTradingPrice = InputValidator.validateDoubleField(stockTradingPriceField);
        boolean isValidDividends = InputValidator.validateDoubleField(stockDividendsField);
        boolean isValidAvailableStocks = InputValidator.validateIntegerField(availableStocksField);
        boolean isValidProfitPercentage = InputValidator.validateDoubleField(stockProfitField);

        if (isValidLabel && isValidCompanyName && isValidInitialPrice && isValidTradingPrice && isValidDividends && isValidAvailableStocks && isValidProfitPercentage) {
            String label = stockCompanyLabelField.getText();
            String companyName = stockCompanyNameField.getText();
            double initialPrice = Double.parseDouble(stockInitialPriceField.getText());
            double tradingPrice = Double.parseDouble(stockTradingPriceField.getText());
            double dividends = Double.parseDouble(stockDividendsField.getText());
            int availableStocks = Integer.parseInt(availableStocksField.getText());
            double profitPercentage = Double.parseDouble(stockProfitField.getText());

            Optional<Boolean> confirmationResult = ConfirmationDialog.show("Confirmation", "Are you sure you want to add this stock?");
            confirmationResult.ifPresent(result -> {
                if (result) {
                    stockDAO.addStock(label, companyName, initialPrice, tradingPrice, dividends, availableStocks, profitPercentage);
                    populateStockTableView();
                } else {
                    System.out.println("Addition canceled.");
                }
            });
        } else {
            System.out.println("Please fill in all fields correctly.");
        }
    }

    public void removeStock() {
        StockSearchModel selectedStock = stockTableView.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            int stockId = selectedStock.getStockId();
            Optional<Boolean> confirmationResult = ConfirmationDialog.show("Confirmation", "Are you sure you want to remove this stock?");
            confirmationResult.ifPresent(result -> {
                if (result) {
                    try {
                        stockDAO.removeStock(stockId);
                        populateStockTableView();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Error removing stock: " + e.getMessage());
                    }
                } else {
                    System.out.println("Removal canceled.");
                }
            });
        } else {
            System.out.println("No stock selected.");
        }
    }
    public int getSelectedStockId() {
        StockSearchModel selectedStock = stockTableView.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            return selectedStock.getStockId();
        } else {
            return 1;// default stock
        }
    }
}
