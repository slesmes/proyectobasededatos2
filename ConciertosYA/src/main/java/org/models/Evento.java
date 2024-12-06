package org.models;

import org.connection.ConexionPostgres;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Evento {

    // Método para crear un evento
    public void crearEvento(String id, String nombre, String fecha, String hora, String descripcion,
                            String generoMusical, String estado, String cartel, String lugarId) {
        String sql = "call proyecto.crear_evento(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionPostgres.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.setDate(3, java.sql.Date.valueOf(fecha)); // Formato YYYY-MM-DD
            stmt.setTime(4, java.sql.Time.valueOf(hora));  // Formato HH:mm:ss
            stmt.setString(5, descripcion);
            stmt.setString(6, generoMusical);
            stmt.setString(7, estado);
            stmt.setString(8, cartel);
            stmt.setString(9, lugarId);

            stmt.execute();
            System.out.println("Evento creado con éxito.");
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error al crear evento: " + e.getMessage());
        }
    }

    // Método para modificar un evento
    public void modificarEvento(String id, String nombre, String fecha, String hora, String descripcion,
                                 String generoMusical, String estado, String cartel, String lugarId) {
        String sql = "call proyecto.modificar_evento(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionPostgres.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.setDate(3, java.sql.Date.valueOf(fecha));
            stmt.setTime(4, java.sql.Time.valueOf(hora));
            stmt.setString(5, descripcion);
            stmt.setString(6, generoMusical);
            stmt.setString(7, estado);
            stmt.setString(8, cartel);
            stmt.setString(9, lugarId);

            stmt.execute();
            System.out.println("Evento modificado con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al modificar evento: " + e.getMessage());
        }
    }

    // Método para eliminar un evento
    public void eliminarEvento(String id) {
        String sql = "call proyecto.eliminar_evento(?)";
        try (Connection conn = ConexionPostgres.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);
            stmt.execute();
            System.out.println("Evento eliminado con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar evento: " + e.getMessage());
        }
    }
}

