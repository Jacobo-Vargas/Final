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
}
