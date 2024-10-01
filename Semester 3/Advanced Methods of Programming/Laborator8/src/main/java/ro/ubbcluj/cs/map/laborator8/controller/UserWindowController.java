package ro.ubbcluj.cs.map.laborator8.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import ro.ubbcluj.cs.map.laborator8.repository.paging.Page;
import ro.ubbcluj.cs.map.laborator8.repository.paging.Pageable;
import ro.ubbcluj.cs.map.laborator8.repository.paging.PagingRepository;
import ro.ubbcluj.cs.map.laborator8.service.Service;
import ro.ubbcluj.cs.map.laborator8.utils.events.ChangeEvent;
import ro.ubbcluj.cs.map.laborator8.utils.events.ChangeEventType;
import ro.ubbcluj.cs.map.laborator8.utils.observer.Observer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserWindowController implements Observer<ChangeEvent> {

    ObservableList<User> userModel = FXCollections.observableArrayList();
    ObservableList<Friendship> friendshipModel = FXCollections.observableArrayList();
    ObservableList<Friendship> messageModel = FXCollections.observableArrayList();

    @FXML
    BorderPane userBorderPane;
    @FXML
    BorderPane friendshipBorderPane;
    @FXML
    BorderPane chatBorderPane;
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
    TableColumn<Friendship, Long> friendshipTableID;
    @FXML
    TableColumn<Friendship, String> friendshipTableFirstName;
    @FXML
    TableColumn<Friendship, String> friendshipTableLastName;
    @FXML
    TableColumn<Friendship, String> friendshipTableStatus;
    @FXML
    TableView<Friendship> messageTableView;
    @FXML
    TableColumn<Friendship, Long> messageTableID;
    @FXML
    TableColumn<Friendship, String> messageTableFirstName;
    @FXML
    TableColumn<Friendship, String> messageTableLastName;
    @FXML
    Label userLabel;
    @FXML
    Label userLabelChat;
    @FXML
    Button previousButton;
    @FXML
    Button nextButton;
    @FXML
    Button setButton;
    @FXML
    TextField numberOfUsers;


    PagingRepository<Long, User> usersrepo;
    Repository<Tuple<Long,Long>, Friendship> friendshiprepo;
    Repository messagerepo;
    private Service serv;
    User loggeduser;
    private int currentPage=0;
    private int numberOfRecordsPerPage = 5;
    private int totalNumberOfElements;

    public UserWindowController(Service serv) {
        this.serv = serv;
    }

    public UserWindowController() {
    }
    public void setService(Service service) {
        this.serv = service;
    }

    public User getLoggeduser() {
        return loggeduser;
    }

    public void setLoggeduser(User loggeduser) {
        this.loggeduser = loggeduser;
    }

    public void initialize() {
        String url = "jdbc:postgresql://localhost:5432/socialnetwork";
        String username = "postgres";
        String password = "12345678";
        usersrepo = new UserPagingRepository(url, username, password, new ValidatorUser());
        friendshiprepo = new FriendshipDBRepository(url, username, password, new ValidatorFriendship());
        messagerepo = new MessageDBRepository(url, username, password, new ValidatorMessage());

        serv = new Service<>(usersrepo, friendshiprepo, messagerepo);
        serv.addObserver(this);
        userTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        userTableFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        userTableLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));


        friendshipTableID.setCellValueFactory(new PropertyValueFactory<>("User2ID"));
        friendshipTableFirstName.setCellValueFactory(new PropertyValueFactory<>("User2firstName"));
        friendshipTableLastName.setCellValueFactory(new PropertyValueFactory<>("User2lastName"));
        friendshipTableStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        messageTableID.setCellValueFactory(new PropertyValueFactory<>("User2ID"));
        messageTableFirstName.setCellValueFactory(new PropertyValueFactory<>("User2firstName"));
        messageTableLastName.setCellValueFactory(new PropertyValueFactory<>("User2lastName"));
        initModel();
        userTableView.setItems(userModel);
        friendshipTableView.setItems(friendshipModel);
        messageTableView.setItems(messageModel);

    }

    public void initModel() {
        Page<User> usersOnCurrentPage = serv.getUsersOnPage(new Pageable(currentPage, numberOfRecordsPerPage));
        totalNumberOfElements = usersOnCurrentPage.getTotalNumberOfElements();
        List<User> userList = StreamSupport.stream(usersOnCurrentPage.getElementsOnPage().spliterator(), false).toList();
        userModel.setAll(userList);
        checkPages();

    }

    public void initFriendshipModel() {
        if(loggeduser == null)
        {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Friendships","You did not select a user!");
        }
        else
        {
            List<Friendship> friendshipList = new ArrayList<>();
            List<User> friends = serv.getFriendshipsOfUser(loggeduser.getId());
            for (User friend : friends) {
                // getFriendshipsOfUser merge bine
                Tuple<Long, Long> ids = new Tuple<>(loggeduser.getId(), friend.getId());
                // tuple merge bine
                Optional<Friendship> fr = friendshiprepo.findOne(ids);
                Friendship friendship = fr.get();
                User u1 = serv.getUser(friendship.getUser1().getId());
                User u2 = serv.getUser(friendship.getUser2().getId());
                friendship.setUser1(u1);
                friendship.setUser2(u2);

                friendshipList.add(friendship);

            }
            friendshipModel.setAll(friendshipList);
        }
    }

    public void initMessageModel() {
        messageTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        if(loggeduser == null)
        {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Friendships","You did not select a user!");
        }
        else
        {
            List<Friendship> friendshipList = new ArrayList<>();
            List<User> friends = serv.getFriendshipsOfUser(loggeduser.getId());
            for (User friend : friends) {
                // getFriendshipsOfUser merge bine
                Tuple<Long, Long> ids = new Tuple<>(loggeduser.getId(), friend.getId());
                // tuple merge bine
                Optional<Friendship> fr = friendshiprepo.findOne(ids);
                Friendship friendship = fr.get();
                User u1 = serv.getUser(friendship.getUser1().getId());
                User u2 = serv.getUser(friendship.getUser2().getId());
                friendship.setUser1(u1);
                friendship.setUser2(u2);
                if(Objects.equals(friendship.getStatus(), "accepted"))
                    friendshipList.add(friendship);

            }
            messageModel.setAll(friendshipList);
        }
    }

    public void setUserWindowService(Service mainWindowService) {
        this.serv = mainWindowService;
        serv.addObserver(this);
        initModel();
    }

    @FXML
    public void showUserWindow() {
        friendshipBorderPane.setVisible(false);
        chatBorderPane.setVisible(false);
        userBorderPane.setVisible(true);
    }

    @FXML
    public void showFriendshipWindow() {
        initFriendshipModel();
        chatBorderPane.setVisible(false);
        userBorderPane.setVisible(false);
        friendshipBorderPane.setVisible(true);
        if(loggeduser != null){
            String nume = loggeduser.getFirstName() + " " + loggeduser.getLastName();
            userLabel.setText(nume);
        }
    }

    @FXML
    public void showChatWindow() {
        initMessageModel();
        userBorderPane.setVisible(false);
        friendshipBorderPane.setVisible(false);
        chatBorderPane.setVisible(true);
        if(loggeduser != null){
            String nume = loggeduser.getFirstName() + " " + loggeduser.getLastName();
            userLabelChat.setText(nume);
        }
    }

    @FXML
    public void showAddUserWindow(){
        showAddWindowDialog("addUser");
    }

    @FXML
    public void showUpdateUserWindow(){
        showAddWindowDialog("updateUser");
    }

    @FXML
    public void showAddFriendshipWindow(){
        showAddWindowDialog("addFriendship");
    }


    @FXML
    public void acceptRequestButton() {
        Friendship selected = friendshipTableView.getSelectionModel().getSelectedItem();
        if(serv.acceptFriendshipRequest(selected) == false) {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Friendship","You cannot accept this friendship request! (it was already accepted or rejected)");
        }
        else {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Friendship","Friendship request accepted!");
        }
    }

    @FXML
    public void rejectRequestButton() {
        Friendship selected = friendshipTableView.getSelectionModel().getSelectedItem();
        if(serv.rejectFriendshipRequest(selected) == false) {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Friendship","You cannot accept this friendship request! (it was already accepted or rejected)");
        }
        else {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Friendship","Friendship request denied!");
        }
    }

    public void showAddWindowDialog(String type) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ro/ubbcluj/cs/map/laborator8/AddWindow.fxml"));

            AnchorPane root = loader.load();
            Stage dialogStage = new Stage();
            if(Objects.equals(type, "addUser")){
                dialogStage.setTitle("Add User");
            }
            if(Objects.equals(type, "addFriendship")){
                dialogStage.setTitle("Add Friendship");
            }
            if(Objects.equals(type, "updateUser")){
                dialogStage.setTitle("Update User");
            }
            // Create the dialog Stage.

            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            AddWindowController addWindowController = loader.getController();
            if(loggeduser != null)
                addWindowController.setService(serv, dialogStage, type, loggeduser.getId());
            else
                addWindowController.setService(serv, dialogStage, type, 0L);

            dialogStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMessageWindowDialog() {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ro/ubbcluj/cs/map/laborator8/MessageWindow.fxml"));

            AnchorPane root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("All Messages");
            // Create the dialog Stage.

            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);
            Friendship selectedFriendship = messageTableView.getSelectionModel().getSelectedItem();
            User selectedTo;
            if(Objects.equals(selectedFriendship.getUser1().getId(), loggeduser.getId()))
                selectedTo = selectedFriendship.getUser2();
            else
                selectedTo = selectedFriendship.getUser1();
            MessageController messageController = loader.getController();
            messageController.setService(serv, dialogStage, loggeduser, selectedTo);

            dialogStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showNewMessageDialog() {
        try {
            List<User> myList = new ArrayList<>();
            List<Friendship> myFriendshipList = messageTableView.getSelectionModel().getSelectedItems();
            myFriendshipList.forEach(fr -> {
                if(Objects.equals(fr.getUser1().getId(), loggeduser.getId()))
                    myList.add(fr.getUser2());
                else
                    myList.add(fr.getUser1());
            });
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ro/ubbcluj/cs/map/laborator8/NewMessage.fxml"));

            AnchorPane root = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Send Messsage");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            NewMessageController newMessController = loader.getController();
            newMessController.setService(serv, dialogStage,loggeduser, myList);
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
            loader.setLocation(getClass().getResource("/ro/ubbcluj/cs/map/laborator8/DeleteWindow.fxml"));


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



    @Override
    public void update(ChangeEvent changeEvent) {
        initModel();
        if(changeEvent.getType() == ChangeEventType.ADDFRIENDSHIP || changeEvent.getType() == ChangeEventType.DELETEFRIENDSHIP)
            initFriendshipModel();
    }


    private void checkPages(){
        previousButton.setDisable(currentPage == 0);
        nextButton.setDisable((currentPage+1)*numberOfRecordsPerPage >= totalNumberOfElements);
    }
    public void nextPage(ActionEvent actionEvent) {
        currentPage++;
        initModel();
    }

    public void previousPage(ActionEvent actionEvent) {
        currentPage--;
        initModel();
    }
    public void pageNumber(ActionEvent actionEvent){
        String text=numberOfUsers.getText();
        if (checkNumber(text) && Integer.parseInt(text) > 0) {
            // Valid number
            currentPage=0;
            numberOfRecordsPerPage=Integer.parseInt(text);
            initModel();
        }
    }
    private boolean checkNumber(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("\\d+");
    }
}
