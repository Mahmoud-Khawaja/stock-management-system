package org.example.stockmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.stockmanagementsystem.helperClasses.StylingActions;

import java.io.IOException;

public class StockManagementSystem extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
    
        stage.initStyle(StageStyle.UNDECORATED);

        Scene scene = new Scene(root, 834, 549);
        stage.setScene(scene);

        StylingActions.enableDrag(stage, root);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
