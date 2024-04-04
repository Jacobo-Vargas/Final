package com.example.mercadouq.controller;

import com.example.mercadouq.entities.Cliente;
import com.example.mercadouq.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/registrarClientes")
    public ResponseEntity<Cliente> registrarClientes(@RequestBody Cliente cliente){
        return clienteService.registrarCliente(cliente);
    }

    @PostMapping("/registrarCliente")
    public ResponseEntity<List<Long>> registrarCliente(@RequestBody MultipartFile file){
        return clienteService.registrarClientes(file);
    }

    @GetMapping("/obtenerClientes")
    public ResponseEntity<List<Cliente>> obtenerClientes(){
        return clienteService.obtenerClientes();
    }

    @PutMapping("/actualizarClientes")
    public ResponseEntity<String> actualizarClientes(@RequestBody List<Cliente> list){
        return clienteService.actualizarClientes(list);
    }

    @DeleteMapping("/eliminarCliente/{id}")
    public ResponseEntity<String> eliminarById(@PathVariable long id){
        return clienteService.eliminarById(id);
    }

    @GetMapping("/obtenerCliente/{id}")
    public ResponseEntity<?> obtenerById(@PathVariable long id){
        try{
            return clienteService.obtenerById(id);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Waiting a long");
        }

    }

}
