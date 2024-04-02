module com.example.mercadoapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;
    requires okhttp3;


    opens com.example.mercadoapp.viewController to javafx.fxml;
    opens com.example.mercadoapp.dto to com.google.gson;
    exports com.example.mercadoapp;
}