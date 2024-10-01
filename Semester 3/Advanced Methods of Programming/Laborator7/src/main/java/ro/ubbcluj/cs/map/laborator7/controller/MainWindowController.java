package ro.ubbcluj.cs.map.laborator7.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.ubbcluj.cs.map.laborator7.domain.Friendship;
import ro.ubbcluj.cs.map.laborator7.domain.Tuple;
import ro.ubbcluj.cs.map.laborator7.domain.User;
import ro.ubbcluj.cs.map.laborator7.domain.validators.ValidatorFriendship;
import ro.ubbcluj.cs.map.laborator7.domain.validators.ValidatorUser;
import ro.ubbcluj.cs.map.laborator7.repository.FriendshipDBRepository;
import ro.ubbcluj.cs.map.laborator7.repository.Repository;
import ro.ubbcluj.cs.map.laborator7.repository.UserDBRepository;
import ro.ubbcluj.cs.map.laborator7.service.Service;
import ro.ubbcluj.cs.map.laborator7.utils.events.ChangeEvent;
import ro.ubbcluj.cs.map.laborator7.utils.observer.Observer;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainWindowController implements Observer<ChangeEvent> {

    ObservableList<User> userModel = FXCollections.observableArrayList();
    ObservableList<Friendship> friendshipModel = FXCollections.observableArrayList();
    @FXML
    TableView<User> userTableView;
    @FXML
    TableColumn<User, Long> userTableID;
    @FXML
    TableColumn<User, String> userTableFirstName;
    @FXML
    TableColumn<User, String> userTableLastName;
    @FXML
    TableView<Friendship> friendshipTableView;
    @FXML
    TableColumn<Friendship, Long> friendshipTableID1;
    @FXML
    TableColumn<Friendship, Long> friendshipTableID2;
    @FXML
    TableColumn<Friendship, LocalDateTime> friendshipTableFrom;

    Repository<Long, User> usersrepo;
    Repository<Tuple<Long,Long>, Friendship> friendshiprepo;
    private Service serv;

    public MainWindowController(Service serv) {
        this.serv = serv;
    }

    public MainWindowController() {
    }

    public void initialize() {
        String url = "jdbc:postgresql://localhost:5432/socialnetwork";
        String username = "postgres";
        String password = "12345678";
        usersrepo = new UserDBRepository(url, username, password, new ValidatorUser());
        friendshiprepo = new FriendshipDBRepository(url, username, password, new ValidatorFriendship());
        serv = new Service<>(usersrepo, friendshiprepo);
        serv.addObserver(this);
        userTableID.setCellValueFactory(new PropertyValueFactory<User, Long>("id"));
        userTableFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        userTableLastName.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));


        friendshipTableID1.setCellValueFactory(new PropertyValueFactory<Friendship, Long>("id1"));
        friendshipTableID2.setCellValueFactory(new PropertyValueFactory<Friendship, Long>("id2"));
        friendshipTableFrom.setCellValueFactory(new PropertyValueFactory<Friendship, LocalDateTime>("date"));
        initModel();
        userTableView.setItems(userModel);
        friendshipTableView.setItems(friendshipModel);
    }

    public void initModel() {
        Iterable<User> users = serv.getAllUsers();
        List<User> userList = StreamSupport.stream(users.spliterator(), false)
                .collect(Collectors.toList());
        userModel.setAll(userList);

        Iterable<Friendship> friendships = serv.getAllFriendships();
        List<Friendship> friendshipList = StreamSupport.stream(friendships.spliterator(), false)
                .collect(Collectors.toList());
        friendshipModel.setAll(friendshipList);
    }

    public void setMainWindowService(Service mainWindowService) {
        this.serv = mainWindowService;
        serv.addObserver(this);
        initModel();
    }

    @FXML
    public void showAddUserWindow(){
        showAddWindowDialog("addUser");
    }

    @FXML
    public void showAddFriendshipWindow(){
        showAddWindowDialog("addFriendship");
    }

    public void showAddWindowDialog(String type) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ro/ubbcluj/cs/map/laborator7/AddWindow.fxml"));


            AnchorPane root = loader.load();
            Stage dialogStage = new Stage();
            if(Objects.equals(type, "addUser")){
                dialogStage.setTitle("Add User");
            }
            else{
                dialogStage.setTitle("Add Friendship");
            }
            // Create the dialog Stage.

            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            AddWindowController addWindowController = loader.getController();
            addWindowController.setService(serv, dialogStage, type);

            dialogStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void showDeleteUserWindow(){
        showDeleteWindowDialog("deleteUser");
    }

    @FXML
    public void showDeleteFriendshipWindow(){
        showDeleteWindowDialog("deleteFriendship");
    }

    public void showDeleteWindowDialog(String type) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ro/ubbcluj/cs/map/laborator7/DeleteWindow.fxml"));


            AnchorPane root = loader.load();
            Stage dialogStage = new Stage();
            if(Objects.equals(type, "deleteUser")){
                dialogStage.setTitle("Delete User");
            }
            else{
                dialogStage.setTitle("Delete Friendship");
            }
            // Create the dialog Stage.

            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            DeleteWindowController deleteWindowController = loader.getController();
            deleteWindowController.setService(serv, dialogStage, type);

            dialogStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showNumberOfCommunities() {
        int nr = serv.Communities();
        String numbertostr = Integer.toString(nr);
        if (nr == 0) {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Number of communities","There are no communities!");
        } else {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Number of communities",numbertostr);
        }
    }

    @Override
    public void update(ChangeEvent changeEvent) {
        initModel();
    }
}
