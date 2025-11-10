package com.autociclo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class FormularioInventarioController implements Initializable {

    @FXML private TextField txtVehiculoSeleccionado;
    @FXML private TextField txtCantidad;
    @FXML private RadioButton rbNuevo;
    @FXML private RadioButton rbUsado;
    @FXML private RadioButton rbReparado;
    @FXML private ToggleGroup grupoContenedor;
    @FXML private TextField txtPieza;
    @FXML private TextField txtPrecioMecanico;
    @FXML private DatePicker dpFechaExtraccion;
    @FXML private Button btnCalendario;
    @FXML private TextArea txtNotas;
    @FXML private Button btnCancelar;
    @FXML private Button btnGuardar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
