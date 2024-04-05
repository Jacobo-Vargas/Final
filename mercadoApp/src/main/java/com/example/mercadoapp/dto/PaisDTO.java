package com.example.mercadoapp.dto;

public class PaisDTO {

    private  Long id;
    private  String nombre;
    private  TipoPais tipoPais;

    public PaisDTO(Long id, String nombre, TipoPais tipoPais) {
        this.id = id;
        this.nombre = nombre;
        this.tipoPais = tipoPais;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoPais getTipoPais() {
        return tipoPais;
    }

    public void setTipoPais(TipoPais tipoPais) {
        this.tipoPais = tipoPais;
    }
}
