package com.example.mercadouq.services;

import com.example.mercadouq.entities.Cliente;
import com.example.mercadouq.entities.DetalleFactura;
import com.example.mercadouq.entities.Factura;
import com.example.mercadouq.repository.IFacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacturaService {

    @Autowired
    IFacturaRepository facturaRepository;
    @Autowired
    ClienteService clienteService;
    @Autowired
    private MercadoUtilService mercadoUtilService;

    public ResponseEntity<?> registrarFacturas(MultipartFile file){
        List<Long> facturasExistentes = new ArrayList<>();
        List<Factura> facturas = new ArrayList<>();

        if(file.isEmpty()){
            return ResponseEntity.badRequest().body("El archivo esta vacío.");
        } else {
            facturas = mercadoUtilService.cargarFacturasDesdeCSV(file);

            for (Factura factura: facturas){
                if(!facturaRepository.existsById(factura.getId())){
                    facturaRepository.save(factura);
                } else {
                    facturasExistentes.add(factura.getId());
                }
            }
        }

        if(!facturas.isEmpty()){
            return ResponseEntity.ok().body(facturasExistentes);
        } else {
            return ResponseEntity.ok().body("Se registraron exitosamente");
        }
    }
}
