package com.example.mercadouq.entities;

import com.example.mercadouq.entities.enums.TipoPais;
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

    @Column(name = "NOMBRE", nullable = false, unique = true)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPOPAIS", nullable = false)
    private TipoPais tipoPais;

    public Pais(Long id, String nombre, TipoPais tipoPais) {
        this.id = id;
        this.nombre = nombre;
        this.tipoPais = tipoPais;
    }

    public Pais() {
    }
}
