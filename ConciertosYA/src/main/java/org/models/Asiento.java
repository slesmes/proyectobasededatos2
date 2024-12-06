package org.models;

import org.connection.ConexionPostgres;

import java.sql.*;

public class Asiento {

    // Método para crear un asiento
    public void crearAsiento(String id, String codigo, String fila, String columna, double precio, double descuento, String tipo, String estado, String idLugar) {
        String sql = "call proyecto.crear_asiento(?, ?, ?, ?, CAST(? AS NUMERIC), CAST(? AS NUMERIC), ?, ?, ?)";
        try (Connection conn = ConexionPostgres.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, codigo);
            stmt.setString(3, fila);
            stmt.setString(4, columna);
            stmt.setDouble(5, precio);
            stmt.setDouble(6, descuento);
            stmt.setString(7, tipo);
            stmt.setString(8, estado);
            stmt.setString(9, idLugar);

            stmt.execute();
            System.out.println("Asiento creado con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al crear asiento: " + e.getMessage());
        }
    }


    // Método para modificar un asiento
    public void modificarAsiento(String id, String codigo, String fila, String columna, double precio, double descuento, String tipo, String estado, String idLugar) {
        String sql = "call proyecto.modificar_asiento(?, ?, ?, ?, CAST(? AS NUMERIC), CAST(? AS NUMERIC), ?, ?, ?)";
        try (Connection conn = ConexionPostgres.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);
            stmt.setString(2, codigo);
            stmt.setString(3, fila);
            stmt.setString(4, columna);
            stmt.setDouble(5, precio);  // Pasar como double
            stmt.setDouble(6, descuento);  // Pasar como double
            stmt.setString(7, tipo);
            stmt.setString(8, estado);
            stmt.setString(9, idLugar);

            stmt.execute();
            System.out.println("Asiento modificado con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al modificar asiento: " + e.getMessage());
        }
    }


    // Método para eliminar un asiento
    public void eliminarAsiento(String id) {
        String sql = "call proyecto.eliminar_asiento(?)";
        try (Connection conn = ConexionPostgres.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);
            stmt.execute();
            System.out.println("Asiento eliminado con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al eliminar asiento: " + e.getMessage());
        }
    }

    public void leerAsientosDisponiblesPorEvento(String eventoId) {
        String sql = "SELECT * FROM proyecto.obtener_asientos_disponibles_por_evento(?)";
        try (Connection conn = ConexionPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, eventoId);


            try (ResultSet rs = stmt.executeQuery()) {
                System.out.println("Asientos disponibles para el evento ID: " + eventoId);
                System.out.println("----------------------------------------------------");

                boolean hayResultados = false;

                while (rs.next()) {
                    hayResultados = true;
                    System.out.println("Asiento ID: " + rs.getString("id"));
                    System.out.println("Código: " + rs.getString("codigo"));
                    System.out.println("Fila: " + rs.getString("fila"));
                    System.out.println("Columna: " + rs.getString("columna"));
                    System.out.println("Precio: " + rs.getBigDecimal("precio"));
                    System.out.println("Descuento: " + rs.getBigDecimal("descuento"));
                    System.out.println("Tipo: " + rs.getString("tipo"));
                    System.out.println("Estado: " + rs.getString("estado"));
                    System.out.println("----------------------------------------------------");
                }

                if (!hayResultados) {
                    System.out.println("No se encontraron asientos disponibles para el evento con ID: " + eventoId);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener los asientos disponibles: " + e.getMessage());
        }
    }

}
