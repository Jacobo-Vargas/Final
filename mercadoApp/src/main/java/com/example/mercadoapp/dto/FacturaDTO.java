package com.example.mercadoapp.dto;

import java.util.Date;

public class FacturaDTO {
    private Long id;
    private Date fecha;
    private ClienteDTO cliente;

    private double total;

    public FacturaDTO(Long id, Date fecha, ClienteDTO cliente, double total) {
        this.id = id;
        this.fecha = fecha;
        this.cliente = cliente;
        this.total = total;
    }

    // Getters y setters

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

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
