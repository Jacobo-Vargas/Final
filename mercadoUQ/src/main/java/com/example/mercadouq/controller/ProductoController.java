package com.example.mercadouq.controller;

import com.example.mercadouq.entities.Factura;
import com.example.mercadouq.entities.Producto;
import com.example.mercadouq.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ProductoController {

    @Autowired
    private ProductoService productoService;


    @GetMapping("/obtenerProducto/{nombre}")
    public ResponseEntity<?> obtenerProducto(@PathVariable String nombre){
        return  productoService.obtenerProducto(nombre);
    }


    @GetMapping("/obtenerProductos")
    public List<Producto> obtenerProductos(){
        return productoService.obtenerProductos();
    }

    @PostMapping("/registrarProductos")
    public ResponseEntity<?> registrarProductos(@RequestBody MultipartFile file){
        return productoService.registrarProductos(file);
    }

    @PostMapping("/registrarProducto")
    public ResponseEntity<?> registrarProducto(@RequestBody Producto producto){
        return productoService.registrarProducto(producto);
    }

    @GetMapping("/obtenerProductoByid")
    public ResponseEntity<?> obtenerProductoById(@RequestBody Long id){
        return productoService.obtenerProductoById(id);
    }



}
