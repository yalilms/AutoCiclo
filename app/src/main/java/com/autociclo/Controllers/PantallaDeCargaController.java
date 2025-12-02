package com.autociclo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;

/**
 * Controlador para la pantalla de carga
 * @author Yalil Musa Talhaoui
 */
public class PantallaDeCargaController {

    @FXML
    private ProgressIndicator progressBar;

    /**
     * Obtiene el indicador de progreso
     * @return ProgressIndicator
     */
    public ProgressIndicator getProgressBar() {
        return progressBar;
    }
}
