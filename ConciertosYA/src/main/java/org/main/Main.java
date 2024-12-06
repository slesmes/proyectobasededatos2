package org.main;

import org.connection.ConexionPostgres;
import org.models.Artista;
import org.models.Asiento;
import org.models.Cliente;
import org.models.Evento;
import org.models.Lugar;

public class Main {
    public static void main(String[] args) {
        Cliente cliente = new Cliente();

//        // Crear un cliente
//        cliente.crearCliente("500", "Juan Pérez", "juan@example.com", "123456789", "Calle Falsa 123");

//        // Leer un cliente
//        cliente.leerCliente("500");

        // Eliminar un cliente
        cliente.eliminarCliente("500");


        // Cerrar la conexión al final del programa
        ConexionPostgres.closeConnection();
    }
}
