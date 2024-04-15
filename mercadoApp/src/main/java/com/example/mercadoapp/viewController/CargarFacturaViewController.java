package com.example.mercadoapp.viewController;

import com.example.mercadoapp.apiService.ApiFacturaService;
import com.example.mercadoapp.apiService.ApiPaisService;
import com.example.mercadoapp.dto.FacturaDTO;
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

import java.io.File;
import java.util.List;

public class CargarFacturaViewController {


    @FXML
    public Label lblNombreArchivo;
    @FXML
    public TableView<FacturaDTO> tableFacturas;

    @FXML
    public TableColumn<FacturaDTO, String>  tcId;
    @FXML
    public TableColumn<FacturaDTO, String> tcFecha;
    @FXML
    public TableColumn<FacturaDTO, String> tcNombreCLiente;

    private ObservableList<FacturaDTO> listaFactura;

    private File archivoSeleccionado;
    private ApiFacturaService apiFacturaService;


    public void initialize(){
        this.apiFacturaService = new ApiFacturaService();
        initDataBinding();
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
                List<String> noRegistrados = apiFacturaService.registrarFacturas(archivoSeleccionado);
                actualizarTabla();
                if (!noRegistrados.isEmpty()){
                    String mensaje = "Las siguientes facturas ya están registradas: \n";
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
        if(listaFactura == null || listaFactura.isEmpty()){
            try{
                listaFactura = FXCollections.observableArrayList(apiFacturaService.obtenerFaturas());
                tableFacturas.setItems(listaFactura);
            }catch (NullPointerException e){
                MercadoUtils.alerta("Atención", "No existen facturas en la base de datos", Alert.AlertType.INFORMATION);
            }
        }
    }

    private void initDataBinding() {

        tcId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        tcNombreCLiente.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCliente().getNombre())));
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFecha())));
    }
}
