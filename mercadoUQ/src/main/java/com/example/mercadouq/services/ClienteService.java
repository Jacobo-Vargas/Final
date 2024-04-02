package com.example.mercadouq.services;

import com.example.mercadouq.entities.Cliente;
import com.example.mercadouq.repository.IClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    IClienteRepository clienteRepository;

    public ResponseEntity<List<Long>> registrarClientes(List<Cliente> list){

        List<Long> listaNoRegistrados = new ArrayList<>();
        for (Cliente c: list) {
            if(clienteRepository.existsById(c.getCedula())){
                listaNoRegistrados.add(c.getCedula());
            } else {
                clienteRepository.save(c);
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
            return ResponseEntity.ok("Cliente no se encuentra registrado");
        }
    }

    public ResponseEntity<?> obtenerById(long id) {
        if(clienteRepository.existsById(id)){
            return ResponseEntity.ok(clienteRepository.findById(id));
        } else {
            return ResponseEntity.ok("Cliente no se encuentra registrado");
        }
    }
}
