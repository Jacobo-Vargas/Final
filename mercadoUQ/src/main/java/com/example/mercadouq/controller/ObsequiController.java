package com.example.mercadouq.controller;

import com.example.mercadouq.entities.Obsequio;
import com.example.mercadouq.entities.enums.Categoria;
import com.example.mercadouq.services.ObsequioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Queue;

@RestController
public class ObsequiController {
    @Autowired
    private ObsequioService obsequioService;

    public Queue<Obsequio> getObsequiosByCategoria(Categoria categoria){
        return obsequioService.getObsequiosByCategoria(categoria);
    }

    public void eliminarObsequio(long id){
        return obsequioService.eliminarObsequio(id);
    }

}
