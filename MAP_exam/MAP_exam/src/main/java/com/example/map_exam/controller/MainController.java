package com.example.map_exam.controller;

import com.example.map_exam.domain.Client;
import com.example.map_exam.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MainController {

    Service service;
    public void setService(Service service) {
        this.service = service;
        initModel();
    }

    @FXML
    Button loginButton;

    @FXML
    TextField usernameTextField;

    private void initModel() {

    }

    @FXML
    public void initialize() {


    }

    public void handleLogin(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        Optional<Client> clientOptional = service.getClientByUsername(username);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            runClientWindow(client);
        } else {
            Alert.showErrorMessage(null, "Username nonexistent!");
        }
    }

    private void runClientWindow(Client client) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/map_exam/client-view.fxml"));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Client " + client.getName());

            ClientController clientController = loader.getController();
            clientController.setService(service, client);

            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }


}
