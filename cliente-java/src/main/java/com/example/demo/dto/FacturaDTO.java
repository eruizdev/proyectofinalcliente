package com.example.demo.dto;

import java.time.LocalDate;

public class FacturaDTO {
    private String idFactura;
    private String nombreCliente;
    private String nombreMascota;
    private String servicioRealizado;
    private LocalDate fecha;
    private double total;
    private String metodoPago;

    public FacturaDTO() {}

    public FacturaDTO(String idFactura, String nombreCliente, String nombreMascota, 
                      String servicioRealizado, LocalDate fecha, double total, String metodoPago) {
        this.idFactura = idFactura;
        this.nombreCliente = nombreCliente;
        this.nombreMascota = nombreMascota;
        this.servicioRealizado = servicioRealizado;
        this.fecha = fecha;
        this.total = total;
        this.metodoPago = metodoPago;
    }

    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getServicioRealizado() {
        return servicioRealizado;
    }

    public void setServicioRealizado(String servicioRealizado) {
        this.servicioRealizado = servicioRealizado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}
