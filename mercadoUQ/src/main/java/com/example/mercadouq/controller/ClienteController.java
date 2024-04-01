package com.example.mercadouq.controller;

import com.example.mercadouq.entities.Cliente;
import com.example.mercadouq.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/registrarClientes")
    public ResponseEntity<List<Long>> registrarClientes(@RequestBody List<Cliente> list){
        return clienteService.registrarClientes(list);
    }

    @GetMapping("/obtenerClientes")
    public ResponseEntity<List<Cliente>> obtenerClientes(){
        return clienteService.obtenerClientes();
    }

}