package org.models;

import org.connection.ConexionPostgres;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ticket {

    public void consultarTicket(String ticketId) {
        String sql = "SELECT * FROM proyecto.consultar_tickets(?)";
        try (Connection conn = ConexionPostgres.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ticketId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Ticket ID: " + rs.getString("id"));
                System.out.println("Fecha de Compra: " + rs.getDate("fecha_compra"));
                System.out.println("Descuento: " + rs.getBigDecimal("descuento"));
                System.out.println("Precio: " + rs.getBigDecimal("precio"));
                System.out.println("Precio con Descuento: " + rs.getBigDecimal("precio_descuento"));
                System.out.println("Asiento ID: " + rs.getString("asiento_id"));
                System.out.println("Tipo de Asiento: " + rs.getString("asiento_tipo"));
                System.out.println("Cliente ID: " + rs.getString("cliente_id"));
                System.out.println("Cliente Nombre: " + rs.getString("cliente_nombre"));
                System.out.println("Evento ID: " + rs.getString("evento_id"));
                System.out.println("Evento Nombre: " + rs.getString("evento_nombre"));
            } else {
                System.out.println("No se encontró un ticket con el ID: " + ticketId);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar el ticket: " + e.getMessage());
        }
    }

}
