module ro.ubbcluj.cs.map.laborator8 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.commons.codec;


    opens ro.ubbcluj.cs.map.laborator8 to javafx.fxml;
    exports ro.ubbcluj.cs.map.laborator8;
    exports ro.ubbcluj.cs.map.laborator8.domain;
    opens ro.ubbcluj.cs.map.laborator8.domain to javafx.fxml;
    exports ro.ubbcluj.cs.map.laborator8.repository;
    opens ro.ubbcluj.cs.map.laborator8.repository to javafx.fxml;
    exports ro.ubbcluj.cs.map.laborator8.service;
    opens ro.ubbcluj.cs.map.laborator8.service to javafx.fxml;
    exports ro.ubbcluj.cs.map.laborator8.controller;
    opens ro.ubbcluj.cs.map.laborator8.controller to javafx.fxml;
}