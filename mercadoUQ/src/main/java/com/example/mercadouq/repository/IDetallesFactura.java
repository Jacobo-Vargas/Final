package com.example.mercadouq.repository;

import com.example.mercadouq.entities.DetalleFactura;
import com.example.mercadouq.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDetallesFactura extends JpaRepository<DetalleFactura, Long> {

    @Query("SELECT e FROM DetalleFactura e WHERE e.factura = :id")
    List<DetalleFactura> findFacturasById(Long id);
}
