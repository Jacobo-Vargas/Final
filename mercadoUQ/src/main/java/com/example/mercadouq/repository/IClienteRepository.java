package com.example.mercadouq.repository;

import com.example.mercadouq.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long>{
}
