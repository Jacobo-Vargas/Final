package com.example.mercadouq.controller;

import com.example.mercadouq.entities.Producto;
import com.example.mercadouq.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/obtenerProducto/{nombre}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable String nombre){
        return ResponseEntity.ok().body(productoService.obtenerProducto(nombre));
    }

    @PostMapping("/registrarProductos")
    public ResponseEntity<?> registrarProductos(@RequestBody MultipartFile file){
        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("El archivo esta vacío.");
        } else {
            return ResponseEntity.ok().body(productoService.registrarProductos(file));
        }
    }

    @PostMapping("/registrarProducto")
    public ResponseEntity<?> registrarProducto(@RequestBody Producto producto){
        return productoService.registrarProducto(producto);
    }

}
