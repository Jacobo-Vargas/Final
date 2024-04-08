package com.example.mercadouq.entities;

import com.example.mercadouq.entities.enums.Genero;
import com.example.mercadouq.entities.enums.TipoPais;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "CLIENTES")
public class Cliente {

    @Id
    @Column(name = "IDCLIENTE")
    private Long cedula;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "APELLIDO", nullable = false)
    private String apellido;

    @Column(name = "DIRECCION", nullable = false)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "IDPAIS", nullable = false)
    private Pais pais;

    @Column(name = "TELEFONO", nullable = false)
    private Long telefono;

    @Column(name = "EDAD", nullable = false)
    private int edad;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENERO", nullable = false)
    private Genero genero;

    @Column(name = "PRIORIDADENVIO", nullable = false)
    private int prioridadEnvio;


    public Cliente() {

    }

    public Cliente(Long cedula, String nombre, String apellido, String direccion, Pais pais, Long telefono, int edad, Genero genero) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.pais = pais;
        this.telefono = telefono;
        this.edad = edad;
        this.genero = genero;
    }

    private int priorityAgeGender() {

        if (this.edad >= 60) {
            return 1 + priorityCountry();
        } else if (this.genero.equals(Genero.FEMALE)) {
            return 2 + priorityCountry();
        } else {
            return 3 + priorityCountry();
        }
    }

    private int priorityCountry() {
        if (this.pais.getTipoPais().equals(TipoPais.CONFLICTO)) {
            return 1;
        } else if (this.pais.getTipoPais().equals(TipoPais.CALAMIDAD)) {
            return 2;
        } else {
            return 3;
        }
    }

    @PrePersist
    public void beforePersist() {
        this.prioridadEnvio = priorityAgeGender();
    }
}
