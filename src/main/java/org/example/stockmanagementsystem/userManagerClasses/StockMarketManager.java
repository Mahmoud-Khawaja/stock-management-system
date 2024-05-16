package org.example.stockmanagementsystem.userManagerClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import org.example.stockmanagementsystem.DAOclasses.PriceDAO;
import org.example.stockmanagementsystem.DAOclasses.StockDAO;
import org.example.stockmanagementsystem.DAOclasses.TradingSessionDAO;
import org.example.stockmanagementsystem.DAOclasses.UserDAO;
import org.example.stockmanagementsystem.DAOclassesImplementation.StockOrderDAOImpl;
import org.example.stockmanagementsystem.DAOclassesImplementation.TradingSessionDAOImpl;
import org.example.stockmanagementsystem.helperClasses.InputValidator;
import org.example.stockmanagementsystem.searchingModels.StockSearchModel;

public class StockMarketManager {

    private final TableView<StockSearchModel> stockMarketTableView;
    private final PriceDAO priceDAO;
    private final StockDAO stockDAO;
    private final UserDAO userDAO;

    public StockMarketManager(TableView<StockSearchModel> stockMarketTableView, PriceDAO priceDAO, StockDAO stockDAO, UserDAO userDAO) {
        this.stockMarketTableView = stockMarketTableView;
        this.priceDAO = priceDAO;
        this.stockDAO = stockDAO;
        this.userDAO = userDAO;
    }

    public void exportToCSV() {
        StockSearchModel selectedStock = stockMarketTableView.getSelectionModel().getSelectedItem();
        if (selectedStock == null) {
            showError("Please select a stock to export.");
            return;
        }

        int stockId = selectedStock.getStockId();
        List<String> priceHistory = priceDAO.getPriceHistory(stockId);

        if (priceHistory.isEmpty()) {
            showError("No price history available for the selected stock.");
            return;
        }

        String directoryPath = "org/example/stockmanagementsystem/CVSFiles/";
        String fileName = directoryPath + "stock_" + selectedStock.getCompanyName() + "_price_history.csv";

        try {
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            PrintWriter writer = new PrintWriter(new File(fileName));
            writer.println("Date,Price");

            for (String entry : priceHistory) {
                writer.println(entry);
            }

            writer.close();
            showSuccess("Price history exported to " + fileName);
        } catch (FileNotFoundException e) {
            showError("Failed to export price history: " + e.getMessage());
        }
    }

    public void buyStock(TextField stockMarketAmountField, int userId) {
        TradingSessionDAO tradingSessionDAO = new TradingSessionDAOImpl();

        if (!tradingSessionDAO.hasActiveTradingSession()) {
            showError("There is no open trading session. Please wait for the next session to buy stocks.");
            return;
        }

        StockSearchModel selectedStock = stockMarketTableView.getSelectionModel().getSelectedItem();
        if (selectedStock == null) {
            showError("Please select a stock to buy.");
            return;
        }

        int stockId = selectedStock.getStockId();
        double orderPrice = selectedStock.getTradingPrice();
        String orderType = "buy";

        if (!InputValidator.validateIntegerField(stockMarketAmountField)) {
            showError("Please enter a valid quantity.");
            return;
        }

        int orderQuantity = Integer.parseInt(stockMarketAmountField.getText());
        if (orderQuantity <= 0) {
            showError("Please enter a valid quantity.");
            return;
        }

        double totalOrderCost = orderPrice * orderQuantity;
        double userBalance = userDAO.getUserBalance(userId);

        if (userBalance < totalOrderCost) {
            showError("Insufficient balance. Please deposit funds to place the order.");
            return;
        }

        boolean orderPlaced = placeOrder(userId, stockId, orderType, orderPrice, orderQuantity);
        if (orderPlaced) {
            double newBalance = userBalance - totalOrderCost;
            userDAO.updateUserBalance(userId, newBalance);
            showSuccess("Order placed successfully.");
        } else {
            showError("Failed to place order.");
        }
    }

    private boolean placeOrder(int userId, int stockId, String orderType, double orderPrice, int orderQuantity) {
        StockOrderDAOImpl stockOrderDAO = new StockOrderDAOImpl();
        try {
            stockOrderDAO.placeOrder(userId, stockId, orderType, orderPrice, orderQuantity);
            int availableStocks = stockDAO.getAvailableStocks(stockId);
            stockDAO.updateStockQuantity(stockId, availableStocks-orderQuantity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showError(String message) {
        System.out.println("Error: " + message);
    }

    private void showSuccess(String message) {
        System.out.println("Success: " + message);
    }
}
