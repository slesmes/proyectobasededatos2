package org.models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import org.connection.ConexionPostgres;

public class Factura {

    public String crearFactura(String id) {
        String sql = "{ ? = call proyecto.crear_factura(?) }";
        String resultado = "";

        try (Connection conn = ConexionPostgres.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.registerOutParameter(1, java.sql.Types.VARCHAR); 

            stmt.setString(2, id); 

            stmt.execute();

            resultado = stmt.getString(1);  

            System.out.println("Factura creada con éxito. Resultado: " + resultado);

        } catch (SQLException e) {
            System.err.println("Error al crear la factura: " + e.getMessage());
        }
        return resultado;
    }
    
     public void crearTicketYDetalleFactura(String idFactura, String idAsiento, String idCliente, String idEvento) {
        String sql = "call proyecto.crear_ticket_y_detalle_factura(?, ?, ?, ?)";

        try (Connection conn = ConexionPostgres.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, idFactura);   
            stmt.setString(2, idAsiento);   
            stmt.setString(3, idCliente);   
            stmt.setString(4, idEvento);   

            stmt.execute();

            System.out.println("Ticket y detalle de factura creados con éxito.");

        } catch (SQLException e) {
            System.err.println("Error al crear el ticket y detalle de factura: " + e.getMessage());
        }
    }
     
     public void actualizarFactura(String idFactura) {
        String sql = "call proyecto.actualizar_factura(?)"; 

        try (Connection conn = ConexionPostgres.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

           
            stmt.setString(1, idFactura); 

            stmt.execute();

            System.out.println("Factura actualizada con éxito.");

        } catch (SQLException e) {
            System.err.println("Error al actualizar la factura: " + e.getMessage());
        }
    }
     
    public void actualizarMetodoPagoFactura(String idFactura, String metodoPago) {
        String sql = "call proyecto.actualizar_metodo_pago_factura(?, ?)"; 

        try (Connection conn = ConexionPostgres.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, idFactura);   
            stmt.setString(2, metodoPago);  

            stmt.execute();

            System.out.println("Método de pago de la factura actualizado con éxito.");

        } catch (SQLException e) {
            System.err.println("Error al actualizar el método de pago de la factura: " + e.getMessage());
        }
    }
    
    public void actualizarDetallesFactura(String idFactura) {
        String sql = "call proyecto.actualizar_detalles_factura(?)";  

        try (Connection conn = ConexionPostgres.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, idFactura);  

            stmt.execute();

            System.out.println("Detalles de la factura actualizados con éxito.");

        } catch (SQLException e) {
            System.err.println("Error al actualizar los detalles de la factura: " + e.getMessage());
        }
    }
}
