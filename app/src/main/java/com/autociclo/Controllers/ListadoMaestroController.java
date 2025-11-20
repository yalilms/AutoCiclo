package com.autociclo.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.autociclo.database.ConexionBD;
import com.autociclo.models.Vehiculo;
import com.autociclo.models.Pieza;
import com.autociclo.models.InventarioPieza;

import com.autociclo.utils.ValidationUtils;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ListadoMaestroController implements Initializable {

    // MenuBar
    @FXML private MenuItem menuSalir;
    @FXML private Menu Menu;
    @FXML private MenuItem menuvehiculos;
    @FXML private MenuItem menupiezas;
    @FXML private MenuItem MenuInventario;
    @FXML private MenuItem menuAcercaDe;

    // ToolBar
    @FXML private Button btnNuevo;
    @FXML private Button btnVer;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnActualizar;
    @FXML private TextField txtBuscar;
    @FXML private Button btnBuscar;

    // Navegación
    @FXML private Button btnNavVehiculos;
    @FXML private Button btnNavPiezas;
    @FXML private Button btnNavInventario;

    // StackPane y TableViews
    @FXML private StackPane stackPaneContenido;
    @FXML private TableView<Vehiculo> tableVehiculos;
    @FXML private TableView<Pieza> tablePiezas;
    @FXML private TableView<InventarioPieza> tableInventario;

    // Columnas TableView Vehiculos
    @FXML private TableColumn<Vehiculo, Integer> colVehiculoId;
    @FXML private TableColumn<Vehiculo, String> colMarca;
    @FXML private TableColumn<Vehiculo, String> colModelo;
    @FXML private TableColumn<Vehiculo, Integer> colAnio;
    @FXML private TableColumn<Vehiculo, Integer> colKm;
    @FXML private TableColumn<Vehiculo, String> colEstado;

    // Columnas TableView Piezas
    @FXML private TableColumn<Pieza, Integer> colPiezaId;
    @FXML private TableColumn<Pieza, String> colCodigo;
    @FXML private TableColumn<Pieza, String> colNombre;
    @FXML private TableColumn<Pieza, String> colCategoria;
    @FXML private TableColumn<Pieza, Double> colPrecio;
    @FXML private TableColumn<Pieza, Integer> colStock;
    @FXML private TableColumn<Pieza, String> colUbicacion;

    // Columnas TableView Inventario
    @FXML private TableColumn<InventarioPieza, String> colInventarioId;
    @FXML private TableColumn<InventarioPieza, String> colProducto;
    @FXML private TableColumn<InventarioPieza, Integer> colCantidad;
    @FXML private TableColumn<InventarioPieza, String> colFechaIngreso;
    @FXML private TableColumn<InventarioPieza, String> colAlmacen;

    // Botones inferior
    @FXML private Button btnAnterior;
    @FXML private Button btnSiguiente;

    // Listas observables
    private ObservableList<Vehiculo> listaVehiculos = FXCollections.observableArrayList();
    private ObservableList<Pieza> listaPiezas = FXCollections.observableArrayList();
    private ObservableList<InventarioPieza> listaInventario = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configurar columnas de TableView Vehículos
        colVehiculoId.setCellValueFactory(new PropertyValueFactory<>("idVehiculo"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colAnio.setCellValueFactory(new PropertyValueFactory<>("anio"));
        colKm.setCellValueFactory(new PropertyValueFactory<>("kilometraje"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Configurar columnas de TableView Piezas
        colPiezaId.setCellValueFactory(new PropertyValueFactory<>("idPieza"));
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoPieza"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stockDisponible"));
        colUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacionAlmacen"));

        // Configurar columnas de TableView Inventario
        colInventarioId.setCellValueFactory(new PropertyValueFactory<>("vehiculoInfo"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("piezaNombre"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colFechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaExtraccion"));
        colAlmacen.setCellValueFactory(new PropertyValueFactory<>("estadoPieza"));

        // Cargar datos desde la base de datos
        cargarVehiculos();
        cargarPiezas();
        cargarInventario();

        // Asignar listas a los TableViews
        tableVehiculos.setItems(listaVehiculos);
        tablePiezas.setItems(listaPiezas);
        tableInventario.setItems(listaInventario);

        // Mostrar vehículos por defecto al iniciar
        mostrarVehiculos();

        // Conectar eventos de botones del toolbar
        btnNuevo.setOnAction(event -> abrirFormularioNuevo());
        btnEditar.setOnAction(event -> editarRegistro());
        btnEliminar.setOnAction(event -> eliminarRegistro());
        btnActualizar.setOnAction(event -> actualizarListado());
    }

    private void cargarVehiculos() {
        listaVehiculos.clear();
        String sql = "SELECT * FROM VEHICULOS";

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
            System.out.println("Vehículos cargados: " + listaVehiculos.size());
        } catch (Exception e) {
            System.err.println("Error al cargar vehículos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarPiezas() {
        listaPiezas.clear();
        String sql = "SELECT p.*, COALESCE(SUM(ip.cantidad), 0) as stock_real " +
                     "FROM PIEZAS p " +
                     "LEFT JOIN INVENTARIO_PIEZAS ip ON p.id_pieza = ip.id_pieza " +
                     "GROUP BY p.id_pieza";

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
                    rs.getInt("stock_real"),
                    rs.getInt("stock_minimo"),
                    rs.getString("ubicacion_almacen"),
                    rs.getString("compatible_marcas"),
                    rs.getString("imagen"),
                    rs.getString("descripcion")
                );
                listaPiezas.add(p);
            }
            System.out.println("Piezas cargadas: " + listaPiezas.size());
        } catch (Exception e) {
            System.err.println("Error al cargar piezas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarInventario() {
        listaInventario.clear();
        String sql = "SELECT ip.*, " +
                     "CONCAT(v.marca, ' ', v.modelo, ' (', v.anio, ')') as vehiculo_info, " +
                     "p.nombre as pieza_nombre " +
                     "FROM INVENTARIO_PIEZAS ip " +
                     "INNER JOIN VEHICULOS v ON ip.id_vehiculo = v.id_vehiculo " +
                     "INNER JOIN PIEZAS p ON ip.id_pieza = p.id_pieza";

        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                InventarioPieza inv = new InventarioPieza(
                    rs.getInt("id_vehiculo"),
                    rs.getInt("id_pieza"),
                    rs.getString("vehiculo_info"),
                    rs.getString("pieza_nombre"),
                    rs.getInt("cantidad"),
                    rs.getString("estado_pieza"),
                    rs.getString("fecha_extraccion"),
                    rs.getDouble("precio_unitario"),
                    rs.getString("notas")
                );
                listaInventario.add(inv);
            }
            System.out.println("Inventario cargado: " + listaInventario.size());
        } catch (Exception e) {
            System.err.println("Error al cargar inventario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void mostrarVehiculos() {
        tableVehiculos.setVisible(true);
        tablePiezas.setVisible(false);
        tableInventario.setVisible(false);

        // Cambiar estilos de botones de navegación
        btnNavVehiculos.getStyleClass().remove("nav-button-inactive");
        btnNavVehiculos.getStyleClass().add("nav-button-active");
        btnNavPiezas.getStyleClass().remove("nav-button-active");
        btnNavPiezas.getStyleClass().add("nav-button-inactive");
        btnNavInventario.getStyleClass().remove("nav-button-active");
        btnNavInventario.getStyleClass().add("nav-button-inactive");
    }

    @FXML
    public void mostrarPiezas() {
        tableVehiculos.setVisible(false);
        tablePiezas.setVisible(true);
        tableInventario.setVisible(false);

        // Cambiar estilos de botones de navegación
        btnNavVehiculos.getStyleClass().remove("nav-button-active");
        btnNavVehiculos.getStyleClass().add("nav-button-inactive");
        btnNavPiezas.getStyleClass().remove("nav-button-inactive");
        btnNavPiezas.getStyleClass().add("nav-button-active");
        btnNavInventario.getStyleClass().remove("nav-button-active");
        btnNavInventario.getStyleClass().add("nav-button-inactive");
    }

    @FXML
    public void mostrarInventario() {
        tableVehiculos.setVisible(false);
        tablePiezas.setVisible(false);
        tableInventario.setVisible(true);

        // Cambiar estilos de botones de navegación
        btnNavVehiculos.getStyleClass().remove("nav-button-active");
        btnNavVehiculos.getStyleClass().add("nav-button-inactive");
        btnNavPiezas.getStyleClass().remove("nav-button-active");
        btnNavPiezas.getStyleClass().add("nav-button-inactive");
        btnNavInventario.getStyleClass().remove("nav-button-inactive");
        btnNavInventario.getStyleClass().add("nav-button-active");
    }

    // Métodos para abrir formularios como ventanas modales
    private void abrirFormularioNuevo() {
        try {
            String fxmlFile = "";
            String titulo = "";

            // Determinar qué tabla está visible y abrir el formulario correspondiente
            if (tableVehiculos.isVisible()) {
                fxmlFile = "/fxml/FormularioVehiculo.fxml";
                titulo = "Nuevo Vehículo";
            } else if (tablePiezas.isVisible()) {
                fxmlFile = "/fxml/FormularioPieza.fxml";
                titulo = "Nueva Pieza";
            } else if (tableInventario.isVisible()) {
                fxmlFile = "/fxml/AsignarPiezaVehiculo.fxml";
                titulo = "Asignar Pieza a Vehículo";
            }

            // Cargar el FXML del formulario
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Pasar referencia del controlador padre
            if (tableVehiculos.isVisible()) {
                FormularioVehiculoController controller = loader.getController();
                controller.setControladorPadre(this);
            } else if (tablePiezas.isVisible()) {
                FormularioPiezaController controller = loader.getController();
                controller.setControladorPadre(this);
            } else if (tableInventario.isVisible()) {
                AsignarPiezaVehiculoController controller = loader.getController();
                controller.setControladorPadre(this);
            }

            // Crear una nueva ventana modal
            Stage modalStage = new Stage();
            modalStage.setTitle(titulo);
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(btnNuevo.getScene().getWindow());

            Scene scene = new Scene(root);
            modalStage.setScene(scene);
            modalStage.setResizable(false);

            // Mostrar la ventana y esperar a que se cierre
            modalStage.showAndWait();

        } catch (Exception e) {
            System.err.println("Error al abrir formulario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Edita el registro seleccionado en la tabla visible
     */
    private void editarRegistro() {
        try {
            if (tableVehiculos.isVisible()) {
                // Editar vehículo
                Vehiculo vehiculoSeleccionado = tableVehiculos.getSelectionModel().getSelectedItem();

                if (vehiculoSeleccionado == null) {
                    ValidationUtils.showAlert("Selección requerida",
                        "Por favor seleccione un vehículo",
                        "Debe seleccionar un vehículo de la tabla para editarlo",
                        Alert.AlertType.WARNING);
                    return;
                }

                // Cargar formulario de vehículo en modo edición
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormularioVehiculo.fxml"));
                Parent root = loader.load();

                FormularioVehiculoController controller = loader.getController();
                controller.setControladorPadre(this);
                controller.setVehiculoEditar(vehiculoSeleccionado);

                Stage modalStage = new Stage();
                modalStage.setTitle("Editar Vehículo");
                modalStage.initModality(Modality.APPLICATION_MODAL);
                modalStage.initOwner(btnEditar.getScene().getWindow());

                Scene scene = new Scene(root);
                modalStage.setScene(scene);
                modalStage.setResizable(false);
                modalStage.showAndWait();

            } else if (tablePiezas.isVisible()) {
                // Editar pieza
                Pieza piezaSeleccionada = tablePiezas.getSelectionModel().getSelectedItem();

                if (piezaSeleccionada == null) {
                    ValidationUtils.showAlert("Selección requerida",
                        "Por favor seleccione una pieza",
                        "Debe seleccionar una pieza de la tabla para editarla",
                        Alert.AlertType.WARNING);
                    return;
                }

                // Cargar formulario de pieza en modo edición
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormularioPieza.fxml"));
                Parent root = loader.load();

                FormularioPiezaController controller = loader.getController();
                controller.setControladorPadre(this);
                controller.setPiezaEditar(piezaSeleccionada);

                Stage modalStage = new Stage();
                modalStage.setTitle("Editar Pieza");
                modalStage.initModality(Modality.APPLICATION_MODAL);
                modalStage.initOwner(btnEditar.getScene().getWindow());

                Scene scene = new Scene(root);
                modalStage.setScene(scene);
                modalStage.setResizable(false);
                modalStage.showAndWait();

            } else if (tableInventario.isVisible()) {
                // Editar asignación de inventario
                InventarioPieza inventarioSeleccionado = tableInventario.getSelectionModel().getSelectedItem();

                if (inventarioSeleccionado == null) {
                    ValidationUtils.showAlert("Selección requerida",
                        "Por favor seleccione una asignación",
                        "Debe seleccionar una asignación de la tabla para editarla",
                        Alert.AlertType.WARNING);
                    return;
                }

                // Cargar formulario de inventario en modo edición
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AsignarPiezaVehiculo.fxml"));
                Parent root = loader.load();

                AsignarPiezaVehiculoController controller = loader.getController();
                controller.setControladorPadre(this);
                controller.setInventarioEditar(inventarioSeleccionado);

                Stage modalStage = new Stage();
                modalStage.setTitle("Editar Asignación");
                modalStage.initModality(Modality.APPLICATION_MODAL);
                modalStage.initOwner(btnEditar.getScene().getWindow());

                Scene scene = new Scene(root);
                modalStage.setScene(scene);
                modalStage.setResizable(false);
                modalStage.showAndWait();
            }

        } catch (Exception e) {
            ValidationUtils.showError("Error al editar",
                "No se pudo abrir el formulario de edición: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Elimina el registro seleccionado con confirmación
     */
    private void eliminarRegistro() {
        try {
            if (tableVehiculos.isVisible()) {
                // Eliminar vehículo
                Vehiculo vehiculoSeleccionado = tableVehiculos.getSelectionModel().getSelectedItem();

                if (vehiculoSeleccionado == null) {
                    ValidationUtils.showAlert("Selección requerida",
                        "Por favor seleccione un vehículo",
                        "Debe seleccionar un vehículo de la tabla para eliminarlo",
                        Alert.AlertType.WARNING);
                    return;
                }

                // Confirmar eliminación
                boolean confirmar = ValidationUtils.showConfirmation(
                    "Confirmar eliminación",
                    "¿Está seguro de que desea eliminar este vehículo?",
                    "Vehículo: " + vehiculoSeleccionado.getMarca() + " " +
                    vehiculoSeleccionado.getModelo() + " (" + vehiculoSeleccionado.getMatricula() + ")\n" +
                    "Esta acción no se puede deshacer y eliminará también las piezas asociadas."
                );

                if (confirmar) {
                    eliminarVehiculo(vehiculoSeleccionado.getIdVehiculo());
                }

            } else if (tablePiezas.isVisible()) {
                // Eliminar pieza
                Pieza piezaSeleccionada = tablePiezas.getSelectionModel().getSelectedItem();

                if (piezaSeleccionada == null) {
                    ValidationUtils.showAlert("Selección requerida",
                        "Por favor seleccione una pieza",
                        "Debe seleccionar una pieza de la tabla para eliminarla",
                        Alert.AlertType.WARNING);
                    return;
                }

                // Confirmar eliminación
                boolean confirmar = ValidationUtils.showConfirmation(
                    "Confirmar eliminación",
                    "¿Está seguro de que desea eliminar esta pieza?",
                    "Pieza: " + piezaSeleccionada.getNombre() + " (" + piezaSeleccionada.getCodigoPieza() + ")\n" +
                    "Esta acción no se puede deshacer y eliminará también las asignaciones en inventario."
                );

                if (confirmar) {
                    eliminarPieza(piezaSeleccionada.getIdPieza());
                }

            } else if (tableInventario.isVisible()) {
                // Eliminar asignación de inventario
                InventarioPieza inventarioSeleccionado = tableInventario.getSelectionModel().getSelectedItem();

                if (inventarioSeleccionado == null) {
                    ValidationUtils.showAlert("Selección requerida",
                        "Por favor seleccione una asignación",
                        "Debe seleccionar una asignación de la tabla para eliminarla",
                        Alert.AlertType.WARNING);
                    return;
                }

                // Confirmar eliminación
                boolean confirmar = ValidationUtils.showConfirmation(
                    "Confirmar eliminación",
                    "¿Está seguro de que desea eliminar esta asignación?",
                    "Pieza: " + inventarioSeleccionado.getPiezaNombre() + "\n" +
                    "Vehículo: " + inventarioSeleccionado.getVehiculoInfo() + "\n" +
                    "Esta acción no se puede deshacer."
                );

                if (confirmar) {
                    eliminarInventario(inventarioSeleccionado.getIdVehiculo(), inventarioSeleccionado.getIdPieza());
                }
            }

        } catch (Exception e) {
            ValidationUtils.showError("Error al eliminar",
                "No se pudo eliminar el registro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Elimina un vehículo de la base de datos
     */
    private void eliminarVehiculo(int idVehiculo) {
        try (Connection conn = ConexionBD.getConexion()) {
            // DELETE con PreparedStatement (antiinyección SQL)
            String sql = "DELETE FROM VEHICULOS WHERE id_vehiculo = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idVehiculo);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                ValidationUtils.showSuccess("Vehículo eliminado",
                    "El vehículo ha sido eliminado correctamente del sistema");
                actualizarListado();
            }

        } catch (SQLException e) {
            ValidationUtils.showError("Error de base de datos",
                "No se pudo eliminar el vehículo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Elimina una pieza de la base de datos
     */
    private void eliminarPieza(int idPieza) {
        try (Connection conn = ConexionBD.getConexion()) {
            // DELETE con PreparedStatement (antiinyección SQL)
            String sql = "DELETE FROM PIEZAS WHERE id_pieza = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idPieza);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                ValidationUtils.showSuccess("Pieza eliminada",
                    "La pieza ha sido eliminada correctamente del sistema");
                actualizarListado();
            }

        } catch (SQLException e) {
            ValidationUtils.showError("Error de base de datos",
                "No se pudo eliminar la pieza: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Elimina una asignación de inventario de la base de datos
     */
    private void eliminarInventario(int idVehiculo, int idPieza) {
        try (Connection conn = ConexionBD.getConexion()) {
            // DELETE con PreparedStatement (antiinyección SQL)
            String sql = "DELETE FROM INVENTARIO_PIEZAS WHERE id_vehiculo = ? AND id_pieza = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idVehiculo);
            pstmt.setInt(2, idPieza);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                ValidationUtils.showSuccess("Asignación eliminada",
                    "La asignación ha sido eliminada correctamente del inventario");
                actualizarListado();
            }

        } catch (SQLException e) {
            ValidationUtils.showError("Error de base de datos",
                "No se pudo eliminar la asignación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void actualizarListado() {
        // Recargar los datos según la tabla visible
        if (tableVehiculos.isVisible()) {
            cargarVehiculos();
        } else if (tablePiezas.isVisible()) {
            cargarPiezas();
        } else if (tableInventario.isVisible()) {
            cargarInventario();
        }
    }
}
