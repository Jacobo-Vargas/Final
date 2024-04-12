package com.example.mercadouq.services;

import com.example.mercadouq.entities.Premio;
import com.example.mercadouq.repository.IPremioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PremioService {

    @Autowired
    private IPremioRepository premioRepository;
    @Autowired
    private MercadoUtilService mercadoUtilService;

    public ResponseEntity<Premio> registrarPremio(Premio premio) {
        return ResponseEntity.ok().body(premioRepository.save(premio));
    }

//    public ResponseEntity<?> escogerPremiados(){
//        try{
//            mercadoUtilService.escogerPremiados();
//            return ResponseEntity.ok().body("Proceso exitoso.");
//        }catch (Exception e){
//            return ResponseEntity.badRequest().body("Ocurrió un error.");
//        }
//    }

}
