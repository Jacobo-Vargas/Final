package com.example.mercadouq.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "PREMIADOS")
public class Premio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPREMIO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FACTURA")
    private Factura factura;
}
