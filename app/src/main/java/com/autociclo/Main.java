package com.autociclo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
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
        javafx.scene.image.Image appIcon = new javafx.scene.image.Image(
                getClass().getResourceAsStream("/imagenes/logo_autociclo.png"));
        primeraEscena.getIcons().add(appIcon);

        // Cargar AMBAS pantallas desde el inicio
        FXMLLoader loaderCarga = new FXMLLoader(getClass().getResource("/fxml/PantallaDeCarga.fxml"));
        Parent pantallaCarga = loaderCarga.load();

        FXMLLoader listadoLoader = new FXMLLoader(getClass().getResource("/fxml/ListadosController.fxml"));
        Parent listado = listadoLoader.load();
        listado.setOpacity(0.0); // Inicialmente invisible

        // Usar StackPane para superponer las dos pantallas
        javafx.scene.layout.StackPane contenedor = new javafx.scene.layout.StackPane();
        contenedor.getChildren().addAll(listado, pantallaCarga);

        Scene escena = new Scene(contenedor, 1150, 750);
        primeraEscena.setScene(escena);
        primeraEscena.setTitle("AutoCiclo - Gestión de Desguace");

        // Hacer la ventana redimensionable y establecer tamaños mínimos
        primeraEscena.setResizable(true);
        primeraEscena.setMinWidth(900);
        primeraEscena.setMinHeight(600);

        primeraEscena.show();
        primeraEscena.centerOnScreen();

        // Configurar confirmación al cerrar la ventana
        primeraEscena.setOnCloseRequest(event -> {
            event.consume(); // Prevenir el cierre automático

            // Crear alerta de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Salir de AutoCiclo");
            alert.setHeaderText("¿Está seguro que desea salir?");
            alert.setContentText("Se cerrará la aplicación AutoCiclo - Gestión de Desguace");

            // Aplicar estilos personalizados
            alert.getDialogPane().getStylesheets().add(
                    getClass().getResource("/css/styles.css").toExternalForm());
            alert.getDialogPane().getStyleClass().add("glass-pane");

            // Mostrar y esperar respuesta
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Usuario confirmó, cerrar la aplicación
                    primeraEscena.close();
                    System.exit(0);
                }
                // Si cancela, no hacer nada (la ventana permanece abierta)
            });
        });

        // Simular tiempo de carga (2.5 segundos) con el indicador girando
        // Usamos PauseTransition en lugar de animar una propiedad de progreso
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(Duration.seconds(2.5));
        pause.setOnFinished(event -> {
            // Transición: Fade out de pantalla de carga y fade in de listado al mismo
            // tiempo
            javafx.animation.FadeTransition fadeOut = new javafx.animation.FadeTransition(Duration.millis(500),
                    pantallaCarga);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            javafx.animation.FadeTransition fadeIn = new javafx.animation.FadeTransition(Duration.millis(500),
                    listado);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            // Ejecutar ambas transiciones al mismo tiempo
            fadeOut.play();
            fadeIn.play();

            // Cuando terminen las transiciones, remover la pantalla de carga del contenedor
            fadeOut.setOnFinished(e -> {
                contenedor.getChildren().remove(pantallaCarga);
                primeraEscena.centerOnScreen();
            });
        });

        // Iniciar la espera
        pause.play();
    }
}
