package com.example.mercadoapp.dto;

public class ClienteDTO {

    private Long cedula;
    private String nombre;
    private String apellido;
    private String direccion;
    private Long telefono;
    private int edad;
    private Genero genero;
    private  int prioridadEnvio;

    public ClienteDTO(Long cedula, String nombre, String apellido, String direccion, Long telefono, int edad, Genero genero, int prioridadEnvio) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.telefono = telefono;
        this.edad = edad;
        this.genero = genero;
        this.prioridadEnvio = prioridadEnvio;
    }

    public int getPrioridadEnvio() {
        return prioridadEnvio;
    }

    public void setPrioridadEnvio(int prioridadEnvio) {
        this.prioridadEnvio = prioridadEnvio;
    }

    public Long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
}
