package com.example.mercadouq.entities;

import com.example.mercadouq.entities.enums.Categoria;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "OBSEQUIOS")
public class Obsequio {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDOBSEQUIO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORIA", nullable = false)
    private Categoria categoria;

    @Column(name = "PRIORIDAD", nullable = false)
    private Integer prioridad;

    public Obsequio(Long id, String nombre, Categoria categoria, Integer prioridad ) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.prioridad = prioridad;


    }




    public Obsequio() {

    }

}
