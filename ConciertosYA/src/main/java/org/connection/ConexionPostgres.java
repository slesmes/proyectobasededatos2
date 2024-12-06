package org.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionPostgres {
    private static ConexionPostgres instance;
    private Connection connection;

    private ConexionPostgres() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres?currentSchema=proyecto",
                "postgres",
                "15172223"
            );
            connection.setAutoCommit(true);
            System.out.println("Conexión Postgres exitosa");
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Error al crear la conexión a la base de datos: " + ex.getMessage());
        }
    }

    public static ConexionPostgres getInstance() {
        if (instance == null || !isConnectionActive(instance.connection)) {
            instance = new ConexionPostgres();
        }
        return instance;
    }

    public static Connection getConnection() {
        return getInstance().connection;
    }

    public static void closeConnection() {
        if (instance != null && instance.connection != null) {
            try {
                instance.connection.close();
                System.out.println("Conexión cerrada exitosamente.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    private static boolean isConnectionActive(Connection connection) {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
