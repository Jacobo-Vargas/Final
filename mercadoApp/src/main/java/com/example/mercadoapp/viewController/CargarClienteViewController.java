package com.example.mercadoapp.viewController;

import com.example.mercadoapp.dto.ClienteDTO;
import com.example.mercadoapp.apiService.ApiClienteService;
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

public class CargarClienteViewController {


    @FXML
    private Label lblNombreArchivo;

    @FXML
    private TableView<ClienteDTO> tableClientes;

    @FXML
    private TableColumn<ClienteDTO, String> tcPrioridadEnvio;

    @FXML
    private TableColumn<ClienteDTO, String> tcApellido;

    @FXML
    private TableColumn<ClienteDTO, String> tcCedula;

    @FXML
    private TableColumn<ClienteDTO, String> tcDireccion;

    @FXML
    private TableColumn<ClienteDTO, String> tcEdad;

    @FXML
    private TableColumn<ClienteDTO, String> tcGenero;

    @FXML
    private TableColumn<ClienteDTO, String> tcNombre;

    @FXML
    private TableColumn<ClienteDTO, String> tcTelefono;

    private ObservableList<ClienteDTO> listaClientes;

    private File archivoSeleccionado;

    private ApiClienteService apiClienteService;

    public void initialize(){
        this.apiClienteService = new ApiClienteService();
        initDataBinding();
    }

    @SuppressWarnings("StringConcatenationInLoop")
    @FXML
    private void load() {
        try{
            if(archivoSeleccionado != null){
                List<Long> noRegistrados = apiClienteService.registrarClientes(archivoSeleccionado);
                actualizarTabla();
                if (!noRegistrados.isEmpty()){
                    String mensaje = "Los siguientes clientes ya están registrados: \n";
                    for (Long i: noRegistrados){
                        mensaje += "-" + i.toString() + "\n";
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

    @FXML
    private void selectFile() {
        archivoSeleccionado = MercadoUtils.buscarArchivo();
        lblNombreArchivo.setText(archivoSeleccionado.getName());
    }

    private void actualizarTabla() throws Exception {
        if(listaClientes == null || listaClientes.isEmpty()){
            try{
                listaClientes  = FXCollections.observableArrayList(apiClienteService.obtenerClientes());
                tableClientes.setItems(listaClientes);
            }catch (NullPointerException e){
                MercadoUtils.alerta("Atención", "No existen clientes en la base de datos", Alert.AlertType.INFORMATION);
            }
        }
    }

    private void initDataBinding() {
        tcPrioridadEnvio.setCellValueFactory(cellData -> new SimpleStringProperty( String.valueOf(cellData.getValue().getPrioridadEnvio())));
        tcCedula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCedula().toString()));
        tcApellido.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getApellido())));
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNombre())));
        tcGenero.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getGenero())));
        tcEdad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEdad())));
        tcDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        tcTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono().toString()));
    }

}
