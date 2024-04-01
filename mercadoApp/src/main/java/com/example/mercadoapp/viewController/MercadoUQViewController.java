package com.example.mercadoapp.viewController;

import com.example.mercadoapp.dto.ClienteDTO;
import com.example.mercadoapp.service.ApiServiceCliente;
import com.example.mercadoapp.util.MercadoUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MercadoUQViewController {

    @FXML
    private TableView<ClienteDTO> tableClientes;

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

    private ApiServiceCliente apiServiceCliente;

    public void initialize(){
        init();
    }

    private void init() {
        tableClientes.setItems(listaClientes);
        this.apiServiceCliente = new ApiServiceCliente();
        initDataBinding();
    }

    @FXML
    private void load() {
        try{
            if(archivoSeleccionado != null){
                List<ClienteDTO> lista = MercadoUtils.loadClientesDesdeCSV(archivoSeleccionado);
                listaClientes = FXCollections.observableArrayList(lista);
                List<Long> noRegistrados = apiServiceCliente.registrarClientes(lista);
                if (!noRegistrados.isEmpty()){
                    new Alert(Alert.AlertType.valueOf(noRegistrados.size() + " ya existen, no se registraron"));
                }
            } else {
                actualizarTabla();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void selectFile() {
       archivoSeleccionado = MercadoUtils.buscarArchivo();
    }

    private void actualizarTabla() throws IOException {
        tableClientes.setItems(listaClientes);
        if(listaClientes == null || listaClientes.isEmpty()){
           listaClientes  = FXCollections.observableArrayList(apiServiceCliente.obtenerClientes());
           tableClientes.setItems(listaClientes);
        }
    }

    private void initDataBinding() {

        tcCedula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCedula().toString()));
        tcApellido.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getApellido())));
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNombre())));
        tcGenero.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getGenero())));
        tcEdad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getEdad())));
        tcDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
        tcTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono().toString()));
    }

}
