package com.autociclo;

import com.autociclo.controllers.PantallaDeCargaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PantallaDeCarga.fxml"));
        Parent pantallaCarga = loader.load();
        PantallaDeCargaController controller = loader.getController();

        Scene sceneCarga = new Scene(pantallaCarga);
        primeraEscena.setScene(sceneCarga);
        primeraEscena.setTitle("AutoCiclo - Gestión de Desguace");
        primeraEscena.show();

        // Animar la barra de progreso de 0 a 100% en 2 segundos
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(20), event -> {
            double currentProgress = controller.getProgressBar().getProgress();
            if (currentProgress < 1.0) {
                controller.getProgressBar().setProgress(currentProgress + 0.01);
            }
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(100); // 100 ciclos * 0.01 = 1.0 (100%)
        timeline.setOnFinished(event -> {
            // Cuando la animación termine, cargar la siguiente pantalla
            try {
                Parent listado = FXMLLoader.load(getClass().getResource("/fxml/ListadosController.fxml"));
                Scene sceneListado = new Scene(listado);
                primeraEscena.setScene(sceneListado);
                primeraEscena.centerOnScreen();
            } catch (Exception e) {
                System.err.println("Error al cargar ListadosController: " + e.getMessage());
                e.printStackTrace();
            }
        });

        // Iniciar la animación
        timeline.play();
    }
}
