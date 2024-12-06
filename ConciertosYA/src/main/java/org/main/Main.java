package org.main;

import java.sql.Date;
import org.connection.ConexionPostgres;
import org.models.Artista;
import org.models.Asiento;
import org.models.Cliente;
import org.models.Evento;
import org.models.Lugar;

public class Main {
    public static void main(String[] args) {
        Evento evento = new Evento();
        
        evento.buscarEventosPorArtista("1");


        // Cerrar la conexión al final del programa
        ConexionPostgres.closeConnection();
    }
}
