package ro.ubbcluj.cs.map.laborator7.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.laborator7.domain.Friendship;
import ro.ubbcluj.cs.map.laborator7.domain.User;
import ro.ubbcluj.cs.map.laborator7.service.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.lang.Long.parseLong;

public class AddWindowController {
    @FXML
    TextField textFieldUp;
    @FXML
    TextField textFieldDown;
    @FXML
    Label labelUp;
    @FXML
    Label labelDown;
    @FXML
    Button button;

    private Service serv;
    Stage dialogStage;
    String type;


    @FXML
    private void initialize() {
    }

    public void setService(Service service, Stage stage, String type) {
        this.serv = service;
        this.dialogStage=stage;
        this.type = type;
        if(Objects.equals(type, "addUser"))
        {
            labelUp.setText("First Name:");
            labelDown.setText("Last Name:");
        }
        if(Objects.equals(type, "addFriendship"))
        {
            labelUp.setText("ID of the first user:");
            labelDown.setText("ID of the second user:");
        }
    }

    @FXML
    public void handleSave(){
        if(Objects.equals(type, "addUser")){
            handleUserSave();
        }
        else{
            handleFriendshipSave();
        }
    }


    public void handleUserSave(){
        String firstName=textFieldUp.getText();
        String lastName=textFieldDown.getText();
        try {
            User user = new User(firstName, lastName);
            if (serv.addUser(user)) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"User Add","The user was added successfully!");
            } else {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"User Add","The user was not added successfully!");
            }
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        dialogStage.close();
    }


    public void handleFriendshipSave(){
        Long id1=parseLong(textFieldUp.getText());
        Long id2=parseLong(textFieldDown.getText());
        try {
            if (serv.addFriendship(id1, id2)) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Friendship Add","The friendship was added successfully!");
            } else {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Friendship Add","The friendship was not added successfully!");
            }
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        dialogStage.close();
    }

    private void clearFields() {
        textFieldUp.setText("");
        textFieldDown.setText("");
    }
}
