package com.autociclo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class FormularioPiezaController implements Initializable {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtCategoria;
    @FXML private TextField txtStockDisponible;
    @FXML private TextField txtUbicacionAlmacen;
    @FXML private TextField txtMaterialesCompatibles;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecioCompra;
    @FXML private TextField txtStockMinimo;
    @FXML private TextArea txtDescripcion;
    @FXML private Button btnCancelar;
    @FXML private Button btnGuardar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
