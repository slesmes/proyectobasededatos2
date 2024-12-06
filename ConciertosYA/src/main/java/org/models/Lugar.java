package org.models;

import org.connection.ConexionPostgres;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Lugar {

    // Método para crear un lugar
    public void crearLugar(String id, String nombre, String direccion, int capacidad, String ciudad, String imagen) {
        String sql = "call proyecto.crear_lugar(?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionPostgres.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, direccion);
            stmt.setInt(4, capacidad);
            stmt.setString(5, ciudad);
            stmt.setString(6, imagen);

            stmt.execute();
            System.out.println("Lugar creado con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al crear lugar: " + e.getMessage());
        }
    }

    // Método para modificar un lugar
    public void modificarLugar(String id, String nombre, String direccion, int capacidad, String ciudad, String imagen) {
        String sql = "call proyecto.modificar_lugar(?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionPostgres.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, direccion);
            stmt.setInt(4, capacidad);
            stmt.setString(5, ciudad);
            stmt.setString(6, imagen);

            stmt.execute();
            System.out.println("Lugar modificado con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al modificar lugar: " + e.getMessage());
        }
    }

    // Método para eliminar un lugar
    public void eliminarLugar(String id) {
        String sql = "call proyecto.eliminar_lugar(?)";
        try (Connection conn = ConexionPostgres.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);
            stmt.execute();
            System.out.println("Lugar eliminado con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar lugar: " + e.getMessage());
        }
    }
}
