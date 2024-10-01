package ro.ubbcluj.cs.map.laborator8.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.laborator8.service.Service;

import java.util.Objects;

import static java.lang.Long.parseLong;

public class DeleteWindowController {
    @FXML
    TextField firstUserTextField;
    @FXML
    TextField secondUserTextField;
    @FXML
    Label firstUserLabel;
    @FXML
    Label secondUserLabel;
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
        if(Objects.equals(type, "deleteUser"))
        {
            firstUserLabel.setText("ID of the user:");
            secondUserLabel.setVisible(false);
            secondUserTextField.setVisible(false);
        }
        if(Objects.equals(type, "deleteFriendship"))
        {
            firstUserLabel.setText("ID of the first user:");
            secondUserLabel.setVisible(true);
            secondUserTextField.setVisible(true);
        }
    }

    @FXML
    public void handleDelete(){
        if(Objects.equals(type, "deleteUser")){
            handleUserDelete();
        }
        else{
            handleFriendshipDelete();
        }
    }


    public void handleUserDelete(){
        Long id=parseLong(firstUserTextField.getText());
        try {
            if (serv.deleteUser(id)) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"User Delete","The user was deleted successfully!");
            } else {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"User Delete","The user was not deleted successfully!");
            }
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        dialogStage.close();
    }


    public void handleFriendshipDelete(){
        Long id1=parseLong(firstUserTextField.getText());
        Long id2=parseLong(secondUserTextField.getText());
        try {
            if (serv.deleteFriendship(id1, id2)) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Friendship Delete","The friendship was deleted successfully!");
            } else {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Friendship Delete","The friendship was not deleted successfully!");
            }
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        dialogStage.close();
    }

    private void clearFields() {
        firstUserTextField.setText("");
        secondUserTextField.setText("");
    }
}
