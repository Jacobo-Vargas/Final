package com.example.mercadouq.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "CLIENTES")
public class Cliente {

    @Id
    @Column(name = "IDCLIENTE")
    private Long cedula;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "APELLIDO", nullable = false)
    private String apellido;

    @Column(name = "DIRECCION", nullable = false)
    private String direccion;

    @Column(name = "TELEFONO", nullable = false)
    private Long telefono;

    @Column(name = "EDAD", nullable = false)
    private int edad;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENERO", nullable = false)
    private Genero genero;

}
