package com.example.mercadouq.services;

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
    private IFacturaRepository facturaRepository;
    @Autowired
    private ClienteService clienteService;
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

    public Factura obtenerFacturaById(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }

    public ResponseEntity<Factura> actualizarPrecioById(Factura factura) {
        return ResponseEntity.ok().body(facturaRepository.save(factura));
    }

    public List<Factura> obtenerFacturas() {
        return facturaRepository.findAll();
    }

    public List<Factura> getFactOrderByClient() {
        return facturaRepository.getFactOrderByClient();
    }
}
