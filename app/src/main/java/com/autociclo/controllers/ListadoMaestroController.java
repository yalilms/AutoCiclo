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
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;

import com.autociclo.database.ConexionBD;
import com.autociclo.models.Vehiculo;
import com.autociclo.models.Pieza;
import com.autociclo.models.InventarioPieza;

import com.autociclo.utils.ValidationUtils;

import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignD;
import org.kordamp.ikonli.materialdesign2.MaterialDesignE;
import org.kordamp.ikonli.materialdesign2.MaterialDesignR;
import org.kordamp.ikonli.materialdesign2.MaterialDesignM;


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

    // Listas filtradas para búsqueda
    private ObservableList<Vehiculo> listaVehiculosFiltrada = FXCollections.observableArrayList();
    private ObservableList<Pieza> listaPiezasFiltrada = FXCollections.observableArrayList();
    private ObservableList<InventarioPieza> listaInventarioFiltrada = FXCollections.observableArrayList();

    // Variables de paginación
    private int paginaActual = 0;
    private static final int ELEMENTOS_POR_PAGINA = 10;

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

        // Mostrar vehículos por defecto al iniciar
        mostrarVehiculos();

        // Configurar botones de paginación
        btnAnterior.setOnAction(event -> paginaAnterior());
        btnSiguiente.setOnAction(event -> paginaSiguiente());
        actualizarBotonesPaginacion();

        // Conectar eventos de botones del toolbar
        btnNuevo.setOnAction(event -> abrirFormularioNuevo());
        btnVer.setOnAction(event -> verDetallesRegistro());
        btnEditar.setOnAction(event -> editarRegistro());
        btnEliminar.setOnAction(event -> eliminarRegistro());
        btnActualizar.setOnAction(event -> {
            animarBotonActualizar();
            actualizarListado();
        });

        // Configurar menús
        menuSalir.setOnAction(event -> salirAplicacion());
        menuAcercaDe.setOnAction(event -> mostrarAcercaDe());

        // Configurar búsqueda en tiempo real
        configurarBusqueda();

        // Configurar iconos de Ikonli
        configurarIconos();

        // ENTREGA 3: Configurar eventos de teclado y ratón
        configurarEventosTeclado();
        configurarEventosRaton();
        configurarMenusContextuales();
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

    /**
     * Configura la búsqueda en tiempo real para todas las tablas
     */
    private void configurarBusqueda() {
        // Listener para búsqueda en tiempo real
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarTablaActual(newValue);
        });

        // Botón de búsqueda también ejecuta el filtro
        btnBuscar.setOnAction(event -> {
            filtrarTablaActual(txtBuscar.getText());
        });

        // Limpiar búsqueda al presionar ESC
        txtBuscar.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                txtBuscar.clear();
                filtrarTablaActual("");
            }
        });
    }

    /**
     * Configura los iconos de Ikonli para los botones de la aplicación
     */
    private void configurarIconos() {
        // Iconos para botones del toolbar (colores blancos para mejor contraste con los fondos de botón)
        FontIcon iconNuevo = new FontIcon(MaterialDesignP.PLUS_CIRCLE);
        iconNuevo.setIconSize(16);
        iconNuevo.setIconColor(javafx.scene.paint.Color.WHITE); // Blanco para contraste con fondo verde
        btnNuevo.setGraphic(iconNuevo);
        btnNuevo.setText("Nuevo");

        FontIcon iconVer = new FontIcon(MaterialDesignE.EYE);
        iconVer.setIconSize(16);
        iconVer.setIconColor(javafx.scene.paint.Color.WHITE); // Blanco para contraste con fondo azul
        btnVer.setGraphic(iconVer);
        btnVer.setText("Ver");

        FontIcon iconEditar = new FontIcon(MaterialDesignP.PENCIL);
        iconEditar.setIconSize(16);
        iconEditar.setIconColor(javafx.scene.paint.Color.WHITE); // Blanco para contraste con fondo azul
        btnEditar.setGraphic(iconEditar);
        btnEditar.setText("Editar");

        FontIcon iconEliminar = new FontIcon(MaterialDesignD.DELETE);
        iconEliminar.setIconSize(16);
        iconEliminar.setIconColor(javafx.scene.paint.Color.WHITE); // Blanco para contraste con fondo rojo
        btnEliminar.setGraphic(iconEliminar);
        btnEliminar.setText("Eliminar");

        FontIcon iconActualizar = new FontIcon(MaterialDesignR.REFRESH);
        iconActualizar.setIconSize(16);
        iconActualizar.setIconColor(javafx.scene.paint.Color.WHITE); // Blanco para contraste
        btnActualizar.setGraphic(iconActualizar);
        btnActualizar.setText("Actualizar");

        FontIcon iconBuscar = new FontIcon(MaterialDesignM.MAGNIFY);
        iconBuscar.setIconSize(18);
        iconBuscar.setIconColor(javafx.scene.paint.Color.WHITE); // Blanco para contraste con fondo del botón
        btnBuscar.setGraphic(iconBuscar);
        btnBuscar.setText("");

        // Iconos para botones de navegación
        FontIcon iconVehiculos = new FontIcon(MaterialDesignC.CAR);
        iconVehiculos.setIconSize(18);
        iconVehiculos.setIconColor(javafx.scene.paint.Color.WHITE); // Blanco para mejor contraste
        btnNavVehiculos.setGraphic(iconVehiculos);
        btnNavVehiculos.setText("Vehículos");

        FontIcon iconPiezas = new FontIcon(MaterialDesignC.COG);
        iconPiezas.setIconSize(18);
        iconPiezas.setIconColor(javafx.scene.paint.Color.WHITE); // Blanco para mejor contraste
        btnNavPiezas.setGraphic(iconPiezas);
        btnNavPiezas.setText("Piezas");

        FontIcon iconInventario = new FontIcon(MaterialDesignC.CLIPBOARD_LIST);
        iconInventario.setIconSize(18);
        iconInventario.setIconColor(javafx.scene.paint.Color.WHITE); // Blanco para mejor contraste
        btnNavInventario.setGraphic(iconInventario);
        btnNavInventario.setText("Inventario");
    }

    /**
     * Filtra la tabla actualmente visible según el texto de búsqueda
     */
    private void filtrarTablaActual(String textoBusqueda) {
        String busqueda = textoBusqueda.toLowerCase().trim();

        if (tableVehiculos.isVisible()) {
            filtrarVehiculos(busqueda);
        } else if (tablePiezas.isVisible()) {
            filtrarPiezas(busqueda);
        } else if (tableInventario.isVisible()) {
            filtrarInventario(busqueda);
        }

        // Resetear paginación y actualizar
        paginaActual = 0;
        actualizarBotonesPaginacion();
    }

    /**
     * Filtra la lista de vehículos según el texto de búsqueda
     */
    private void filtrarVehiculos(String busqueda) {
        if (busqueda.isEmpty()) {
            listaVehiculosFiltrada.setAll(listaVehiculos);
        } else {
            listaVehiculosFiltrada.clear();
            for (Vehiculo v : listaVehiculos) {
                if (v.getMarca().toLowerCase().contains(busqueda) ||
                    v.getModelo().toLowerCase().contains(busqueda) ||
                    v.getMatricula().toLowerCase().contains(busqueda) ||
                    v.getColor().toLowerCase().contains(busqueda) ||
                    v.getEstado().toLowerCase().contains(busqueda) ||
                    String.valueOf(v.getAnio()).contains(busqueda)) {
                    listaVehiculosFiltrada.add(v);
                }
            }
        }
        actualizarTablaVehiculos();
    }

    /**
     * Filtra la lista de piezas según el texto de búsqueda
     */
    private void filtrarPiezas(String busqueda) {
        if (busqueda.isEmpty()) {
            listaPiezasFiltrada.setAll(listaPiezas);
        } else {
            listaPiezasFiltrada.clear();
            for (Pieza p : listaPiezas) {
                if (p.getNombre().toLowerCase().contains(busqueda) ||
                    p.getCodigoPieza().toLowerCase().contains(busqueda) ||
                    p.getCategoria().toLowerCase().contains(busqueda) ||
                    p.getUbicacionAlmacen().toLowerCase().contains(busqueda)) {
                    listaPiezasFiltrada.add(p);
                }
            }
        }
        actualizarTablaPiezas();
    }

    /**
     * Filtra la lista de inventario según el texto de búsqueda
     */
    private void filtrarInventario(String busqueda) {
        if (busqueda.isEmpty()) {
            listaInventarioFiltrada.setAll(listaInventario);
        } else {
            listaInventarioFiltrada.clear();
            for (InventarioPieza inv : listaInventario) {
                if (inv.getVehiculoInfo().toLowerCase().contains(busqueda) ||
                    inv.getPiezaNombre().toLowerCase().contains(busqueda) ||
                    inv.getEstadoPieza().toLowerCase().contains(busqueda) ||
                    inv.getFechaExtraccion().toLowerCase().contains(busqueda)) {
                    listaInventarioFiltrada.add(inv);
                }
            }
        }
        actualizarTablaInventario();
    }

    /**
     * Actualiza la tabla de vehículos con la lista filtrada
     */
    private void actualizarTablaVehiculos() {
        int inicio = paginaActual * ELEMENTOS_POR_PAGINA;
        int fin = Math.min(inicio + ELEMENTOS_POR_PAGINA, listaVehiculosFiltrada.size());

        if (inicio < listaVehiculosFiltrada.size()) {
            tableVehiculos.setItems(FXCollections.observableArrayList(
                listaVehiculosFiltrada.subList(inicio, fin)
            ));
        } else {
            tableVehiculos.setItems(FXCollections.observableArrayList());
        }
    }

    /**
     * Actualiza la tabla de piezas con la lista filtrada
     */
    private void actualizarTablaPiezas() {
        int inicio = paginaActual * ELEMENTOS_POR_PAGINA;
        int fin = Math.min(inicio + ELEMENTOS_POR_PAGINA, listaPiezasFiltrada.size());

        if (inicio < listaPiezasFiltrada.size()) {
            tablePiezas.setItems(FXCollections.observableArrayList(
                listaPiezasFiltrada.subList(inicio, fin)
            ));
        } else {
            tablePiezas.setItems(FXCollections.observableArrayList());
        }
    }

    /**
     * Actualiza la tabla de inventario con la lista filtrada
     */
    private void actualizarTablaInventario() {
        int inicio = paginaActual * ELEMENTOS_POR_PAGINA;
        int fin = Math.min(inicio + ELEMENTOS_POR_PAGINA, listaInventarioFiltrada.size());

        if (inicio < listaInventarioFiltrada.size()) {
            tableInventario.setItems(FXCollections.observableArrayList(
                listaInventarioFiltrada.subList(inicio, fin)
            ));
        } else {
            tableInventario.setItems(FXCollections.observableArrayList());
        }
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
            modalStage.setResizable(true);
            modalStage.setMinWidth(700);
            modalStage.setMinHeight(600);

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
                modalStage.setResizable(true);
                modalStage.setMinWidth(700);
                modalStage.setMinHeight(600);
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
                modalStage.setResizable(true);
                modalStage.setMinWidth(700);
                modalStage.setMinHeight(600);
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
                modalStage.setResizable(true);
                modalStage.setMinWidth(700);
                modalStage.setMinHeight(600);
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

        // Actualizar tabla paginada después de recargar datos
        actualizarTablaPaginada();
        actualizarBotonesPaginacion();
    }

    // ==================================================================================
    // ENTREGA 3: ANIMACIONES
    // ==================================================================================

    /**
     * Anima el botón actualizar con rotación de 360 grados
     */
    private void animarBotonActualizar() {
        RotateTransition rotate = new RotateTransition(Duration.millis(500), btnActualizar);
        rotate.setByAngle(360);
        rotate.setCycleCount(1);
        rotate.play();
    }

    /**
     * Aplica animación FadeTransition al mostrar vehículos
     */
    @FXML
    public void mostrarVehiculos() {
        // Hacer visible la tabla antes de animar
        tableVehiculos.setVisible(true);
        tablePiezas.setVisible(false);
        tableInventario.setVisible(false);

        // Limpiar búsqueda e inicializar lista filtrada
        txtBuscar.clear();
        listaVehiculosFiltrada.setAll(listaVehiculos);

        // Reiniciar paginación
        reiniciarPaginacion();

        // Animación de entrada
        aplicarFadeTransition(tableVehiculos);

        // Cambiar estilos de botones de navegación
        btnNavVehiculos.getStyleClass().clear();
        btnNavVehiculos.getStyleClass().addAll("button", "button-primary");

        btnNavPiezas.getStyleClass().clear();
        btnNavPiezas.getStyleClass().add("button");

        btnNavInventario.getStyleClass().clear();
        btnNavInventario.getStyleClass().add("button");
    }

    /**
     * Aplica animación FadeTransition al mostrar piezas
     */
    @FXML
    public void mostrarPiezas() {
        tableVehiculos.setVisible(false);
        tablePiezas.setVisible(true);
        tableInventario.setVisible(false);

        // Limpiar búsqueda e inicializar lista filtrada
        txtBuscar.clear();
        listaPiezasFiltrada.setAll(listaPiezas);

        // Reiniciar paginación
        reiniciarPaginacion();

        // Animación de entrada
        aplicarFadeTransition(tablePiezas);

        // Cambiar estilos de botones de navegación
        btnNavVehiculos.getStyleClass().clear();
        btnNavVehiculos.getStyleClass().add("button");

        btnNavPiezas.getStyleClass().clear();
        btnNavPiezas.getStyleClass().addAll("button", "button-primary");

        btnNavInventario.getStyleClass().clear();
        btnNavInventario.getStyleClass().add("button");
    }

    /**
     * Aplica animación FadeTransition al mostrar inventario
     */
    @FXML
    public void mostrarInventario() {
        tableVehiculos.setVisible(false);
        tablePiezas.setVisible(false);
        tableInventario.setVisible(true);

        // Limpiar búsqueda e inicializar lista filtrada
        txtBuscar.clear();
        listaInventarioFiltrada.setAll(listaInventario);

        // Reiniciar paginación
        reiniciarPaginacion();

        // Animación de entrada
        aplicarFadeTransition(tableInventario);

        // Cambiar estilos de botones de navegación
        btnNavVehiculos.getStyleClass().clear();
        btnNavVehiculos.getStyleClass().add("button");

        btnNavPiezas.getStyleClass().clear();
        btnNavPiezas.getStyleClass().add("button");

        btnNavInventario.getStyleClass().clear();
        btnNavInventario.getStyleClass().addAll("button", "button-primary");
    }

    /**
     * Método auxiliar para aplicar FadeTransition a cualquier nodo
     */
    private void aplicarFadeTransition(javafx.scene.Node nodo) {
        FadeTransition fade = new FadeTransition(Duration.millis(400), nodo);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.play();
    }

    /**
     * Aplica animación de escala al hacer hover en botones (complemento a CSS)
     */
    private void aplicarAnimacionBotonHover(Button boton) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), boton);
        scaleUp.setToX(1.1);
        scaleUp.setToY(1.1);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100), boton);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        boton.setOnMouseEntered(e -> scaleUp.playFromStart());
        boton.setOnMouseExited(e -> scaleDown.playFromStart());
    }

    // ==================================================================================
    // ENTREGA 3: EVENTOS DE TECLADO
    // ==================================================================================

    /**
     * Configura eventos de teclado para las tres tablas
     */
    private void configurarEventosTeclado() {
        // Eventos para TableView Vehículos
        tableVehiculos.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.E) {
                editarRegistro();
            } else if (event.getCode() == KeyCode.DELETE) {
                eliminarRegistro();
            } else if (event.getCode() == KeyCode.F5) {
                animarBotonActualizar();
                actualizarListado();
            } else if (event.getCode() == KeyCode.N && event.isControlDown()) {
                abrirFormularioNuevo();
            }
        });

        // Eventos para TableView Piezas
        tablePiezas.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.E) {
                editarRegistro();
            } else if (event.getCode() == KeyCode.DELETE) {
                eliminarRegistro();
            } else if (event.getCode() == KeyCode.F5) {
                animarBotonActualizar();
                actualizarListado();
            } else if (event.getCode() == KeyCode.N && event.isControlDown()) {
                abrirFormularioNuevo();
            }
        });

        // Eventos para TableView Inventario
        tableInventario.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.E) {
                editarRegistro();
            } else if (event.getCode() == KeyCode.DELETE) {
                eliminarRegistro();
            } else if (event.getCode() == KeyCode.F5) {
                animarBotonActualizar();
                actualizarListado();
            } else if (event.getCode() == KeyCode.N && event.isControlDown()) {
                abrirFormularioNuevo();
            }
        });

        // Evento Enter en campo de búsqueda
        txtBuscar.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                realizarBusqueda();
            }
        });
    }

    /**
     * Realiza búsqueda en la tabla visible
     */
    private void realizarBusqueda() {
        String textoBusqueda = txtBuscar.getText().toLowerCase().trim();

        if (textoBusqueda.isEmpty()) {
            // Si está vacío, mostrar todos
            actualizarListado();
            return;
        }

        if (tableVehiculos.isVisible()) {
            ObservableList<Vehiculo> filtrados = FXCollections.observableArrayList();
            for (Vehiculo v : listaVehiculos) {
                if (v.getMatricula().toLowerCase().contains(textoBusqueda) ||
                    v.getMarca().toLowerCase().contains(textoBusqueda) ||
                    v.getModelo().toLowerCase().contains(textoBusqueda)) {
                    filtrados.add(v);
                }
            }
            tableVehiculos.setItems(filtrados);
        } else if (tablePiezas.isVisible()) {
            ObservableList<Pieza> filtrados = FXCollections.observableArrayList();
            for (Pieza p : listaPiezas) {
                if (p.getCodigoPieza().toLowerCase().contains(textoBusqueda) ||
                    p.getNombre().toLowerCase().contains(textoBusqueda) ||
                    p.getCategoria().toLowerCase().contains(textoBusqueda)) {
                    filtrados.add(p);
                }
            }
            tablePiezas.setItems(filtrados);
        } else if (tableInventario.isVisible()) {
            ObservableList<InventarioPieza> filtrados = FXCollections.observableArrayList();
            for (InventarioPieza inv : listaInventario) {
                if (inv.getVehiculoInfo().toLowerCase().contains(textoBusqueda) ||
                    inv.getPiezaNombre().toLowerCase().contains(textoBusqueda)) {
                    filtrados.add(inv);
                }
            }
            tableInventario.setItems(filtrados);
        }
    }

    // ==================================================================================
    // ENTREGA 3: EVENTOS DE RATÓN (DOBLE CLIC)
    // ==================================================================================

    /**
     * Configura eventos de doble clic para editar
     */
    private void configurarEventosRaton() {
        // Doble clic en TableView Vehículos
        tableVehiculos.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                if (tableVehiculos.getSelectionModel().getSelectedItem() != null) {
                    editarRegistro();
                }
            }
        });

        // Doble clic en TableView Piezas
        tablePiezas.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                if (tablePiezas.getSelectionModel().getSelectedItem() != null) {
                    editarRegistro();
                }
            }
        });

        // Doble clic en TableView Inventario
        tableInventario.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                if (tableInventario.getSelectionModel().getSelectedItem() != null) {
                    editarRegistro();
                }
            }
        });
    }

    // ==================================================================================
    // ENTREGA 3: MENÚS CONTEXTUALES
    // ==================================================================================

    /**
     * Configura menús contextuales para las tres tablas
     */
    private void configurarMenusContextuales() {
        // Menú contextual para Vehículos
        ContextMenu menuVehiculos = crearMenuContextual();
        tableVehiculos.setContextMenu(menuVehiculos);

        // Menú contextual para Piezas
        ContextMenu menuPiezas = crearMenuContextual();
        tablePiezas.setContextMenu(menuPiezas);

        // Menú contextual para Inventario
        ContextMenu menuInventario = crearMenuContextual();
        tableInventario.setContextMenu(menuInventario);

        // Menú contextual para campos de texto
        ContextMenu menuTexto = crearMenuContextualTexto();
        txtBuscar.setContextMenu(menuTexto);
    }

    /**
     * Crea un menú contextual genérico para las tablas
     */
    private ContextMenu crearMenuContextual() {
        ContextMenu contextMenu = new ContextMenu();

        // Opción: Nuevo
        MenuItem itemNuevo = new MenuItem("➕ Nuevo");
        itemNuevo.setOnAction(e -> abrirFormularioNuevo());

        // Opción: Editar
        MenuItem itemEditar = new MenuItem("✏ Editar");
        itemEditar.setOnAction(e -> editarRegistro());

        // Opción: Eliminar
        MenuItem itemEliminar = new MenuItem("🗑 Eliminar");
        itemEliminar.setOnAction(e -> eliminarRegistro());

        // Separador
        SeparatorMenuItem separador = new SeparatorMenuItem();

        // Opción: Actualizar
        MenuItem itemActualizar = new MenuItem("🔄 Actualizar");
        itemActualizar.setOnAction(e -> {
            animarBotonActualizar();
            actualizarListado();
        });

        contextMenu.getItems().addAll(itemNuevo, itemEditar, itemEliminar, separador, itemActualizar);
        return contextMenu;
    }

    /**
     * Crea un menú contextual para campos de texto
     */
    private ContextMenu crearMenuContextualTexto() {
        ContextMenu contextMenu = new ContextMenu();

        // Opción: Copiar
        MenuItem itemCopiar = new MenuItem("📋 Copiar");
        itemCopiar.setOnAction(e -> txtBuscar.copy());

        // Opción: Pegar
        MenuItem itemPegar = new MenuItem("📌 Pegar");
        itemPegar.setOnAction(e -> txtBuscar.paste());

        // Opción: Cortar
        MenuItem itemCortar = new MenuItem("✂ Cortar");
        itemCortar.setOnAction(e -> txtBuscar.cut());

        // Separador
        SeparatorMenuItem separador = new SeparatorMenuItem();

        // Opción: Seleccionar todo
        MenuItem itemSeleccionar = new MenuItem("🔘 Seleccionar todo");
        itemSeleccionar.setOnAction(e -> txtBuscar.selectAll());

        // Opción: Limpiar
        MenuItem itemLimpiar = new MenuItem("🧹 Limpiar");
        itemLimpiar.setOnAction(e -> {
            txtBuscar.clear();
            actualizarListado();
        });

        contextMenu.getItems().addAll(itemCopiar, itemPegar, itemCortar, separador, itemSeleccionar, itemLimpiar);
        return contextMenu;
    }

    // ==================================================================================
    // PAGINACIÓN CON ANIMACIONES
    // ==================================================================================

    /**
     * Avanza a la página siguiente con animación
     */
    private void paginaSiguiente() {
        int totalPaginas = calcularTotalPaginas();

        if (paginaActual < totalPaginas - 1) {
            paginaActual++;
            aplicarAnimacionCambioPagina(true);
            actualizarTablaPaginada();
            actualizarBotonesPaginacion();
        }
    }

    /**
     * Retrocede a la página anterior con animación
     */
    private void paginaAnterior() {
        if (paginaActual > 0) {
            paginaActual--;
            aplicarAnimacionCambioPagina(false);
            actualizarTablaPaginada();
            actualizarBotonesPaginacion();
        }
    }

    /**
     * Calcula el número total de páginas según la tabla visible
     */
    private int calcularTotalPaginas() {
        int totalElementos = 0;

        if (tableVehiculos.isVisible()) {
            totalElementos = listaVehiculosFiltrada.size();
        } else if (tablePiezas.isVisible()) {
            totalElementos = listaPiezasFiltrada.size();
        } else if (tableInventario.isVisible()) {
            totalElementos = listaInventarioFiltrada.size();
        }

        return (int) Math.ceil((double) totalElementos / ELEMENTOS_POR_PAGINA);
    }

    /**
     * Actualiza la tabla visible con los elementos de la página actual
     */
    private void actualizarTablaPaginada() {
        int inicio = paginaActual * ELEMENTOS_POR_PAGINA;
        int fin = Math.min(inicio + ELEMENTOS_POR_PAGINA, obtenerTotalElementosActual());

        if (tableVehiculos.isVisible()) {
            ObservableList<Vehiculo> paginaActualLista = FXCollections.observableArrayList(
                listaVehiculosFiltrada.subList(inicio, fin)
            );
            tableVehiculos.setItems(paginaActualLista);
        } else if (tablePiezas.isVisible()) {
            ObservableList<Pieza> paginaActualLista = FXCollections.observableArrayList(
                listaPiezasFiltrada.subList(inicio, fin)
            );
            tablePiezas.setItems(paginaActualLista);
        } else if (tableInventario.isVisible()) {
            ObservableList<InventarioPieza> paginaActualLista = FXCollections.observableArrayList(
                listaInventarioFiltrada.subList(inicio, fin)
            );
            tableInventario.setItems(paginaActualLista);
        }
    }

    /**
     * Obtiene el total de elementos de la lista visible
     */
    private int obtenerTotalElementosActual() {
        if (tableVehiculos.isVisible()) {
            return listaVehiculosFiltrada.size();
        } else if (tablePiezas.isVisible()) {
            return listaPiezasFiltrada.size();
        } else if (tableInventario.isVisible()) {
            return listaInventarioFiltrada.size();
        }
        return 0;
    }

    /**
     * Actualiza el estado de los botones de paginación (habilitado/deshabilitado)
     */
    private void actualizarBotonesPaginacion() {
        int totalPaginas = calcularTotalPaginas();

        // Deshabilitar botón Anterior si estamos en la primera página
        btnAnterior.setDisable(paginaActual == 0);

        // Deshabilitar botón Siguiente si estamos en la última página
        btnSiguiente.setDisable(paginaActual >= totalPaginas - 1 || totalPaginas == 0);
    }

    /**
     * Aplica animación de transición al cambiar de página
     * @param adelante true si avanza, false si retrocede
     */
    private void aplicarAnimacionCambioPagina(boolean adelante) {
        TableView<?> tablaVisible = obtenerTablaVisible();

        if (tablaVisible == null) return;

        // Animación de fade out y slide
        FadeTransition fadeOut = new FadeTransition(Duration.millis(150), tablaVisible);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.3);

        // Animación de fade in y slide
        FadeTransition fadeIn = new FadeTransition(Duration.millis(150), tablaVisible);
        fadeIn.setFromValue(0.3);
        fadeIn.setToValue(1.0);

        // Animación de escala
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(150), tablaVisible);
        scaleOut.setFromX(1.0);
        scaleOut.setFromY(1.0);
        scaleOut.setToX(0.95);
        scaleOut.setToY(0.95);

        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(150), tablaVisible);
        scaleIn.setFromX(0.95);
        scaleIn.setFromY(0.95);
        scaleIn.setToX(1.0);
        scaleIn.setToY(1.0);

        // Ejecutar animaciones en secuencia
        fadeOut.setOnFinished(e -> {
            fadeIn.play();
        });
        scaleOut.setOnFinished(e -> {
            scaleIn.play();
        });

        fadeOut.play();
        scaleOut.play();
    }

    /**
     * Obtiene la tabla actualmente visible
     */
    private TableView<?> obtenerTablaVisible() {
        if (tableVehiculos.isVisible()) {
            return tableVehiculos;
        } else if (tablePiezas.isVisible()) {
            return tablePiezas;
        } else if (tableInventario.isVisible()) {
            return tableInventario;
        }
        return null;
    }

    /**
     * Reinicia la paginación al cambiar de tabla
     */
    private void reiniciarPaginacion() {
        paginaActual = 0;
        actualizarTablaPaginada();
        actualizarBotonesPaginacion();
    }

    /**
     * Cierra la aplicación después de pedir confirmación
     */
    private void salirAplicacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Salir de AutoCiclo");
        alert.setHeaderText("¿Está seguro que desea salir?");
        alert.setContentText("Se cerrará la aplicación AutoCiclo - Gestión de Desguace");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Cerrar la ventana actual
                Stage stage = (Stage) tableVehiculos.getScene().getWindow();
                stage.close();

                // Cerrar la aplicación completamente
                System.exit(0);
            }
        });
    }

    /**
     * Muestra el diálogo "Acerca de" con información de la aplicación
     */
    private void mostrarAcercaDe() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de AutoCiclo");
        alert.setHeaderText("AutoCiclo - Sistema de Gestión de Desguace");

        String contenido = "Versión: 1.0\n\n" +
                          "Desarrollado por: Yalil Musa Talhaoui\n\n" +
                          "Descripción:\n" +
                          "Sistema integral para la gestión de vehículos,\n" +
                          "piezas e inventario de un desguace.\n\n" +
                          "Funcionalidades:\n" +
                          "• Gestión de vehículos\n" +
                          "• Gestión de piezas\n" +
                          "• Control de inventario\n" +
                          "• Sistema de búsqueda y paginación\n" +
                          "• Interfaz moderna y responsive\n\n" +
                          "© 2024 AutoCiclo - Todos los derechos reservados";

        alert.setContentText(contenido);
        alert.showAndWait();
    }

    /**
     * Muestra un diálogo con los detalles del registro seleccionado
     */
    private void verDetallesRegistro() {
        if (tableVehiculos.isVisible()) {
            verDetallesVehiculo();
        } else if (tablePiezas.isVisible()) {
            verDetallesPieza();
        } else if (tableInventario.isVisible()) {
            verDetallesInventario();
        }
    }

    /**
     * Muestra los detalles de un vehículo seleccionado
     */
    private void verDetallesVehiculo() {
        Vehiculo vehiculoSeleccionado = tableVehiculos.getSelectionModel().getSelectedItem();

        if (vehiculoSeleccionado == null) {
            ValidationUtils.showAlert("Selección requerida",
                "Por favor seleccione un vehículo",
                "Debe seleccionar un vehículo de la tabla para ver sus detalles",
                Alert.AlertType.WARNING);
            return;
        }

        try {
            // Cargar el FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DetalleVehiculo.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasarle los datos
            DetalleVehiculoController controller = loader.getController();
            controller.setVehiculo(vehiculoSeleccionado);

            // Crear y mostrar la ventana modal
            Stage modalStage = new Stage();
            modalStage.setTitle("Detalles del Vehículo");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(btnVer.getScene().getWindow());

            Scene scene = new Scene(root);
            modalStage.setScene(scene);
            modalStage.setResizable(true);
            modalStage.setMinWidth(850);
            modalStage.setMinHeight(750);
            modalStage.showAndWait();

        } catch (Exception e) {
            System.err.println("Error al abrir detalles del vehículo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Muestra los detalles de una pieza seleccionada
     */
    private void verDetallesPieza() {
        Pieza piezaSeleccionada = tablePiezas.getSelectionModel().getSelectedItem();

        if (piezaSeleccionada == null) {
            ValidationUtils.showAlert("Selección requerida",
                "Por favor seleccione una pieza",
                "Debe seleccionar una pieza de la tabla para ver sus detalles",
                Alert.AlertType.WARNING);
            return;
        }

        try {
            // Cargar el FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DetallePieza.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasarle los datos
            DetallePiezaController controller = loader.getController();
            controller.setPieza(piezaSeleccionada);

            // Crear y mostrar la ventana modal
            Stage modalStage = new Stage();
            modalStage.setTitle("Detalles de la Pieza");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(btnVer.getScene().getWindow());

            Scene scene = new Scene(root);
            modalStage.setScene(scene);
            modalStage.setResizable(true);
            modalStage.setMinWidth(850);
            modalStage.setMinHeight(750);
            modalStage.showAndWait();

        } catch (Exception e) {
            System.err.println("Error al abrir detalles de la pieza: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Muestra los detalles de un inventario seleccionado
     */
    private void verDetallesInventario() {
        InventarioPieza inventarioSeleccionado = tableInventario.getSelectionModel().getSelectedItem();

        if (inventarioSeleccionado == null) {
            ValidationUtils.showAlert("Selección requerida",
                "Por favor seleccione un registro",
                "Debe seleccionar un registro de inventario de la tabla para ver sus detalles",
                Alert.AlertType.WARNING);
            return;
        }

        try {
            // Cargar el FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DetalleInventario.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasarle los datos
            DetalleInventarioController controller = loader.getController();
            controller.setInventario(inventarioSeleccionado);

            // Crear y mostrar la ventana modal
            Stage modalStage = new Stage();
            modalStage.setTitle("Detalles del Inventario");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(btnVer.getScene().getWindow());

            Scene scene = new Scene(root);
            modalStage.setScene(scene);
            modalStage.setResizable(true);
            modalStage.setMinWidth(850);
            modalStage.setMinHeight(750);
            modalStage.showAndWait();

        } catch (Exception e) {
            System.err.println("Error al abrir detalles del inventario: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
