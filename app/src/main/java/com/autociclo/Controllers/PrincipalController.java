package com.autociclo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    @FXML private Button btnVehiculos;
    @FXML private Button btnPiezas;
    @FXML private Button btnInventario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar eventos de los botones
        btnVehiculos.setOnAction(event -> abrirListado("vehiculos"));
        btnPiezas.setOnAction(event -> abrirListado("piezas"));
        btnInventario.setOnAction(event -> abrirListado("inventario"));
    }

    private void abrirListado(String tipo) {
        try {
            // Cargar la pantalla de listados
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ListadosController.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y mostrar la vista correspondiente
            ListadoMaestroController controller = loader.getController();

            // Mostrar la vista correcta según el botón pulsado
            if (tipo.equals("vehiculos")) {
                controller.mostrarVehiculos();
            } else if (tipo.equals("piezas")) {
                controller.mostrarPiezas();
            } else if (tipo.equals("inventario")) {
                controller.mostrarInventario();
            }

            // Cambiar a la escena de listados
            Stage stage = (Stage) btnVehiculos.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("AutoCiclo - Listados");

        } catch (Exception e) {
            System.err.println("Error al abrir listado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
