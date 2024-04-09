package com.example.mercadouq.controller;

import com.example.mercadouq.entities.Factura;
import com.example.mercadouq.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FacturaController {
    @Autowired
    private FacturaService facturaService;

    @PostMapping("/registrarFacturas")
    public ResponseEntity<?> registrarFacturas(@RequestBody MultipartFile file){
        return facturaService.registrarFacturas(file);
    }

    @GetMapping("/obtenerFacturaByid")
    public Factura obtenerFacturaById(@RequestBody Long id){
        return facturaService.obtenerFacturaById(id);
    }

    @PutMapping("/actualizarFactura")
    public Factura actualizarFacturaById(@RequestBody Long id){
        return facturaService.
    }
}
