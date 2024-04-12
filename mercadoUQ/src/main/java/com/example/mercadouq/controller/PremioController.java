package com.example.mercadouq.controller;

import com.example.mercadouq.entities.Premio;
import com.example.mercadouq.services.PremioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PremioController {

    @Autowired
    private PremioService premioService;

    @GetMapping("/registrarPremio")
    public ResponseEntity<?> registrarPremio(@RequestBody Premio premio){
        return premioService.registrarPremio(premio);
    }

//    @GetMapping("/escogerPremiados")
//    public ResponseEntity<?> escogerPremiados(){
//        return premioService.escogerPremiados();
//    }
}
