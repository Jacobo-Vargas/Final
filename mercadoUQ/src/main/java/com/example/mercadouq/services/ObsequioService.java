package com.example.mercadouq.services;

import com.example.mercadouq.entities.Obsequio;
import com.example.mercadouq.entities.enums.Categoria;
import com.example.mercadouq.repository.IObsequioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Queue;

@Service
public class ObsequioService {

    @Autowired
    private IObsequioRepository obsequioRepository;


    public Queue<Obsequio> getObsequiosByCategoria(Categoria categoria) {
        return obsequioRepository.getObsequiosByCategoria(categoria);
    }

    public void eliminarObsequio(Long id) {
        obsequioRepository.deleteById(id);
    }
}
