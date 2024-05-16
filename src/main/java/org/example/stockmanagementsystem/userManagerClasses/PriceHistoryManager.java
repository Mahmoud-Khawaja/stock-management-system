package org.example.stockmanagementsystem.userManagerClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.stockmanagementsystem.DAOclasses.PriceDAO;
import org.example.stockmanagementsystem.DAOclasses.StockDAO;
import org.example.stockmanagementsystem.DAOclassesImplementation.PriceDAOImpl;
import org.example.stockmanagementsystem.DAOclassesImplementation.StockDAOImpl;
import org.example.stockmanagementsystem.searchingModels.PriceHistorySearchModel;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

public class PriceHistoryManager {
    private final TableView<PriceHistorySearchModel> priceHistoryTableView;
    private final ObservableList<PriceHistorySearchModel> priceHistoryObservableList;

    public PriceHistoryManager(TableView<PriceHistorySearchModel> priceHistoryTableView,
                               TableColumn<PriceHistorySearchModel, String> priceHistoryCompanyNameColumn,
                               TableColumn<PriceHistorySearchModel, Double> priceHistoryPriceColumn,
                               TableColumn<PriceHistorySearchModel, String> priceHistoryDateColumn, // Updated type
                               TableColumn<PriceHistorySearchModel, Integer> priceHistoryStockIdColumn) {
        this.priceHistoryTableView = priceHistoryTableView;

        priceHistoryCompanyNameColumn.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        priceHistoryPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceHistoryDateColumn.setCellValueFactory(new PropertyValueFactory<>("date")); // Updated property name
        priceHistoryStockIdColumn.setCellValueFactory(new PropertyValueFactory<>("stockId"));

        styleTableColumns(priceHistoryCompanyNameColumn, priceHistoryPriceColumn, priceHistoryDateColumn, priceHistoryStockIdColumn);

        this.priceHistoryObservableList = FXCollections.observableArrayList();
        this.priceHistoryTableView.setItems(priceHistoryObservableList);
    }

    public void initialize(TextField priceHistorySearchBar, Map<Integer, Map<String, Object>> stocksWithLastPrice) {
        setupPriceHistorySearchFilter(priceHistorySearchBar);
        populatePriceHistoryTableView(stocksWithLastPrice);
    }

    private void styleTableColumns(TableColumn<?, ?>... columns) {
        for (TableColumn<?, ?> column : columns) {
            column.setStyle("-fx-alignment: CENTER;");
        }
    }

    private void setupPriceHistorySearchFilter(TextField priceHistorySearchBar) {
        FilteredList<PriceHistorySearchModel> filteredData = new FilteredList<>(priceHistoryObservableList, b -> true);
        priceHistorySearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(priceHistoryModel -> {
                if (newValue == null || newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return priceHistoryModelMatches(priceHistoryModel, lowerCaseFilter);
            });
        });
        SortedList<PriceHistorySearchModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(priceHistoryTableView.comparatorProperty());
        priceHistoryTableView.setItems(sortedData);
    }

    private boolean priceHistoryModelMatches(PriceHistorySearchModel priceHistoryModel, String filter) {
        return priceHistoryModel.getCompanyName().toLowerCase().contains(filter)
                || String.valueOf(priceHistoryModel.getPrice()).contains(filter)
                || String.valueOf(priceHistoryModel.getDate()).contains(filter);
    }

    private void populatePriceHistoryTableView(Map<Integer, Map<String, Object>> stocksWithLastPrice) {
        priceHistoryObservableList.clear();
        PriceDAO priceDAO = new PriceDAOImpl();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Map.Entry<Integer, Map<String, Object>> entry : stocksWithLastPrice.entrySet()) {
            int stockId = entry.getKey();
            Map<String, Object> stockInfo = entry.getValue();
            String companyName = (String) stockInfo.get("companyName");
            Timestamp lastPriceTimestamp = (Timestamp) stockInfo.get("updatedAt");
            double lastPrice = priceDAO.getLastPrice(stockId);

            String lastPriceDate = "";
            if (lastPriceTimestamp != null) {
                lastPriceDate = dateFormat.format(lastPriceTimestamp);
            }

            PriceHistorySearchModel model = new PriceHistorySearchModel(stockId, companyName, lastPrice, lastPriceDate);
            priceHistoryObservableList.add(model);
        }
    }

    public void updatePrice(int stockId, double newPrice) {
        PriceDAO priceDAO = new PriceDAOImpl();
        boolean success = priceDAO.updatePrice(stockId, newPrice);
        if (success) {
            System.out.println("Price updated successfully.");
            refreshPriceHistory();
        } else {
            System.out.println("Failed to update price.");
        }
    }

    private void refreshPriceHistory() {
        StockDAO stockDAO = new StockDAOImpl();
        Map<Integer, Map<String, Object>> stocksWithLastPrice = stockDAO.getAllStocksWithLastPrice();
        populatePriceHistoryTableView(stocksWithLastPrice);
    }
}
