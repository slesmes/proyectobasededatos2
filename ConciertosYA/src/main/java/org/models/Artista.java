package org.models;

import org.connection.ConexionPostgres;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;

public class Artista {

    // Método para crear un artista
    public void crearArtista(String id, String nombre, String generoMusical) {
        String sql = "call proyecto.crear_artista(?, ?, ?)";
        try (Connection conn = ConexionPostgres.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, generoMusical);
            stmt.execute();
            SQLWarning warning = stmt.getWarnings();
            if (warning != null) {
                System.out.println("Aviso: " + warning.getMessage());
            } else {
                System.out.println("Artista creado con éxito.");
            }
        } catch (SQLException e) {
            System.err.println("Error al crear artista: " + e.getMessage());
        }
    }

    public void leerArtista(String id) {
        String sql = "call proyecto.leer_artista(?)";  // Llamada al procedimiento
        try (Connection conn = ConexionPostgres.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);  // Establecemos el parámetro de entrada

            // Ejecutar el procedimiento
            stmt.execute();

            // Capturando las advertencias del procedimiento (RAISE NOTICE)
            SQLWarning warning = stmt.getWarnings();
            if (warning != null) {
                System.out.println("Aviso: " + warning.getMessage());
            } else {
                System.out.println("No se encontró un artista con el ID " + id);
            }

        } catch (SQLException e) {
            System.err.println("Error al leer artista: " + e.getMessage());
        }
    }


    // Método para modificar un artista
    public void modificarArtista(String id, String nombre, String generoMusical) {
        String sql = "call proyecto.modificar_artista(?, ?, ?)";
        try (Connection conn = ConexionPostgres.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, generoMusical);
            stmt.execute();
            SQLWarning warning = stmt.getWarnings();
            if (warning != null) {
                System.out.println("Aviso: " + warning.getMessage());
            } else {
                System.out.println("Artista modificado con éxito.");
            }

        } catch (SQLException e) {
            System.err.println("Error al modificar artista: " + e.getMessage());
        }
    }

    // Método para eliminar un artista
    public void eliminarArtista(String id) {
        String sql = "call proyecto.eliminar_artista(?)";
        try (Connection conn = ConexionPostgres.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);
            stmt.execute();
            SQLWarning warning = stmt.getWarnings();
            if (warning != null) {
                System.out.println("Aviso: " + warning.getMessage());
            } else {
                System.out.println("Artista eliminado con éxito.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar artista: " + e.getMessage());
        }
    }


}
