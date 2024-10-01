module ro.iss2024.laborator5iss {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.logging.log4j;


    opens ro.iss2024.laborator5iss to javafx.fxml;
    exports ro.iss2024.laborator5iss;
    exports ro.iss2024.laborator5iss.controller;
    exports ro.iss2024.laborator5iss.domain;
    exports ro.iss2024.laborator5iss.repository;
    exports ro.iss2024.laborator5iss.service;
    opens ro.iss2024.laborator5iss.controller to javafx.fxml;
    opens ro.iss2024.laborator5iss.domain to javafx.fxml;
    opens ro.iss2024.laborator5iss.repository to javafx.fxml;
    opens ro.iss2024.laborator5iss.service to javafx.fxml;
}