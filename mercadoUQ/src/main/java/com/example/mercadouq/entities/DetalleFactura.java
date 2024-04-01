package com.example.mercadouq.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "DETALLESDEFACTURA")
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDDETALLEFACTURA")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IDFACTURA", nullable = false)
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "IDPRODUCTO", nullable = false)
    private Producto producto;

}
