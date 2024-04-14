package com.example.mercadouq.entities;

import com.example.mercadouq.entities.enums.Estado;
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

    @OneToOne
    @JoinColumn(name = "OBSEQUIO", nullable = false)
    private Obsequio obsequio;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false)
    private Estado estado;

    @Column(name = "NUMEROAVION")
    private int numeroAvion;

    public Premio(Factura factura, Obsequio obsequio) {
        this.factura = factura;
        this.obsequio = obsequio;
        this.nombreCliente = factura.getCliente().getNombre() + factura.getCliente().getApellido();
    }

    public Premio() {
    }
}
