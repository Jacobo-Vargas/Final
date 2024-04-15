package com.example.mercadoapp.dto;

import java.util.Date;

public class FacturaDTO {
    private Long id;
    private Date fecha;
    private ClienteDTO cliente;

    public FacturaDTO(Long id, Date fecha, ClienteDTO cliente) {
        this.id = id;
        this.fecha = fecha;
        this.cliente = cliente;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
}
