package com.example.map_toysocialnetwork_gui.controller;

import com.example.map_toysocialnetwork_gui.domain.FriendRequest;
import com.example.map_toysocialnetwork_gui.domain.Utilizator;
import com.example.map_toysocialnetwork_gui.repository.paging.Page;
import com.example.map_toysocialnetwork_gui.repository.paging.Pageable;
import com.example.map_toysocialnetwork_gui.service.DBService;
import com.example.map_toysocialnetwork_gui.service.Service;
import com.example.map_toysocialnetwork_gui.utils.events.ChangeEvent;
import com.example.map_toysocialnetwork_gui.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FriendRequestController implements Observer<ChangeEvent> {

    @FXML
    private Button addButton;
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
    TextField nrOfElemOnPageTextField;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;

    private int nrElemPerPage = 4;
    private int currentPage = 0;
    private int totalNrOfElems = 0;

    public void setService(DBService service) {
        this.service = service;
        service.addObserver(this);
        nrOfElemOnPageTextField.setText(String.valueOf(nrElemPerPage));
        initModel();
    }

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
        nrOfElemOnPageTextField.setText(String.valueOf(nrElemPerPage));
        initModel();
//        initialize();

    }
    private void initModel(){
        Iterable<Utilizator> users = service.getPotentialFriends(this.id);
        List<Utilizator> userList = StreamSupport.stream(users.spliterator(), false).collect(Collectors.toList());

//        List<Utilizator> userList = service.getPotentialFriends(id);
        System.out.println(userList);
        model.setAll(userList);
//
//        Page<Utilizator> page = service.findAllOnPageFriendRequest(new Pageable(currentPage, nrElemPerPage));
//        int maxPage = (int) Math.ceil((double) page.getTotalNrOfElems() / nrElemPerPage) - 1;
//        if (maxPage < 0)
//            maxPage = 0;
//        if (currentPage > maxPage){
//            currentPage = maxPage;
//            page = service.findAllOnPage(new Pageable(currentPage, nrElemPerPage));
//        }
//
//        model.setAll(StreamSupport.stream(page.getElemsOnPage().spliterator(), false).collect(Collectors.toList()));
//        totalNrOfElems = page.getTotalNrOfElems();
//
//        prevButton.setDisable(currentPage == 0);
//        nextButton.setDisable((currentPage + 1) * nrElemPerPage >= totalNrOfElems);
    }

    public void handleAddFriend(ActionEvent action){
        ObservableList<Utilizator> selected = tableView.getSelectionModel().getSelectedItems();
        if(!(selected.isEmpty())){
            try{
                service.saveRequest("pending", id, selected.get(0).getId());
                initModel();
                Alert.showMessage(null, javafx.scene.control.Alert.AlertType.INFORMATION, null, "Friend request sent!");
            } catch (Exception e){
                Alert.showErrorMessage(null, e.getMessage());
            }
        }
        else{
            Alert.showErrorMessage(null, "No user selected");
        }
    }

    @Override
    public void update(ChangeEvent changeEvent) {
        initModel();
    }

    public void onPressPrevButton(ActionEvent actionEvent) {
    }

    public void onPressNextButton(ActionEvent actionEvent) {
    }
}
