package org.models;

import org.connection.ConexionPostgres;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Artista {

    // Método para crear un artista
    public void crearArtista(String id, String nombre, String generoMusical) {
        String sql = "call proyecto.crear_artista(?, ?, ?)";
        try (Connection conn = ConexionPostgres.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, generoMusical);
            stmt.execute();
            System.out.println("Artista creado con éxito.");
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error al crear artista: " + e.getMessage());
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
            System.out.println("Artista modificado con éxito.");
            stmt.close();
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
            System.out.println("Artista eliminado con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar artista: " + e.getMessage());
        }
    }

    

}
