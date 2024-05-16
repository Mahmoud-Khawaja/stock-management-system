package org.example.stockmanagementsystem.searchingModels;

import javafx.beans.property.*;

public class OrderSearchModel {
    private final IntegerProperty orderId;
    private final StringProperty orderDate;
    private final DoubleProperty orderPrice;
    private final IntegerProperty orderQuantity;
    private final StringProperty orderStatus;
    private final StringProperty orderType;

    public OrderSearchModel(int orderId, String orderDate, double orderPrice, int orderQuantity, String orderStatus, String orderType) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.orderDate = new SimpleStringProperty(orderDate);
        this.orderPrice = new SimpleDoubleProperty(orderPrice);
        this.orderQuantity = new SimpleIntegerProperty(orderQuantity);
        this.orderStatus = new SimpleStringProperty(orderStatus);
        this.orderType = new SimpleStringProperty(orderType);
    }

    public int getOrderId() {
        return orderId.get();
    }

    public IntegerProperty orderIdProperty() {
        return orderId;
    }

    public String getOrderDate() {
        return orderDate.get();
    }

    public StringProperty orderDateProperty() {
        return orderDate;
    }

    public double getOrderPrice() {
        return orderPrice.get();
    }

    public DoubleProperty orderPriceProperty() {
        return orderPrice;
    }

    public int getOrderQuantity() {
        return orderQuantity.get();
    }

    public IntegerProperty orderQuantityProperty() {
        return orderQuantity;
    }

    public String getOrderStatus() {
        return orderStatus.get();
    }

    public StringProperty orderStatusProperty() {
        return orderStatus;
    }

    public String getOrderType() {
        return orderType.get();
    }

    public StringProperty orderTypeProperty() {
        return orderType;
    }
}
