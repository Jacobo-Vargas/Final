package com.example.mercadouq.controller;

import com.example.mercadouq.entities.Pais;
import com.example.mercadouq.services.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PaisController {

    @Autowired
    private PaisService paisService;

    @PostMapping("/obtenerPais/{id}")
    public ResponseEntity<Pais> obtenerPais(@PathVariable Long id){
        return ResponseEntity.ok(paisService.obtenerPais(id));
    }

    @PostMapping("/registrarPaises")
    public ResponseEntity<?> obtenerPais(@RequestBody MultipartFile file){
        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("El archivo esta vacío.");
        }
        return ResponseEntity.ok(paisService.registrarPaises(file));
    }

}