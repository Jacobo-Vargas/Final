package com.example.mercadouq.controller;

import com.example.mercadouq.entities.Factura;
import com.example.mercadouq.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public ResponseEntity<Factura> actualizarPrecioFacturaById(@RequestBody Factura factura){
        return facturaService.actualizarPrecioById(factura);
    }

    @GetMapping("/obtenerFacturas")
    public List<Factura> obtenerFacturas(){
        return facturaService.obtenerFacturas();
    }

    @GetMapping("/getFactOrderByClient")
    public List<Factura> getFactOrderByClient(){
        return facturaService.getFactOrderByClient();
    }

    @GetMapping
    public List<Factura> obtenerFacturasByIdClient(Long id){
        return facturaService.obtenerFacturasByIdClient(id);
    }
}
