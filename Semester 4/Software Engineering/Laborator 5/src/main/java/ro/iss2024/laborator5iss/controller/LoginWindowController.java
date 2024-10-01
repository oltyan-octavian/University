package ro.iss2024.laborator5iss.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.iss2024.laborator5iss.domain.User;
import ro.iss2024.laborator5iss.service.Service;

import java.io.IOException;

public class LoginWindowController {

    private Service serv;

    @FXML
    TextField loginName;
    @FXML
    PasswordField loginPassword;
    @FXML
    TextField registerName;
    @FXML
    PasswordField registerPassword;
    @FXML
    TextField registerPNC;
    @FXML
    TextField registerAddress;
    @FXML
    TextField registerPhoneNumber;
    @FXML
    BorderPane loginPane;
    @FXML
    BorderPane registerPane;

    public LoginWindowController() {
    }

    public void setServ(Service serv) {
        this.serv = serv;
    }

    public void initialize() {
    }

    @FXML
    public void viewLogin(){
        registerPane.setVisible(false);
        loginPane.setVisible(true);
    }
    @FXML
    public void viewRegister(){
        loginPane.setVisible(false);
        registerPane.setVisible(true);
    }

    public void login() {
        try {
            String name = loginName.getText();
            String password = loginPassword.getText();
            try {
                User selected = serv.connect(name, password);
                if (selected != null && selected.getType().equals("client")) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/ro/iss2024/laborator5iss/UserWindow.fxml"));

                    AnchorPane root = loader.load();
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("User Window");
                    // Create the dialog Stage.
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    //dialogStage.initOwner(primaryStage);
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/ro/iss2024/laborator5iss/style.css").toExternalForm());
                    dialogStage.setScene(scene);

                    UserWindowController userWindowController = loader.getController();
                    serv.addObserver(userWindowController);
                    userWindowController.setServ(serv);
                    userWindowController.setUser(selected);
                    userWindowController.initModel();
                    dialogStage.show();
                }
                if (selected != null && selected.getType().equals("admin")) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/ro/iss2024/laborator5iss/AdminWindow.fxml"));

                    AnchorPane root = loader.load();
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("User Window");
                    // Create the dialog Stage.
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    //dialogStage.initOwner(primaryStage);
                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("/ro/iss2024/laborator5iss/style.css").toExternalForm());
                    dialogStage.setScene(scene);

                    AdminWindowController adminWindowController = loader.getController();
                    serv.addObserver(adminWindowController);
                    adminWindowController.setServ(serv);
                    adminWindowController.setUser(selected);
                    adminWindowController.initModel();
                    dialogStage.show();
                }
                if (selected == null) {
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "User Login", "Incorrect name or password!");
                }
            }
                catch (Exception e) {
                MessageAlert.showErrorMessage(null,e.getMessage());
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(){
        String name=registerName.getText();
        String password=registerPassword.getText();
        String PNC = registerPNC.getText();
        String address = registerAddress.getText();
        String phoneNumber = registerPhoneNumber.getText();
        try {
            if (serv.addUser(name, password, PNC, address, phoneNumber)) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"User Register","The user was registered successfully!");
            } else {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"User Register","The user was not registered successfully!");
            }
        } catch (Exception e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        viewLogin();
    }

}
