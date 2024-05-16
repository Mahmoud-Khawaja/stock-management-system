package org.example.stockmanagementsystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.stockmanagementsystem.helperClasses.StylingActions;

import java.io.IOException;
import java.util.Optional;

public class ConfirmationDialog {

    public static Optional<Boolean> show(String title, String message) {
        try {
            FXMLLoader loader = new FXMLLoader(ConfirmationDialog.class.getResource("CustomConfirmationDialog.fxml"));
            AnchorPane root = loader.load();

            CustomConfirmationDialogController controller = loader.getController();
            controller.setMessage(message);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            StylingActions.enableDrag(stage, root);
            stage.showAndWait();

            return Optional.of(controller.isConfirmed());
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}