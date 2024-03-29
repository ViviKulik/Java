package com.example.demo;

import com.example.demo.Domain.Erou;
import com.example.demo.Domain.Nevoie;
import com.example.demo.Domain.Persoana;
import com.example.demo.Observer.Observer;
import com.example.demo.Service.Action.NevoieAction;
import com.example.demo.Service.ServiceNevoie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.stream.StreamSupport;

public class ControllerTab implements Observer {


    @FXML
    TabPane tabPane;

    @FXML
    Tab dorescSaAjut;
    @FXML
    Tab dorescSaFiuAjutat;


    private Persoana persoana;

    @FXML
    TableView<Nevoie> nevoieTableView;

    @FXML
    TableColumn<Nevoie, String> titlu;
    @FXML
    TableColumn<Nevoie, String> descriere;
    @FXML
    TableColumn<Nevoie, LocalDateTime> deadline;

    @FXML
    TableColumn<Nevoie, Erou> status;
    @FXML
    TableView<Nevoie> nevoieTableView1;

    @FXML
    TableColumn<Nevoie, String> titlu1;
    @FXML
    TableColumn<Nevoie, String> descriere1;
    @FXML
    TableColumn<Nevoie, LocalDateTime> deadline1;

    @FXML
    TableColumn<Nevoie, Erou> status1;
    @FXML
    ObservableList<Nevoie> observableList;
    @FXML
    ObservableList<Nevoie> observableList1;
    private ServiceNevoie serviceNevoie;

    @FXML
    private TextField titlutextfield;
    @FXML
    private TextField descrieretextfield;
    @FXML
    private DatePicker datePicker;

    public void SetControllerTab(Persoana persoana, ServiceNevoie serviceNevoie) {
        this.persoana = persoana;
        this.serviceNevoie = serviceNevoie;
        observableList.setAll(StreamSupport.stream(serviceNevoie.nevoies(persoana.getId()).spliterator(), false).filter(x-> !Objects.equals(x.getOmnevoie(),persoana.getId())).toList());
        observableList1.setAll(StreamSupport.stream(serviceNevoie.fapta_buna(persoana.getId()).spliterator(), false).toList());
    }

    @FXML
    public void initialize() {
        observableList = FXCollections.observableArrayList();
        titlu.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        descriere.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        deadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        status.setCellValueFactory(new PropertyValueFactory<>("erou"));
        nevoieTableView.setItems(observableList);
        observableList1 = FXCollections.observableArrayList();
        titlu1.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        descriere1.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        deadline1.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        status1.setCellValueFactory(new PropertyValueFactory<>("erou"));
        nevoieTableView1.setItems(observableList1);
    }

    public void handle_select() {

        var rez = nevoieTableView.getSelectionModel().getSelectedItem();
        if (rez.getErou().equals(Erou.caut_erou)) {
            serviceNevoie.update(rez.getId(), persoana.getId());
            NevoieAction.showMessage(null, Alert.AlertType.INFORMATION, "Operation status", "Nevoie Atribuita");
        }

    }


    @Override
    public void update() {
        observableList = FXCollections.observableArrayList();
        titlu.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        descriere.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        deadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        status.setCellValueFactory(new PropertyValueFactory<>("erou"));
        nevoieTableView.setItems(observableList);
        observableList.setAll(StreamSupport.stream(serviceNevoie.nevoies(persoana.getId()).spliterator(), false).filter(x-> !Objects.equals(x.getOmnevoie(),persoana.getId())).toList());
        observableList1 = FXCollections.observableArrayList();
        titlu1.setCellValueFactory(new PropertyValueFactory<>("titlu"));
        descriere1.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        deadline1.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        status1.setCellValueFactory(new PropertyValueFactory<>("erou"));
        nevoieTableView1.setItems(observableList1);
        observableList1.setAll(StreamSupport.stream(serviceNevoie.fapta_buna(persoana.getId()).spliterator(), false).toList());

    }

    public void handle_submit() throws Exception {
        try {
            var t = titlutextfield.getText();
            var d = descrieretextfield.getText();
            var dt = datePicker.getValue();
            Nevoie nevoie = new Nevoie(t, d, LocalDateTime.of(dt, LocalTime.now()));
            nevoie.setOmnevoie(persoana.getId());
            serviceNevoie.save(nevoie);
        } catch (Exception e) {
            NevoieAction.showMessage(null, Alert.AlertType.INFORMATION, "Titlu", "mesaj");
        }
    }
}
