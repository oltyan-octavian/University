package ro.iss2024.laborator5iss.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import ro.iss2024.laborator5iss.service.Service;

public class AddBookWindowController {
    private Service serv;

    public void setServ(Service serv) {
        this.serv = serv;
    }
    @FXML
    TextField titleField;

    @FXML
    TextField authorField;

    @FXML
    TextField publishingHouseField;

    @FXML
    TextField yearField;

    @FXML
    private void addBook() {
        String title = titleField.getText();
        String author = authorField.getText();
        String publishingHouse = publishingHouseField.getText();
        int year = Integer.parseInt(yearField.getText());
        serv.addBook(title, author, publishingHouse, year);
        titleField.setText("");
        authorField.setText("");
        publishingHouseField.setText("");
        yearField.setText("");
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Add Book","The book was added successfully!");
    }
}
