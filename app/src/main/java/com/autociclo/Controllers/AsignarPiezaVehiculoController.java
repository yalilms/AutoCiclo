package com.autociclo.controllers;

import com.autociclo.database.ConexionBD;
import com.autociclo.models.InventarioPieza;
import com.autociclo.models.Vehiculo;
import com.autociclo.models.Pieza;
import com.autociclo.utils.ValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Controlador para asignar piezas a vehículos (Inventario)
 * @author Yalil Musa Talhaoui
 */
public class AsignarPiezaVehiculoController implements Initializable {

    @FXML private ComboBox<Vehiculo> cmbVehiculo;
    @FXML private ComboBox<Pieza> cmbPieza;
    @FXML private TextField txtCantidad;
    @FXML private RadioButton rbNuevo;
    @FXML private RadioButton rbUsado;
    @FXML private RadioButton rbReparado;
    @FXML private ToggleGroup grupoContenedor;
    @FXML private TextField txtPrecioMecanico;
    @FXML private DatePicker dpFechaAsignacion;
    @FXML private Button btnCalendario;
    @FXML private TextArea txtNotas;
    @FXML private Button btnCancelar;
    @FXML private Button btnAsignar;

    // Variables para el modo edición
    private InventarioPieza inventarioEditar = null;
    private boolean modoEdicion = false;
    private ListadoMaestroController controladorPadre;

    // Listas para ComboBoxes
    private ObservableList<Vehiculo> listaVehiculos = FXCollections.observableArrayList();
    private ObservableList<Pieza> listaPiezas = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar el ToggleGroup para los RadioButtons
        grupoContenedor = new ToggleGroup();
        rbNuevo.setToggleGroup(grupoContenedor);
        rbUsado.setToggleGroup(grupoContenedor);
        rbReparado.setToggleGroup(grupoContenedor);

        // Seleccionar "Usado" por defecto
        rbUsado.setSelected(true);

        // Establecer fecha actual por defecto
        dpFechaAsignacion.setValue(LocalDate.now());

        // Conectar eventos de los botones
        btnAsignar.setOnAction(event -> guardarInventario());
        btnCancelar.setOnAction(event -> cerrarVentana());
        btnCalendario.setOnAction(event -> dpFechaAsignacion.show());

        // Cargar listas de vehículos y piezas
        cargarVehiculos();
        cargarPiezas();
    }

    /**
     * Configura el controlador padre para poder actualizar el listado
     */
    public void setControladorPadre(ListadoMaestroController controlador) {
        this.controladorPadre = controlador;
    }

    /**
     * Configura el inventario a editar y rellena los campos
     */
    public void setInventarioEditar(InventarioPieza inventario) {
        this.inventarioEditar = inventario;
        this.modoEdicion = true;
        cargarDatosInventario();
    }

    /**
     * Carga los vehículos disponibles en el ComboBox
     */
    private void cargarVehiculos() {
        listaVehiculos.clear();
        String sql = "SELECT * FROM VEHICULOS ORDER BY marca, modelo";

        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Vehiculo v = new Vehiculo(
                    rs.getInt("id_vehiculo"),
                    rs.getString("matricula"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getInt("anio"),
                    rs.getString("color"),
                    rs.getString("fecha_entrada"),
                    rs.getString("estado"),
                    rs.getDouble("precio_compra"),
                    rs.getInt("kilometraje"),
                    rs.getString("ubicacion_gps"),
                    rs.getString("observaciones")
                );
                listaVehiculos.add(v);
            }

            cmbVehiculo.setItems(listaVehiculos);

        } catch (Exception e) {
            System.err.println("Error al cargar vehículos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga las piezas disponibles en el ComboBox
     */
    private void cargarPiezas() {
        listaPiezas.clear();
        String sql = "SELECT * FROM PIEZAS ORDER BY nombre";

        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pieza p = new Pieza(
                    rs.getInt("id_pieza"),
                    rs.getString("codigo_pieza"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio_venta"),
                    rs.getInt("stock_disponible"),
                    rs.getInt("stock_minimo"),
                    rs.getString("ubicacion_almacen"),
                    rs.getString("compatible_marcas"),
                    rs.getString("imagen"),
                    rs.getString("descripcion")
                );
                listaPiezas.add(p);
            }

            cmbPieza.setItems(listaPiezas);

        } catch (Exception e) {
            System.err.println("Error al cargar piezas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Carga los datos del inventario en el formulario
     */
    private void cargarDatosInventario() {
        if (inventarioEditar != null) {
            // Buscar y seleccionar el vehículo en el ComboBox
            for (Vehiculo v : listaVehiculos) {
                if (v.getIdVehiculo() == inventarioEditar.getIdVehiculo()) {
                    cmbVehiculo.setValue(v);
                    cmbVehiculo.setDisable(true); // No permitir cambiar en edición
                    break;
                }
            }

            // Buscar y seleccionar la pieza en el ComboBox
            for (Pieza p : listaPiezas) {
                if (p.getIdPieza() == inventarioEditar.getIdPieza()) {
                    cmbPieza.setValue(p);
                    cmbPieza.setDisable(true); // No permitir cambiar en edición
                    break;
                }
            }

            txtCantidad.setText(String.valueOf(inventarioEditar.getCantidad()));

            // Seleccionar el estado correspondiente
            String estado = inventarioEditar.getEstadoPieza();
            if ("Nuevo".equalsIgnoreCase(estado)) {
                rbNuevo.setSelected(true);
            } else if ("Usado".equalsIgnoreCase(estado)) {
                rbUsado.setSelected(true);
            } else if ("Reparado".equalsIgnoreCase(estado)) {
                rbReparado.setSelected(true);
            }

            txtPrecioMecanico.setText(String.valueOf(inventarioEditar.getPrecioUnitario()));

            // Parsear fecha
            try {
                dpFechaAsignacion.setValue(LocalDate.parse(inventarioEditar.getFechaExtraccion()));
            } catch (Exception e) {
                dpFechaAsignacion.setValue(LocalDate.now());
            }

            txtNotas.setText(inventarioEditar.getNotas());

            // Cambiar texto del botón
            btnAsignar.setText("Actualizar");
        }
    }

    /**
     * Valida todos los campos del formulario
     */
    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();
        boolean valido = true;

        // Validar vehículo seleccionado
        if (!ValidationUtils.validateComboBox(cmbVehiculo, null, "un vehículo")) {
            errores.append("• Vehículo: Debe seleccionar un vehículo\n");
            valido = false;
        }

        // Validar pieza seleccionada
        if (!ValidationUtils.validateComboBox(cmbPieza, null, "una pieza")) {
            errores.append("• Pieza: Debe seleccionar una pieza\n");
            valido = false;
        }

        // Validar cantidad (entero >= 1)
        if (!ValidationUtils.validateIntegerRange(txtCantidad, null, "Cantidad", 1, 9999)) {
            errores.append("• Cantidad: Debe estar entre 1 y 9999\n");
            valido = false;
        }

        // Validar estado seleccionado
        if (grupoContenedor.getSelectedToggle() == null) {
            errores.append("• Estado: Debe seleccionar Nuevo, Usado o Reparado\n");
            valido = false;
        }

        // Validar precio unitario (decimal >= 0)
        if (!ValidationUtils.validateDoubleMinimum(txtPrecioMecanico, null, "Precio unitario", 0)) {
            errores.append("• Precio unitario: Debe ser un número mayor o igual a 0\n");
            valido = false;
        }

        // Validar fecha
        if (dpFechaAsignacion.getValue() == null) {
            errores.append("• Fecha: Debe seleccionar una fecha de extracción\n");
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
     * Guarda la asignación de inventario en la base de datos
     */
    private void guardarInventario() {
        // Validar campos (el Alert de errores ya se muestra dentro de validarCampos())
        if (!validarCampos()) {
            return;
        }

        // Obtener valores
        int idVehiculo = cmbVehiculo.getValue().getIdVehiculo();
        int idPieza = cmbPieza.getValue().getIdPieza();
        int cantidad = Integer.parseInt(txtCantidad.getText().trim());

        String estado = "";
        if (rbNuevo.isSelected()) estado = "Nuevo";
        else if (rbUsado.isSelected()) estado = "Usado";
        else if (rbReparado.isSelected()) estado = "Reparado";

        double precioUnitario = Double.parseDouble(txtPrecioMecanico.getText().trim().replace(",", "."));
        LocalDate fechaExtraccion = dpFechaAsignacion.getValue();
        String notas = txtNotas.getText().trim();

        // Comprobar si ya existe la asignación (solo en modo inserción)
        if (!modoEdicion && existeAsignacion(idVehiculo, idPieza)) {
            ValidationUtils.showError("Asignación duplicada",
                "Ya existe esta pieza asignada a este vehículo.\n" +
                "Use la opción Editar para modificar la cantidad o estado.");
            return;
        }

        try (Connection conn = ConexionBD.getConexion()) {
            String sql;

            if (modoEdicion) {
                // UPDATE con PreparedStatement (antiinyección SQL)
                sql = "UPDATE INVENTARIO_PIEZAS SET cantidad=?, estado_pieza=?, " +
                      "fecha_extraccion=?, precio_unitario=?, notas=? " +
                      "WHERE id_vehiculo=? AND id_pieza=?";
            } else {
                // INSERT con PreparedStatement (antiinyección SQL)
                sql = "INSERT INTO INVENTARIO_PIEZAS (id_vehiculo, id_pieza, cantidad, " +
                      "estado_pieza, fecha_extraccion, precio_unitario, notas) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?)";
            }

            PreparedStatement pstmt = conn.prepareStatement(sql);

            if (modoEdicion) {
                // UPDATE: asignar parámetros
                pstmt.setInt(1, cantidad);
                pstmt.setString(2, estado);
                pstmt.setDate(3, Date.valueOf(fechaExtraccion));
                pstmt.setDouble(4, precioUnitario);
                pstmt.setString(5, notas);
                pstmt.setInt(6, idVehiculo);
                pstmt.setInt(7, idPieza);
            } else {
                // INSERT: asignar parámetros
                pstmt.setInt(1, idVehiculo);
                pstmt.setInt(2, idPieza);
                pstmt.setInt(3, cantidad);
                pstmt.setString(4, estado);
                pstmt.setDate(5, Date.valueOf(fechaExtraccion));
                pstmt.setDouble(6, precioUnitario);
                pstmt.setString(7, notas);
            }

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                String mensaje = modoEdicion ?
                    "Asignación actualizada correctamente" :
                    "Pieza asignada correctamente al vehículo";

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
                "No se pudo guardar la asignación: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            ValidationUtils.showError("Error de formato",
                "Verifique que los campos numéricos tengan el formato correcto");
        }
    }

    /**
     * Comprueba si ya existe la asignación de esa pieza a ese vehículo
     */
    private boolean existeAsignacion(int idVehiculo, int idPieza) {
        try (Connection conn = ConexionBD.getConexion()) {
            String sql = "SELECT COUNT(*) FROM INVENTARIO_PIEZAS WHERE id_vehiculo = ? AND id_pieza = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idVehiculo);
            pstmt.setInt(2, idPieza);

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
