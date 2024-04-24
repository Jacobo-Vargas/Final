package com.example.mercadouq.services;

import com.example.mercadouq.entities.Cliente;
import com.example.mercadouq.entities.Factura;
import com.example.mercadouq.entities.Obsequio;
import com.example.mercadouq.entities.enums.Categoria;
import com.example.mercadouq.repository.IObsequioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

@Service
public class ObsequioService {

    @Autowired
    private IObsequioRepository obsequioRepository;
    @Autowired
    private MercadoUtilService mercadoUtilService;


    public Queue<Obsequio> getObsequiosByCategoria(Categoria categoria) {
        return obsequioRepository.getObsequiosByCategoria(categoria);
    }

    public void eliminarObsequio(Long id) {
        obsequioRepository.deleteById(id);
    }

    public ResponseEntity<List<Long>> registrarObsequios(MultipartFile file){

        List<Obsequio> list = null;
        List<Long> listaNoRegistrados = new ArrayList<>();

        try {
            list = mercadoUtilService.cargarObsequiosDesdeCSV(file);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
        if(list.isEmpty()){
            return ResponseEntity.badRequest().body(null);
        } else {
            for (Obsequio c: list) {
                if(obsequioRepository.existsById(c.getId())){
                    listaNoRegistrados.add(c.getId());
                } else {
                    obsequioRepository.save(c);
                }
            }
        }


        return ResponseEntity.ok().body(listaNoRegistrados);
    }

    public List<Obsequio> obtenerObsequios() {
        return  obsequioRepository.findAll();
    }
}
