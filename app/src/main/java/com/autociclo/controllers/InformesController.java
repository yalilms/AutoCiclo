package com.autociclo.controllers;

import com.autociclo.database.ConexionBD;
import com.autociclo.utils.LoggerUtil;
import com.autociclo.utils.ValidationUtils;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;

import java.io.File;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de Informes JasperReports
 * Permite generar informes embebidos o en modal
 */
public class InformesController implements Initializable {

    @FXML
    private ComboBox<String> cmbTipoInforme;

    @FXML
    private ComboBox<String> cmbFiltro;

    @FXML
    private Label lblFiltro;

    @FXML
    private Button btnGenerarInforme;

    @FXML
    private CheckBox chkIncrustado;

    @FXML
    private Button btnAbrirModal;

    @FXML
    private Button btnExportarPDF;

    @FXML
    private Label lblEstado;

    @FXML
    private Label lblPlaceholder;

    @FXML
    private StackPane contenedorInforme;

    // WebView para mostrar el informe embebido (creación diferida)
    private WebView webViewInforme;

    // Contenido HTML del último informe generado
    private String ultimoHTMLContent = null;

    // JasperPrint del último informe para exportar a PDF
    private JasperPrint ultimoJasperPrint = null;

    // Nombre del último informe generado
    private String ultimoNombreInforme = null;

    // Stage para ventana modal
    private Stage modalStage = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // NO crear WebView aquí - se crea de forma diferida cuando se necesite

        // Configurar ComboBox con los tipos de informe
        cmbTipoInforme.setItems(FXCollections.observableArrayList(
                "Informe de Piezas",
                "Informe de Vehículos",
                "Informe de Inventario"));

        // Establecer checkbox incrustado como seleccionado por defecto
        chkIncrustado.setSelected(true);

        // Configurar evento de cambio en tipo de informe para mostrar filtros
        cmbTipoInforme.setOnAction(e -> actualizarFiltros());

        // Configurar eventos
        btnGenerarInforme.setOnAction(e -> generarInforme());
        btnAbrirModal.setOnAction(e -> abrirEnModal());
        btnExportarPDF.setOnAction(e -> exportarPDF());
    }

    /**
     * Actualiza el ComboBox de filtros según el tipo de informe seleccionado
     */
    private void actualizarFiltros() {
        String seleccion = cmbTipoInforme.getValue();
        if (seleccion == null) {
            lblFiltro.setVisible(false);
            cmbFiltro.setVisible(false);
            return;
        }

        try {
            Connection conn = ConexionBD.getConexion();
            Statement stmt = conn.createStatement();
            java.util.List<String> opciones = new java.util.ArrayList<>();
            opciones.add("Todos"); // Opción por defecto

            if (seleccion.contains("Piezas")) {
                // Filtrar por categoría
                lblFiltro.setText("Categoría:");
                ResultSet rs = stmt.executeQuery("SELECT DISTINCT categoria FROM PIEZAS ORDER BY categoria");
                while (rs.next()) {
                    String cat = rs.getString("categoria");
                    opciones.add(cat.substring(0, 1).toUpperCase() + cat.substring(1));
                }
                rs.close();
            } else if (seleccion.contains("Vehículos")) {
                // Filtrar por marca
                lblFiltro.setText("Marca:");
                ResultSet rs = stmt.executeQuery("SELECT DISTINCT marca FROM VEHICULOS ORDER BY marca");
                while (rs.next()) {
                    opciones.add(rs.getString("marca"));
                }
                rs.close();
            } else if (seleccion.contains("Inventario")) {
                // Filtrar por estado de pieza
                lblFiltro.setText("Estado pieza:");
                ResultSet rs = stmt.executeQuery("SELECT DISTINCT estado_pieza FROM INVENTARIO_PIEZAS ORDER BY estado_pieza");
                while (rs.next()) {
                    String estado = rs.getString("estado_pieza");
                    opciones.add(estado.substring(0, 1).toUpperCase() + estado.substring(1));
                }
                rs.close();
            }
            stmt.close();

            cmbFiltro.setItems(FXCollections.observableArrayList(opciones));
            cmbFiltro.setValue("Todos");
            lblFiltro.setVisible(true);
            cmbFiltro.setVisible(true);

        } catch (Exception e) {
            LoggerUtil.error("Error al cargar filtros", e);
            lblFiltro.setVisible(false);
            cmbFiltro.setVisible(false);
        }
    }

    /**
     * Obtiene o crea el WebView de forma diferida
     */
    private WebView getWebView() {
        if (webViewInforme == null) {
            webViewInforme = new WebView();
            webViewInforme.setVisible(false);
            contenedorInforme.getChildren().add(webViewInforme);
        }
        return webViewInforme;
    }

    /**
     * Genera el informe según la selección del checkbox (embebido o modal)
     */
    private void generarInforme() {
        String seleccion = cmbTipoInforme.getValue();

        if (seleccion == null) {
            ValidationUtils.showAlert("Atención", null, "Por favor, selecciona un tipo de informe.",
                    Alert.AlertType.WARNING);
            return;
        }

        // Limpiar WebView anterior para evitar texto superpuesto
        if (webViewInforme != null) {
            webViewInforme.getEngine().loadContent("");
        }

        lblEstado.setText("Generando informe...");
        lblEstado.setStyle("-fx-text-fill: #f39c12;");

        // Ejecutar en un hilo separado para no bloquear la UI
        new Thread(() -> {
            try {
                Connection conexion = ConexionBD.getConexion();
                Map<String, Object> parametros = new HashMap<>();
                String rutaInforme;
                String nombreInforme;

                // Obtener valor del filtro
                String filtroValor = cmbFiltro.getValue();
                boolean filtrarTodos = filtroValor == null || filtroValor.equals("Todos");

                // Determinar qué informe generar
                if (seleccion.contains("Piezas")) {
                    rutaInforme = "/informes/InformePiezas.jasper";
                    nombreInforme = "InformePiezas";
                    // Parámetro de categoría
                    if (filtrarTodos) {
                        parametros.put("P_CATEGORIA", "%");
                    } else {
                        parametros.put("P_CATEGORIA", filtroValor.toLowerCase());
                    }
                } else if (seleccion.contains("Vehículos")) {
                    rutaInforme = "/informes/InformeVehiculos.jasper";
                    nombreInforme = "InformeVehiculos";
                    // Parámetros de vehículos
                    parametros.put("P_ESTADO", "%");
                    parametros.put("P_ANIO_MIN", 1900);
                    parametros.put("P_ANIO_MAX", 2100);
                    if (filtrarTodos) {
                        parametros.put("P_MARCA", "%");
                    } else {
                        parametros.put("P_MARCA", filtroValor);
                    }
                } else {
                    rutaInforme = "/informes/InformeInventario.jasper";
                    nombreInforme = "InformeInventario";
                    // Parámetro de estado de pieza
                    if (filtrarTodos) {
                        parametros.put("P_ESTADO_PIEZA", "%");
                    } else {
                        parametros.put("P_ESTADO_PIEZA", filtroValor.toLowerCase());
                    }
                }

                // Cargar el archivo .jasper compilado
                JasperReport report = (JasperReport) JRLoader.loadObject(
                        getClass().getResourceAsStream(rutaInforme));

                // Llenar el informe con datos
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, conexion);

                // Guardar JasperPrint y nombre para exportar PDF después
                ultimoJasperPrint = jasperPrint;
                ultimoNombreInforme = nombreInforme;

                // Exportar a HTML para visualización
                StringWriter stringWriter = new StringWriter();
                HtmlExporter exporter = new HtmlExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleHtmlExporterOutput(stringWriter));
                exporter.exportReport();

                String htmlContent = stringWriter.toString();
                // Añadir gráficas estadísticas al HTML
                String htmlContenidoFinal = añadirGraficasEstadisticas(htmlContent, nombreInforme, conexion);

                // Guardar contenido HTML
                ultimoHTMLContent = htmlContenidoFinal;

                // Verificar si debe mostrarse embebido o en modal
                boolean esIncrustado = chkIncrustado.isSelected();

                // Actualizar UI en el hilo de JavaFX
                Platform.runLater(() -> {
                    if (esIncrustado) {
                        // Mostrar embebido en WebView
                        lblPlaceholder.setVisible(false);
                        WebView wv = getWebView();
                        wv.setVisible(true);
                        wv.getEngine().loadContent(htmlContenidoFinal);

                        // Mostrar botones
                        btnAbrirModal.setVisible(true);
                        btnExportarPDF.setVisible(true);

                        lblEstado.setText("Informe generado (embebido)");
                    } else {
                        // Abrir en modal
                        abrirEnModal();
                        btnExportarPDF.setVisible(true);

                        lblEstado.setText("Informe generado (modal)");
                    }

                    lblEstado.setStyle("-fx-text-fill: #27ae60;");
                });

                LoggerUtil.info("Informe generado: " + nombreInforme);

            } catch (Exception e) {
                Platform.runLater(() -> {
                    lblEstado.setText("Error al generar informe");
                    lblEstado.setStyle("-fx-text-fill: #e74c3c;");
                    LoggerUtil.error("Error al generar informe", e);
                    ValidationUtils.showError("Error", "No se pudo generar el informe: " + e.getMessage());
                });
            }
        }).start();
    }

    /**
     * Añade gráficas estadísticas al HTML del informe
     * Solo para informes de Vehículos y Piezas (NO para Inventario)
     */
    private String añadirGraficasEstadisticas(String htmlOriginal, String tipoInforme, Connection conn) {
        // No añadir gráficas al informe de Inventario
        if (tipoInforme.equals("InformeInventario")) {
            return htmlOriginal;
        }

        try {
            StringBuilder stats = new StringBuilder();
            stats.append("<div style='margin-top: 30px; padding: 20px; background: #f8f9fa;'>");
            stats.append(
                    "<h2 style='text-align: center; color: #4A90E2; margin-bottom: 20px;'>ESTADÍSTICAS VISUALES</h2>");

            if (tipoInforme.equals("InformeVehiculos")) {
                stats.append(generarGraficoVehiculos(conn));
            } else if (tipoInforme.equals("InformePiezas")) {
                stats.append(generarGraficoPiezas(conn));
            }

            stats.append("</div>");

            // Insertar antes del cierre del body
            int bodyCloseIndex = htmlOriginal.lastIndexOf("</body>");
            if (bodyCloseIndex > 0) {
                return htmlOriginal.substring(0, bodyCloseIndex) + stats.toString()
                        + htmlOriginal.substring(bodyCloseIndex);
            }
            return htmlOriginal;
        } catch (Exception e) {
            LoggerUtil.error("Error al añadir gráficas", e);
            return htmlOriginal;
        }
    }

    private String generarGraficoVehiculos(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt
                    .executeQuery("SELECT marca, COUNT(*) as count FROM VEHICULOS GROUP BY marca ORDER BY count DESC");

            // Recopilar datos
            java.util.List<String> marcas = new java.util.ArrayList<>();
            java.util.List<Integer> cantidades = new java.util.ArrayList<>();
            int maxCount = 0;

            while (rs.next()) {
                String marca = rs.getString("marca");
                int count = rs.getInt("count");
                marcas.add(marca);
                cantidades.add(count);
                if (count > maxCount) maxCount = count;
            }
            rs.close();
            stmt.close();

            if (marcas.isEmpty()) {
                return "<p style='text-align:center; color:#666;'>No hay datos de vehículos para mostrar</p>";
            }

            // Generar gráfico de barras con SVG puro (funciona en JavaFX WebView)
            StringBuilder svg = new StringBuilder();
            int chartWidth = 500;
            int chartHeight = 250;
            int barWidth = Math.min(60, (chartWidth - 100) / marcas.size());
            int spacing = 20;
            int startX = 60;
            int baseY = chartHeight - 40;
            int maxBarHeight = baseY - 30;

            svg.append("<div style='text-align:center; margin: 20px 0;'>");
            svg.append("<h3 style='color:#4A90E2; margin-bottom:15px;'>Vehículos por Marca</h3>");
            svg.append("<svg width='").append(chartWidth).append("' height='").append(chartHeight).append("' style='background:#fff; border:1px solid #ddd; border-radius:8px;'>");

            // Líneas de fondo y etiquetas del eje Y
            for (int i = 0; i <= 4; i++) {
                int y = baseY - (i * maxBarHeight / 4);
                int valor = (maxCount * i) / 4;
                svg.append("<line x1='55' y1='").append(y).append("' x2='").append(chartWidth - 20).append("' y2='").append(y).append("' stroke='#eee' stroke-width='1'/>");
                svg.append("<text x='50' y='").append(y + 4).append("' text-anchor='end' font-size='10' fill='#666'>").append(valor).append("</text>");
            }

            // Barras
            String[] colors = {"#4A90E2", "#50C878", "#FF6B6B", "#FFD93D", "#6BCB77", "#9B59B6"};
            for (int i = 0; i < marcas.size(); i++) {
                int barHeight = maxCount > 0 ? (cantidades.get(i) * maxBarHeight) / maxCount : 0;
                int x = startX + i * (barWidth + spacing);
                int y = baseY - barHeight;
                String color = colors[i % colors.length];

                // Barra con gradiente
                svg.append("<rect x='").append(x).append("' y='").append(y).append("' width='").append(barWidth)
                   .append("' height='").append(barHeight).append("' fill='").append(color).append("' rx='4' ry='4'/>");

                // Valor encima de la barra
                svg.append("<text x='").append(x + barWidth/2).append("' y='").append(y - 5)
                   .append("' text-anchor='middle' font-size='12' font-weight='bold' fill='#333'>").append(cantidades.get(i)).append("</text>");

                // Etiqueta de marca (rotada si es necesario)
                svg.append("<text x='").append(x + barWidth/2).append("' y='").append(baseY + 15)
                   .append("' text-anchor='middle' font-size='10' fill='#333'>").append(marcas.get(i)).append("</text>");
            }

            svg.append("</svg></div>");
            return svg.toString();
        } catch (Exception e) {
            LoggerUtil.error("Error al generar gráfico de vehículos", e);
            return "<p style='color:red;'>Error al generar gráfico de vehículos: " + e.getMessage() + "</p>";
        }
    }

    private String generarGraficoPiezas(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT categoria, COUNT(*) as count FROM PIEZAS GROUP BY categoria ORDER BY count DESC");

            // Recopilar datos
            java.util.List<String> categorias = new java.util.ArrayList<>();
            java.util.List<Integer> cantidades = new java.util.ArrayList<>();
            int total = 0;

            while (rs.next()) {
                String categoria = rs.getString("categoria");
                int count = rs.getInt("count");
                categorias.add(categoria);
                cantidades.add(count);
                total += count;
            }
            rs.close();
            stmt.close();

            if (categorias.isEmpty()) {
                return "<p style='text-align:center; color:#666;'>No hay datos de piezas para mostrar</p>";
            }

            // Generar gráfico circular (pie chart) con SVG puro
            StringBuilder svg = new StringBuilder();
            String[] colors = {"#FF6384", "#36A2EB", "#FFCE56", "#4BC0C0", "#9966FF", "#FF9F40"};
            int centerX = 150;
            int centerY = 130;
            int radius = 100;

            svg.append("<div style='text-align:center; margin: 20px 0;'>");
            svg.append("<h3 style='color:#4A90E2; margin-bottom:15px;'>Piezas por Categoría</h3>");
            svg.append("<div style='display:flex; justify-content:center; align-items:center; gap:30px; flex-wrap:wrap;'>");

            // SVG del gráfico circular
            svg.append("<svg width='300' height='280' style='background:#fff;'>");

            double startAngle = 0;
            for (int i = 0; i < categorias.size(); i++) {
                double percentage = (double) cantidades.get(i) / total;
                double angle = percentage * 360;
                double endAngle = startAngle + angle;

                // Calcular puntos del arco
                double startRad = Math.toRadians(startAngle - 90);
                double endRad = Math.toRadians(endAngle - 90);

                int x1 = (int) (centerX + radius * Math.cos(startRad));
                int y1 = (int) (centerY + radius * Math.sin(startRad));
                int x2 = (int) (centerX + radius * Math.cos(endRad));
                int y2 = (int) (centerY + radius * Math.sin(endRad));

                int largeArc = angle > 180 ? 1 : 0;
                String color = colors[i % colors.length];

                // Dibujar sector
                svg.append("<path d='M").append(centerX).append(",").append(centerY)
                   .append(" L").append(x1).append(",").append(y1)
                   .append(" A").append(radius).append(",").append(radius)
                   .append(" 0 ").append(largeArc).append(",1 ")
                   .append(x2).append(",").append(y2)
                   .append(" Z' fill='").append(color).append("' stroke='#fff' stroke-width='2'/>");

                // Etiqueta con porcentaje en el centro del sector
                double midAngle = Math.toRadians((startAngle + endAngle) / 2 - 90);
                int labelX = (int) (centerX + (radius * 0.65) * Math.cos(midAngle));
                int labelY = (int) (centerY + (radius * 0.65) * Math.sin(midAngle));

                if (percentage >= 0.08) { // Solo mostrar % si el sector es suficientemente grande
                    svg.append("<text x='").append(labelX).append("' y='").append(labelY)
                       .append("' text-anchor='middle' font-size='11' font-weight='bold' fill='#fff'>")
                       .append(String.format("%.0f%%", percentage * 100)).append("</text>");
                }

                startAngle = endAngle;
            }
            svg.append("</svg>");

            // Leyenda
            svg.append("<div style='text-align:left;'>");
            for (int i = 0; i < categorias.size(); i++) {
                String color = colors[i % colors.length];
                String categoria = categorias.get(i).substring(0, 1).toUpperCase() + categorias.get(i).substring(1);
                svg.append("<div style='margin:8px 0; display:flex; align-items:center;'>");
                svg.append("<span style='display:inline-block; width:16px; height:16px; background:").append(color)
                   .append("; border-radius:3px; margin-right:10px;'></span>");
                svg.append("<span style='font-size:13px;'>").append(categoria).append(" (").append(cantidades.get(i)).append(")</span>");
                svg.append("</div>");
            }
            svg.append("</div>");

            svg.append("</div></div>");
            return svg.toString();
        } catch (Exception e) {
            LoggerUtil.error("Error al generar gráfico de piezas", e);
            return "<p style='color:red;'>Error al generar gráfico de piezas: " + e.getMessage() + "</p>";
        }
    }

    /**
     * Abre el informe en una ventana modal
     */
    private void abrirEnModal() {
        if (ultimoHTMLContent == null) {
            ValidationUtils.showError("Error", "No hay ningún informe generado para mostrar en modal.");
            return;
        }

        try {
            // Cerrar modal anterior si existe
            if (modalStage != null && modalStage.isShowing()) {
                modalStage.close();
            }

            // Crear nuevo Stage modal
            modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Informe - AutoCiclo");

            // Crear WebView para el modal
            WebView modalWebView = new WebView();
            modalWebView.getEngine().loadContent(ultimoHTMLContent);

            // Crear escena con el WebView
            Scene scene = new Scene(modalWebView, 900, 700);
            modalStage.setScene(scene);

            // Mostrar modal
            modalStage.show();

            LoggerUtil.info("Informe abierto en ventana modal");
        } catch (Exception e) {
            LoggerUtil.error("Error al abrir modal", e);
            ValidationUtils.showError("Error", "No se pudo abrir el informe en modal: " + e.getMessage());
        }
    }

    /**
     * Exporta el informe actual a PDF y lo guarda en la carpeta de exportados
     */
    private void exportarPDF() {
        if (ultimoJasperPrint == null || ultimoNombreInforme == null) {
            ValidationUtils.showError("Error", "No hay ningún informe generado para exportar.");
            return;
        }

        try {
            // Crear nombre de archivo con fecha y hora
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String timestamp = sdf.format(new Date());
            String nombreArchivo = ultimoNombreInforme + "_" + timestamp + ".pdf";

            // Obtener ruta del proyecto para guardar en resources/informes/exportados
            String userDir = System.getProperty("user.dir");
            String rutaExportados;

            // Determinar la ruta correcta según desde dónde se ejecute
            if (userDir.endsWith("app")) {
                rutaExportados = userDir + "/src/main/resources/informes/exportados/";
            } else {
                rutaExportados = userDir + "/app/src/main/resources/informes/exportados/";
            }

            // Crear directorio si no existe
            File directorio = new File(rutaExportados);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            String rutaCompleta = rutaExportados + nombreArchivo;

            // Exportar a PDF
            JasperExportManager.exportReportToPdfFile(ultimoJasperPrint, rutaCompleta);

            // Mostrar mensaje de éxito
            lblEstado.setText("PDF exportado: " + nombreArchivo);
            lblEstado.setStyle("-fx-text-fill: #27ae60;");

            LoggerUtil.info("Informe exportado a PDF: " + rutaCompleta);

            // Mostrar alerta de confirmación
            ValidationUtils.showAlert("Éxito", "PDF Exportado",
                "El informe se ha guardado en:\n" + rutaCompleta, Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            LoggerUtil.error("Error al exportar PDF", e);
            lblEstado.setText("Error al exportar PDF");
            lblEstado.setStyle("-fx-text-fill: #e74c3c;");
            ValidationUtils.showError("Error", "No se pudo exportar el PDF: " + e.getMessage());
        }
    }

    /**
     * Método para refrescar los informes (llamado desde el controlador principal)
     */
    public void refrescar() {
        // Resetear estado
        lblEstado.setText("");
        lblPlaceholder.setVisible(true);
        lblPlaceholder.setText("Selecciona un informe y haz clic en 'Generar Informe'");
        if (webViewInforme != null) {
            webViewInforme.setVisible(false);
        }
        btnAbrirModal.setVisible(false);
        btnExportarPDF.setVisible(false);
        lblFiltro.setVisible(false);
        cmbFiltro.setVisible(false);
        cmbTipoInforme.setValue(null);
        chkIncrustado.setSelected(true);
        ultimoHTMLContent = null;
        ultimoJasperPrint = null;
        ultimoNombreInforme = null;

        // Cerrar modal si está abierto
        if (modalStage != null && modalStage.isShowing()) {
            modalStage.close();
        }
    }
}
