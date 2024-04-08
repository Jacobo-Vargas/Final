package com.example.mercadouq.services;

import com.example.mercadouq.repository.IDetallesFactura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class DetalleFacturaService {

    @Autowired
    IDetallesFactura detallesFactura;

    public ResponseEntity<?> FindFacturasById(Long id){
        return ResponseEntity.ok().body(detallesFactura.findFacturasById(id));
    }
}
