package com.example.mercadouq.services;

import com.example.mercadouq.entities.Producto;
import com.example.mercadouq.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private IProductoRepository productoRepository;
    @Autowired
    private MercadoUtilService mercadoUtilService;


    public ResponseEntity<String> registrarProducto(Producto producto){
        if(productoRepository.existsById(producto.getId())){
            return ResponseEntity.badRequest().body("El usuario ya existe.");
        } else {
            productoRepository.save(producto);
            return ResponseEntity.ok().body("Se registró con éxito.");
        }
    }

    public ResponseEntity<List<String>> registrarProductos(MultipartFile file){
        List<Producto> productos = null;
        List<String> existentes = new ArrayList<>();
        try{
            productos = mercadoUtilService.cargarProductosDesdeCSV(file);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        if(productos.isEmpty()){
            return ResponseEntity.badRequest().body(null);
        } else {
            for (Producto producto: productos){
                if(productoRepository.existsById(producto.getId())){
                    existentes.add(producto.getNombre());
                } else {
                    productoRepository.save(producto);
                }
            }
        }
        return ResponseEntity.ok().body(existentes);
    }

    public ResponseEntity<?> obtenerProducto(String nombre){
        return productoRepository.findByName(nombre) != null ? ResponseEntity.ok().body(productoRepository.findByName(nombre)): ResponseEntity.badRequest().body("No existe el producto.");
    }

    public ResponseEntity<?> obtenerProductoById(Long id){
        return ResponseEntity.ok().body(productoRepository.findById(id).orElse(null));
    }

    public List<Producto> obtenerProductos() {
return  productoRepository.findAll();

    }
}
