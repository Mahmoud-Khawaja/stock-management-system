package org.example.stockmanagementsystem;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.example.stockmanagementsystem.AdminManagerClasses.ApprovalSystemTransactionManager;
import org.example.stockmanagementsystem.AdminManagerClasses.TradingSessionManager;
import org.example.stockmanagementsystem.DAOclasses.*;
import org.example.stockmanagementsystem.DAOclassesImplementation.*;
import org.example.stockmanagementsystem.helperClasses.StockChart;
import org.example.stockmanagementsystem.AdminManagerClasses.StockManager;
import org.example.stockmanagementsystem.AdminManagerClasses.UserManager;
import org.example.stockmanagementsystem.searchingModels.*;
import org.example.stockmanagementsystem.userManagerClasses.PriceHistoryManager;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

public class AdminDashboardController {

    // Start User Management Dashboard Configurations
    //============================>
    @FXML
    private AnchorPane userManagementDashboard;

    @FXML
    private TableView<UserSearchModel> userTableView;

    @FXML
    private TableColumn<UserSearchModel,Double> userBalanceColumn;

    @FXML
    private TableColumn<UserSearchModel,String> userCreatedAtColumn;

    @FXML
    private TableColumn<UserSearchModel,String> userFirstNameColumn;

    @FXML
    private TableColumn<UserSearchModel,String> userIsAdminColumn;

    @FXML
    private TableColumn<UserSearchModel,String> userLastNameColumn;

    @FXML
    private TableColumn<UserSearchModel,Integer> userIdColumn;

    @FXML
    private TableColumn<UserSearchModel,String> usernameColumn;

    @FXML
    private TextField usersSearchBar;

    @FXML
    private Button removeUserButton;

    // End User Management Dashboard Configurations
    //============================>

    // Start Stock Chart Configurations
    //============================>
    @FXML
    private LineChart<String, Number> recentStockChart;

    @FXML
    private AnchorPane dashboardStockPane;

    @FXML
    private Label maxPriceLabel;

    @FXML
    private Label minPriceLabel;
    // End Stock Chart Configurations
    //============================>
    // Left Buttons
    @FXML
    private Button priceHistoryButton;

    @FXML
    private Button stockManagementButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Button tradingSessionControlButton;

    @FXML
    private Button userManagementButton;

    @FXML
    private Button homeButton;
    // End Left Buttons
    //============================>

    // Start User Information Labels
    //============================>

    @FXML
    private Label userNameLabel;

    @FXML
    private Label requestsLabel;

    @FXML
    private Label stockNameLabel;

    @FXML
    private AnchorPane userInfoPane;

    @FXML
    private Label availableStocksLabel;

    @FXML
    private Label CurrentUsersLabel;

    // End User Information Labels
    //============================>

    // start Stock Management configuration
    //============================>

    @FXML
    private AnchorPane StockManagementPane;

    @FXML
    private TableColumn<StockSearchModel, Integer> availableStocksColumn;

    @FXML
    private TableColumn<StockSearchModel, String> stockCompanyNameColumn;

    @FXML
    private TableColumn<StockSearchModel, String> stockCreatedAtcolumn;

    @FXML
    private TableColumn<StockSearchModel, Double> stockDividendsColumn;

    @FXML
    private TableColumn<StockSearchModel, Integer> stockIdColumn;

    @FXML
    private TableColumn<StockSearchModel, Double> stockInitialPriceColumn;

    @FXML
    private TableColumn<StockSearchModel, String> stockLabelColumn;

    @FXML
    private TableColumn<StockSearchModel, Double> stockProfitPercentageColumn;

    @FXML
    private TableView<StockSearchModel> stockTableView;

    @FXML
    private TableColumn<StockSearchModel, Double> stockTradingPriceColumn;

    @FXML
    private TextField stockCompanyNameField;

    @FXML
    private TextField stockDividendsField;

    @FXML
    private TextField stockInitialPriceField;

    @FXML
    private TextField stockProfitField;

    @FXML
    private TextField stockTradingPriceField;

    @FXML
    private TextField stockCompanyLabelField;

    @FXML
    private TextField availableStocksField;

    @FXML
    private TextField stockSearchBar;

    @FXML
    private Button removeStockButton;

    @FXML
    private Button addStockButton;

    // end Stock Management configuration
    //============================>

    // Start Trading Sessions
    //============================>
    @FXML
    private TableColumn<TradingSessionSearchModel,Integer> TradingSessionAdminUserIdColumn;
    @FXML
    private Button closeTradingSessionButton;
    @FXML
    private Button openTradingSessionButton;
    @FXML
    private TableColumn<TradingSessionSearchModel, Integer> tradingSessionIdColumn;
    @FXML
    private TableColumn<TradingSessionSearchModel, Timestamp> tradingSessionIdEndingTimeColumn;
    @FXML
    private AnchorPane tradingSessionPane;
    @FXML
    private TableColumn<TradingSessionSearchModel,String> tradingSessionStatusColumn;
    @FXML
    private TableView<TradingSessionSearchModel> tradingSessionTableView;
    @FXML
    private TableColumn<TradingSessionSearchModel, String> tradingSessionStartingTimeColumn;
    // End Trading Sessions
    //============================>

    // Start Transaction Approval System
    //============================>

    @FXML
    private  AnchorPane transactionApprovalSystemPane;

    @FXML
    private TableView<ApprovalSystemTransactionSearchModel> approvalSystemTableView;
    @FXML
    private Button approvalSystemButton;

    @FXML
    private TableColumn<ApprovalSystemTransactionSearchModel, String> approvalSystemCreatedAtColumn;

    @FXML
    private Button approvalSystemDeclineButton;

    @FXML
    private Button approvalSystemApproveButton;

    @FXML
    private TextField approvalSystemSearchBar;
    @FXML
    private TableColumn<ApprovalSystemTransactionSearchModel, Integer> approvalSystemTransactionIdColumn;

    @FXML
    private TableColumn<ApprovalSystemTransactionSearchModel, String> approvalSystemTransactionTypeColumn;
    @FXML
    private TableColumn<ApprovalSystemTransactionSearchModel, String> approvalSystemUpdatedAtColumn;
    @FXML
    private TableColumn<ApprovalSystemTransactionSearchModel, Integer> approvalSystemUserIdColumn;
    @FXML
    private TableColumn<ApprovalSystemTransactionSearchModel,String> approvalSystemUsernameColumn;
    @FXML
    private TableColumn<ApprovalSystemTransactionSearchModel, String> approvalSystemStatusColumn;
    @FXML
    private TableColumn<ApprovalSystemTransactionSearchModel, Double> approvalSystemAmountColumn;

    // End Transaction Approval System
    //============================>

    // Start Price History
    //============================>
    @FXML
    private AnchorPane priceHistoryPane;
    @FXML
    private TableView<PriceHistorySearchModel> priceHistoryTableView;
    @FXML
    private TextField priceHistorySearchBar;
    @FXML
    private TableColumn<PriceHistorySearchModel, String> priceHistoryCompanyNameColumn;
    @FXML
    private TableColumn<PriceHistorySearchModel, String> priceHistoryDateColumn;
    @FXML
    private TableColumn<PriceHistorySearchModel, Double> priceHistoryPriceColumn;
    @FXML
    private TableColumn<PriceHistorySearchModel, Integer> priceHistoryStockIdColumn;

    @FXML
    private Button priceHistoryUpdatePriceButton;
    @FXML
    private TextField priceHistoryUpdatePriceField;
    // End Price History
    //============================>

    // start Helpers
    private UserManager userManager;
    private StockManager stockManager;
    private TradingSessionManager tradingSessionManager;
    private ApprovalSystemTransactionManager approvalSystemTransactionManager;
    private PriceHistoryManager priceHistoryManager;
    private String userId;
    private String firstName;
    private String lastName;
    private StockChart stockChart;
    // end Helpers


    //start functions
    //============================>
    // start initialize
    @FXML
    void initialize(String userId) {
        this.userId = userId;
        initialState();
        setupUserManagement();
        setupStockManagement();
        setupTradingSessionManager();
        setupPriceHistoryManager();
        setupApprovalSystemTransactionManager();
    }
    private void setupTradingSessionManager() {
        TradingSessionDAO tradingSessionDAO = new TradingSessionDAOImpl();
        tradingSessionManager = new TradingSessionManager(tradingSessionDAO, tradingSessionTableView,
                tradingSessionIdColumn,  tradingSessionStartingTimeColumn, tradingSessionIdEndingTimeColumn,
                tradingSessionStatusColumn, TradingSessionAdminUserIdColumn);
    }
    private void setupUserManagement() {
        UserDAO userDAO = new UserDAOImpl();
        userManager = new UserManager(userDAO, userTableView, userIdColumn, userFirstNameColumn,
                userLastNameColumn, usernameColumn, userCreatedAtColumn, userIsAdminColumn,
                userBalanceColumn, userId);
        userManager.initialize(usersSearchBar);
    }

    private void setupStockManagement() {
        StockDAO stockDAO = new StockDAOImpl();
        stockManager = new StockManager(stockDAO, stockTableView, stockIdColumn, stockLabelColumn,
                stockCompanyNameColumn, stockInitialPriceColumn, stockTradingPriceColumn,
                stockDividendsColumn, availableStocksColumn, stockProfitPercentageColumn,
                stockCreatedAtcolumn);
        stockManager.initialize(stockSearchBar);
    }
    private void setupApprovalSystemTransactionManager() {
        approvalSystemTransactionManager = new ApprovalSystemTransactionManager(
                approvalSystemTableView,approvalSystemTransactionIdColumn,
                approvalSystemUserIdColumn,approvalSystemAmountColumn,approvalSystemTransactionTypeColumn,
                approvalSystemStatusColumn,approvalSystemCreatedAtColumn,approvalSystemUpdatedAtColumn,
                approvalSystemUsernameColumn
        );
        approvalSystemTransactionManager.initialize(approvalSystemSearchBar);
    }
    private void setupPriceHistoryManager() {
        StockDAO stockDAO = new StockDAOImpl();
        this.priceHistoryManager = new PriceHistoryManager(
                priceHistoryTableView,
                priceHistoryCompanyNameColumn,
                priceHistoryPriceColumn,
                priceHistoryDateColumn,
                priceHistoryStockIdColumn
        );
        // Assuming you have a method to fetch the stock data
        Map<Integer, Map<String, Object>> stocksWithLastPrice = stockDAO.getAllStocksWithLastPrice();
        this.priceHistoryManager.initialize(priceHistorySearchBar, stocksWithLastPrice);
    }
    // end initialize


    // start home
    //============================>
    @FXML
    void homeButtonAction(ActionEvent event) {
        setInvisible();
        userInfoPane.setVisible(true);
        dashboardStockPane.setVisible(true);
        fetchMostRecentStockData();
    }
    private void initialState(){
        StockDAO stockDAO = new StockDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        setUserDetails(userId);
        setInvisible();
        userInfoPane.setVisible(true);
        dashboardStockPane.setVisible(true);
        CurrentUsersLabel.setText(userDAO.getAllUsers().size() + "");
        availableStocksLabel.setText(stockDAO.getAllStocks().size() + "");
        requestsLabel.setText(transactionDAO.getAdminTransactionHistory().size()+ "");
        userNameLabel.setText(firstName + " " + lastName);
        stockChart = new StockChart(recentStockChart, stockNameLabel, maxPriceLabel, minPriceLabel);
        fetchMostRecentStockData();
    }

    private void fetchMostRecentStockData() {
        StockDAO stockDAO = new StockDAOImpl();
        PriceDAO priceDAO = new PriceDAOImpl();
        int mostRecentStockId = stockDAO.getMostRecentStockId();
        if (mostRecentStockId != -1) {
            stockChart.displayStockChart(mostRecentStockId, priceDAO, stockDAO);
        } else {
            System.out.println("No recent stock found.");
        }
    }
    // end home
    //============================>

    // Start Transaction Approval System
    //============================>

    @FXML
    void approvalSystemButtonAction(ActionEvent event) {
        setInvisible();
        transactionApprovalSystemPane.setVisible(true);
    }

    @FXML
    void approvalSystemApproveButtonAction(ActionEvent event) {
        ApprovalSystemTransactionSearchModel selectedTransaction = approvalSystemTableView.getSelectionModel().getSelectedItem();
        UserDAO userDAO = new UserDAOImpl();
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        if (selectedTransaction != null) {
            int selectedUserId = selectedTransaction.getUserId();
            int selectedTransactionId = selectedTransaction.getTransactionId();
            double selectedTransactionAmount = selectedTransaction.getTransactionAmount();
            double selectedUserBalance = userDAO.getUserBalance(selectedUserId);
            String transactionType = selectedTransaction.getTransactionType();
            userDAO.updateUserBalance(selectedUserId, Objects.equals(transactionType, "deposit") ?selectedUserBalance+selectedTransactionAmount :selectedUserBalance );
            transactionDAO.completeTransaction(selectedTransactionId);
        } else {
            System.out.println("Please select a transaction");
        }
        approvalSystemTransactionManager.initialize(approvalSystemSearchBar);
        requestsLabel.setText(transactionDAO.getAdminTransactionHistory().size()+ "");
    }
    @FXML
    void approvalSystemDeclineButtonAction(ActionEvent event) {
        UserDAO userDAO = new UserDAOImpl();
        ApprovalSystemTransactionSearchModel selectedTransaction = approvalSystemTableView.getSelectionModel().getSelectedItem();
        TransactionDAO transactionDAO = new TransactionDAOImpl();
        if (selectedTransaction != null) {
            int selectedUserId = selectedTransaction.getUserId();
            int selectedTransactionId = selectedTransaction.getTransactionId();
            double selectedTransactionAmount = selectedTransaction.getTransactionAmount();
            double selectedUserBalance = userDAO.getUserBalance(selectedUserId);
            String transactionType = selectedTransaction.getTransactionType();
            transactionDAO.cancelTransaction(selectedTransactionId);
            userDAO.updateUserBalance(selectedUserId, Objects.equals(transactionType, "deposit") ?selectedUserBalance :selectedUserBalance+selectedTransactionAmount);
        } else {
            System.out.println("Please select a transaction");
        }
        approvalSystemTransactionManager.initialize(approvalSystemSearchBar);
        requestsLabel.setText(transactionDAO.getAdminTransactionHistory().size()+ "");
    }

    // End Transaction Approval System
    //============================>

    @FXML
    void logoutButtonAction(ActionEvent event) {
        Platform.exit();
    }

    //Start Trading Sessions
    @FXML
    void closeTradingSessionButtonAction(ActionEvent event) {
        TradingSessionDAO tradingSessionDAO = new TradingSessionDAOImpl();
        tradingSessionDAO.closeTradingSession();
        tradingSessionManager.initialize();
    }
    @FXML
    void openTradingSessionButtonAction(ActionEvent event) {
        TradingSessionDAO tradingSessionDAO = new TradingSessionDAOImpl();
        tradingSessionDAO.openTradingSession(Integer.parseInt(userId));
        tradingSessionManager.initialize();
    }
    @FXML
    void tradingSessionControlButtonAction(ActionEvent event) {
        setInvisible();
        tradingSessionPane.setVisible(true);
        tradingSessionManager.initialize();
    }

    // end Trading Sessions

    // start stock management
    @FXML
    void stockManagementButtonAction(ActionEvent event) {
        setInvisible();
        StockManagementPane.setVisible(true);
        userManager.initialize(usersSearchBar);
    }
    @FXML
    void addStockButtonAction(ActionEvent event) {
        StockDAO stockDAO = new StockDAOImpl();
        stockManager.addStock(stockCompanyLabelField, stockCompanyNameField,
                stockInitialPriceField, stockTradingPriceField,
                stockDividendsField, availableStocksField,
                stockProfitField);
        availableStocksLabel.setText(stockDAO.getAllStocks().size() + "");
    }

    @FXML
    void removeStockButtonAction(ActionEvent event) {
        StockDAO stockDAO = new StockDAOImpl();
        stockManager.removeStock();
        availableStocksLabel.setText(stockDAO.getAllStocks().size() + "");
    }
    // end stock management

    // start user Management
    @FXML
    void userManagementButtonAction(ActionEvent event) {
        setInvisible();
        userManagementDashboard.setVisible(true);
        userManager.initialize(usersSearchBar);
    }

    @FXML
    void removeUserButtonAction(ActionEvent event) {
        UserDAO userDAO = new UserDAOImpl();
        userManager.removeUser();
        CurrentUsersLabel.setText(userDAO.getAllUsers().size() + "");
    }

    // end user Management
    // start Price History
    @FXML
    void priceHistoryButtonAction(ActionEvent event) {
    setInvisible();
    priceHistoryPane.setVisible(true);
    }

    @FXML
    private void priceHistoryUpdatePriceButtonAction(ActionEvent event) {
        PriceHistorySearchModel selectedPriceHistory = priceHistoryTableView.getSelectionModel().getSelectedItem();
        if (selectedPriceHistory == null) {
            System.out.println("Please select a price history entry.");
            return;
        }
        String newPriceText = priceHistoryUpdatePriceField.getText().trim();
        if (newPriceText.isEmpty()) {
            System.out.println("Please enter a new price.");
            return;
        }
        double newPrice;
        try {
            newPrice = Double.parseDouble(newPriceText);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid price value.");
            return;
        }

        if (newPrice <= 0) {
            System.out.println("Please enter a positive price value.");
            return;
        }
        int stockId = selectedPriceHistory.getStockId();

        PriceDAO priceDAO = new PriceDAOImpl();

        priceHistoryManager.updatePrice(stockId, newPrice);
    }
    // End Price History


    // start helper functions

    private void setInvisible() {
        dashboardStockPane.setVisible(false);
        userInfoPane.setVisible(false);
        userManagementDashboard.setVisible(false);
        StockManagementPane.setVisible(false);
        tradingSessionPane.setVisible(false);
        transactionApprovalSystemPane.setVisible(false);
        priceHistoryPane.setVisible(false);
    }

    public void setUserDetails(String userId) {
        UserDAO userDAO = new UserDAOImpl();
        this.userId = userId;
        this.firstName = userDAO.getUserInfo(Integer.parseInt(userId))[0];
        this.lastName = userDAO.getUserInfo(Integer.parseInt(userId))[1];
    }

}
