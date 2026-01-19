package com.autociclo.utils;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;

import java.io.FileWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.Map;

/**
 * Clase utilidad para generar y lanzar informes JasperReports en JavaFX
 */
public class InformeUtil {

    /**
     * Lanza un informe JasperReports
     *
     * @param rutaInforme Ruta del archivo .jasper compilado (ej: "/informes/InformePiezas.jasper")
     * @param parametros  Mapa de parámetros a pasar al informe (puede ser null si no hay parámetros)
     * @param conexion    Conexión a la base de datos
     * @param tipo        Tipo de visualización: 0 = embebido en WebView actual, 1 = ventana nueva
     * @param webView     WebView donde mostrar el informe (solo si tipo = 0)
     * @throws Exception  Si hay algún error al generar o mostrar el informe
     */
    public static void lanzarInforme(String rutaInforme, Map<String, Object> parametros,
                                     Connection conexion, int tipo, WebView webView) throws Exception {

        // Cargar el archivo .jasper compilado
        JasperReport report = (JasperReport) JRLoader.loadObject(
                InformeUtil.class.getResourceAsStream(rutaInforme)
        );

        // Llenar el informe con datos
        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, conexion);

        // Extraer el nombre del informe (sin extensión) de la ruta
        String nombreInforme = rutaInforme.substring(rutaInforme.lastIndexOf("/") + 1, rutaInforme.lastIndexOf("."));

        // Exportar a PDF
        String rutaPDF = System.getProperty("user.home") + "/" + nombreInforme + ".pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint, rutaPDF);

        // Exportar a HTML para visualización en WebView
        StringWriter stringWriter = new StringWriter();
        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(stringWriter));
        exporter.exportReport();

        String htmlContent = stringWriter.toString();

        // Guardar HTML a archivo
        String rutaHTML = System.getProperty("user.home") + "/" + nombreInforme + ".html";
        try (FileWriter fileWriter = new FileWriter(rutaHTML)) {
            fileWriter.write(htmlContent);
        }
        System.out.println("HTML exportado a: " + rutaHTML);

        // Mostrar según el tipo
        if (tipo == 0) {
            // Embebido en WebView actual
            if (webView != null) {
                Platform.runLater(() -> webView.getEngine().loadContent(htmlContent));
            } else {
                throw new IllegalArgumentException("WebView no puede ser null para tipo embebido");
            }
        } else {
            // Nueva ventana - debe ejecutarse en el hilo de JavaFX
            Platform.runLater(() -> {
                Stage stage = new Stage();
                stage.setTitle("Informe AutoCiclo");

                WebView visorWeb = new WebView();
                visorWeb.getEngine().loadContent(htmlContent);

                Scene scene = new Scene(visorWeb, 800, 600);
                stage.setScene(scene);
                stage.show();
            });
        }

        System.out.println("Informe generado correctamente en: " + rutaPDF);
    }

    /**
     * Exporta un informe directamente a PDF sin visualización
     *
     * @param rutaInforme     Ruta del archivo .jasper compilado
     * @param parametros      Mapa de parámetros
     * @param conexion        Conexión a la base de datos
     * @param rutaDestinoPDF  Ruta donde guardar el PDF
     * @throws Exception      Si hay algún error al generar el PDF
     */
    public static void exportarPDF(String rutaInforme, Map<String, Object> parametros,
                                   Connection conexion, String rutaDestinoPDF) throws Exception {

        JasperReport report = (JasperReport) JRLoader.loadObject(
                InformeUtil.class.getResourceAsStream(rutaInforme)
        );

        JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, conexion);

        JasperExportManager.exportReportToPdfFile(jasperPrint, rutaDestinoPDF);

        System.out.println("PDF exportado a: " + rutaDestinoPDF);
    }

    /**
     * Compila un archivo .jrxml a .jasper
     * Útil para desarrollo, pero en producción se usan directamente los .jasper
     *
     * @param rutaJRXML  Ruta del archivo .jrxml
     * @param rutaJasper Ruta donde guardar el .jasper compilado
     * @throws Exception Si hay error en la compilación
     */
    public static void compilarInforme(String rutaJRXML, String rutaJasper) throws Exception {
        JasperCompileManager.compileReportToFile(rutaJRXML, rutaJasper);
        System.out.println("Informe compilado: " + rutaJasper);
    }
}
