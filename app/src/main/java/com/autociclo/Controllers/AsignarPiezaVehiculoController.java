package com.autociclo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AsignarPiezaVehiculoController implements Initializable {

    @FXML private TextField txtVehiculoSeleccionado;
    @FXML private TextField txtCantidad;
    @FXML private RadioButton rbNuevo;
    @FXML private RadioButton rbUsado;
    @FXML private RadioButton rbReparado;
    @FXML private ToggleGroup grupoContenedor;
    @FXML private TextField txtPieza;
    @FXML private TextField txtPrecioMecanico;
    @FXML private DatePicker dpFechaAsignacion;
    @FXML private Button btnCalendario;
    @FXML private TextArea txtNotas;
    @FXML private Button btnCancelar;
    @FXML private Button btnAsignar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización del controlador
    }

    @FXML
    private void handleAsignar() {
        // Lógica para asignar la pieza al vehículo
    }

    @FXML
    private void handleCancelar() {
        // Lógica para cancelar
    }

    @FXML
    private void handleCalendario() {
        // Lógica para abrir el calendario
    }
}
