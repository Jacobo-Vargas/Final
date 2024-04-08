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

    @Column(name = "VALORUNITARIO", nullable = false)
    private double valorUnitario;

    @Column(name = "CANTIDAD", nullable = false)
    private int cantidad;

    @Column(name = "TOTAL", nullable = false)
    private double total;

    public DetalleFactura(Long id, Factura factura, Producto producto, double valorUnitario, int cantidad) {
        this.id = id;
        this.factura = factura;
        this.producto = producto;
        this.valorUnitario = valorUnitario;
        this.cantidad = cantidad;
    }

    public DetalleFactura() {

    }

    @PrePersist
    public void beforePersist() {
        this.total = this.cantidad * this.valorUnitario;
    }
}
