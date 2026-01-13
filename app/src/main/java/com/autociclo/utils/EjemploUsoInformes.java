package com.autociclo.utils;

import com.autociclo.database.ConexionBD;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase de ejemplo que muestra cómo usar los informes JasperReports en AutoCiclo
 *
 * IMPORTANTE: Esta clase es solo un ejemplo educativo.
 * Para integrar los informes en la aplicación real, debes:
 * 1. Añadir botones en los controladores correspondientes (PiezasController, VehiculosController, etc.)
 * 2. Llamar a InformeUtil.lanzarInforme() desde esos controladores
 * 3. Usar la conexión de DatabaseConnection.getConnection()
 */
public class EjemploUsoInformes extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(15);
        root.setStyle("-fx-padding: 20;");

        // Botón para informe de piezas (simple con imágenes)
        Button btnInformePiezas = new Button("📋 Generar Informe de Piezas");
        btnInformePiezas.setOnAction(e -> generarInformePiezas());

        // Botón para informe de vehículos (con parámetros)
        Button btnInformeVehiculos = new Button("🚗 Generar Informe de Vehículos");
        btnInformeVehiculos.setOnAction(e -> generarInformeVehiculos());

        // Botón para informe de inventario (con JOIN y gráficos)
        Button btnInformeInventario = new Button("📊 Generar Informe de Inventario");
        btnInformeInventario.setOnAction(e -> generarInformeInventario());

        root.getChildren().addAll(
            btnInformePiezas,
            btnInformeVehiculos,
            btnInformeInventario
        );

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Ejemplo de Uso de Informes - AutoCiclo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Ejemplo 1: Informe simple de piezas con imágenes
     * Este informe muestra todas las piezas con sus imágenes decodificadas desde Base64
     */
    private void generarInformePiezas() {
        try {
            Connection conexion = ConexionBD.getConexion();

            // No necesita parámetros, mostrará todas las piezas
            Map<String, Object> parametros = new HashMap<>();

            // Tipo 1 = ventana nueva (tipo 0 = embebido en WebView actual)
            InformeUtil.lanzarInforme(
                "/informes/InformePiezas.jasper",
                parametros,
                conexion,
                1, // Nueva ventana
                null // No necesita WebView porque es ventana nueva
            );

            System.out.println("✓ Informe de Piezas generado exitosamente");

        } catch (Exception e) {
            System.err.println("✗ Error al generar informe de piezas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Ejemplo 2: Informe de vehículos con parámetros condicionales
     * Este informe usa parámetros SQL para filtrar los resultados
     */
    private void generarInformeVehiculos() {
        try {
            Connection conexion = ConexionBD.getConexion();

            // Parámetros para filtrar el informe
            Map<String, Object> parametros = new HashMap<>();

            // Ejemplos de filtros (puedes cambiarlos):
            // Para mostrar todos, usa "%" como comodín
            parametros.put("P_ESTADO", "%");           // Todos los estados
            // parametros.put("P_ESTADO", "Disponible"); // Solo disponibles

            parametros.put("P_MARCA", "%");            // Todas las marcas
            // parametros.put("P_MARCA", "BMW");       // Solo BMW

            parametros.put("P_ANIO_MIN", 2000);        // Desde el año 2000
            parametros.put("P_ANIO_MAX", 2024);        // Hasta el año 2024

            InformeUtil.lanzarInforme(
                "/informes/InformeVehiculos.jasper",
                parametros,
                conexion,
                1, // Nueva ventana
                null
            );

            System.out.println("✓ Informe de Vehículos generado exitosamente");

        } catch (Exception e) {
            System.err.println("✗ Error al generar informe de vehículos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Ejemplo 3: Informe de inventario con JOIN y gráficos
     * Este informe une tres tablas (VEHICULOS, PIEZAS, INVENTARIO_PIEZAS)
     * y genera gráficos de barras y circular
     */
    private void generarInformeInventario() {
        try {
            Connection conexion = ConexionBD.getConexion();

            // Este informe no necesita parámetros, la consulta JOIN es automática
            Map<String, Object> parametros = new HashMap<>();

            InformeUtil.lanzarInforme(
                "/informes/InformeInventario.jasper",
                parametros,
                conexion,
                1, // Nueva ventana
                null
            );

            System.out.println("✓ Informe de Inventario generado exitosamente");

        } catch (Exception e) {
            System.err.println("✗ Error al generar informe de inventario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Ejemplo de cómo usar el informe embebido en una vista existente
     * (Para usar en un controlador con WebView ya creado)
     */
    @SuppressWarnings("unused")
    private void ejemploInformeEmbebido(WebView webView) {
        try {
            Connection conexion = ConexionBD.getConexion();
            Map<String, Object> parametros = new HashMap<>();

            // Tipo 0 = embebido en el WebView proporcionado
            InformeUtil.lanzarInforme(
                "/informes/InformePiezas.jasper",
                parametros,
                conexion,
                0, // Embebido
                webView // El WebView donde se mostrará
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
