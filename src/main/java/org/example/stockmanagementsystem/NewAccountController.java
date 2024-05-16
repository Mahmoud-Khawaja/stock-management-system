package org.example.stockmanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.stockmanagementsystem.DAOclassesImplementation.UserDAOImpl;
import org.example.stockmanagementsystem.helperClasses.StylingActions;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;

public class NewAccountController {

    @FXML
    private Button createNewAccountButton;

    @FXML
    private Button returnButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label usernameErrorLabel;

    @FXML
    private Label passwordErrorLabel;

    @FXML
    private TextField firstNameField;

    @FXML
    private Label firstNameErrorLabel;

    @FXML
    private TextField lastNameField;

    @FXML
    private Label lastNameErrorLabel;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    private final UserDAOImpl userDAO = new UserDAOImpl();

    @FXML
    private void initialize() {
        clearValidationErrors();
    }

    @FXML
    private void returnButtonAction(ActionEvent event) {
        switchToLoginPage();
    }

    @FXML
    private void createNewAccountButtonAction(ActionEvent event) {
        clearValidationErrors();
        if (validateInput()) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();

            Optional<Boolean> confirmationResult = ConfirmationDialog.show("Confirmation", "Are you sure ?");
            if (confirmationResult.isPresent() && confirmationResult.get()) {
                if (userDAO.addUser(username, password, firstName, lastName)) {
                    switchToLoginPage();
                } else {
                    showErrorMessage("Error", "Failed to create a new account. Please try again.");
                }
            }
        }
    }


    private void clearValidationErrors() {
        usernameErrorLabel.setText("");
        passwordErrorLabel.setText("");
        firstNameErrorLabel.setText("");
        lastNameErrorLabel.setText("");
    }

    private void switchToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) returnButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

            StylingActions.enableDrag(stage, root);
        } catch (IOException e) {
            showErrorMessage("Error", "Failed to load login page.");
        }
    }

    private boolean validateInput() {
        boolean isValid = true;

        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        if (username.isEmpty() || !EMAIL_PATTERN.matcher(username).matches()) {
            usernameErrorLabel.setText("Username should be in the format example@domain.com");
            isValid = false;
        }

        if (password.isEmpty() || password.length() < 8) {
            passwordErrorLabel.setText("Password must be at least 8 characters");
            isValid = false;
        }

        if (firstName.trim().isEmpty()) {
            firstNameErrorLabel.setText("First name is required");
            isValid = false;
        }

        if (lastName.trim().isEmpty()) {
            lastNameErrorLabel.setText("Last name is required");
            isValid = false;
        }

        return isValid;
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
