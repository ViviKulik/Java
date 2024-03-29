package com.example.map_exam.controller;

import com.example.map_exam.domain.Client;
import com.example.map_exam.domain.Flight;
import com.example.map_exam.domain.Ticket;
import com.example.map_exam.observer.Observer;
import com.example.map_exam.service.Service;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class ClientController implements Observer {

    Service service;
    Client client;

    ObservableList<Ticket> boughtTicketsModel = FXCollections.observableArrayList();
    ObservableList<Ticket> janTicketsModel = FXCollections.observableArrayList();

    @FXML
    TableView<Ticket> boughtTicketsTableView;
    @FXML
    TableView<Ticket> janTicketsTableView;

    @FXML
    TableColumn<Ticket, Long> boughtTicketsColumnFlightID;
    @FXML
    TableColumn<Ticket, LocalDateTime> boughtTicketsColumnDate;
    @FXML
    TableColumn<Ticket, String> boughtTicketsColumnFrom;
    @FXML
    TableColumn<Ticket, String> boughtTicketsColumnTo;
    @FXML
    TableColumn<Ticket, LocalDateTime> boughtTicketsColumnDepartureTime;
    @FXML
    TableColumn<Ticket, LocalDateTime> boughtTicketsColumnLandingTime;
    @FXML
    TableColumn<Ticket, Long> janTicketsColumnFlightID;
    @FXML
    TableColumn<Ticket, String> janTicketsColumnFrom;
    @FXML
    TableColumn<Ticket, String> janTicketsColumnTo;
    @FXML
    TableColumn<Ticket, LocalDateTime> janTicketsColumnDepartureTime;
    @FXML
    TableColumn<Ticket, LocalDateTime> janTicketsColumnLandingTime;

    @FXML
    ComboBox<String> toComboBox;
    @FXML
    ComboBox<String> fromComboBox;
    @FXML
    DatePicker datePicker;
    ObservableList<String>  toComboBoxModel = FXCollections.observableArrayList();
    ObservableList<String> fromComboBoxModel = FXCollections.observableArrayList();
    ObservableList<Flight> flightsModel = FXCollections.observableArrayList();
    @FXML
    TableView<Flight> flightsTableView;
    @FXML
    TableColumn<Flight, String> flightFrom;
    @FXML
    TableColumn<Flight, String>  flightTo;
    @FXML
    TableColumn<Flight, LocalDateTime>  departureDate;
    @FXML
    TableColumn<Flight, LocalDateTime>  landingDate;
    @FXML
    TableColumn<Flight, Integer>  seats;


    public void setService(Service service, Client client) {
        this.service = service;
        this.client = client;
        service.addObserver(this);
        initModel();
        initComboBoxesModel();
    }

    private void initFlightsTableModel(String from, String to, LocalDate date) {
        to = toComboBox.getValue();
        from = fromComboBox.getValue();
        date = datePicker.getValue();
        flightsModel.setAll(service.filterFlights(from, to, date).stream().filter(flight -> flight.getSeats() > 0).collect(Collectors.toList()));
    }

    private void initComboBoxesModel() {
        toComboBoxModel.setAll(service.getToLocations());
        fromComboBoxModel.setAll(service.getFromLocations());
    }

    private void initModel() {
        boughtTicketsModel.setAll(service.getBoughtTickets(client.getUsername()));
        LocalDate desiredDepartureDate = LocalDate.of(2024, 1, 26);
        janTicketsModel.setAll(service.getTicketsBoughtOnDate(client.getUsername(), desiredDepartureDate));
    }

    String to;
    String from;
    LocalDate date = null;

    @FXML
    private void handleSearch() {
        to = toComboBox.getValue();
        from = fromComboBox.getValue();
        date = datePicker.getValue();
        if (!to.isEmpty() && !from.isEmpty() && date != null)
            initFlightsTableModel(from, to, date);
        else {
            Alert.showErrorMessage(null, "please select filters");
        }
    }

    @FXML
    public void initialize() {
        boughtTicketsColumnFlightID.setCellValueFactory(new PropertyValueFactory<Ticket, Long>("flightId"));
        boughtTicketsColumnDate.setCellValueFactory(new PropertyValueFactory<Ticket, LocalDateTime>("purchaseTime"));
        boughtTicketsColumnFrom.setCellValueFactory(data -> {
            Ticket ticket = data.getValue();
            Flight flight = service.getFlightById(ticket.getFlightId()).get();
            return new SimpleStringProperty(flight.getFrom());
        });

        boughtTicketsColumnTo.setCellValueFactory(data -> {
            Ticket ticket = data.getValue();
            Flight flight = service.getFlightById(ticket.getFlightId()).get();
            return new SimpleStringProperty(flight.getTo());
        });

        boughtTicketsColumnDepartureTime.setCellValueFactory(data -> {
            Ticket ticket = data.getValue();
            Flight flight = service.getFlightById(ticket.getFlightId()).get();
            return new SimpleObjectProperty<>(flight.getDepartureTime());
        });

        boughtTicketsColumnLandingTime.setCellValueFactory(data -> {
            Ticket ticket = data.getValue();
            Flight flight = service.getFlightById(ticket.getFlightId()).get();
            return new SimpleObjectProperty<>(flight.getLandingTime());
        });

        janTicketsColumnFlightID.setCellValueFactory(new PropertyValueFactory<Ticket, Long>("flightId"));

        janTicketsColumnFrom.setCellValueFactory(data -> {
            Ticket ticket = data.getValue();
            Flight flight = service.getFlightById(ticket.getFlightId()).get();
            return new SimpleStringProperty(flight.getFrom());
        });

        janTicketsColumnTo.setCellValueFactory(data -> {
            Ticket ticket = data.getValue();
            Flight flight = service.getFlightById(ticket.getFlightId()).get();
            return new SimpleStringProperty(flight.getTo());
        });

        janTicketsColumnDepartureTime.setCellValueFactory(data -> {
            Ticket ticket = data.getValue();
            Flight flight = service.getFlightById(ticket.getFlightId()).get();
            return new SimpleObjectProperty<>(flight.getDepartureTime());
        });

        janTicketsColumnLandingTime.setCellValueFactory(data -> {
            Ticket ticket = data.getValue();
            Flight flight = service.getFlightById(ticket.getFlightId()).get();
            return new SimpleObjectProperty<>(flight.getLandingTime());
        });


        boughtTicketsTableView.getColumns().addAll(boughtTicketsColumnFrom, boughtTicketsColumnTo,
                boughtTicketsColumnDepartureTime, boughtTicketsColumnLandingTime);
        janTicketsTableView.getColumns().addAll(janTicketsColumnFrom, janTicketsColumnTo,
                janTicketsColumnDepartureTime, janTicketsColumnLandingTime);

        boughtTicketsTableView.setItems(boughtTicketsModel);
        janTicketsTableView.setItems(janTicketsModel);
        toComboBox.setItems(toComboBoxModel);
        fromComboBox.setItems(fromComboBoxModel);

        flightFrom.setCellValueFactory(new PropertyValueFactory<Flight, String>("from"));
        flightTo.setCellValueFactory(new PropertyValueFactory<Flight, String>("to"));
        departureDate.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("departureTime"));
        landingDate.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("landingTime"));
        seats.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("seats"));

        flightsTableView.setItems(flightsModel);
    }

    public void handleBuy() {
        ObservableList<Flight> flights = flightsTableView.getSelectionModel().getSelectedItems();
        if (!flights.isEmpty()) {
            Flight flight = flights.get(0);
            service.decrementSeats(flight.getId());
            service.addTicket(client.getUsername(), flight.getId(), LocalDateTime.now());
            service.notifyObservers();
        }
        else {
            Alert.showErrorMessage(null, "please select flight");
        }
    }

    @Override
    public void update() {
            initModel();
            initFlightsTableModel(to, from, date);
    }

}
