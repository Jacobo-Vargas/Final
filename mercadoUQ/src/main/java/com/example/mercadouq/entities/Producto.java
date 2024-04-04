package com.example.mercadouq.entities;

import com.example.mercadouq.entities.enums.Categoria;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPRODUCTO")
    private Long id;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "Precio", nullable = false)
    private double precio;

    @Enumerated(EnumType.STRING)
    @Column(name = "Categoria", nullable = false)
    private Categoria categoria;

}
