package com.example.mercadouq.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "PAISES")
public class Pais {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "IDPAIS")
    private Long id;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPOPAIS", nullable = false)
    private TipoPais tipoPais;

}
