package org.models;

import java.sql.Date;

public class DocumentoAuditoria {
    String id;
    Date fecha;
    String nombreCliente;
    String idCliente;
    String codigoAsiento;
    String tipoAsiento;
    Double precioDescuento;

    public DocumentoAuditoria(String id, Date fecha, String nombreCliente, String idCliente, String codigoAsiento, String tipoAsiento, Double precioDescuento) {
        this.id = id;
        this.fecha = fecha;
        this.nombreCliente = nombreCliente;
        this.idCliente = idCliente;
        this.codigoAsiento = codigoAsiento;
        this.tipoAsiento = tipoAsiento;
        this.precioDescuento = precioDescuento;
    }
}
