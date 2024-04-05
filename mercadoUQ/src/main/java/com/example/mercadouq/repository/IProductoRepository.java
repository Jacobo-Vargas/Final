package com.example.mercadouq.repository;

import com.example.mercadouq.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT e FROM Producto e WHERE e.nombre = :nombre")
    Producto findByName(String nombre);
}
