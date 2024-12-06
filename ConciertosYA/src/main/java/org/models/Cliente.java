package org.models;

import org.connection.ConexionPostgres;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.SQLWarning;

public class Cliente {

    // Método para crear un cliente
    public void crearCliente(String id, String nombre, String email, String telefono, String direccion) {
        String sql = "call proyecto.crear_cliente(?, ?, ?, ?, ?)";
        try (Connection conn = ConexionPostgres.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, email);
            stmt.setString(4, telefono);
            stmt.setString(5, direccion);

            stmt.execute();
            System.out.println("Cliente creado con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al crear cliente: " + e.getMessage());
        }
    }

    // Método para leer un cliente
    public void leerCliente(String id) {
    String sql = "call proyecto.leer_cliente(?)";
    try (Connection conn = ConexionPostgres.getConnection();
         CallableStatement stmt = conn.prepareCall(sql)) {

        stmt.setString(1, id);
        stmt.execute();

        // Capturando las advertencias del procedimiento (RAISE NOTICE)
        SQLWarning warning = stmt.getWarnings();
        if (warning != null) {
            // Aquí puedes imprimir los mensajes de RAISE NOTICE
            System.out.println("Aviso: " + warning.getMessage());
        } else {
            System.out.println("No se encontró un cliente con el ID " + id);
        }

    } catch (SQLException e) {
        System.err.println("Error al leer cliente: " + e.getMessage());
    }
}


    // Método para eliminar un cliente
    public void eliminarCliente(String id) {
        String sql = "call proyecto.eliminar_cliente(?)";
        try (Connection conn = ConexionPostgres.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);
            stmt.execute();
            System.out.println("Cliente eliminado con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
        }
    }
    
    public void modificarCliente(String id, String nombre, String email, String telefono, String direccion) {
    String sql = "CALL proyecto.modificar_cliente(?, ?, ?, ?, ?)";
    
    try (Connection conn = ConexionPostgres.getConnection();
         CallableStatement stmt = conn.prepareCall(sql)) {

        // Establecer los parámetros del procedimiento
        stmt.setString(1, id);
        stmt.setString(2, nombre);
        stmt.setString(3, email);
        stmt.setString(4, telefono);
        stmt.setString(5, direccion);

        // Ejecutar el procedimiento
        stmt.execute();
        System.out.println("Cliente modificado con éxito.");

        // Capturar las advertencias del procedimiento (RAISE NOTICE)
        SQLWarning warning = stmt.getWarnings();
        if (warning != null) {
            System.out.println("Aviso: " + warning.getMessage());
        }

    } catch (SQLException e) {
        System.err.println("Error al modificar cliente: " + e.getMessage());
    }
}

}
