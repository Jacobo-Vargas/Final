package com.example.mercadoapp.viewController;

import com.example.mercadoapp.apiService.ApiObsequioService;
import com.example.mercadoapp.dto.ObsequioDTO;
import com.example.mercadoapp.util.MercadoUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.util.List;

public class CargarObsequiosViewController {

    @FXML
    public Label lblNombreArchivo;
    @FXML
    public TableView<ObsequioDTO> tableObsequios;
    @FXML
    public TableColumn<ObsequioDTO, String> tcId;
    @FXML
    public TableColumn<ObsequioDTO, String> tcNombre;
    @FXML
    public TableColumn<ObsequioDTO, String> tcCategoria;
    @FXML
    public TableColumn<ObsequioDTO, String> tcPrioridad;

    private ObservableList<ObsequioDTO> listaObsequios;
    private File archivoSeleccionado;
    private ApiObsequioService apiObsequioService;

    public void initialize() {
        this.apiObsequioService = new ApiObsequioService();
        initDataBinding();
    }

    @FXML
    public void selectFile() {
        archivoSeleccionado = MercadoUtils.buscarArchivo();
        lblNombreArchivo.setText(archivoSeleccionado.getName());
    }

    @FXML
    public void load() {
        try {
            if (archivoSeleccionado != null) {
                List<String> noRegistrados = apiObsequioService.registrarObsequios(archivoSeleccionado);
                actualizarTabla();
                if (!noRegistrados.isEmpty()) {
                    String mensaje = "Los siguientes obsequios ya están registrados: \n";
                    for (String detalle : noRegistrados) {
                        mensaje += "-" + detalle + "\n";
                    }
                    MercadoUtils.alerta("Ya existen", mensaje, Alert.AlertType.WARNING);
                } else {
                    MercadoUtils.alerta("Éxito", "Se realizó el registro de obsequios con éxito.", Alert.AlertType.CONFIRMATION);
                }
            } else {
                actualizarTabla();
            }
        } catch (Exception e) {
            System.out.println("Error archivo");
            throw new RuntimeException(e);
        }
    }

    private void actualizarTabla() throws Exception {
        if (listaObsequios == null || listaObsequios.isEmpty()) {
            try {
                listaObsequios = FXCollections.observableArrayList(apiObsequioService.obtenerObsequios());
                tableObsequios.setItems(listaObsequios);
            } catch (NullPointerException e) {
                MercadoUtils.alerta("Atención", "No existen obsequios en la base de datos", Alert.AlertType.INFORMATION);
            }
        }
    }

    private void initDataBinding() {
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCategoria())));
        tcPrioridad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrioridad())));
    }
}
