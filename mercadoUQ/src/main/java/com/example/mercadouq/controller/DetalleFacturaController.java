package com.example.mercadouq.controller;

import com.example.mercadouq.services.DetalleFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetalleFacturaController {

    @Autowired
    DetalleFacturaService detalleFacturaService;

    @GetMapping("/findFacturasByid")
    public ResponseEntity<?> findFacturasById(@RequestBody Long id){
        return detalleFacturaService.FindFacturasById(id);
    }
}
