package org.models;

import org.connection.ConexionPostgres;

import java.sql.*;

public class Evento {

    // Método para crear un evento
    public void crearEvento(String nombre, String fecha, String hora, String descripcion,
            String generoMusical, String estado, String cartel, String lugarId) {
        String sql = "call proyecto.crear_evento(?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionPostgres.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, nombre);
            stmt.setDate(2, java.sql.Date.valueOf(fecha)); // Formato YYYY-MM-DD
            stmt.setTime(3, java.sql.Time.valueOf(hora));  // Formato HH:mm:ss
            stmt.setString(4, descripcion);
            stmt.setString(5, generoMusical);
            stmt.setString(6, estado);
            stmt.setString(7, cartel);
            stmt.setString(8, lugarId);

            stmt.execute();
            SQLWarning warning = stmt.getWarnings();
            if (warning != null) {
                System.out.println("Aviso: " + warning.getMessage());
            } else {
                System.out.println("Evento creado con éxito.");
            }
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error al crear evento: " + e.getMessage());
        }
    }

    // Método para modificar un evento
    public void modificarEvento(String id, String nombre, String fecha, String hora, String descripcion,
            String generoMusical, String estado, String cartel, String lugarId) {
        String sql = "call proyecto.modificar_evento(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionPostgres.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

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
            SQLWarning warning = stmt.getWarnings();
            if (warning != null) {
                System.out.println("Aviso: " + warning.getMessage());
            } else {
                System.out.println("Evento modificado con éxito.");
            }

        } catch (SQLException e) {
            System.err.println("Error al modificar evento: " + e.getMessage());
        }
    }

    // Método para eliminar un evento
    public void eliminarEvento(String id) {
        String sql = "call proyecto.eliminar_evento(?)";
        try (Connection conn = ConexionPostgres.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, id);
            stmt.execute();
            SQLWarning warning = stmt.getWarnings();
            if (warning != null) {
                System.out.println("Aviso: " + warning.getMessage());
            } else {
                System.out.println("Evento eliminado con éxito.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar evento: " + e.getMessage());
        }
    }

    // Método para añadir artista a evento
    public void agregarArtistaEvento(String eventoId, String artistaId) {
        String sql = "call proyecto.crear_evento_detallado(?, ?)";
        try (Connection conn = ConexionPostgres.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, eventoId);
            stmt.setString(2, artistaId);
            stmt.execute();
            SQLWarning warning = stmt.getWarnings();
            if (warning != null) {
                System.out.println("Aviso: " + warning.getMessage());
            } else {
                System.out.println("Artista agregado al evento con éxito.");
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar artista al evento: " + e.getMessage());
        }
    }


    public void buscarEventosPorFecha(Date fecha) {
        String sql = "SELECT * FROM proyecto.buscar_eventos_por_fecha(?)";
        try (Connection conn = ConexionPostgres.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, fecha);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Evento ID: " + rs.getString("id"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Fecha: " + rs.getDate("fecha"));
                System.out.println("Hora: " + rs.getTime("hora"));
                System.out.println("Descripción: " + rs.getString("descripcion"));
                System.out.println("Género: " + rs.getString("genero_musical"));
                System.out.println("Estado: " + rs.getString("estado"));
                System.out.println("Cartel: " + rs.getString("cartel"));
                System.out.println("Lugar: " + rs.getString("lugar_nombre"));

                System.out.println("Precio VIP: " + rs.getBigDecimal("precio_vip"));
                System.out.println("Precio Palco: " + rs.getBigDecimal("precio_palco"));
                System.out.println("Precio General: " + rs.getBigDecimal("precio_general"));

                System.out.println("--------------------------------------------------------");
            }
            SQLWarning warning = stmt.getWarnings();
            if (warning != null) {
                System.out.println("Aviso: " + warning.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar eventos: " + e.getMessage());
        }
    }

    public void buscarEventosPorLugar(String lugarId) {
        String sql = "SELECT * FROM proyecto.buscar_eventos_por_lugar(?)";
        try (Connection conn = ConexionPostgres.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lugarId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Evento ID: " + rs.getString("id"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Fecha: " + rs.getDate("fecha"));
                System.out.println("Hora: " + rs.getTime("hora"));
                System.out.println("Descripción: " + rs.getString("descripcion"));
                System.out.println("Género Musical: " + rs.getString("genero_musical"));
                System.out.println("Estado: " + rs.getString("estado"));
                System.out.println("Cartel: " + rs.getString("cartel"));
                System.out.println("Lugar: " + rs.getString("lugar_nombre"));
                System.out.println("Precio VIP: " + rs.getBigDecimal("precio_vip"));
                System.out.println("Precio Palco: " + rs.getBigDecimal("precio_palco"));
                System.out.println("Precio General: " + rs.getBigDecimal("precio_general"));
                System.out.println("----------------------------------------------------");
            }
            SQLWarning warning = stmt.getWarnings();
            if (warning != null) {
                System.out.println("Aviso: " + warning.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar eventos por lugar: " + e.getMessage());
        }
    }

    public void buscarEventosPorArtista(String artistaId) {
        String sql = "SELECT * FROM proyecto.buscar_eventos_por_artista(?)";
        try (Connection conn = ConexionPostgres.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, artistaId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Evento ID: " + rs.getString("id"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Fecha: " + rs.getDate("fecha"));
                System.out.println("Hora: " + rs.getTime("hora"));
                System.out.println("Descripción: " + rs.getString("descripcion"));
                System.out.println("Género Musical: " + rs.getString("genero_musical"));
                System.out.println("Estado: " + rs.getString("estado"));
                System.out.println("Cartel: " + rs.getString("cartel"));
                System.out.println("Lugar: " + rs.getString("lugar_nombre"));
                System.out.println("Precio VIP: " + rs.getBigDecimal("precio_vip"));
                System.out.println("Precio Palco: " + rs.getBigDecimal("precio_palco"));
                System.out.println("Precio General: " + rs.getBigDecimal("precio_general"));
                System.out.println("----------------------------------------------------");
            }

            SQLWarning warning = stmt.getWarnings();
            if (warning != null) {
                System.out.println("Aviso: " + warning.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar eventos por artista: " + e.getMessage());
        }
    }

    public void leerEventosProgramados() {
        String sql = "SELECT * FROM proyecto.leer_evento()";
        try (Connection conn = ConexionPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("Eventos programados:");
            System.out.println("----------------------------------------------------");

            while (rs.next()) {
                System.out.println("Evento ID: " + rs.getString("id"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Género Musical: " + rs.getString("genero_musical"));
                System.out.println("----------------------------------------------------");
            }

        } catch (SQLException e) {
            System.err.println("Error al leer eventos programados: " + e.getMessage());
        }
    }


}
