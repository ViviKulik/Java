package com.example.map_toysocialnetwork_gui.controller;

import com.example.map_toysocialnetwork_gui.domain.Utilizator;
import com.example.map_toysocialnetwork_gui.service.DBService;
import com.example.map_toysocialnetwork_gui.utils.events.ChangeEvent;
import com.example.map_toysocialnetwork_gui.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FriendListController implements Observer<ChangeEvent> {
    @FXML
    TableView<Utilizator> tableView;
    @FXML
    TableColumn<Utilizator, Long> tableColumnID;
    @FXML
    TableColumn<Utilizator, String> tableColumnFirstName;
    @FXML
    TableColumn<Utilizator, String> tableColumnLastName;
    ObservableList<Utilizator> model = FXCollections.observableArrayList();
    private DBService service;
    Stage dialogStage;
    String username;
    Long id;
    @FXML
    public void initialize(){
        tableColumnID.setCellValueFactory(new PropertyValueFactory<Utilizator, Long>("id"));
        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("lastName"));
        tableView.setItems(model);
    }

    public void setService(DBService service, Stage stage, Long id){
        this.service = service;
        this.dialogStage = stage;
        this.id = id;
        service.addObserver(this);
        initModel();
//        initialize();

    }
    private void initModel(){
        Iterable<Utilizator> users = service.getUserFriends(id);
        List<Utilizator> userList = StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());

        model.setAll(userList);
    }

    @Override
    public void update(ChangeEvent changeEvent) {
        initModel();
    }
}
