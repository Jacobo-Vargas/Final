package com.example.mercadouq.services;

import com.example.mercadouq.entities.Premio;
import com.example.mercadouq.entities.enums.Estado;
import com.example.mercadouq.repository.IPremioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PremioService {

    @Autowired
    private IPremioRepository premioRepository;
    @Autowired
    private MercadoUtilService mercadoUtilService;

    public ResponseEntity<Premio> registrarPremio(Premio premio) {
        return ResponseEntity.ok().body(premioRepository.save(premio));
    }


    public ResponseEntity<?> getPremioByEstado(Estado estado) {
        return ResponseEntity.ok().body(premioRepository.getPremioByEstado(estado));
    }
    public ResponseEntity<?> getPremios() {
        return ResponseEntity.ok().body(premioRepository.findAll());
    }

    public void actualizarEstadoEncolado(Premio p) {
        premioRepository.save(p);
    }

    /**
     * Método que hace el proceso de encolar los premios en orden y así mismo despacharlos
     */
    public ResponseEntity<List<Premio>> enviarPremios(int premiosPorAvion, int cantidadAviones) {
        return ResponseEntity.ok().body(mercadoUtilService.enviarPremios(premiosPorAvion, cantidadAviones));
    }

    // Método que acciona el proceso de escoger los premiados
    public ResponseEntity<?> escogerPremiados(){
        try{
            mercadoUtilService.escogerPremiados();
            return ResponseEntity.ok().body("Proceso exitoso.");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Ocurrió un error.");
        }
    }

}
