package com.autociclo;

import java.sql.Connection;

import com.autociclo.database.ConexionBD;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Yalil Musa Talhaoui
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primeraEscena) throws Exception {
        

        //Cargar pantalla principal
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Principal.fxml"));

        Scene scene = new Scene(root);
        primeraEscena.setScene(scene);
        primeraEscena.setTitle("AutoCiclo - Gestión de Desguace");
        primeraEscena.show();

    }
}
