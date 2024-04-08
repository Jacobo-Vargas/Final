package com.example.mercadouq.services;

import com.example.mercadouq.entities.Pais;
import com.example.mercadouq.repository.IPaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaisService {

    @Autowired
    private IPaisRepository paisRepository;

    @Autowired
    private MercadoUtilService mercadoUtilService;

    public Pais obtenerPais(Long id){
        return paisRepository.findById(id).orElse(null);
    }

    public ResponseEntity<List<Pais>> obtenerPaises(){
        return ResponseEntity.ok().body(paisRepository.findAll());
    }
    public ResponseEntity<List<String>> registrarPaises(MultipartFile file){
        List<Pais> list = null;
        try {
            list = mercadoUtilService.cargarPaisesDesdeCSV(file);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        List<String> resultado = new ArrayList<>();
        if(list != null){
            for (Pais pais: list){
                if(paisRepository.existsById(pais.getId())){
                    resultado.add(pais.getNombre());
                } else {
                    paisRepository.save(pais);
                }
            }
            return ResponseEntity.ok().body(resultado);
        }
        return ResponseEntity.badRequest().body(resultado);
    }
}
