package com.autociclo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controlador para la ventana "Acerca de AutoCiclo"
 * Muestra información sobre la aplicación, funcionalidades, atajos de teclado y ayuda
 *
 * @author Yalil Musa Talhaoui
 * @version 1.0
 */
public class AcercaDeController {

    @FXML
    private Label lblVersion;

    /**
     * Inicializa el controlador
     * Se ejecuta automáticamente después de cargar el FXML
     */
    @FXML
    private void initialize() {
        // Configurar versión dinámica si es necesario
        lblVersion.setText("Versión 1.0");
    }

    /**
     * Cierra la ventana "Acerca de"
     * Asociado al botón Cerrar
     */
    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) lblVersion.getScene().getWindow();
        stage.close();
    }
}
