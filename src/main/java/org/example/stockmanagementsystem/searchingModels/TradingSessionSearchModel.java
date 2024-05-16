package org.example.stockmanagementsystem.searchingModels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

public class TradingSessionSearchModel {
    private final IntegerProperty sessionId;
    private final ObjectProperty<Timestamp> startTime;
    private final ObjectProperty<Timestamp> endTime;
    private final StringProperty status;
    private final IntegerProperty adminUserId;

    public TradingSessionSearchModel(int sessionId, Timestamp startTime, Timestamp endTime, String status, int adminUserId) {
        this.sessionId = new SimpleIntegerProperty(sessionId);
        this.startTime = new SimpleObjectProperty<>(startTime);
        this.endTime = new SimpleObjectProperty<>(endTime);
        this.status = new SimpleStringProperty(status);
        this.adminUserId = new SimpleIntegerProperty(adminUserId);
    }

    public int getSessionId() {
        return sessionId.get();
    }

    public IntegerProperty sessionIdProperty() {
        return sessionId;
    }

    public Timestamp getStartTime() {
        return startTime.get();
    }

    public ObjectProperty<Timestamp> startTimeProperty() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime.get();
    }

    public ObjectProperty<Timestamp> endTimeProperty() {
        return endTime;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public int getAdminUserId() {
        return adminUserId.get();
    }

    public IntegerProperty adminUserIdProperty() {
        return adminUserId;
    }
}
