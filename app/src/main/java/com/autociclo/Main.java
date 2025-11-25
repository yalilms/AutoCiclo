package com.autociclo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * @author Yalil Musa Talhaoui
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primeraEscena) throws Exception {
        // Establecer icono de la aplicación
        javafx.scene.image.Image icon = new javafx.scene.image.Image(
            getClass().getResourceAsStream("/fxml/Logo_autociclo.png")
        );
        primeraEscena.getIcons().add(icon);

        // Cargar pantalla de carga
        Parent pantallaCarga = FXMLLoader.load(getClass().getResource("/fxml/PantallaDeCarga.fxml"));

        Scene sceneCarga = new Scene(pantallaCarga);
        primeraEscena.setScene(sceneCarga);
        primeraEscena.setTitle("AutoCiclo - Gestión de Desguace");
        primeraEscena.show();

        // Crear una pausa de 2 segundos antes de cambiar a ListadosController
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> {
            try {
                // Cargar ListadosController después de 2 segundos
                Parent listado = FXMLLoader.load(getClass().getResource("/fxml/ListadosController.fxml"));
                Scene sceneListado = new Scene(listado);
                primeraEscena.setScene(sceneListado);
                primeraEscena.centerOnScreen();
            } catch (Exception e) {
                System.err.println("Error al cargar ListadosController: " + e.getMessage());
                e.printStackTrace();
            }
        });
        delay.play();
    }
}
