package org.example.stockmanagementsystem.userManagerClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.stockmanagementsystem.DAOclasses.*;
import org.example.stockmanagementsystem.DAOclassesImplementation.*;
import org.example.stockmanagementsystem.searchingModels.CompletedOrderSearchModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserStocksManager {

    private final StockOrderDAO orderDAO;
    private final TableView<CompletedOrderSearchModel> userStocksTableView;
    private final ObservableList<CompletedOrderSearchModel> userStocksObservableList;

    public UserStocksManager(StockOrderDAO orderDAO, TableView<CompletedOrderSearchModel> userStocksTableView,
                             TableColumn<CompletedOrderSearchModel, Integer> orderIdColumn,
                             TableColumn<CompletedOrderSearchModel, String> stockLabelColumn,
                             TableColumn<CompletedOrderSearchModel, String> companyNameColumn,
                             TableColumn<CompletedOrderSearchModel, Double> stockPriceColumn,
                             TableColumn<CompletedOrderSearchModel, Integer> amountColumn,
                             TableColumn<CompletedOrderSearchModel, String> orderedAtColumn) {
        this.orderDAO = orderDAO;
        this.userStocksTableView = userStocksTableView;
        this.userStocksObservableList = FXCollections.observableArrayList();
        this.userStocksTableView.setItems(userStocksObservableList);

        orderIdColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty().asObject());
        stockLabelColumn.setCellValueFactory(cellData -> cellData.getValue().stockLabelProperty());
        companyNameColumn.setCellValueFactory(cellData -> cellData.getValue().companyNameProperty());
        stockPriceColumn.setCellValueFactory(cellData -> cellData.getValue().stockPriceProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
        orderedAtColumn.setCellValueFactory(cellData -> cellData.getValue().orderedAtProperty());

        styleTableColumns(orderIdColumn, stockLabelColumn, companyNameColumn, stockPriceColumn, amountColumn, orderedAtColumn);
    }

    private void styleTableColumns(TableColumn<?, ?>... columns) {
        for (TableColumn<?, ?> column : columns) {
            column.setStyle("-fx-alignment: CENTER;");
        }
    }

    private void populateUserStocksTableView(int userId) {
        try {
            List<List<String>> userStocksData = orderDAO.getCompletedOrders(userId);
            userStocksObservableList.clear();

            for (List<String> stockInfo : userStocksData) {
                int orderId = Integer.parseInt(stockInfo.get(0));
                String stockLabel = stockInfo.get(1);
                String companyName = stockInfo.get(2);
                double stockPrice = Double.parseDouble(stockInfo.get(3));
                int amount = Integer.parseInt(stockInfo.get(4));
                LocalDateTime orderedAt = LocalDateTime.parse(stockInfo.get(5), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
                String orderedAtString = orderedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                CompletedOrderSearchModel stockModel = new CompletedOrderSearchModel(orderId, stockLabel, companyName, stockPrice, amount, orderedAtString);
                userStocksObservableList.add(stockModel);
            }
        } catch (Exception e) {
            System.out.println("Failed to populate user stocks table: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void sellStock(int userId, int orderId, int amount) {
        TradingSessionDAO tradingSessionDAO = new TradingSessionDAOImpl();
        PriceDAO priceDAO = new PriceDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        StockDAO stockDAO = new StockDAOImpl();
        StockOrderDAO stockOrderDAO = new StockOrderDAOImpl();

        try {
            if (!tradingSessionDAO.hasActiveTradingSession()) {
                System.out.println("There is no open trading session. Please wait for the next session to sell stocks.");
                return;
            }

            int userOrderAmount = stockOrderDAO.getOrderAmount(orderId);
            int stockId = stockOrderDAO.getStockId(orderId);
            double userBalance = userDAO.getUserBalance(userId);
            double stockMarketPrice = priceDAO.getLastPrice(stockId);
            int stockAvailableAmount = stockDAO.getAvailableStocks(stockId);
            String orderType = "sell";

            if (amount <= 0 || amount > userOrderAmount) {
                System.out.println("Invalid amount. Please enter a valid quantity.");
                return;
            }

            boolean orderPlaced = placeOrder(userId, stockId, orderType, stockMarketPrice, amount, stockOrderDAO, stockDAO);

            if (orderPlaced) {
                double saleAmount = amount * stockMarketPrice;
                double newBalance = userBalance + saleAmount;
                userDAO.updateUserBalance(userId, newBalance);
                stockDAO.updateStockQuantity(stockId, stockAvailableAmount + amount);
                stockOrderDAO.updateOrderAmount(orderId, userOrderAmount - amount);
                System.out.println("Order placed successfully.");
            } else {
                System.out.println("Failed to place order.");
            }
        } catch (Exception e) {
            System.out.println("Error selling stock: " + e.getMessage());
            e.printStackTrace();
        }
        populateUserStocksTableView(userId);
    }

    private boolean placeOrder(int userId, int stockId, String orderType, double orderPrice, int orderQuantity, StockOrderDAO stockOrderDAO, StockDAO stockDAO) {
        try {
            stockOrderDAO.placeOrder(userId, stockId, orderType, orderPrice, orderQuantity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void initialize(int userId) {
        populateUserStocksTableView(userId);
    }
}
