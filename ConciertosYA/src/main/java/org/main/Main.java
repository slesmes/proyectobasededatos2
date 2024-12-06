package org.main;

import org.connection.ConexionPostgres;
import org.models.Artista;
import org.models.Asiento;
import org.models.Evento;
import org.models.Lugar;

public class Main {
    public static void main(String[] args) {
         Asiento asiento = new Asiento();

        // Crear un asiento
        asiento.crearAsiento("5000", "5000", "A", "2", 50000, 0.1, "vip", "disponible", "9");

        // Modificar un asiento
        asiento.modificarAsiento("5000", "5000", "B", "2", 60000, 0.1, "palco", "vendido", "9");

//        // Eliminar un asiento
//        asiento.eliminarAsiento("5000");

        // Cerrar la conexión al final del programa
        ConexionPostgres.closeConnection();
    }
}
