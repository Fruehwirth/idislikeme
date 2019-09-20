package client.controller;

import client.model.Event;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    int port = 6666;
    ObjectInputStream ois = null;
    ObjectOutputStream oos = null;

    @FXML
    private TableColumn<Event, String> eventColumn;

    @FXML
    private TableColumn<Event, String> ticketsColumn;

    @FXML
    private TableColumn<Event, String> preisColumn;

    @FXML
    private TableView<Event> tabelview;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private TextField lastnameTextField;

    @FXML
    private TextField ticketsTextfield;

    @FXML
    private Label priceLabel;

    @FXML
    private Button bookButton;

    @FXML
    private Button connectButton;

    @FXML
    private TextField serverTextField;


    @FXML
    private CheckBox localhostCheckBox;

    @FXML
    void localhostCheckBoxOnAction(ActionEvent event) {
        serverTextField.setText(serverTextField.getText());
    }

    @FXML
    void connectButtonOnAction(ActionEvent event){
        try(Socket socket = new Socket("localhost", port)){
            eventColumn.setCellValueFactory(new PropertyValueFactory<>("eventname"));
            ticketsColumn.setCellValueFactory(new PropertyValueFactory<>("avalibletickets"));
            preisColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());

            ArrayList <Event> eventArrayList;
            eventArrayList = (ArrayList<Event>) ois.readObject();
            System.out.println(eventArrayList.toString());

            tabelview.setItems(getEventsFromDatabase(eventArrayList));

            //while (true) {
            //    System.out.println("no operation");
            //    try {
            //        Thread.sleep(10000);
            //    } catch(InterruptedException e) {
            //        e.printStackTrace();
            //    }
            //}


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Event> getEventsFromDatabase(ArrayList<Event> eventArrayList){
        ObservableList<Event> events = FXCollections.observableArrayList(eventArrayList);
        return events;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connectButton.disableProperty().bind(serverTextField.textProperty().isEmpty().or(serverTextField.textProperty().isEmpty()));
        serverTextField.disableProperty().bind(localhostCheckBox.selectedProperty());
    }
}

