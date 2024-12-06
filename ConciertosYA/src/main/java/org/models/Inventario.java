package org.models;

public class Inventario {
    String id;
    int cantidadDisponible;
    int cantidadVendido;
    int cantidadReservado;
    String idEvento;
    String idLugar;

    public Inventario(String id, int cantidadDisponible, int cantidadVendido, int cantidadReservado, String idEvento, String idLugar) {
        this.id = id;
        this.cantidadDisponible = cantidadDisponible;
        this.cantidadVendido = cantidadVendido;
        this.cantidadReservado = cantidadReservado;
        this.idEvento = idEvento;
        this.idLugar = idLugar;
    }
}
