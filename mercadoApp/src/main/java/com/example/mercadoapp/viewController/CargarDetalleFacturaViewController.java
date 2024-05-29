package com.example.mercadoapp.viewController;

import com.example.mercadoapp.MercadoUQ;
import com.example.mercadoapp.apiService.ApiDetalleFacturaService;
import com.example.mercadoapp.dto.DetalleFacturaDTO;
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

public class CargarDetalleFacturaViewController {

    @FXML
    public Label lblNombreArchivo;
    @FXML
    public TableView<DetalleFacturaDTO> tableDetallesFactura;
    @FXML
    public TableColumn<DetalleFacturaDTO, String> tcId;
    @FXML
    public TableColumn<DetalleFacturaDTO, String> tcIdFactura;
    @FXML
    public TableColumn<DetalleFacturaDTO, String> tcProducto;
    @FXML
    public TableColumn<DetalleFacturaDTO, String> tcCantidad;
    @FXML
    public TableColumn<DetalleFacturaDTO, String> tcValorUnitario;
    @FXML
    public TableColumn<DetalleFacturaDTO, String> tcTotal;

    private ObservableList<DetalleFacturaDTO> listaDetallesFactura;
    private File archivoSeleccionado;
    private ApiDetalleFacturaService apiDetalleFacturaService;


    @FXML
    private ImageView backgroundImage;

    public void initialize(){
        // Load the image
        Image image = new Image(MercadoUQ.class.getResource("background.png").toExternalForm());
        backgroundImage.setImage(image);
        this.apiDetalleFacturaService = new ApiDetalleFacturaService();
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
                List<String> noRegistrados = apiDetalleFacturaService.registrarDetalleFacturas(archivoSeleccionado);
                actualizarTabla();
                if (!noRegistrados.isEmpty()) {
                    String mensaje = "Los siguientes detalles de factura ya están registrados: \n";
                    for (String detalle : noRegistrados) {
                        mensaje += "-" + detalle + "\n";
                    }
                    MercadoUtils.alerta("Ya existen", mensaje, Alert.AlertType.WARNING);
                } else {
                    MercadoUtils.alerta("Éxito", "Se realizó el registro de detalles de factura con éxito.", Alert.AlertType.CONFIRMATION);
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
        if (listaDetallesFactura == null || listaDetallesFactura.isEmpty()) {
            try {
                listaDetallesFactura = FXCollections.observableArrayList(apiDetalleFacturaService.obtenerDetalleFaturas());
                tableDetallesFactura.setItems(listaDetallesFactura);
            } catch (NullPointerException e) {
                MercadoUtils.alerta("Atención", "No existen detalles de factura en la base de datos", Alert.AlertType.INFORMATION);
            }
        }
    }

    private void initDataBinding() {
        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcIdFactura.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdFactura().toString()));
        tcProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProducto().getNombre()));
        tcCantidad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCantidad())));
        tcValorUnitario.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getValorUnitario())));
        tcTotal.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTotal())));
    }
}
