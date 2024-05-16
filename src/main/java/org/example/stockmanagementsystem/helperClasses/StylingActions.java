package org.example.stockmanagementsystem.helperClasses;

import javafx.scene.Parent;
import javafx.stage.Stage;

public class StylingActions {

    private static final double DRAGGING_OPACITY = 0.7;
    private static final double NORMAL_OPACITY = 1.0;

    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void enableDrag(Stage stage, Parent root) {
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
            stage.setOpacity(DRAGGING_OPACITY);
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        root.setOnMouseReleased(event -> {
            stage.setOpacity(NORMAL_OPACITY);
        });
    }
}
