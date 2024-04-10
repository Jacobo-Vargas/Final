package com.example.mercadouq.repository;

import com.example.mercadouq.entities.Premio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPremioRepository extends JpaRepository<Premio, Long> {
}
