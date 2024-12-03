package org.models;

public class DetalleFactura {
    String id;
    int cantidad;
    Double precioUnitario;
    Double descuento;
    Double precioTotal;
    String idFactura;
    String idTicket;

    public DetalleFactura(String id, int cantidad, Double precioUnitario, Double descuento, Double precioTotal, String idFactura, String idTicket) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
        this.precioTotal = precioTotal;
        this.idFactura = idFactura;
        this.idTicket = idTicket;
    }
}
