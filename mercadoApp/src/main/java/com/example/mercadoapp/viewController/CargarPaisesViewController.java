package com.example.mercadoapp.viewController;

import com.example.mercadoapp.MercadoUQ;
import com.example.mercadoapp.apiService.ApiPaisService;
import com.example.mercadoapp.dto.PaisDTO;
import com.example.mercadoapp.util.MercadoUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.List;

public class CargarPaisesViewController {


    @FXML
    public Label lblNombreArchivo;
    @FXML
    public TableView<PaisDTO> tablePaises;
    @FXML
    public TableColumn<PaisDTO, String> tcNombre;
    @FXML
    public TableColumn<PaisDTO, String>  tcId;
    @FXML
    public TableColumn<PaisDTO, String>  tcTipo;

    private ObservableList<PaisDTO> listaPais;

    private File archivoSeleccionado;
    private ApiPaisService apiPaisService;

    @FXML
    private ImageView backgroundImage;

    public void initialize(){
        this.apiPaisService = new ApiPaisService();
        initDataBinding();
        // Load the image
        Image image = new Image(MercadoUQ.class.getResource("background.png").toExternalForm());
        backgroundImage.setImage(image);
    }
    @FXML
    public void selectFile() {
        archivoSeleccionado = MercadoUtils.buscarArchivo();
        lblNombreArchivo.setText(archivoSeleccionado.getName());
    }

    @FXML
    public void load() {
        try{
            if(archivoSeleccionado != null){
                List<String> noRegistrados = apiPaisService.registrarPaises(archivoSeleccionado);
                actualizarTabla();
                if (!noRegistrados.isEmpty()){
                    String mensaje = "Los siguientes paises ya están registrados: \n";
                    for (String i: noRegistrados){
                        mensaje += "-" + i + "\n";
                    }

                    MercadoUtils.alerta("Ya existen", mensaje, Alert.AlertType.WARNING);
                } else {
                    MercadoUtils.alerta("Éxito", "Se realizó el registro con éxito.", Alert.AlertType.CONFIRMATION);
                }
            } else {
                actualizarTabla();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void actualizarTabla() throws Exception {
        if(listaPais == null || listaPais.isEmpty()){
            try{
                listaPais = FXCollections.observableArrayList(apiPaisService.obtenerPaises());
                tablePaises.setItems(listaPais);
            }catch (NullPointerException e){
                MercadoUtils.alerta("Atención", "No existen paises en la base de datos", Alert.AlertType.INFORMATION);
            }
        }
    }

    private void initDataBinding() {

        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNombre())));
        tcTipo.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTipoPais())));
    }
}
