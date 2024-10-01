package ro.iss2024.laborator5iss.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ro.iss2024.laborator5iss.domain.Book;
import ro.iss2024.laborator5iss.domain.User;
import ro.iss2024.laborator5iss.repository.IRepository;
import ro.iss2024.laborator5iss.service.Service;
import ro.iss2024.laborator5iss.utils.Observer;

import java.util.Iterator;

public class UserWindowController implements Observer {

    ObservableList<Book> bookModel = FXCollections.observableArrayList();

    @FXML
    TableView<Book> tableView;
    @FXML
    TableColumn<Book, String> titleColumn;
    @FXML
    TableColumn<Book, String> authorColumn;
    @FXML
    TableColumn<Book, String> publishingHouseColumn;
    @FXML
    TableColumn<Book, Integer> yearColumn;
    private Service serv;
    private User user;

    public void initialize(){
        titleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        publishingHouseColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("publishingHouse"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<Book, Integer>("year"));
        tableView.setItems(bookModel);

    }

    public void setServ(Service serv) {
        this.serv = serv;
    }

    public void initModel(){
        bookModel.clear();
        Iterable<Book> books = serv.getBooksNotRented();
        for (Book book : books) {
            bookModel.add(book);
        }
        tableView.setItems(bookModel);
    }

    @FXML
    public void rentBook(){
        Book book = tableView.getSelectionModel().getSelectedItem();
        if (book == null){
            return;
        }
        serv.rentBook(book.getId(), user.getId());
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Book Rental", "Book successfully rented!");
        initModel();
    }

    public void setUser(User selected) {
        this.user = selected;
    }

    @Override
    public void update() {
        initModel();
    }
}
