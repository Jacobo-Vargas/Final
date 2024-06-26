package com.example.mercadouq.services;

import com.example.mercadouq.entities.Cliente;
import com.example.mercadouq.entities.Pais;
import com.example.mercadouq.repository.IClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private MercadoUtilService mercadoUtilService;

    @Autowired
    private PaisService paisService;

    public ResponseEntity<?> registrarCliente(Cliente cliente){
        Pais pais = paisService.obtenerPais(cliente.getPais().getId());
        if(pais != null){
            cliente.setPais(pais);
            if(!clienteRepository.existsById(cliente.getCedula())){
                clienteRepository.save(cliente);
                return ResponseEntity.ok().body(cliente);
            } else {
                return ResponseEntity.badRequest().body("Cliente ya existe.");
            }


        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<Long>> registrarClientes(MultipartFile file){

        List<Cliente> list = null;
        List<Long> listaNoRegistrados = new ArrayList<>();

        try {
            list = mercadoUtilService.cargarClientesDesdeCSV(file);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        if(list.isEmpty()){
            return ResponseEntity.badRequest().body(null);
        } else {
            for (Cliente c: list) {
                if(clienteRepository.existsById(c.getCedula())){
                    listaNoRegistrados.add(c.getCedula());
                } else {
                    clienteRepository.save(c);
                }
            }
        }


        return ResponseEntity.ok().body(listaNoRegistrados);
    }

    public ResponseEntity<List<Cliente>> obtenerClientes(){
        List<Cliente> lista = clienteRepository.findAll();

        if(lista.isEmpty()){
            return ResponseEntity.ok().body(null);
        }
        return ResponseEntity.ok().body(lista);
    }

    public ResponseEntity<String> actualizarClientes(List<Cliente> list){
        String mensaje = "Usuarios: \n";
        for (Cliente c : list){
            if(clienteRepository.existsById(c.getCedula())){
                clienteRepository.deleteById(c.getCedula());
                clienteRepository.save(c);
            } else {
                mensaje += c.getCedula() + " no existe.";
            }
        }
        if(mensaje.contains("no existe")){
            return ResponseEntity.ok().body(mensaje);
        } else {
            return ResponseEntity.ok("Se actualizaron con éxito");
        }
    }

    public ResponseEntity<String> eliminarById(long id) {
        if(clienteRepository.existsById(id)){
            clienteRepository.deleteById(id);
            return ResponseEntity.ok("Se elimino con éxito");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> obtenerById(long id) {
        try{
            if(clienteRepository.existsById(id)){
                Cliente cliente = clienteRepository.findById(id).orElse(null);
                return ResponseEntity.ok(cliente);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Waiting to Long");
        }
    }
}
