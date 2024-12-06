package org.models;

import java.sql.Date;

public class Factura {
    String id;
    Date fechaEmision;
    Double total;
    String idMetodoPago;
    String idCliente;

    public Factura(String id, Date fechaEmision, Double total, String idMetodoPago, String idCliente) {
        this.id = id;
        this.fechaEmision = fechaEmision;
        this.total = total;
        this.idMetodoPago = idMetodoPago;
        this.idCliente = idCliente;
    }
}
