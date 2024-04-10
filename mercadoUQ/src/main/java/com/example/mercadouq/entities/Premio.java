package com.example.mercadouq.entities;

import com.example.mercadouq.entities.enums.Categoria;
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

    @Column(name = "NOMBRECLIENTE")
    private String nombreCliente;

    @Column(name = "PRODUCTODEPREMIO", nullable = false)
    private String premio;

    public Premio(Factura factura, String premio) {
        this.factura = factura;
        this.premio = premio;
        this.nombreCliente = factura.getCliente().getNombre() + factura.getCliente().getApellido();
    }

    public Premio() {

    }
}
