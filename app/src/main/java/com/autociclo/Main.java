package com.autociclo;

import java.sql.Connection;

import com.autociclo.database.ConexionBD;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Yalil Musa Talhaoui
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primeraEscena) throws Exception {
        // PRUEBA DE CONEXIÓN - BORRAR DESPUÉS
        System.out.println("Probando conexion a la base de datos...");
        try (Connection conn = ConexionBD.getConexion()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexion exitosa!");
            }
        } catch (Exception e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        // FIN DE LA PRUEBA

        //puede lanzar excep'
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ejemplo_holamundo.fxml"));

        Scene scene = new Scene(root);
        primeraEscena.setScene(scene);
        primeraEscena.setTitle("Primer Ejemplo JavaFX - Hola Mundo");
        primeraEscena.show();

    }
}
