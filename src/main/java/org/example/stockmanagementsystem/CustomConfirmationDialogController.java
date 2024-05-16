package org.example.stockmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CustomConfirmationDialogController{

    @FXML
    private Label messageLabel;

    @FXML
    private Button okButton;

    @FXML
    private Button cancelButton;

    private boolean confirmed = false;

    @FXML
    private void handleOkButtonAction(ActionEvent event) {
        confirmed = true;
        closeDialog();
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        closeDialog();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    private void closeDialog() {
        okButton.getScene().getWindow().hide();
    }
}