package org.example.stockmanagementsystem.AdminManagerClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.stockmanagementsystem.DAOclasses.TradingSessionDAO;
import org.example.stockmanagementsystem.searchingModels.TradingSessionSearchModel;

import java.sql.Timestamp;
import java.util.List;

public class TradingSessionManager {

    private final TradingSessionDAO tradingSessionDAO;
    private final TableView<TradingSessionSearchModel> tradingSessionTableView;
    private final ObservableList<TradingSessionSearchModel> tradingSessionObservableList;

    public TradingSessionManager(TradingSessionDAO tradingSessionDAO, TableView<TradingSessionSearchModel> tradingSessionTableView,
                                 TableColumn<TradingSessionSearchModel, Integer> sessionIdColumn,
                                 TableColumn<TradingSessionSearchModel, String> startTimeColumn,
                                 TableColumn<TradingSessionSearchModel, Timestamp> endTimeColumn,
                                 TableColumn<TradingSessionSearchModel, String> statusColumn,
                                 TableColumn<TradingSessionSearchModel, Integer> adminUserIdColumn) {
        this.tradingSessionDAO = tradingSessionDAO;
        this.tradingSessionTableView = tradingSessionTableView;
        this.tradingSessionObservableList = FXCollections.observableArrayList();
        this.tradingSessionTableView.setItems(tradingSessionObservableList);

        sessionIdColumn.setCellValueFactory(cellData -> cellData.getValue().sessionIdProperty().asObject());
        startTimeColumn.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty().asString());
        endTimeColumn.setCellValueFactory(cellData -> cellData.getValue().endTimeProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        adminUserIdColumn.setCellValueFactory(cellData -> cellData.getValue().adminUserIdProperty().asObject());

        styleTableColumns(sessionIdColumn, startTimeColumn, endTimeColumn, statusColumn, adminUserIdColumn);

        populateTradingSessionTableView();
    }

    private void styleTableColumns(TableColumn<?, ?>... columns) {
        for (TableColumn<?, ?> column : columns) {
            column.setStyle("-fx-alignment: CENTER;");
        }
    }

    private void populateTradingSessionTableView() {
        try {
            List<List<String>> allTradingSessions = tradingSessionDAO.getAllTradingSessions();
            tradingSessionObservableList.clear();

            for (List<String> tradingSessionInfo : allTradingSessions) {
                int sessionId = Integer.parseInt(tradingSessionInfo.get(0));
                Timestamp startTime = Timestamp.valueOf(tradingSessionInfo.get(1).replace("T", " ").replace("Z", ""));
                Timestamp endTime = null;
                if (tradingSessionInfo.get(2) != null && !tradingSessionInfo.get(2).isEmpty()) {
                    endTime = Timestamp.valueOf(tradingSessionInfo.get(2).replace("T", " ").replace("Z", ""));
                }
                String status = tradingSessionInfo.get(3);
                int adminUserId = Integer.parseInt(tradingSessionInfo.get(4));

                TradingSessionSearchModel tradingSessionModel = new TradingSessionSearchModel(sessionId, startTime, endTime, status, adminUserId);
                tradingSessionObservableList.add(tradingSessionModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        populateTradingSessionTableView();
    }
}
