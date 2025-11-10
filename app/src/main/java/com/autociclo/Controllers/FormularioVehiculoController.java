package com.autociclo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class FormularioVehiculoController implements Initializable {

    @FXML private TextField txtMarca;
    @FXML private TextField txtMatricula;
    @FXML private TextField txtColor;
    @FXML private TextField txtEstado;
    @FXML private TextField txtUbicacionKm;
    @FXML private TextField txtModelo;
    @FXML private TextField txtAnio;
    @FXML private TextField txtKilometraje;
    @FXML private TextField txtPrecioCompra;
    @FXML private TextArea txtObservaciones;
    @FXML private Button btnCancelar;
    @FXML private Button btnGuardar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
