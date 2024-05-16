package org.example.stockmanagementsystem;

import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.example.stockmanagementsystem.AdminManagerClasses.StockManager;
import org.example.stockmanagementsystem.DAOclasses.*;
import org.example.stockmanagementsystem.DAOclassesImplementation.*;
import org.example.stockmanagementsystem.helperClasses.InputValidator;
import org.example.stockmanagementsystem.helperClasses.StockChart;
import org.example.stockmanagementsystem.searchingModels.CompletedOrderSearchModel;
import org.example.stockmanagementsystem.searchingModels.OrderSearchModel;
import org.example.stockmanagementsystem.searchingModels.StockSearchModel;
import org.example.stockmanagementsystem.searchingModels.TransactionSearchModel;
import org.example.stockmanagementsystem.userManagerClasses.OrderManager;
import org.example.stockmanagementsystem.userManagerClasses.StockMarketManager;
import org.example.stockmanagementsystem.userManagerClasses.TransactionManager;
import org.example.stockmanagementsystem.userManagerClasses.UserStocksManager;

public class UserDashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label userBalanceLabel;

    @FXML
    private Button homeButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button myTransactionHistoryButton;

    @FXML
    private Button requestsButton;

    @FXML
    private Button stockMarketButton;

    @FXML
    private Label stockNameLabel;

    @FXML
    private AnchorPane userInfoPane;

    @FXML
    private Label userNameLabel;

    @FXML
    private LineChart<String, Number> recentStockChart;

    @FXML
    private AnchorPane dashboardStockPane;

    @FXML
    private Label maxPriceLabel;

    @FXML
    private Label minPriceLabel;

    // start transactionHistoryPane
    @FXML
    private TextField transactionSearachBar;

    @FXML
    private AnchorPane transactionHistoryPane;

    @FXML
    private TableColumn<TransactionSearchModel, Integer> transactionIdColumn;

    @FXML
    private TableColumn<TransactionSearchModel, String> transactionTypeColumn;

    @FXML
    private TableColumn<TransactionSearchModel, String> transactionStatusColumn;

    @FXML
    private TableColumn<TransactionSearchModel, Double> transactionAmountColumn;

    @FXML
    private TableColumn<TransactionSearchModel, String> transactionCreatedAtColumn;

    @FXML
    private TableColumn<TransactionSearchModel, String> transactionUpdatedAtColumn;

    @FXML
    private TableView<TransactionSearchModel> transactionTableView;

    // end transactionHistoryPane

    // Start My Orders

    @FXML
    private AnchorPane myOrdersPane;
    @FXML
    private TableColumn<OrderSearchModel, String> orderDateColumn;
    @FXML
    private TableColumn<OrderSearchModel, Integer> orderIdColumn;
    @FXML
    private TableColumn<OrderSearchModel, Double> orderPriceColumn;
    @FXML
    private TableColumn<OrderSearchModel, Integer> orderQuantityColumn;
    @FXML
    private TableColumn<OrderSearchModel, String> orderStatusColumn;
    @FXML
    private TableColumn<OrderSearchModel, String> orderTypeColumn;

    @FXML
    private TableView<OrderSearchModel> ordersTableView;

    @FXML
    private Button myOrdersButton;

    // end My Orders

    // start stock market
    @FXML
    private AnchorPane stockMarketPane;

    @FXML
    private TableView<StockSearchModel> StockMarketTableView;

    @FXML
    private TableColumn<StockSearchModel, Integer> stockMarketAvailableStocksColumn;

    @FXML
    private TableColumn<StockSearchModel, String> stockMarketCompanyNameColumn;

    @FXML
    private TableColumn<StockSearchModel, String> stockMarketCreatedAtColumn;

    @FXML
    private TableColumn<StockSearchModel, Double> stockMarketDividendsColumn;

    @FXML
    private TableColumn<StockSearchModel, Double> stockMarketInitialPriceColumn;

    @FXML
    private TableColumn<StockSearchModel, Double> stockMarketTradingPriceColumn;

    @FXML
    private TableColumn<StockSearchModel, Double> stockMarketProfitPercentageColumn;

    @FXML
    private TableColumn<StockSearchModel, Integer> stockMarketStockIdColumn;

    @FXML
    private TableColumn<StockSearchModel, String> stockMarketStockLabelColumn;

    @FXML
    private Button stockMarketBuyButton;

    @FXML
    private LineChart<String, Number> stockMarketChart;

    @FXML
    private Label stockMarketMaxPriceLabel;

    @FXML
    private Label stockMarketMinPriceLabel;

    @FXML
    private Label stockMarketStockNameLabel;

    @FXML
    private TextField stockMarketSearchBar;

    @FXML
    private TextField stockMarketAmountField;

    @FXML
    private Button exportToCVSButton;
    // end stock market

    // Start My stocks
    @FXML
    private Button myStocksButton;
    @FXML
    private Label sellStockForLabel;
    @FXML
    private TableColumn<CompletedOrderSearchModel, Integer> myStocksAmountColumn;
    @FXML
    private TableColumn<CompletedOrderSearchModel, String> myStocksCompanyNameColumn;
    @FXML
    private TableColumn<CompletedOrderSearchModel, Integer> myStocksOrderIdColumn;
    @FXML
    private TableColumn<CompletedOrderSearchModel, String> myStocksOrderedAtColumn;
    @FXML
    private TableColumn<CompletedOrderSearchModel, String> myStocksStockLabelColumn;
    @FXML
    private AnchorPane myStocksPane;
    @FXML
    private TableColumn<CompletedOrderSearchModel, Double> myStocksStockPriceColumn;
    @FXML
    private TableView<CompletedOrderSearchModel> myStocksTableView;
    @FXML
    private TextField myStocksAmountField;

    // end My stocks

    // Start deposit withdraw
    @FXML
    private Button depositButton;
    @FXML
    private Button withdrawButton;
    @FXML
    private Button depositWithdrawAmountButton;
    @FXML
    private TextField depositAmountField;
    @FXML
    private TextField withdrawAmountField;
    @FXML
    private AnchorPane depositWithdrawPane;
    // End deposit withdraw

    private TransactionManager transactionManager;
    private StockMarketManager stockMarketManager;
    private OrderManager orderManager;
    private UserStocksManager userStocksManager;
    private String userId;
    private String firstName;
    private String lastName;
    private StockChart homeStockChartViewer;
    private StockChart marketStockChartViewer;

    void initialize(String userId) {
        this.userId = userId;
        initializeTransactionManager();
        initializeStockChartViewers();
        initializeStockManager();
        initializeOrderManager();
        initializeUserStocksManager();
        initialState();
    }

    // Start initializing manager classes
    // ======================>
    void initializeStockChartViewers() {
        homeStockChartViewer = new StockChart(recentStockChart, stockNameLabel, maxPriceLabel, minPriceLabel);
        marketStockChartViewer = new StockChart(stockMarketChart, stockMarketStockNameLabel, stockMarketMaxPriceLabel,
                stockMarketMinPriceLabel);
    }

    void initializeTransactionManager() {
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        this.transactionManager = new TransactionManager(transactionTableView, transactionIdColumn,
                transactionTypeColumn, transactionStatusColumn, transactionAmountColumn,
                transactionCreatedAtColumn, transactionUpdatedAtColumn);
        this.transactionManager.initialize(transactionSearachBar,
                transactionDAO.getUserTransactionHistory(Integer.parseInt(userId)));
    }

    void initializeStockManager() {
        StockDAO stockDAO = new StockDAOImpl();
        PriceDAO priceDAO = new PriceDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        this.stockMarketManager = new StockMarketManager(StockMarketTableView, priceDAO, stockDAO, userDAO);
    }

    void initializeOrderManager() {
        StockOrderDAO stockOrderDAO = new StockOrderDAOImpl();
        orderManager = new OrderManager(stockOrderDAO, ordersTableView, orderIdColumn, orderDateColumn,
                orderPriceColumn,
                orderQuantityColumn, orderStatusColumn, orderTypeColumn);
        orderManager.initialize(Integer.parseInt(userId));
    }

    private void setupStockManagement() {
        StockDAO stockDAO = new StockDAOImpl();
        StockManager stockManager = new StockManager(stockDAO, StockMarketTableView, stockMarketStockIdColumn,
                stockMarketStockLabelColumn,
                stockMarketCompanyNameColumn, stockMarketInitialPriceColumn, stockMarketTradingPriceColumn,
                stockMarketDividendsColumn, stockMarketAvailableStocksColumn, stockMarketProfitPercentageColumn,
                stockMarketCreatedAtColumn);
        stockManager.initialize(stockMarketSearchBar);
    }

    private void initializeUserStocksManager() {
        StockOrderDAO stockOrderDAO = new StockOrderDAOImpl();
        userStocksManager = new UserStocksManager(stockOrderDAO, myStocksTableView,
                myStocksOrderIdColumn, myStocksStockLabelColumn, myStocksCompanyNameColumn,
                myStocksStockPriceColumn, myStocksAmountColumn, myStocksOrderedAtColumn);
        userStocksManager.initialize(Integer.parseInt(userId));
    }

    // End initializing manager classes
    // ======================>

    // Start initial state
    // ======================>

    private void initialState() {
        UserDAO userDAO = new UserDAOImpl();
        setInvisible();
        setUserDetails(userId);
        userInfoPane.setVisible(true);
        dashboardStockPane.setVisible(true);
        userNameLabel.setText(firstName + " " + lastName);
        userBalanceLabel.setText(String.valueOf(userDAO.getUserBalance(Integer.parseInt(userId))) + "$");
        homeStockChartViewer = new StockChart(recentStockChart, stockNameLabel, maxPriceLabel, minPriceLabel);
        marketStockChartViewer = new StockChart(stockMarketChart, stockMarketStockNameLabel, stockMarketMaxPriceLabel,
                stockMarketMinPriceLabel);
        fetchMostRecentStockData();
        setupStockManagement();
    }

    private void fetchMostRecentStockData() {
        StockDAO stockDAO = new StockDAOImpl();
        PriceDAO priceDAO = new PriceDAOImpl();
        int mostRecentStockId = stockDAO.getMostRecentStockId();
        if (mostRecentStockId != -1) {
            homeStockChartViewer.displayStockChart(mostRecentStockId, priceDAO, stockDAO);
        } else {
            System.out.println("No recent stock found.");
        }
    }

    // End initial state
    // ======================>

    // Start Stock Market
    // ======================>
    @FXML
    void stockMarketButtonAction(ActionEvent event) {
        setInvisible();
        stockMarketPane.setVisible(true);
        initializeStockManager();
    }

    @FXML
    void stockMarketTableViewMouseClicked(MouseEvent event) {
        StockDAO stockDAO = new StockDAOImpl();
        PriceDAO priceDAO = new PriceDAOImpl();
        if (event.getClickCount() == 1) {
            StockSearchModel selectedStock = StockMarketTableView.getSelectionModel().getSelectedItem();
            if (selectedStock != null) {
                int stockId = selectedStock.getStockId();
                marketStockChartViewer.displayStockChart(stockId, priceDAO, stockDAO);
            }
        }
    }
    // end Stock Market
    // ======================>

    // Start my stocks
    // ======================>
    @FXML
    void myStocksTableViewOnMouseClicked(MouseEvent event) {
        PriceDAO priceDAO = new PriceDAOImpl();
        StockOrderDAO stockOrderDAO = new StockOrderDAOImpl();
        if (event.getClickCount() == 1) {
            CompletedOrderSearchModel selectedStock = myStocksTableView.getSelectionModel().getSelectedItem();
            if (selectedStock != null) {
                int orderId = selectedStock.getOrderId();
                int stockId = stockOrderDAO.getStockId(orderId);
                double stockMarketPrice = priceDAO.getLastPrice(stockId);
                double stockUserPrice = selectedStock.getStockPrice();
                if (stockMarketPrice < stockUserPrice) {
                    sellStockForLabel.setText("Sell Stock for " + stockMarketPrice + "$ (Loss)");
                    sellStockForLabel.setStyle("-fx-text-fill: red;");
                } else if (stockMarketPrice > stockUserPrice) {
                    sellStockForLabel.setText("Sell Stock for " + stockMarketPrice + "$ (Profit)");
                    sellStockForLabel.setStyle("-fx-text-fill: green;");
                } else {
                    sellStockForLabel.setText("Sell Stock for " + stockMarketPrice + "$ (No Change)");
                    sellStockForLabel.setStyle("-fx-text-fill: black;");
                }
            }
        }
    }

    @FXML
    void sellStockButtonAction(ActionEvent event) {
        CompletedOrderSearchModel selectedStock = myStocksTableView.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            int orderId = selectedStock.getOrderId();
            try {
                int amount = Integer.parseInt(myStocksAmountField.getText().trim());
                userStocksManager.sellStock(Integer.parseInt(userId), orderId, amount);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        } else {
            System.out.println("Please select a stock to sell.");
        }
    }

    @FXML
    void myStocksButtonAction(ActionEvent event) {
        setInvisible();
        myStocksPane.setVisible(true);
        userStocksManager.initialize(Integer.parseInt(userId));
    }
    // end my stocks
    // ======================>

    // start deposit withdraw
    // ======================>

    @FXML
    void depositWithdrawAmountButtonAction(ActionEvent event) {
        setInvisible();
        depositWithdrawPane.setVisible(true);
    }

    @FXML
    void depositButtonAction(ActionEvent event) {
        UserDAO userDAO = new UserDAOImpl();
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        if (InputValidator.validateDoubleField(depositAmountField)) {
            double depositAmount = Double.parseDouble(depositAmountField.getText());
            transactionDAO.deposit(Integer.parseInt(userId), depositAmount);
            userBalanceLabel.setText(String.valueOf(userDAO.getUserBalance(Integer.parseInt(userId))) + "$");
            depositAmountField.clear();
        } else {
            System.out.println("Please enter a valid deposit amount.");
        }
    }

    @FXML
    void withdrawButtonAction(ActionEvent event) {
        UserDAO userDAO = new UserDAOImpl();
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        if (InputValidator.validateDoubleField(withdrawAmountField)) {
            double withdrawAmount = Double.parseDouble(withdrawAmountField.getText());
            double userBalance = userDAO.getUserBalance(Integer.parseInt(userId));
            if (userBalance >= withdrawAmount) {
                transactionDAO.withdraw(Integer.parseInt(userId), withdrawAmount);
                userDAO.updateUserBalance(Integer.parseInt(userId), userBalance - withdrawAmount);
                userBalanceLabel.setText(String.valueOf(userDAO.getUserBalance(Integer.parseInt(userId))) + "$");
                withdrawAmountField.clear();
            } else {
                System.out.println("Insufficient balance to withdraw.");
            }
        } else {
            System.out.println("Please enter a valid withdraw amount.");
        }
    }

    // end deposit withdraw
    // ======================>

    // Start my Orders
    // ======================>

    @FXML
    void myOrdersButtonAction(ActionEvent event) {
        setInvisible();
        myOrdersPane.setVisible(true);
        orderManager.initialize(Integer.parseInt(userId));
    }

    // End my Orders
    // ======================>

    @FXML
    void homeButtonAction(ActionEvent event) {
        setInvisible();
        initialState();
    }

    @FXML
    void logoutButtonAction(ActionEvent event) {
        Platform.exit();
    }

    // Start Transaction
    // ======================>
    @FXML
    void myTransactionHistoryButtonAction(ActionEvent event) {
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        setInvisible();
        transactionHistoryPane.setVisible(true);
        transactionManager.initialize(transactionSearachBar,
                transactionDAO.getUserTransactionHistory(Integer.parseInt(userId)));
    }
    // end Transaction
    // ======================>

    @FXML
    void stockMarketBuyButtonAction(ActionEvent event) {
        stockMarketManager.buyStock(stockMarketAmountField, Integer.parseInt(userId));
        initializeStockManager();
    }

    @FXML
    void exportToCVSButtonAction() {
        stockMarketManager.exportToCSV();
    }

    // Helper functions
    public void setUserDetails(String userId) {
        UserDAO userDAO = new UserDAOImpl();
        this.userId = userId;
        this.firstName = userDAO.getUserInfo(Integer.parseInt(userId))[0];
        this.lastName = userDAO.getUserInfo(Integer.parseInt(userId))[1];
    }

    private void setInvisible() {
        dashboardStockPane.setVisible(false);
        userInfoPane.setVisible(false);
        transactionHistoryPane.setVisible(false);
        stockMarketPane.setVisible(false);
        myOrdersPane.setVisible(false);
        myStocksPane.setVisible(false);
        depositWithdrawPane.setVisible(false);
    }
}
