package com.example.mercadouq.services;

import com.example.mercadouq.entities.DetalleFactura;
import com.example.mercadouq.repository.IDetallesFacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class DetalleFacturaService {

    @Autowired
    private IDetallesFacturaRepository detallesFacturaRepository;

    @Autowired
    private MercadoUtilService mercadoUtilService;

    @Autowired
    private FacturaService facturaService;

    public List<DetalleFactura> findDetallesFacturasById(Long idFactura){
        return detallesFacturaRepository.findFacturasById(idFactura);
    }

    public ResponseEntity<?> registrarDetallesFacturas(MultipartFile file) {
        List<DetalleFactura> lista = new ArrayList<>();
        List<Long> repetidos = new ArrayList<>();

        try{
            lista = mercadoUtilService.cargarDetallesFacturaDesdeCsv(file);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("No se pudo leer el archivo.");
        }
        if(lista.isEmpty()){
            return ResponseEntity.badRequest().body("Verifique que el archivo no este vacío.");
        } else {
            for(DetalleFactura d: lista){
                if(!detallesFacturaRepository.existsById(d.getId())){
                    detallesFacturaRepository.save(d);
                } else {
                    repetidos.add(d.getId());
                }
            }
        }
        mercadoUtilService.actualizarValorTotalFacturas();
        return ResponseEntity.ok().body(repetidos);
    }
}
