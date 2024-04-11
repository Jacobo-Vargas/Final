package com.example.mercadouq.entities;


import com.example.mercadouq.entities.enums.Categoria;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "OBSEQUIOS")
public class Obsequio {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "IDOBSEQUIO")
    private Long id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORIA")
    private Categoria categoria;

    @Column(name = "PRIORIDAD")
    private int prioridad;

}
