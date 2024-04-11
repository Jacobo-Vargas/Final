package com.example.mercadouq.repository;

import com.example.mercadouq.entities.DetalleFactura;
import com.example.mercadouq.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFacturaRepository extends JpaRepository<Factura, Long> {

    @Query("SELECT e FROM Factura e order by e.cliente.cedula")
    List<Factura> getFactOrderByClient();

    @Query("SELECT e FROM Factura e WHERE e.cliente.cedula = :id")
    List<Factura> obtenerFacturasByIdClient(Long id);
}
