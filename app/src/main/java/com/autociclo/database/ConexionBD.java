package com.autociclo.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    // Bloque estático para inicializar las propiedades una sola vez
    static {
        Properties props = new Properties();
        try (InputStream input = ConexionBD.class.getClassLoader()
                .getResourceAsStream("database.properties")) {

            if (input == null) {
                throw new RuntimeException("No se encontró database.properties");
            }

            props.load(input);

            String host = props.getProperty("db.host");
            String port = props.getProperty("db.port");
            String database = props.getProperty("db.name");

            URL = "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");

        } catch (Exception e) {
            throw new RuntimeException("Error al cargar configuración de BD", e);
        }
    }

    // Obtener conexión directamente
    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}