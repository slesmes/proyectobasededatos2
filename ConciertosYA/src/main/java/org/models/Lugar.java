package org.models;

public class Lugar {
    String nombre;
    String direccion;
    int capacidad;
    String ciudad;
    String imagen;

    public Lugar(String nombre, String direccion, int capacidad, String ciudad, String imagen) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidad = capacidad;
        this.ciudad = ciudad;
        this.imagen = imagen;
    }
}
