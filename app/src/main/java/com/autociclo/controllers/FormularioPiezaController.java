package com.autociclo.controllers;

import com.autociclo.database.ConexionBD;
import com.autociclo.models.Pieza;
import com.autociclo.utils.ValidationUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Controlador para el formulario de piezas
 * @author Yalil Musa Talhaoui
 */
public class FormularioPiezaController implements Initializable {

    @FXML private Label lblTitulo;
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

    // Variables para el modo edición
    private Pieza piezaEditar = null;
    private boolean modoEdicion = false;
    private ListadoMaestroController controladorPadre;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar título por defecto (modo nuevo)
        lblTitulo.setText("NUEVA PIEZA");

        // Configurar eventos de los botones
        btnGuardar.setOnAction(event -> guardarPieza());
        btnCancelar.setOnAction(event -> cerrarVentana());
    }

    /**
     * Configura el controlador padre para poder actualizar el listado
     */
    public void setControladorPadre(ListadoMaestroController controlador) {
        this.controladorPadre = controlador;
    }

    /**
     * Configura la pieza a editar y rellena los campos
     */
    public void setPiezaEditar(Pieza pieza) {
        this.piezaEditar = pieza;
        this.modoEdicion = true;
        lblTitulo.setText("EDITAR PIEZA");
        cargarDatosPieza();
    }

    /**
     * Carga los datos de la pieza en el formulario
     */
    private void cargarDatosPieza() {
        if (piezaEditar != null) {
            txtCodigo.setText(piezaEditar.getCodigoPieza());
            txtCodigo.setEditable(false); // No permitir editar el código (clave única)
            txtNombre.setText(piezaEditar.getNombre());
            txtCategoria.setText(piezaEditar.getCategoria());
            txtPrecioCompra.setText(String.valueOf(piezaEditar.getPrecioVenta()));
            txtStockDisponible.setText(String.valueOf(piezaEditar.getStockDisponible()));
            txtStockMinimo.setText(String.valueOf(piezaEditar.getStockMinimo()));
            txtUbicacionAlmacen.setText(piezaEditar.getUbicacionAlmacen());
            txtMaterialesCompatibles.setText(piezaEditar.getCompatibleMarcas());
            txtDescripcion.setText(piezaEditar.getDescripcion());
        }
    }

    /**
     * Valida todos los campos del formulario
     */
    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();
        boolean valido = true;

        // Validar código de pieza (obligatorio y alfanumérico)
        if (!ValidationUtils.validateCodigoPieza(txtCodigo, null)) {
            errores.append("• Código: Debe ser alfanumérico (letras, números y guión)\n");
            valido = false;
        }

        // Validar nombre (obligatorio)
        if (!ValidationUtils.validateNotEmpty(txtNombre, null, "Nombre")) {
            errores.append("• Nombre: Campo obligatorio\n");
            valido = false;
        }

        // Validar categoría (obligatorio y debe ser del enum)
        String categoria = txtCategoria.getText().trim().toLowerCase();
        if (categoria.isEmpty()) {
            errores.append("• Categoría: Campo obligatorio\n");
            txtCategoria.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            valido = false;
        } else if (!categoria.equals("motor") && !categoria.equals("carroceria") &&
                   !categoria.equals("interior") && !categoria.equals("electronica") &&
                   !categoria.equals("ruedas") && !categoria.equals("otros")) {
            errores.append("• Categoría: Debe ser motor, carroceria, interior, electronica, ruedas u otros\n");
            txtCategoria.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            valido = false;
        } else {
            txtCategoria.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
        }

        // Validar precio de venta (decimal >= 0)
        if (!ValidationUtils.validateDoubleMinimum(txtPrecioCompra, null, "Precio de venta", 0)) {
            errores.append("• Precio de venta: Debe ser un número mayor o igual a 0\n");
            valido = false;
        }

        // Validar stock disponible (entero >= 0)
        String stockText = txtStockDisponible.getText().trim();
        if (!stockText.isEmpty()) {
            try {
                int stock = Integer.parseInt(stockText);
                if (stock < 0) {
                    errores.append("• Stock disponible: Debe ser un número positivo\n");
                    txtStockDisponible.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    valido = false;
                } else {
                    txtStockDisponible.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
                }
            } catch (NumberFormatException e) {
                errores.append("• Stock disponible: Debe ser un número entero válido\n");
                txtStockDisponible.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                valido = false;
            }
        }

        // Validar stock mínimo (entero >= 0)
        String stockMinText = txtStockMinimo.getText().trim();
        if (!stockMinText.isEmpty()) {
            try {
                int stockMin = Integer.parseInt(stockMinText);
                if (stockMin < 0) {
                    errores.append("• Stock mínimo: Debe ser un número positivo\n");
                    txtStockMinimo.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    valido = false;
                } else {
                    txtStockMinimo.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
                }
            } catch (NumberFormatException e) {
                errores.append("• Stock mínimo: Debe ser un número entero válido\n");
                txtStockMinimo.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                valido = false;
            }
        }

        // Validar ubicación (obligatorio)
        if (!ValidationUtils.validateNotEmpty(txtUbicacionAlmacen, null, "Ubicación almacén")) {
            errores.append("• Ubicación almacén: Campo obligatorio\n");
            valido = false;
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
     * Guarda la pieza en la base de datos
     */
    private void guardarPieza() {
        // Validar campos (el Alert de errores ya se muestra dentro de validarCampos())
        if (!validarCampos()) {
            return;
        }

        // Comprobar si el código ya existe (solo en modo inserción)
        if (!modoEdicion && existeCodigo(txtCodigo.getText().trim())) {
            ValidationUtils.showError("Código duplicado",
                "Ya existe una pieza con ese código en el sistema");
            return;
        }

        try (Connection conn = ConexionBD.getConexion()) {
            String sql;

            if (modoEdicion) {
                // UPDATE con PreparedStatement (antiinyección SQL)
                sql = "UPDATE PIEZAS SET nombre=?, categoria=?, precio_venta=?, stock_disponible=?, " +
                      "stock_minimo=?, ubicacion_almacen=?, compatible_marcas=?, descripcion=? " +
                      "WHERE codigo_pieza=?";
            } else {
                // INSERT con PreparedStatement (antiinyección SQL)
                sql = "INSERT INTO PIEZAS (codigo_pieza, nombre, categoria, precio_venta, stock_disponible, " +
                      "stock_minimo, ubicacion_almacen, compatible_marcas, imagen, descripcion) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);

            if (modoEdicion) {
                // UPDATE: asignar parámetros
                pstmt.setString(1, txtNombre.getText().trim());
                pstmt.setString(2, txtCategoria.getText().trim());
                pstmt.setDouble(3, Double.parseDouble(txtPrecioCompra.getText().trim().replace(",", ".")));
                pstmt.setInt(4, txtStockDisponible.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtStockDisponible.getText().trim()));
                pstmt.setInt(5, txtStockMinimo.getText().trim().isEmpty() ? 1 : Integer.parseInt(txtStockMinimo.getText().trim()));
                pstmt.setString(6, txtUbicacionAlmacen.getText().trim());
                pstmt.setString(7, txtMaterialesCompatibles.getText().trim());
                pstmt.setString(8, txtDescripcion.getText().trim());
                pstmt.setString(9, txtCodigo.getText().trim()); // WHERE codigo_pieza
            } else {
                // INSERT: asignar parámetros
                pstmt.setString(1, txtCodigo.getText().trim().toUpperCase());
                pstmt.setString(2, txtNombre.getText().trim());
                pstmt.setString(3, txtCategoria.getText().trim());
                pstmt.setDouble(4, Double.parseDouble(txtPrecioCompra.getText().trim().replace(",", ".")));
                pstmt.setInt(5, txtStockDisponible.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtStockDisponible.getText().trim()));
                pstmt.setInt(6, txtStockMinimo.getText().trim().isEmpty() ? 1 : Integer.parseInt(txtStockMinimo.getText().trim()));
                pstmt.setString(7, txtUbicacionAlmacen.getText().trim());
                pstmt.setString(8, txtMaterialesCompatibles.getText().trim());
                pstmt.setString(9, null); // imagen (por ahora null)
                pstmt.setString(10, txtDescripcion.getText().trim());
            }

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                String mensaje = modoEdicion ?
                    "Pieza actualizada correctamente" :
                    "Pieza registrada correctamente con código: " + txtCodigo.getText().trim().toUpperCase();

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
                "No se pudo guardar la pieza: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            ValidationUtils.showError("Error de formato",
                "Verifique que los campos numéricos tengan el formato correcto");
        }
    }

    /**
     * Comprueba si ya existe una pieza con el código indicado
     */
    private boolean existeCodigo(String codigo) {
        try (Connection conn = ConexionBD.getConexion()) {
            String sql = "SELECT COUNT(*) FROM PIEZAS WHERE codigo_pieza = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, codigo.toUpperCase());

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
