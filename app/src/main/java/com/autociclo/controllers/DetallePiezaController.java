package com.autociclo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import com.autociclo.models.Pieza;

public class DetallePiezaController {

    @FXML private Label lblTitulo;
    @FXML private Label lblId;
    @FXML private Label lblCodigo;
    @FXML private Label lblNombre;
    @FXML private Label lblCategoria;
    @FXML private Label lblPrecio;
    @FXML private Label lblStockDisponible;
    @FXML private Label lblStockMinimo;
    @FXML private Label lblUbicacion;
    @FXML private Label lblCompatible;
    @FXML private TextArea txtDescripcion;
    @FXML private Button btnCerrar;

    /**
     * Establece los datos de la pieza a mostrar
     */
    public void setPieza(Pieza pieza) {
        if (pieza != null) {
            lblTitulo.setText("DETALLES DE LA PIEZA: " + pieza.getNombre());
            lblId.setText(String.valueOf(pieza.getIdPieza()));
            lblCodigo.setText(pieza.getCodigoPieza());
            lblNombre.setText(pieza.getNombre());
            lblCategoria.setText(pieza.getCategoria());
            lblPrecio.setText(String.format("%.2f €", pieza.getPrecioVenta()));
            lblStockDisponible.setText(pieza.getStockDisponible() + " unidades");
            lblStockMinimo.setText(pieza.getStockMinimo() + " unidades");
            lblUbicacion.setText(pieza.getUbicacionAlmacen());
            lblCompatible.setText(pieza.getCompatibleMarcas() != null && !pieza.getCompatibleMarcas().isEmpty()
                                 ? pieza.getCompatibleMarcas() : "No especificado");
            txtDescripcion.setText(pieza.getDescripcion() != null && !pieza.getDescripcion().isEmpty()
                                  ? pieza.getDescripcion() : "Sin descripción");
        }

        // Configurar botón cerrar
        btnCerrar.setOnAction(event -> cerrarVentana());
    }

    /**
     * Cierra la ventana de detalles
     */
    private void cerrarVentana() {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }
}
