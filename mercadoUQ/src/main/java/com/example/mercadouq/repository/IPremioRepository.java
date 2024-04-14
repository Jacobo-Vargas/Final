package com.example.mercadouq.repository;

import com.example.mercadouq.entities.Premio;
import com.example.mercadouq.entities.enums.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPremioRepository extends JpaRepository<Premio, Long> {

    @Query("SELECT e FROM Premio e WHERE e.estado = :estado")
    List<Premio> getPremioByEstado(Estado estado);

}
