package com.example.mercadouq.controller;

import com.example.mercadouq.entities.DetalleFactura;
import com.example.mercadouq.services.DetalleFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class DetalleFacturaController {

    @Autowired
    private DetalleFacturaService detalleFacturaService;

    @GetMapping("/obtenerDetalleFacturas")
    public List<DetalleFactura> obtenerDetalleFacturas(){
        return detalleFacturaService.obtenerDetalleFacturas();
    }

    @GetMapping("/findFacturasByid")
    public List<DetalleFactura> findDetallesFacturasByIdFactura(@RequestBody Long idFactura){
        return detalleFacturaService.findDetallesFacturasById(idFactura);
    }

    @PostMapping("/registrarDetallesFacturas")
    public ResponseEntity<?> registrarDetallesFacturas(@RequestBody MultipartFile file){
        return detalleFacturaService.registrarDetallesFacturas(file);
    }
}
