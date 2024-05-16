package org.example.stockmanagementsystem.userManagerClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.stockmanagementsystem.DAOclasses.StockOrderDAO;
import org.example.stockmanagementsystem.DAOclassesImplementation.StockOrderDAOImpl;
import org.example.stockmanagementsystem.searchingModels.OrderSearchModel;

import java.util.List;

public class OrderManager {

    private final StockOrderDAO orderDAO;
    private final TableView<OrderSearchModel> ordersTableView;
    private final ObservableList<OrderSearchModel> ordersObservableList;

    public OrderManager(StockOrderDAO orderDAO, TableView<OrderSearchModel> ordersTableView,
            TableColumn<OrderSearchModel, Integer> orderIdColumn,
            TableColumn<OrderSearchModel, String> orderDateColumn,
            TableColumn<OrderSearchModel, Double> orderPriceColumn,
            TableColumn<OrderSearchModel, Integer> orderQuantityColumn,
            TableColumn<OrderSearchModel, String> orderStatusColumn,
            TableColumn<OrderSearchModel, String> orderTypeColumn) {
        this.orderDAO = orderDAO;
        this.ordersTableView = ordersTableView;
        this.ordersObservableList = FXCollections.observableArrayList();
        this.ordersTableView.setItems(ordersObservableList);

        orderIdColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty().asObject());
        orderDateColumn.setCellValueFactory(cellData -> cellData.getValue().orderDateProperty());
        orderPriceColumn.setCellValueFactory(cellData -> cellData.getValue().orderPriceProperty().asObject());
        orderQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().orderQuantityProperty().asObject());
        orderStatusColumn.setCellValueFactory(cellData -> cellData.getValue().orderStatusProperty());
        orderTypeColumn.setCellValueFactory(cellData -> cellData.getValue().orderTypeProperty());

        styleTableColumns(orderIdColumn, orderDateColumn, orderPriceColumn, orderQuantityColumn, orderStatusColumn,
                orderTypeColumn);
    }

    private void styleTableColumns(TableColumn<?, ?>... columns) {
        for (TableColumn<?, ?> column : columns) {
            column.setStyle("-fx-alignment: CENTER;");
        }
    }

    private void populateOrdersTableView(int userId) {
        try {
            List<List<String>> userOrdersData = orderDAO.getOrdersForUser(userId);
            ordersObservableList.clear();

            for (List<String> orderInfo : userOrdersData) {
                int orderId = Integer.parseInt(orderInfo.get(0));
                String orderStatus = orderInfo.get(1);
                String orderDate = orderInfo.get(4);
                double orderPrice = Double.parseDouble(orderInfo.get(2));
                int orderQuantity = Integer.parseInt(orderInfo.get(3));
                String orderType = orderInfo.get(5);

                OrderSearchModel orderModel = new OrderSearchModel(orderId, orderStatus, orderPrice, orderQuantity,
                        orderDate, orderType);
                ordersObservableList.add(orderModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialize(int userId) {
        populateOrdersTableView(userId);
    }

}
