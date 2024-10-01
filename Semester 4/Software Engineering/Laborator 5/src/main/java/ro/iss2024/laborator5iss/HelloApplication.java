package ro.iss2024.laborator5iss;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import ro.iss2024.laborator5iss.repository.BookDBRepository;
import ro.iss2024.laborator5iss.repository.RentalDBRepository;
import ro.iss2024.laborator5iss.repository.UserDBRepository;
import ro.iss2024.laborator5iss.service.Service;
import ro.iss2024.laborator5iss.controller.LoginWindowController;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Properties serverProps = new Properties();
        try {
            serverProps.load(new FileReader("bd.properties"));
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.properties " + e);
        }

        UserDBRepository userRepo = new UserDBRepository(serverProps);
        BookDBRepository bookRepo = new BookDBRepository(serverProps);
        RentalDBRepository rentalRepo = new RentalDBRepository(serverProps);
        Service service = new Service(userRepo, bookRepo, rentalRepo);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        LoginWindowController controller = fxmlLoader.getController();
        controller.setServ(service);
        stage.setTitle("Login!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}