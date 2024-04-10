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

    @Column(name = "TOTALFACTURA", updatable = true, nullable = false)
    private double total;



    public Factura(Long id, Date fecha, Cliente cliente) {
        this.id = id;
        this.fecha = fecha;
        this.cliente = cliente;
    }


    public double calcularTotalFactura(List<DetalleFactura> detalles) {
        if(detalles != null){
            return detalles.stream().mapToDouble(DetalleFactura::getTotal).sum();
        } else {
            return 0;
        }
    }

    public Factura() {

    }
}
