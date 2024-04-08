package com.example.mercadouq.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "FACTURAS")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDFACTURA")
    private Long id;

    @Column(name = "FECHA", nullable = false)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "IDCLIENTE", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<DetalleFactura> detalles;

    public Factura(Long id, Date fecha, Cliente cliente, List<DetalleFactura> detalles) {
        this.id = id;
        this.fecha = fecha;
        this.cliente = cliente;
        this.detalles = detalles;
    }

    public Factura() {

    }
}
