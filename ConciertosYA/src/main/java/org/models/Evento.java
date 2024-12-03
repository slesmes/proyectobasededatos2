package org.models;

import java.sql.Date;

public class Evento {
    String id;
    String nombre;
    Date fecha;
    String hora;
    String descripcion;
    String generoMusical;
    String estadoEvento;
    String cartel;
    String idLugar;

    public Evento(String id, String nombre, Date fecha, String hora, String descripcion, String generoMusical, String estadoEvento, String cartel, String idLugar) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.generoMusical = generoMusical;
        this.estadoEvento = estadoEvento;
        this.cartel = cartel;
        this.idLugar = idLugar;
    }
}
