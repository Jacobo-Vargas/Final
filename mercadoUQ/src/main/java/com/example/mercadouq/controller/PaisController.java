package com.example.mercadouq.controller;

import com.example.mercadouq.entities.Pais;
import com.example.mercadouq.services.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class PaisController {

    @Autowired
    private PaisService paisService;

    @PostMapping("/obtenerPais/{id}")
    public ResponseEntity<Pais> obtenerPais(@PathVariable Long id){
        return ResponseEntity.ok(paisService.obtenerPais(id));
    }

    @PostMapping("/registrarPaises")
    public ResponseEntity<List<String>> obtenerPais(@RequestBody MultipartFile file){
        return paisService.registrarPaises(file);
    }

    @GetMapping("/obtenerPaises")
    public ResponseEntity<List<Pais>> obtenerPaises(){
        return paisService.obtenerPaises();
    }

}