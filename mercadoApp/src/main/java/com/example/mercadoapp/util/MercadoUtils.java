package com.example.mercadoapp.util;


import com.example.mercadoapp.dto.ClienteDTO;
import com.example.mercadoapp.dto.Genero;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MercadoUtils {

    public static final String URL = "http://localhost:8081";

    public static File buscarArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        return  fileChooser.showOpenDialog(new Stage());
    }

    public static void alerta(String title, String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
