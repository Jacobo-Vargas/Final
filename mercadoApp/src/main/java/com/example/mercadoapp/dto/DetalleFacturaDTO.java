package com.example.mercadoapp.dto;

public class DetalleFacturaDTO {
    private Long id;
    private Long idFactura;
    private ProductoDTO producto;
    private double valorUnitario;
    private int cantidad;
    private double total;

    public DetalleFacturaDTO(Long id, Long idFactura, ProductoDTO producto, int cantidad) {
        this.id = id;
        this.idFactura = idFactura;
        this.producto = producto;
        this.valorUnitario = producto.getPrecio();
        this.cantidad = cantidad;
        this.total = this.cantidad * this.valorUnitario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
