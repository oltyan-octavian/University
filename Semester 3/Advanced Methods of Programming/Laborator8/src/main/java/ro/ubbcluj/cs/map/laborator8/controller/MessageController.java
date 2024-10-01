package ro.ubbcluj.cs.map.laborator8.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.laborator8.domain.Message;
import ro.ubbcluj.cs.map.laborator8.domain.User;
import ro.ubbcluj.cs.map.laborator8.domain.validators.ValidatorException;
import ro.ubbcluj.cs.map.laborator8.service.Service;
import ro.ubbcluj.cs.map.laborator8.utils.events.ChangeEvent;
import ro.ubbcluj.cs.map.laborator8.utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageController implements Observer<ChangeEvent> {

    private Service<Long> service;
    private Stage dialogStage;
    private User myUser, toUser;

    private Message replyToThisMsg, myMessage;

    private final List<User> sendToList = new ArrayList<>();

    @FXML
    private ScrollPane messageScrollPane;

    @FXML
    private TextField messageTextField;

    @FXML
    private TextField replyTextField;
    ArrayList<Message> messages = new ArrayList<>();

    public void init() {
        messages = service.findMessages(myUser.getId(), toUser.getId());
        VBox messageContainer = new VBox();
        messageContainer.setPadding(new Insets(10));
        messageContainer.setPrefHeight(300);

        for (Message message : messages) {
            Text messageText = createMessageText(message);
            VBox container = new VBox(messageText);
            messageContainer.getChildren().add(container);
        }


        messageScrollPane.setContent(messageContainer);
    }

    @FXML
    private void initialize() {

        messageTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleSend();
                event.consume();
            }
        });
    }

    private Text createMessageText(Message message) {
        Text messageText;
        if (message.getReply() != 0) {
            messageText = new Text(
                    message.getId() + ". " +
                            message.getFrom().getFirstName() + " replied to " +
                            message.getReply() + ": " +
                            message.getMessage() + "\n"
            );
        } else {
            messageText = new Text(message.getId() + ". " + message.getFrom().getFirstName() + ": " + message.getMessage() + "\n");
        }
        if (myUser.getId().equals(message.getFrom().getId())) {
            messageText.setStyle("-fx-fill: black; -fx-font-weight: bold; ");
        } else {
            messageText.setStyle("-fx-fill: red; -fx-font-weight: bold; ");
        }
        messageText.setWrappingWidth(550);
        return messageText;
    }

    public void setService(Service<Long> serv, Stage dialogStage, User myUser, User toUser) {
        this.service = serv;
        this.service.addObserver(this);
        this.dialogStage = dialogStage;
        this.myUser = myUser;
        this.toUser = toUser;
        this.sendToList.add(toUser);
        init();
    }

    @FXML
    public void handleSend() {
        String msg = messageTextField.getText();
        messageTextField.clear();
        Long id;
        myMessage = new Message(msg, myUser, sendToList, LocalDateTime.now());
        if (!replyTextField.getText().isEmpty()) {
            id = Long.parseLong(replyTextField.getText());
            replyToThisMsg = service.FindByID(id);
            replyTextField.clear();
            if (replyToThisMsg == null) {
                MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Error!", "There are no messages with this ID!");
                return;
            } else {
                myMessage.setReply(id);
            }
        }
        handleSend(myMessage);
    }

    public void handleSend(Message msg) {
        try {
            this.service.addMessage(msg);
        } catch (ValidatorException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
    }


    @Override
    public void update(ChangeEvent messageChangeEvent) {
        init();
    }
}