package org.models;

public class Asiento {
    String id;
    String codigo;
    String fila;
    String columna;
    Double precio;
    Double descuento;
    String tipoAsiento;
    String estadoAsiento;
    String idLugar;

    public Asiento(String id, String codigo, String fila, String columna, Double precio, Double descuento, String tipoAsiento, String estadoAsiento, String idLugar) {
        this.id = id;
        this.codigo = codigo;
        this.fila = fila;
        this.columna = columna;
        this.precio = precio;
        this.descuento = descuento;
        this.tipoAsiento = tipoAsiento;
        this.estadoAsiento = estadoAsiento;
        this.idLugar = idLugar;
    }
}
