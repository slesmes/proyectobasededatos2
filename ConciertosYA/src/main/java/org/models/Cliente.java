package org.models;

public class Cliente {
    String id;
    String nombre;
    String email;
    String telefono;
    String direccion;

    public Cliente(String id, String nombre, String email, String telefono, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
    }
}
