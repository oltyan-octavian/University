package ro.ubbcluj.cs.map.laborator8.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.laborator8.domain.Message;
import ro.ubbcluj.cs.map.laborator8.domain.User;
import ro.ubbcluj.cs.map.laborator8.domain.validators.ValidatorException;
import ro.ubbcluj.cs.map.laborator8.controller.UserWindowController;
import ro.ubbcluj.cs.map.laborator8.service.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class NewMessageController {

    @FXML
    private TextField toField;
    @FXML
    private Button sendMessage;
    private Service<Long> service;

    @FXML
    private TextField messageField;
    Stage dialogStage;
    List<User> sendToList;

    User myUser;

    @FXML
    private void initialize() {
        messageField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Handle the Enter key press
                handleSend();
                // Consume the event to prevent a new line in the TextArea
                event.consume();
            }
        });
    }

    public void setService(Service<Long> service, Stage stage, User myUser, List<User> sendToList) {
        this.service = service;
        this.dialogStage = stage;
        this.sendToList = sendToList;
        this.myUser = myUser;
        if (!this.sendToList.isEmpty()) {
            setFields(sendToList);
            toField.setEditable(false);
        }
    }



    @FXML
    public void handleSend() {
        String msg = messageField.getText();
        Message myMessage = new Message(msg,myUser,sendToList,LocalDateTime.now());
        handleSend(myMessage);
    }

    public void handleSend(Message msg) {
        try {
            boolean r = this.service.addMessage(msg);
            if (r)
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Message send", "The message has been sent!");
        } catch (ValidatorException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
        dialogStage.close();
    }

    private void setFields(List<User> myList) {
        String concatenatedNames = myList.stream()
                .map(User::getFirstName)
                .collect(Collectors.joining(", "));
        toField.setText(concatenatedNames);
    }
}
