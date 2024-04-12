package com.example.mercadouq.repository;

import com.example.mercadouq.entities.Obsequio;
import com.example.mercadouq.entities.enums.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Queue;

@Repository
public interface IObsequioRepository extends JpaRepository<Obsequio, Long> {

    @Query("SELECT e FROM Obsequio e WHERE e.categoria = :categoria order by e.prioridad")
    Queue<Obsequio> getObsequiosByCategoria(Categoria categoria);
}
