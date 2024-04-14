package com.example.mercadouq.controller;

import com.example.mercadouq.entities.Premio;
import com.example.mercadouq.entities.enums.Estado;
import com.example.mercadouq.services.PremioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PremioController {

    @Autowired
    private PremioService premioService;

    @GetMapping("/registrarPremio")
    public ResponseEntity<?> registrarPremio(@RequestBody Premio premio){
        return premioService.registrarPremio(premio);
    }

    @GetMapping("/obtenerPremiosByEstado")
    public ResponseEntity<?> getPremioByEstado(Estado estado){
        return premioService.getPremioByEstado(estado);
    }

    @GetMapping("/obtenerPremios")
    public ResponseEntity<?> getPremios(){
        return premioService.getPremios();
    }

    @PostMapping("/actualizarEstadoEncolado")
    public void actualizarEstadoEncolado(Premio p) {
        premioService.actualizarEstadoEncolado(p);
    }

    @GetMapping("/escogerPremiados")
    public ResponseEntity<?> escogerPremiados(){
        return premioService.escogerPremiados();
    }

    @PostMapping("/enviarPremios")
    public ResponseEntity<List<Premio>> enviarPremios(@RequestBody int premiosPorAvion, int cantidadAviones){
        return premioService.enviarPremios(premiosPorAvion, cantidadAviones);
    }
}
