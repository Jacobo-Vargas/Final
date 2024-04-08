package com.example.mercadouq.controller;

import com.example.mercadouq.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FacturaController {
    @Autowired
    private FacturaService facturaService;

    @PostMapping("/registrarFacturas")
    public ResponseEntity<?> registrarFacturas(@RequestBody MultipartFile file){
        return facturaService.registrarFacturas(file);
    }
}
