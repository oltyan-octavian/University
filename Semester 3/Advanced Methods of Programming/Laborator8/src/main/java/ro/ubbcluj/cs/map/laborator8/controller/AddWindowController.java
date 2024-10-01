package ro.ubbcluj.cs.map.laborator8.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.laborator8.domain.User;
import ro.ubbcluj.cs.map.laborator8.service.Service;

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
    Long id;


    @FXML
    private void initialize() {
    }

    public void setService(Service service, Stage stage, String type, Long id) {
        this.serv = service;
        this.dialogStage=stage;
        this.type = type;
        this.id = id;
        if(Objects.equals(type, "addUser"))
        {
            labelDown.setVisible(true);
            textFieldDown.setVisible(true);
            labelUp.setText("First Name:");
            labelDown.setText("Last Name:");
        }
        if(Objects.equals(type, "updateUser"))
        {
            labelDown.setVisible(true);
            textFieldDown.setVisible(true);
            labelUp.setText("New First Name:");
            labelDown.setText("New Last Name:");
        }
        if(Objects.equals(type, "addFriendship"))
        {
            labelUp.setText("ID of the user you want to add:");
            labelDown.setVisible(false);
            textFieldDown.setVisible(false);
        }
    }

    @FXML
    public void handleSave(){
        if(Objects.equals(type, "addUser")){
            handleUserSave();
        }
        if(Objects.equals(type, "addFriendship")){
            handleFriendshipSave();
        }
        if(Objects.equals(type, "updateUser")){
            handleUserUpdate();
        }
    }

    private void handleUserUpdate() {
        String firstName=textFieldUp.getText();
        String lastName=textFieldDown.getText();
        try {
            if (serv.updateUser(id, firstName, lastName)) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"User Update","The user was updated successfully!");
            } else {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"User Update","The user was not updated successfully!");
            }
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        dialogStage.close();
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
        try {
            if (serv.addFriendship(id, id1)) {
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
