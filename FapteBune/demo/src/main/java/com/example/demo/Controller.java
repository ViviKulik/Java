package com.example.demo;

import com.example.demo.Domain.Oras;
import com.example.demo.Domain.Persoana;
import com.example.demo.Observer.Observer;
import com.example.demo.Service.ServiceNevoie;
import com.example.demo.Service.ServicePersoana;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.stream.StreamSupport;

public class Controller implements Observer {

    private ServicePersoana servicePersoana;
    @FXML
    private ComboBox<String> users;
    ObservableList<String> strings;
    @FXML
    private TextField nume;
    @FXML
    private TextField prenume;
    @FXML
    private TextField username;
    @FXML
    private PasswordField parola;
    @FXML
    private ComboBox<com.example.demo.Domain.Oras> Oras;
    @FXML
    ObservableList<Oras> observableList;
    @FXML
    private TextField strada;
    @FXML
    private TextField nrstrada;
    @FXML
    private TextField telefon;

    private ServiceNevoie serviceNevoie;

    public void SetController(ServicePersoana servicePersoana, ServiceNevoie persoana) {
        this.servicePersoana = servicePersoana;
        this.serviceNevoie = persoana;
        servicePersoana.addObserver(this);
        observableList.setAll(com.example.demo.Domain.Oras.values());
        strings.setAll(StreamSupport.stream(servicePersoana.allusers().spliterator(),false).toList());
    }

    @FXML
    public void initialize()
    {
        observableList = FXCollections.observableArrayList();
        Oras.setItems(observableList);

        strings = FXCollections.observableArrayList();
        users.setItems(strings);
    }

    public void handle_login() throws IOException {
        if(users.getValue() !=null) {
            if (servicePersoana.findonlogin(users.getValue().trim()) != null) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("view2.fxml"));
                AnchorPane root = fxmlLoader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                ControllerTab controllerTab = fxmlLoader.getController();
                serviceNevoie.addObserver(controllerTab);
                controllerTab.SetControllerTab(servicePersoana.findonlogin(users.getValue().trim()), serviceNevoie);
                stage.setScene(scene);
                stage.show();
            }
        }
        else
            {
                FXMLLoader fxmlLoader =new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("view2.fxml"));
                AnchorPane root= fxmlLoader.load();
                Stage stage =new Stage();
                Scene scene =new Scene(root);
                ControllerTab controllerTab = fxmlLoader.getController();
                serviceNevoie.addObserver(controllerTab);
                controllerTab.SetControllerTab(servicePersoana.findonlogin(username.getText().trim()),serviceNevoie);
                stage.setScene(scene);
                stage.show();
            }
    }

    public void register_handle() throws Exception {
        try {
            servicePersoana.save(new Persoana(nume.getText(), prenume.getText(), username.getText(), parola.getText(), Oras.getValue().toString(), strada.getText(), nrstrada.getText(), telefon.getText()));
            handle_login();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }


    @Override
    public void update() {
        strings = FXCollections.observableArrayList();
        users.setItems(strings);
        strings.setAll(StreamSupport.stream(servicePersoana.allusers().spliterator(),false).toList());

    }
}
