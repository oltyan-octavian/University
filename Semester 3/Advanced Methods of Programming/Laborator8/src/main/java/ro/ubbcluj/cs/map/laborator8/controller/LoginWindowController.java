package ro.ubbcluj.cs.map.laborator8.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.laborator8.domain.Friendship;
import ro.ubbcluj.cs.map.laborator8.domain.Tuple;
import ro.ubbcluj.cs.map.laborator8.domain.User;
import ro.ubbcluj.cs.map.laborator8.domain.validators.ValidatorFriendship;
import ro.ubbcluj.cs.map.laborator8.domain.validators.ValidatorMessage;
import ro.ubbcluj.cs.map.laborator8.domain.validators.ValidatorUser;
import ro.ubbcluj.cs.map.laborator8.repository.*;
import ro.ubbcluj.cs.map.laborator8.repository.paging.PagingRepository;
import ro.ubbcluj.cs.map.laborator8.service.Service;
import ro.ubbcluj.cs.map.laborator8.utils.events.ChangeEvent;
import ro.ubbcluj.cs.map.laborator8.utils.events.ChangeEventType;
import ro.ubbcluj.cs.map.laborator8.utils.observer.Observer;

import java.io.IOException;
import java.util.Objects;

public class LoginWindowController {

    private Service serv;
    PagingRepository<Long, User> usersrepo;
    Repository<Tuple<Long,Long>, Friendship> friendshiprepo;
    Repository messagerepo;

    @FXML
    TextField loginEmail;
    @FXML
    PasswordField loginPassword;
    @FXML
    TextField registerEmail;
    @FXML
    PasswordField registerPassword;
    @FXML
    TextField registerFirstName;
    @FXML
    TextField registerLastName;
    @FXML
    BorderPane loginPane;
    @FXML
    BorderPane registerPane;

    public LoginWindowController(Service serv) {
        this.serv = serv;
    }

    public LoginWindowController() {
    }
    public void initialize() {
        String url = "jdbc:postgresql://localhost:5432/socialnetwork";
        String username = "postgres";
        String password = "12345678";
        usersrepo = new UserPagingRepository(url, username, password, new ValidatorUser());
        friendshiprepo = new FriendshipDBRepository(url, username, password, new ValidatorFriendship());
        messagerepo = new MessageDBRepository(url, username, password, new ValidatorMessage());

        serv = new Service<>(usersrepo, friendshiprepo, messagerepo);

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
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ro/ubbcluj/cs/map/laborator8/UserWindow.fxml"));

            AnchorPane root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Interactia");
            // Create the dialog Stage.

            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            String email = loginEmail.getText();
            String password = loginPassword.getText();
            try{
                User selected = serv.connect(email, password);
                if(selected!=null){
                    UserWindowController userWindowController = loader.getController();
                    userWindowController.setService(serv);
                    userWindowController.setLoggeduser(selected);
                    dialogStage.show();
                }
                else {
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"User Login","The email or password is incorrect!");
                }
                } catch (Exception e) {
                MessageAlert.showErrorMessage(null,e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void register(){
        String email=registerEmail.getText();
        String password=registerPassword.getText();
        password=serv.encode(password);
        String firstName=registerFirstName.getText();
        String lastName=registerLastName.getText();
        try {
            User user = new User(firstName, lastName, email, password);
            if (serv.addUser(user)) {
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
