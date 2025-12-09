package com.autociclo.controllers;

import com.autociclo.database.ConexionBD;
import com.autociclo.models.Vehiculo;
import com.autociclo.utils.ValidationUtils;
import com.autociclo.utils.LoggerUtil;
import com.autociclo.utils.VehiculosJsonLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador para el formulario de vehículos
 * 
 * @author Yalil Musa Talhaoui
 */
public class FormularioVehiculoController implements Initializable {

    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtFiltroMarca;
    @FXML
    private ComboBox<String> cmbMarca;
    @FXML
    private TextField txtMatricula;
    @FXML
    private TextField txtColor;
    @FXML
    private ComboBox<String> cmbEstado;
    @FXML
    private ComboBox<String> cmbUbicacion;
    @FXML
    private ComboBox<String> cmbModelo;
    @FXML
    private TextField txtAnio;
    @FXML
    private TextField txtKilometraje;
    @FXML
    private TextField txtPrecioCompra;
    @FXML
    private TextArea txtObservaciones;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;

    // Variables para el modo edición
    private Vehiculo vehiculoEditar = null;
    private boolean modoEdicion = false;
    private ListadoMaestroController controladorPadre;

    // Listas para filtrado
    private ObservableList<String> listaMarcas = FXCollections.observableArrayList();
    private FilteredList<String> marcasFiltradas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar título por defecto (modo nuevo)
        lblTitulo.setText("NUEVO VEHÍCULO");

        // Inicializar ComboBox de estado
        cmbEstado.getItems().addAll("Completo", "Desguazando", "Desguazado");

        // Cargar ubicaciones desde el JSON
        cargarUbicaciones();

        // Cargar marcas desde el JSON
        cargarMarcas();

        // Configurar filtrado de marcas
        configurarFiltradoMarcas();

        // Listener para cuando se selecciona una marca, cargar los modelos
        // correspondientes
        cmbMarca.setOnAction(event -> cargarModelos());

        // Configurar eventos de los botones
        btnGuardar.setOnAction(event -> guardarVehiculo());
        btnCancelar.setOnAction(event -> cerrarVentana());
    }

    /**
     * Carga las marcas de vehículos desde el archivo JSON
     */
    private void cargarMarcas() {
        try {
            List<String> marcas = VehiculosJsonLoader.obtenerMarcas();
            listaMarcas.setAll(marcas);
            LoggerUtil.info("Marcas cargadas: " + marcas.size());
        } catch (Exception e) {
            LoggerUtil.error("Error al cargar marcas", e);
            ValidationUtils.showError("Error", "No se pudieron cargar las marcas de vehículos");
        }
    }

    /**
     * Carga las ubicaciones de vehículos desde el archivo JSON
     */
    private void cargarUbicaciones() {
        try {
            java.io.InputStream is = getClass().getResourceAsStream("/ubicaciones.json");
            if (is != null) {
                com.google.gson.JsonObject json = com.google.gson.JsonParser.parseReader(
                        new java.io.InputStreamReader(is, java.nio.charset.StandardCharsets.UTF_8)).getAsJsonObject();

                com.google.gson.JsonArray ubicaciones = json.getAsJsonArray("vehiculos");
                for (int i = 0; i < ubicaciones.size(); i++) {
                    cmbUbicacion.getItems().add(ubicaciones.get(i).getAsString());
                }
                LoggerUtil.info("Ubicaciones de vehículos cargadas: " + ubicaciones.size());
            }
        } catch (Exception e) {
            LoggerUtil.error("Error al cargar ubicaciones", e);
        }
    }

    /**
     * Configura el filtrado en tiempo real para marcas
     */
    private void configurarFiltradoMarcas() {
        // Crear lista filtrada basada en la lista original
        marcasFiltradas = new FilteredList<>(listaMarcas, p -> true);

        // Vincular el ComboBox a la lista filtrada
        cmbMarca.setItems(marcasFiltradas);

        // Listener para el campo de filtro
        txtFiltroMarca.textProperty().addListener((observable, oldValue, newValue) -> {
            marcasFiltradas.setPredicate(marca -> {
                // Si el filtro está vacío, mostrar todas
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Comparar marca (ignorar mayúsculas/minúsculas)
                String filtroMinusculas = newValue.toLowerCase();
                return marca.toLowerCase().contains(filtroMinusculas);
            });

            // Si solo queda un elemento tras filtrar, seleccionarlo automáticamente
            if (marcasFiltradas.size() == 1) {
                cmbMarca.setValue(marcasFiltradas.get(0));
            }
        });
    }

    /**
     * Carga los modelos correspondientes a la marca seleccionada
     */
    private void cargarModelos() {
        String marcaSeleccionada = cmbMarca.getValue();
        if (marcaSeleccionada != null && !marcaSeleccionada.isEmpty()) {
            try {
                List<String> modelos = VehiculosJsonLoader.obtenerModelos(marcaSeleccionada);
                cmbModelo.setItems(FXCollections.observableArrayList(modelos));
                cmbModelo.setDisable(false);

                // Si solo hay un modelo, seleccionarlo automáticamente
                if (modelos.size() == 1) {
                    cmbModelo.setValue(modelos.get(0));
                }

                LoggerUtil.info("Modelos cargados para " + marcaSeleccionada + ": " + modelos.size());
            } catch (Exception e) {
                LoggerUtil.error("Error al cargar modelos", e);
            }
        } else {
            cmbModelo.setItems(FXCollections.observableArrayList());
            cmbModelo.setDisable(true);
        }
    }

    /**
     * Configura el controlador padre para poder actualizar el listado
     */
    public void setControladorPadre(ListadoMaestroController controlador) {
        this.controladorPadre = controlador;
    }

    /**
     * Configura el vehículo a editar y rellena los campos
     */
    public void setVehiculoEditar(Vehiculo vehiculo) {
        this.vehiculoEditar = vehiculo;
        this.modoEdicion = true;
        lblTitulo.setText("EDITAR VEHÍCULO");
        cargarDatosVehiculo();
    }

    /**
     * Carga los datos del vehículo en el formulario
     */
    private void cargarDatosVehiculo() {
        if (vehiculoEditar != null) {
            txtMatricula.setText(vehiculoEditar.getMatricula());
            txtMatricula.setEditable(false); // No permitir editar la matrícula (clave única)

            // Establecer marca y cargar modelos
            cmbMarca.setValue(vehiculoEditar.getMarca());
            cargarModelos(); // Cargar modelos de la marca seleccionada
            cmbModelo.setValue(vehiculoEditar.getModelo());

            txtAnio.setText(String.valueOf(vehiculoEditar.getAnio()));
            txtColor.setText(vehiculoEditar.getColor());
            cmbEstado.setValue(vehiculoEditar.getEstado());
            txtPrecioCompra.setText(String.valueOf(vehiculoEditar.getPrecioCompra()));
            txtKilometraje.setText(String.valueOf(vehiculoEditar.getKilometraje()));
            cmbUbicacion.setValue(vehiculoEditar.getUbicacionGps());
            txtObservaciones.setText(vehiculoEditar.getObservaciones());
        }
    }

    /**
     * Valida todos los campos del formulario
     */
    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();
        boolean valido = true;

        // Validar matrícula (formato 1234-ABC)
        if (!ValidationUtils.validateMatricula(txtMatricula, null)) {
            errores.append("• Matrícula: Debe tener formato 1234-ABC\n");
            valido = false;
        }

        // Validar marca (obligatorio)
        if (!ValidationUtils.validateComboBox(cmbMarca, null, "una marca")) {
            errores.append("• Marca: Debe seleccionar una marca\n");
            valido = false;
        }

        // Validar modelo (obligatorio)
        if (!ValidationUtils.validateComboBox(cmbModelo, null, "un modelo")) {
            errores.append("• Modelo: Debe seleccionar un modelo\n");
            valido = false;
        }

        // Validar año (entero entre 1900 y 2025)
        if (!ValidationUtils.validateIntegerRange(txtAnio, null, "Año", 1900, 2025)) {
            errores.append("• Año: Debe estar entre 1900 y 2025\n");
            valido = false;
        }

        // Validar color (obligatorio)
        if (!ValidationUtils.validateNotEmpty(txtColor, null, "Color")) {
            errores.append("• Color: Campo obligatorio\n");
            valido = false;
        }

        // Validar estado (obligatorio)
        if (cmbEstado.getValue() == null || cmbEstado.getValue().toString().isEmpty()) {
            errores.append("• Estado: Debe seleccionar un estado\n");
            cmbEstado.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            valido = false;
        } else {
            cmbEstado.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
        }

        // Validar precio de compra (decimal >= 0)
        if (!ValidationUtils.validateDoubleMinimum(txtPrecioCompra, null, "Precio de compra", 0)) {
            errores.append("• Precio de compra: Debe ser un número mayor o igual a 0\n");
            valido = false;
        }

        // Validar kilometraje (entero >= 0)
        String kmText = txtKilometraje.getText().trim();
        if (!kmText.isEmpty()) {
            try {
                int km = Integer.parseInt(kmText);
                if (km < 0) {
                    errores.append("• Kilometraje: Debe ser un número positivo\n");
                    txtKilometraje.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    valido = false;
                } else {
                    txtKilometraje.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
                }
            } catch (NumberFormatException e) {
                errores.append("• Kilometraje: Debe ser un número entero válido\n");
                txtKilometraje.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                valido = false;
            }
        }

        // Si hay errores, mostrarlos en el Alert
        if (!valido) {
            ValidationUtils.showAlert("Errores de validación",
                    "Por favor, corrija los siguientes errores:",
                    errores.toString(),
                    Alert.AlertType.ERROR);
        }

        return valido;
    }

    /**
     * Guarda el vehículo en la base de datos
     */
    private void guardarVehiculo() {
        // Validar campos (el Alert de errores ya se muestra dentro de validarCampos())
        if (!validarCampos()) {
            return;
        }

        // Comprobar si la matrícula ya existe (solo en modo inserción)
        if (!modoEdicion && existeMatricula(txtMatricula.getText().trim())) {
            ValidationUtils.showError("Matrícula duplicada",
                    "Ya existe un vehículo con esa matrícula en el sistema");
            return;
        }

        try (Connection conn = ConexionBD.getConexion()) {
            String sql;

            if (modoEdicion) {
                // UPDATE con PreparedStatement (antiinyección SQL)
                sql = "UPDATE VEHICULOS SET marca=?, modelo=?, anio=?, color=?, fecha_entrada=?, " +
                        "estado=?, precio_compra=?, kilometraje=?, ubicacion_gps=?, observaciones=? " +
                        "WHERE matricula=?";
            } else {
                // INSERT con PreparedStatement (antiinyección SQL)
                sql = "INSERT INTO VEHICULOS (matricula, marca, modelo, anio, color, fecha_entrada, " +
                        "estado, precio_compra, kilometraje, ubicacion_gps, observaciones) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);

            if (modoEdicion) {
                // UPDATE: asignar parámetros
                pstmt.setString(1, cmbMarca.getValue());
                pstmt.setString(2, cmbModelo.getValue());
                pstmt.setInt(3, Integer.parseInt(txtAnio.getText().trim()));
                pstmt.setString(4, txtColor.getText().trim());
                pstmt.setDate(5, Date.valueOf(LocalDate.now())); // fecha actual
                pstmt.setString(6, cmbEstado.getValue());
                pstmt.setDouble(7, Double.parseDouble(txtPrecioCompra.getText().trim().replace(",", ".")));
                pstmt.setInt(8, txtKilometraje.getText().trim().isEmpty() ? 0
                        : Integer.parseInt(txtKilometraje.getText().trim()));
                pstmt.setString(9, cmbUbicacion.getValue() != null ? cmbUbicacion.getValue() : "");
                pstmt.setString(10, txtObservaciones.getText().trim());
                pstmt.setString(11, txtMatricula.getText().trim()); // WHERE matricula
            } else {
                // INSERT: asignar parámetros
                pstmt.setString(1, txtMatricula.getText().trim().toUpperCase());
                pstmt.setString(2, cmbMarca.getValue());
                pstmt.setString(3, cmbModelo.getValue());
                pstmt.setInt(4, Integer.parseInt(txtAnio.getText().trim()));
                pstmt.setString(5, txtColor.getText().trim());
                pstmt.setDate(6, Date.valueOf(LocalDate.now())); // fecha actual
                pstmt.setString(7, cmbEstado.getValue());
                pstmt.setDouble(8, Double.parseDouble(txtPrecioCompra.getText().trim().replace(",", ".")));
                pstmt.setInt(9, txtKilometraje.getText().trim().isEmpty() ? 0
                        : Integer.parseInt(txtKilometraje.getText().trim()));
                pstmt.setString(10, cmbUbicacion.getValue() != null ? cmbUbicacion.getValue() : "");
                pstmt.setString(11, txtObservaciones.getText().trim());
            }

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                String mensaje = modoEdicion ? "Vehículo actualizado correctamente"
                        : "Vehículo registrado correctamente con matrícula: "
                                + txtMatricula.getText().trim().toUpperCase();

                ValidationUtils.showSuccess("Operación exitosa", mensaje);

                // Actualizar el listado del controlador padre
                if (controladorPadre != null) {
                    controladorPadre.actualizarListado();
                }

                // Cerrar ventana
                cerrarVentana();
            }

        } catch (SQLException e) {
            LoggerUtil.error("Error al guardar vehículo en BD", e);
            ValidationUtils.showError("Error de base de datos",
                    "No se pudo guardar el vehículo: " + e.getMessage());
        } catch (NumberFormatException e) {
            LoggerUtil.warning("Error de formato en campos numéricos del formulario de vehículo");
            ValidationUtils.showError("Error de formato",
                    "Verifique que los campos numéricos tengan el formato correcto");
        }
    }

    /**
     * Comprueba si ya existe un vehículo con la matrícula indicada
     */
    private boolean existeMatricula(String matricula) {
        try (Connection conn = ConexionBD.getConexion()) {
            String sql = "SELECT COUNT(*) FROM VEHICULOS WHERE matricula = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, matricula.toUpperCase());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            LoggerUtil.error("Error al verificar existencia de matrícula", e);
        }
        return false;
    }

    /**
     * Cierra la ventana del formulario
     */
    private void cerrarVentana() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}
