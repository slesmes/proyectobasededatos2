package org.models;

import java.sql.Date;

public class Ticket {
    String id;
    Date fechaCompra;
    Double descuento;
    Double precioUnitario;
    Double precioDescuento;
    String idAsiento;
    String idCliente;
    String idEvento;

    public Ticket(String id, Date fechaCompra, Double descuento, Double precioUnitario, Double precioDescuento, String idAsiento, String idCliente, String idEvento) {
        this.id = id;
        this.fechaCompra = fechaCompra;
        this.descuento = descuento;
        this.precioUnitario = precioUnitario;
        this.precioDescuento = precioDescuento;
        this.idAsiento = idAsiento;
        this.idCliente = idCliente;
        this.idEvento = idEvento;
    }
}
