package ro.iss2024.laborator5iss.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.iss2024.laborator5iss.domain.Book;
import ro.iss2024.laborator5iss.domain.User;
import ro.iss2024.laborator5iss.service.Service;
import ro.iss2024.laborator5iss.utils.Observer;

import java.util.List;

public class AdminWindowController implements Observer {
    ObservableList<Book> bookModel = FXCollections.observableArrayList();
    ObservableList<String> rentalModel = FXCollections.observableArrayList();

    @FXML
    TableView<Book> bookTableView;
    @FXML
    TableColumn<Book, String> titleColumn;
    @FXML
    TableColumn<Book, String> authorColumn;
    @FXML
    TableColumn<Book, String> publishingHouseColumn;
    @FXML
    TableColumn<Book, Integer> yearColumn;
    @FXML
    ListView<String> rentalList;
    private Service serv;
    private User user;

    public void initialize(){
        titleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        publishingHouseColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("publishingHouse"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("year"));
        bookTableView.setItems(bookModel);
        rentalList.setItems(rentalModel);
    }

    public void setServ(Service serv) {
        this.serv = serv;
    }

    public void setUser(User selected) {
        this.user = selected;
    }

    public void initModel(){
        bookModel.clear();
        Iterable<Book> books = serv.getAllBooks();
        for (Book book : books) {
            bookModel.add(book);
        }
        bookTableView.setItems(bookModel);
        rentalModel.clear();
        List<String> rentals = serv.getRentedList();
        rentalModel.addAll(rentals);
        rentalList.setItems(rentalModel);
    }

    @FXML
    public void addBook(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ro/iss2024/laborator5iss/AddBookWindow.fxml"));

            AnchorPane root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Book Window");
            // Create the dialog Stage.
            dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/ro/iss2024/laborator5iss/style.css").toExternalForm());
            dialogStage.setScene(scene);

            AddBookWindowController addBookWindowController = loader.getController();
            addBookWindowController.setServ(serv);
            dialogStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteBook(){
        Book book = bookTableView.getSelectionModel().getSelectedItem();
        if (book == null){
            return;
        }
        if(book.isRented()){
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Book Delete","You cannot delete a book that is currently rented!");
        }
        else{
            serv.deleteBook(book.getId());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Book Delete","You have deleted the book successfully!");
        }
        initModel();
    }

    @FXML
    public void confirmReturn(){
        String rental = rentalList.getSelectionModel().getSelectedItem();
        if (rental == null){
            return;
        }
        String[] parts = rental.split(" ");
        serv.confirmReturn(Integer.parseInt(parts[0]));
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Rental Confirmation","You have confirmed the rental successfully!");
        initModel();
    }

    @Override
    public void update() {
        initModel();
    }
}
