package com.example.mercadouq.services;

import com.example.mercadouq.entities.Pais;
import com.example.mercadouq.repository.IPaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PaisService {

    @Autowired
    private IPaisRepository paisRepository;

    public Pais obtenerPais(Long id){
        return paisRepository.findById(id).orElse(null);
    }
}
