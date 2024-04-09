package com.example.mercadouq.controller;

import com.example.mercadouq.services.DetalleFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DetalleFacturaController {

    @Autowired
    DetalleFacturaService detalleFacturaService;

    @GetMapping("/findFacturasByid")
    public ResponseEntity<?> findFacturasById(@RequestBody Long id){
        return detalleFacturaService.findDetallesFacturasById(id);
    }

    @PostMapping("/registrarDetallesFacturas")
    public ResponseEntity<?> registrarDetallesFacturas(@RequestBody MultipartFile file){
        return detalleFacturaService.registrarDetallesFacturas(file);
    }
}
