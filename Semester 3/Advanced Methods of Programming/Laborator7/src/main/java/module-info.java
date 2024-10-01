module ro.ubbcluj.cs.map.laborator7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens ro.ubbcluj.cs.map.laborator7 to javafx.fxml;
    exports ro.ubbcluj.cs.map.laborator7;
    exports ro.ubbcluj.cs.map.laborator7.domain;
    opens ro.ubbcluj.cs.map.laborator7.domain to javafx.fxml;
    exports ro.ubbcluj.cs.map.laborator7.repository;
    opens ro.ubbcluj.cs.map.laborator7.repository to javafx.fxml;
    exports ro.ubbcluj.cs.map.laborator7.service;
    opens ro.ubbcluj.cs.map.laborator7.service to javafx.fxml;
    exports ro.ubbcluj.cs.map.laborator7.controller;
    opens ro.ubbcluj.cs.map.laborator7.controller to javafx.fxml;
}