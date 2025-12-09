package com.autociclo.controllers;

import com.autociclo.database.ConexionBD;
import com.autociclo.models.Vehiculo;
import com.autociclo.utils.ValidationUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Controlador para el formulario de vehículos
 * @author Yalil Musa Talhaoui
 */
public class FormularioVehiculoController implements Initializable {

    @FXML private Label lblTitulo;
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

    // Variables para el modo edición
    private Vehiculo vehiculoEditar = null;
    private boolean modoEdicion = false;
    private ListadoMaestroController controladorPadre;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar título por defecto (modo nuevo)
        lblTitulo.setText("NUEVO VEHÍCULO");

        // Configurar eventos de los botones
        btnGuardar.setOnAction(event -> guardarVehiculo());
        btnCancelar.setOnAction(event -> cerrarVentana());
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
            txtMarca.setText(vehiculoEditar.getMarca());
            txtModelo.setText(vehiculoEditar.getModelo());
            txtAnio.setText(String.valueOf(vehiculoEditar.getAnio()));
            txtColor.setText(vehiculoEditar.getColor());
            txtEstado.setText(vehiculoEditar.getEstado());
            txtPrecioCompra.setText(String.valueOf(vehiculoEditar.getPrecioCompra()));
            txtKilometraje.setText(String.valueOf(vehiculoEditar.getKilometraje()));
            txtUbicacionKm.setText(vehiculoEditar.getUbicacionGps());
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
        if (!ValidationUtils.validateNotEmpty(txtMarca, null, "Marca")) {
            errores.append("• Marca: Campo obligatorio\n");
            valido = false;
        }

        // Validar modelo (obligatorio)
        if (!ValidationUtils.validateNotEmpty(txtModelo, null, "Modelo")) {
            errores.append("• Modelo: Campo obligatorio\n");
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

        // Validar estado (obligatorio y debe ser uno de los valores del enum)
        String estado = txtEstado.getText().trim().toLowerCase();
        if (estado.isEmpty()) {
            errores.append("• Estado: Campo obligatorio\n");
            txtEstado.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            valido = false;
        } else if (!estado.equals("completo") && !estado.equals("desguazando") && !estado.equals("desguazado")) {
            errores.append("• Estado: Debe ser 'completo', 'desguazando' o 'desguazado'\n");
            txtEstado.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            valido = false;
        } else {
            txtEstado.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
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
                pstmt.setString(1, txtMarca.getText().trim());
                pstmt.setString(2, txtModelo.getText().trim());
                pstmt.setInt(3, Integer.parseInt(txtAnio.getText().trim()));
                pstmt.setString(4, txtColor.getText().trim());
                pstmt.setDate(5, Date.valueOf(LocalDate.now())); // fecha actual
                pstmt.setString(6, txtEstado.getText().trim());
                pstmt.setDouble(7, Double.parseDouble(txtPrecioCompra.getText().trim().replace(",", ".")));
                pstmt.setInt(8, txtKilometraje.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtKilometraje.getText().trim()));
                pstmt.setString(9, txtUbicacionKm.getText().trim());
                pstmt.setString(10, txtObservaciones.getText().trim());
                pstmt.setString(11, txtMatricula.getText().trim()); // WHERE matricula
            } else {
                // INSERT: asignar parámetros
                pstmt.setString(1, txtMatricula.getText().trim().toUpperCase());
                pstmt.setString(2, txtMarca.getText().trim());
                pstmt.setString(3, txtModelo.getText().trim());
                pstmt.setInt(4, Integer.parseInt(txtAnio.getText().trim()));
                pstmt.setString(5, txtColor.getText().trim());
                pstmt.setDate(6, Date.valueOf(LocalDate.now())); // fecha actual
                pstmt.setString(7, txtEstado.getText().trim());
                pstmt.setDouble(8, Double.parseDouble(txtPrecioCompra.getText().trim().replace(",", ".")));
                pstmt.setInt(9, txtKilometraje.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtKilometraje.getText().trim()));
                pstmt.setString(10, txtUbicacionKm.getText().trim());
                pstmt.setString(11, txtObservaciones.getText().trim());
            }

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                String mensaje = modoEdicion ?
                    "Vehículo actualizado correctamente" :
                    "Vehículo registrado correctamente con matrícula: " + txtMatricula.getText().trim().toUpperCase();

                ValidationUtils.showSuccess("Operación exitosa", mensaje);

                // Actualizar el listado del controlador padre
                if (controladorPadre != null) {
                    controladorPadre.actualizarListado();
                }

                // Cerrar ventana
                cerrarVentana();
            }

        } catch (SQLException e) {
            ValidationUtils.showError("Error de base de datos",
                "No se pudo guardar el vehículo: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
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
            e.printStackTrace();
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
