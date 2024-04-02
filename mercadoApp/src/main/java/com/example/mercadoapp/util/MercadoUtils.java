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

    public static File buscarArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        return  fileChooser.showOpenDialog(new Stage());
    }

    public static List<ClienteDTO> loadClientesDesdeCSV(File archivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        List<ClienteDTO> listaClientes = new ArrayList<>();
        while ((linea = br.readLine()) != null) {
            String[] datos = linea.split(","); // Separar los datos por coma

            // Crear un nuevo cliente con los datos del CSV y guardarlo en la lista

            Long cedula = Long.valueOf(datos[0]);
            String nombre = datos[1];
            String apellido = datos[2];
            String direccion = datos[3];
            Long telefono = Long.valueOf(datos[4]);
            int edad = Integer.parseInt(datos[5]);
            Genero genero = Genero.valueOf(datos[6]);

            ClienteDTO cliente = new ClienteDTO(cedula, nombre, apellido, direccion, telefono, edad, genero);

            listaClientes.add(cliente);

        }
        br.close();
        return listaClientes;
    }

    public static void alerta(String title, String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
