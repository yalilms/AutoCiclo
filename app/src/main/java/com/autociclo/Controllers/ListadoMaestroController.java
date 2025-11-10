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

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
    @FXML private TableColumn<InventarioPieza, Integer> colInventarioId;
    @FXML private TableColumn<InventarioPieza, Integer> colProducto;
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
        colInventarioId.setCellValueFactory(new PropertyValueFactory<>("idVehiculo"));
        colProducto.setCellValueFactory(new PropertyValueFactory<>("idPieza"));
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
        String sql = "SELECT * FROM PIEZAS";

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
            System.out.println("Piezas cargadas: " + listaPiezas.size());
        } catch (Exception e) {
            System.err.println("Error al cargar piezas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cargarInventario() {
        listaInventario.clear();
        String sql = "SELECT * FROM INVENTARIO_PIEZAS";

        try (Connection conn = ConexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                InventarioPieza inv = new InventarioPieza(
                    rs.getInt("id_vehiculo"),
                    rs.getInt("id_pieza"),
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
                fxmlFile = "/fxml/FormularioInventario.fxml";
                titulo = "Nuevo Inventario";
            }

            // Cargar el FXML del formulario
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));

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

            // Actualizar el listado después de cerrar el formulario
            actualizarListado();

        } catch (Exception e) {
            System.err.println("Error al abrir formulario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void actualizarListado() {
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
