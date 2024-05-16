package org.example.stockmanagementsystem;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.stockmanagementsystem.DAOclasses.*;
import org.example.stockmanagementsystem.DAOclassesImplementation.UserDAOImpl;
import org.example.stockmanagementsystem.helperClasses.StylingActions;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button createNewAccountButton;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button cancelButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField usernameField;

    @FXML
    void initialize() {
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginValidation();
            }
        });
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginValidation();
            }
        });
    }

    @FXML
    void createNewAccountButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateNewAccount.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) createNewAccountButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            StylingActions.enableDrag(stage, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loginButtonAction(ActionEvent e) {
        if (!usernameField.getText().isBlank() && !passwordField.getText().isBlank()) {
            loginValidation();
        } else {
            loginMessageLabel.setText("Please enter your Username and Password");
        }
    }

    @FXML
    public void cancelButton(ActionEvent event) {
        Platform.exit();
    }

    private void loginValidation() {
        UserDAO userDAO = new UserDAOImpl();
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean isValidUser = userDAO.isValidUser(username, password);
        if (isValidUser) {
            String id = userDAO.getUserId(username, password);
            handleValidUser(id);
        } else {
            loginMessageLabel.setText("Invalid username or password");
        }
    }

    private void handleValidUser(String id) {
        try {
            FXMLLoader loader = new FXMLLoader();
            UserDAO userDAO = new UserDAOImpl();
            String[] userInfo = userDAO.getUserInfo(Integer.parseInt(id));

            if (userInfo != null && userInfo.length >= 3) {
                String role = userInfo[2];
                Parent root;
                if ("admin".equals(role)) {
                    loader.setLocation(getClass().getResource("AdminDashboard.fxml"));
                    root = loader.load();
                    AdminDashboardController controller = loader.getController();
                    controller.initialize(id);
                } else {
                    loader.setLocation(getClass().getResource("UserDashboard.fxml"));
                    root = loader.load();
                    UserDashboardController controller = loader.getController();
                    controller.initialize(id);
                }
                Stage dashboardStage = new Stage();
                dashboardStage.initStyle(StageStyle.UNDECORATED);
                Scene scene = new Scene(root);
                StylingActions.enableDrag(dashboardStage, root);
                dashboardStage.setScene(scene);
                dashboardStage.show();
                Stage loginStage = (Stage) cancelButton.getScene().getWindow();
                loginStage.close();
            } else {
                throw new RuntimeException("User info array does not contain sufficient data");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
