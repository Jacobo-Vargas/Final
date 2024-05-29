package com.example.mercadoapp.viewController;

import com.example.mercadoapp.MercadoUQ;
import com.example.mercadoapp.apiService.ApiDetalleFacturaService;
import com.example.mercadoapp.apiService.ApiProductoService;
import com.example.mercadoapp.dto.ProductoDTO;
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

public class CargarProductosViewController {

    @FXML
    public Label lblNombreArchivo;
    @FXML
    public TableView<ProductoDTO> tableProductos;
    @FXML
    public TableColumn<ProductoDTO, String> tcId;
    @FXML
    public TableColumn<ProductoDTO, String> tcNombre;
    @FXML
    public TableColumn<ProductoDTO, String> tcPrecio;
    @FXML
    public TableColumn<ProductoDTO, String> tcCategoria;
    

    private ObservableList<ProductoDTO> listaProductos;
    private File archivoSeleccionado;
    private ApiProductoService apiProductoService;

    @FXML
    private ImageView backgroundImage;



    public void initialize(){
        // Load the image
        Image image = new Image(MercadoUQ.class.getResource("background.png").toExternalForm());
        backgroundImage.setImage(image);
        this.apiProductoService = new ApiProductoService();
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
                List<String> noRegistrados = apiProductoService.registrarProductos(archivoSeleccionado);
                actualizarTabla();
                if (!noRegistrados.isEmpty()) {
                    String mensaje = "Los siguientes productos ya están registrados: \n";
                    for (String detalle : noRegistrados) {
                        mensaje += "-" + detalle + "\n";
                    }
                    MercadoUtils.alerta("Ya existen", mensaje, Alert.AlertType.WARNING);
                } else {
                    MercadoUtils.alerta("Éxito", "Se realizó el registro de productos con éxito.", Alert.AlertType.CONFIRMATION);
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
        if (listaProductos == null || listaProductos.isEmpty()) {
            try {
                listaProductos = FXCollections.observableArrayList(apiProductoService.obtenerProductos());
                tableProductos.setItems(listaProductos);
            } catch (NullPointerException e) {
                MercadoUtils.alerta("Atención", "No existen productos en la base de datos", Alert.AlertType.INFORMATION);
            }
        }
    }

    private void initDataBinding() {
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecio())));
        tcCategoria.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCategoria())));

    }
}
