package com.example.mercadoapp.dto;

public class ObsequioDTO {
    private Long id;
    private String nombre;
    private Categoria categoria;
    private Integer prioridad;

    public ObsequioDTO(Long id, String nombre, Categoria categoria, Integer prioridad) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.prioridad = prioridad;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }


}
