package com.example.mercadouq.controller;

import com.example.mercadouq.entities.Producto;
import com.example.mercadouq.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/obtenerProducto/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id){
        return ResponseEntity.ok().body(productoService.obtenerProducto(id));
    }


}
