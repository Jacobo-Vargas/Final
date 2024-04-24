package com.example.mercadouq.controller;

import com.example.mercadouq.entities.Obsequio;
import com.example.mercadouq.entities.Pais;
import com.example.mercadouq.entities.enums.Categoria;
import com.example.mercadouq.services.ObsequioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Queue;

@RestController
public class ObsequioController {
    @Autowired
    private ObsequioService obsequioService;

    public Queue<Obsequio> getObsequiosByCategoria (@RequestBody Categoria categoria){
        return obsequioService.getObsequiosByCategoria(categoria);
    }

    public void eliminarObsequio(@RequestBody Long id){
         obsequioService.eliminarObsequio(id);
    }


    @PostMapping("/registrarObsequios")
    public ResponseEntity<?> registrarObsequios(@RequestBody MultipartFile file){
        return obsequioService.registrarObsequios(file);
    }

    @GetMapping("/obtenerObsequios")
    public List<Obsequio> obtenerObsequios(){
        return obsequioService.obtenerObsequios();
    }
}
