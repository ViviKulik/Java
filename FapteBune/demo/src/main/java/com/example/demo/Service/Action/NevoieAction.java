package com.example.demo.Service.Action;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class NevoieAction {
    public static void showMessage(Stage stage, Alert.AlertType alertType, String title, String msg)
    {
        Alert alert = new Alert(alertType);
        alert.initOwner(stage);
        alert.setContentText(msg);
        alert.setTitle(title);
        alert.showAndWait();
    }
}
