module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.demo.Domain to javafx.base;
    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}