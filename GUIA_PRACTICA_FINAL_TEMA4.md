# GUIA COMPLETA - PRACTICA FINAL TEMA 4
## AutoCiclo - Documentacion, Distribucion y Pruebas

**Alumno:** Yalil Musa
**Aplicacion:** AutoCiclo
**Fecha de entrega:** Consultar Moodle

---

## INDICE

1. [Resumen de lo que hay que entregar](#1-resumen-de-lo-que-hay-que-entregar)
2. [FASE 0: Corregir fallos del 1er Trimestre](#2-fase-0-corregir-fallos-del-1er-trimestre) **<-- EMPIEZA AQUI**
3. [FASE 1: Documento de Pruebas (PDF)](#3-fase-1-documento-de-pruebas-pdf)
4. [FASE 2: Configurar ip.properties](#4-fase-2-configurar-ipproperties)
5. [FASE 3: Desplegar BBDD en remoto](#5-fase-3-desplegar-bbdd-en-remoto)
6. [FASE 4: Crear Instalador Windows](#6-fase-4-crear-instalador-windows)
7. [FASE 5: Crear Instalador Linux](#7-fase-5-crear-instalador-linux)
8. [FASE 6: Preparar entrega final](#8-fase-6-preparar-entrega-final)

---

## 1. RESUMEN DE LO QUE HAY QUE ENTREGAR

### Ficheros finales a entregar:

| Fichero | Descripcion |
|---------|-------------|
| `AutoCiclo_DIST.zip` | Proyecto completo (sin carpeta build) |
| `Pruebas_YalilMusa_AutoCiclo.pdf` | Documento con todas las pruebas |
| `autociclo_db.sql` | Exportacion de la base de datos |
| `Carpeta Instaladores/` | Instaladores + proyectos InstallBuilder |

### Estructura de la carpeta Instaladores:

```
Instaladores/
├── CarpetaInstalacion/
│   ├── AutoCiclo.jar
│   ├── lib/
│   ├── iconos/
│   └── scripts/
├── AutoCiclo-1.0-windows-x64-installer.exe
├── AutoCiclo-1.0-linux-x64-installer.run
├── ProyectoInstaladorWindows.xml
└── ProyectoInstaladorLinux.xml
```

---

## 2. FASE 0: CORREGIR FALLOS DEL 1ER TRIMESTRE

### TUS FALLOS ESPECIFICOS A CORREGIR:

| # | FALLO | PRIORIDAD | ESTADO |
|---|-------|-----------|--------|
| 1 | Falta icono en la ventanita de salir/confirmacion final | Menor | [ ] Pendiente |
| 2 | Faltan tooltips en TODOS los botones | Menor | [ ] Pendiente |
| 3 | Buscadores sin indicar por que campo buscan (promptHolder/tooltip) | Menor | [ ] Pendiente |
| 4 | Falta MnemonicParsing en todos los botones | Menor | [ ] Pendiente |

**NOTA:** No tuviste fallos de funcionamiento ni fallos importantes de diseno. Solo fallos menores.

---

### FALLO 1: Icono en ventana de confirmacion de salida

**Problema:** La ventana de confirmacion al salir no tiene icono.

**Solucion:** Anadir icono al Alert o Stage de confirmacion.

**Codigo a modificar:** Busca donde creas el Alert de confirmacion de salida.

```java
// ANTES (sin icono):
Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
alert.setTitle("Confirmar salida");
alert.setContentText("¿Seguro que deseas salir?");

// DESPUES (con icono):
Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
alert.setTitle("Confirmar salida");
alert.setContentText("¿Seguro que deseas salir?");

// Anadir icono a la ventana del Alert
Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icono.png")));
```

**Captura para el PDF:** Haz una captura mostrando la ventana de salida CON el icono visible.

---

### FALLO 2: Tooltips en TODOS los botones

**Problema:** Los botones no tienen tooltips explicativos.

**Solucion:** Anadir tooltip a cada boton de la aplicacion.

#### Opcion A: En FXML (recomendado)

```xml
<Button fx:id="btnGuardar" text="_Guardar" mnemonicParsing="true">
    <tooltip>
        <Tooltip text="Guarda los cambios realizados (Alt+G)"/>
    </tooltip>
</Button>

<Button fx:id="btnCancelar" text="_Cancelar" mnemonicParsing="true">
    <tooltip>
        <Tooltip text="Cancela y cierra sin guardar (Alt+C)"/>
    </tooltip>
</Button>

<Button fx:id="btnEliminar" text="_Eliminar" mnemonicParsing="true">
    <tooltip>
        <Tooltip text="Elimina el elemento seleccionado (Alt+E)"/>
    </tooltip>
</Button>

<Button fx:id="btnNuevo" text="_Nuevo" mnemonicParsing="true">
    <tooltip>
        <Tooltip text="Crea un nuevo registro (Alt+N)"/>
    </tooltip>
</Button>

<Button fx:id="btnEditar" text="E_ditar" mnemonicParsing="true">
    <tooltip>
        <Tooltip text="Edita el elemento seleccionado (Alt+D)"/>
    </tooltip>
</Button>

<Button fx:id="btnBuscar" text="_Buscar" mnemonicParsing="true">
    <tooltip>
        <Tooltip text="Busca en la lista (Alt+B)"/>
    </tooltip>
</Button>
```

#### Opcion B: En el Controller (Java)

```java
import javafx.scene.control.Tooltip;

@FXML
public void initialize() {
    // Tooltips para botones
    btnGuardar.setTooltip(new Tooltip("Guarda los cambios realizados (Alt+G)"));
    btnCancelar.setTooltip(new Tooltip("Cancela y cierra sin guardar (Alt+C)"));
    btnEliminar.setTooltip(new Tooltip("Elimina el elemento seleccionado (Alt+E)"));
    btnNuevo.setTooltip(new Tooltip("Crea un nuevo registro (Alt+N)"));
    btnEditar.setTooltip(new Tooltip("Edita el elemento seleccionado (Alt+D)"));
    btnBuscar.setTooltip(new Tooltip("Busca en la lista (Alt+B)"));
}
```

### LISTA COMPLETA DE BOTONES A REVISAR EN AUTOCICLO:

#### Pantalla Principal / ListadoMaestro:
- [ ] Boton Vehiculos - Tooltip: "Accede a la gestion de vehiculos (Alt+V)"
- [ ] Boton Piezas - Tooltip: "Accede a la gestion de piezas (Alt+P)"
- [ ] Boton Inventario - Tooltip: "Accede al inventario de piezas (Alt+I)"
- [ ] Boton Informes - Tooltip: "Genera informes en PDF (Alt+R)"
- [ ] Boton Estadisticas - Tooltip: "Muestra estadisticas (Alt+E)"
- [ ] Boton Salir - Tooltip: "Cierra la aplicacion (Alt+S)"

#### FormularioVehiculo:
- [ ] Boton Guardar - Tooltip
- [ ] Boton Cancelar - Tooltip
- [ ] Boton Seleccionar Imagen - Tooltip: "Selecciona una imagen para el vehiculo"

#### FormularioPieza:
- [ ] Boton Guardar - Tooltip
- [ ] Boton Cancelar - Tooltip

#### FormularioInventario:
- [ ] Boton Guardar - Tooltip
- [ ] Boton Cancelar - Tooltip

#### Listados (DetalleVehiculo, DetallePieza, etc.):
- [ ] Boton Editar - Tooltip
- [ ] Boton Eliminar - Tooltip
- [ ] Boton Volver - Tooltip

#### Informes:
- [ ] Boton Generar Informe Vehiculos - Tooltip
- [ ] Boton Generar Informe Piezas - Tooltip
- [ ] Boton Generar Informe Inventario - Tooltip

**Captura para el PDF:** Haz una captura mostrando un tooltip visible al pasar el raton por un boton.

---

### FALLO 3: Buscadores sin indicar campo de busqueda

**Problema:** Los campos de busqueda no indican por que campo estan buscando.

**Solucion:** Anadir promptText (placeholder) y/o tooltip al TextField de busqueda.

#### En FXML:

```xml
<TextField fx:id="txtBuscar" promptText="Buscar por marca o modelo...">
    <tooltip>
        <Tooltip text="Escribe para filtrar por marca o modelo del vehiculo"/>
    </tooltip>
</TextField>
```

#### En Java:

```java
@FXML
public void initialize() {
    // PromptText (placeholder visible cuando esta vacio)
    txtBuscar.setPromptText("Buscar por marca o modelo...");

    // Tooltip (aparece al pasar el raton)
    txtBuscar.setTooltip(new Tooltip("Filtra vehiculos por marca o modelo"));
}
```

### BUSCADORES A REVISAR:

| Pantalla | Campo | PromptText sugerido |
|----------|-------|---------------------|
| Listado Vehiculos | txtBuscar | "Buscar por marca o modelo..." |
| Listado Piezas | txtBuscar | "Buscar por nombre de pieza..." |
| Listado Inventario | txtBuscar | "Buscar por vehiculo o pieza..." |
| Pantalla Principal | txtBuscar | "Buscar vehiculo por marca/modelo..." |

**Captura para el PDF:** Haz una captura mostrando el campo de busqueda con el promptText visible.

---

### FALLO 4: MnemonicParsing en botones

**Problema:** Los botones no tienen atajos de teclado (Alt + letra).

**Que es MnemonicParsing:** Permite usar Alt + letra subrayada para activar el boton.
- `text="_Guardar"` -> Alt+G activa el boton (la G aparece subrayada)
- `text="_Cancelar"` -> Alt+C activa el boton
- `text="E_liminar"` -> Alt+L activa el boton

#### En FXML:

```xml
<!-- El guion bajo (_) antes de una letra indica el mnemonic -->
<Button text="_Guardar" mnemonicParsing="true"/>
<Button text="_Cancelar" mnemonicParsing="true"/>
<Button text="_Nuevo" mnemonicParsing="true"/>
<Button text="E_liminar" mnemonicParsing="true"/>
<Button text="E_ditar" mnemonicParsing="true"/>
<Button text="_Buscar" mnemonicParsing="true"/>
<Button text="_Volver" mnemonicParsing="true"/>
```

#### En Java (si el boton ya existe):

```java
@FXML
public void initialize() {
    btnGuardar.setMnemonicParsing(true);
    btnGuardar.setText("_Guardar");  // Alt+G

    btnCancelar.setMnemonicParsing(true);
    btnCancelar.setText("_Cancelar");  // Alt+C
}
```

### TABLA DE MNEMONICS SUGERIDOS:

| Boton | Texto con Mnemonic | Atajo |
|-------|-------------------|-------|
| Guardar | `_Guardar` | Alt+G |
| Cancelar | `_Cancelar` | Alt+C |
| Nuevo | `_Nuevo` | Alt+N |
| Editar | `E_ditar` | Alt+D |
| Eliminar | `E_liminar` | Alt+L |
| Buscar | `_Buscar` | Alt+B |
| Volver | `_Volver` | Alt+V |
| Salir | `_Salir` | Alt+S |
| Informes | `_Informes` | Alt+I |
| Piezas | `_Piezas` | Alt+P |

**IMPORTANTE:** Evita repetir la misma letra en la misma pantalla.

**Captura para el PDF:** Haz una captura mostrando un boton con la letra subrayada (pulsa Alt para verla).

---

### RESUMEN DE CAMBIOS A HACER EN CODIGO:

#### Ficheros FXML a modificar:
- [ ] `ListadoMaestro.fxml` - Tooltips + Mnemonics + PromptText buscador
- [ ] `FormularioVehiculo.fxml` - Tooltips + Mnemonics
- [ ] `FormularioPieza.fxml` - Tooltips + Mnemonics
- [ ] `FormularioInventario.fxml` - Tooltips + Mnemonics
- [ ] `DetalleVehiculo.fxml` - Tooltips + Mnemonics
- [ ] `DetallePieza.fxml` - Tooltips + Mnemonics
- [ ] `DetalleInventario.fxml` - Tooltips + Mnemonics
- [ ] `Informes.fxml` - Tooltips + Mnemonics
- [ ] `Estadisticas.fxml` - Tooltips + Mnemonics

#### Ficheros Java a modificar:
- [ ] Controlador donde esta el Alert de salida - Anadir icono

---

### PLANTILLA PARA DOCUMENTAR EN EL PDF DE PRUEBAS:

Usa esta tabla para documentar cada fallo corregido:

| FALLO | DESCRIPCION | SOLUCION APLICADA | CAPTURA |
|-------|-------------|-------------------|---------|
| 1 | Faltaba icono en ventana de salida | Se ha anadido icono al Alert de confirmacion usando `stage.getIcons().add()` | [Imagen 1] |
| 2 | Faltaban tooltips en botones | Se han anadido tooltips a todos los botones de la aplicacion explicando su funcion | [Imagen 2] |
| 3 | Buscador sin indicar campo | Se ha anadido promptText "Buscar por marca o modelo..." y tooltip explicativo | [Imagen 3] |
| 4 | Faltaba MnemonicParsing | Se ha activado mnemonicParsing en todos los botones con atajos Alt+Letra | [Imagen 4] |

---

## 3. FASE 1: DOCUMENTO DE PRUEBAS (PDF)

### Nombre del fichero: `Pruebas_YalilMusa_AutoCiclo.pdf`

Este PDF debe contener las siguientes secciones:

---

### 3.1 SECCION 1: FALLOS CORREGIDOS DEL 1ER TRIMESTRE

Incluye la tabla del apartado anterior con:
- Descripcion de cada fallo
- Como lo has solucionado
- Captura de pantalla mostrando la correccion

---

### 3.2 SECCION 2: PRUEBAS FUNCIONALES (50% de Pruebas)

**Como no tuviste fallos de funcionamiento, documenta las pruebas que ya pasaban correctamente.**

Crea una tabla por cada ventana:

#### TABLA 1: Formulario Vehiculo

| VENTANA | TABLA BD | CAMPOS | EVENTO | COMPROBACIONES |
|---------|----------|--------|--------|----------------|
| FormularioVehiculo | vehiculos | Marca (String), Modelo (String), Anio (Integer), Precio (Double), Imagen (String) | Pulsar "Guardar" | 1. Campos vacios - NO permite guardar ✓, 2. Tipos correctos - Valida numeros ✓, 3. Imagen - Funciona sin imagen ✓ |

#### TABLA 2: Formulario Pieza

| VENTANA | TABLA BD | CAMPOS | EVENTO | COMPROBACIONES |
|---------|----------|--------|--------|----------------|
| FormularioPieza | piezas | Nombre, Descripcion, Precio, Stock | Pulsar "Guardar" | 1. Campos vacios - Valida ✓, 2. Precio positivo - Valida ✓, 3. Stock >= 0 - Valida ✓ |

#### TABLA 3: Formulario Inventario

| VENTANA | TABLA BD | CAMPOS | EVENTO | COMPROBACIONES |
|---------|----------|--------|--------|----------------|
| FormularioInventario | inventario_piezas | Pieza (FK), Vehiculo (FK), Cantidad | Pulsar "Guardar" | 1. Seleccion obligatoria - Valida ✓, 2. Cantidad > 0 - Valida ✓ |

#### TABLA 4: Listados

| VENTANA | TABLA BD | EVENTO | COMPROBACIONES |
|---------|----------|--------|----------------|
| ListadoVehiculos | vehiculos | Eliminar | Pide confirmacion ✓, Actualiza lista ✓ |
| ListadoPiezas | piezas | Eliminar | Pide confirmacion ✓, Actualiza lista ✓ |

---

### 3.3 SECCION 3: PRUEBAS DE SISTEMA (10% de Pruebas)

| ACCION | TABLAS AFECTADAS | RESULTADO ESPERADO | RESULTADO | ESTADO |
|--------|------------------|-------------------|-----------|--------|
| Insertar vehiculo | vehiculos | Aparece en listado | Aparece correctamente | OK |
| Eliminar vehiculo | vehiculos, inventario_piezas | Se elimina y sus piezas asignadas | (Describir) | OK |
| Asignar pieza a vehiculo | inventario_piezas | Se crea registro | (Describir) | OK |
| Generar informe vehiculos | vehiculos | PDF generado | (Describir) | OK |
| Generar informe piezas | piezas | PDF generado | (Describir) | OK |
| Generar informe inventario | todas | PDF con datos cruzados | (Describir) | OK |

---

### 3.4 SECCION 4: PRUEBAS ALFA - TEST DE GUERRILLA (40% de Pruebas)

**OBLIGATORIO:** Debes hacer esto con 1-2 personas reales.

#### Datos de los usuarios:

| Usuario | Perfil | Conocimientos informaticos |
|---------|--------|---------------------------|
| Usuario 1 | (Ej: Familiar, 45 anos) | Basicos (usa movil y correo) |
| Usuario 2 | (Ej: Amigo, 25 anos) | Medios (usa ordenador a diario) |

#### Tabla de tareas:

| TAREA | TIEMPO MAX | U1 TIEMPO | U1 EXITO | U2 TIEMPO | U2 EXITO |
|-------|------------|-----------|----------|-----------|----------|
| Abrir la aplicacion | 30 seg | | Si/No | | Si/No |
| Anadir un vehiculo nuevo | 3 min | | Si/No | | Si/No |
| Buscar un vehiculo | 1 min | | Si/No | | Si/No |
| Modificar precio de pieza | 2 min | | Si/No | | Si/No |
| Asignar pieza a vehiculo | 2 min | | Si/No | | Si/No |
| Generar informe PDF | 2 min | | Si/No | | Si/No |
| Cerrar aplicacion | 30 seg | | Si/No | | Si/No |

#### Observaciones:

| USUARIO | PROBLEMAS | COMENTARIOS |
|---------|-----------|-------------|
| Usuario 1 | (Anotar problemas) | (Anotar lo que dice) |
| Usuario 2 | (Anotar problemas) | (Anotar lo que dice) |

#### CONCLUSIONES (MUY IMPORTANTE):

Escribe 1-2 parrafos con:
- Que problemas se detectaron
- Si los usuarios completaron las tareas
- Que mejoras se podrian hacer
- Valoracion general de usabilidad

**Ejemplo:**
> "Se realizaron pruebas alfa con 2 usuarios sin conocimientos de la aplicacion. Ambos completaron el 100% de las tareas. El usuario 1 tardo mas en encontrar el boton de informes (1:45 min). El usuario 2 comento que los tooltips le ayudaron a entender las opciones. No se detectaron fallos de funcionamiento. Como mejora futura se propone anadir iconos mas grandes en los botones principales."

---

## 4. FASE 2: CONFIGURAR ip.properties

### Paso 1: Crear el fichero

Ubicacion: `app/src/main/resources/ip.properties`

```properties
# Configuracion de conexion a base de datos
# Cambiar esta IP para apuntar al servidor remoto

db.host=localhost
db.port=3306
db.name=autociclo_db
db.user=root
db.password=tu_password
```

### Paso 2: Modificar ConexionBD.java

```java
import java.io.InputStream;
import java.util.Properties;

public class ConexionBD {

    private static String host;
    private static String port;
    private static String dbName;
    private static String user;
    private static String password;

    static {
        cargarConfiguracion();
    }

    private static void cargarConfiguracion() {
        Properties props = new Properties();
        try (InputStream input = ConexionBD.class.getResourceAsStream("/ip.properties")) {
            if (input != null) {
                props.load(input);
                host = props.getProperty("db.host", "localhost");
                port = props.getProperty("db.port", "3306");
                dbName = props.getProperty("db.name", "autociclo_db");
                user = props.getProperty("db.user", "root");
                password = props.getProperty("db.password", "");
            }
        } catch (Exception e) {
            host = "localhost";
            port = "3306";
            dbName = "autociclo_db";
            user = "root";
            password = "";
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;
        return DriverManager.getConnection(url, user, password);
    }
}
```

---

## 5. FASE 3: DESPLEGAR BBDD EN REMOTO

### OPCION RECOMENDADA: Railway (Gratis y facil)

1. Ve a https://railway.app/
2. Crea cuenta con GitHub
3. "New Project" > "Provision MySQL"
4. Copia las variables de conexion
5. Actualiza ip.properties
6. Importa tu SQL en la pestana "Data" > "Query"

### Alternativas:
- **PlanetScale:** https://planetscale.com/
- **AWS RDS:** Consultar PDF `DI - TEMA 4-4 DESPLIEGUE EN AWS.pdf`
- **Supabase:** https://supabase.com/
- **Clever Cloud:** https://www.clever-cloud.com/

---

## 6. FASE 4: CREAR INSTALADOR WINDOWS

### Paso 1: Generar JAR

```bash
cd /home/yalilms/Documentos/2ºDAM/Desarrollo_interfaces/AutoCiclo/app
./gradlew clean build jar
```

### Paso 2: Preparar CarpetaInstalacion

```
CarpetaInstalacion/
├── AutoCiclo.jar
├── lib/
├── resources/
│   └── ip.properties
├── iconos/
│   ├── autociclo.ico
│   └── autociclo.png
└── run.bat
```

### Paso 3: Script run.bat

```bat
@echo off
cd /d "%~dp0"
java -jar AutoCiclo.jar
pause
```

### Paso 4: InstallBuilder

1. File > New Project
2. Product Name: AutoCiclo
3. Version: 1.0
4. Anadir ficheros
5. Crear shortcut con icono
6. **IMPORTANTE - Seccion Advanced (15% nota):** Anadir algo extra como:
   - Verificar Java instalado
   - Splash screen
   - Licencia personalizada
7. Build para Windows x64

---

## 7. FASE 5: CREAR INSTALADOR LINUX

### Paso 1: Crear autociclo.desktop

```ini
[Desktop Entry]
Name=AutoCiclo
Comment=Gestion de vehiculos y piezas
Exec=/opt/autociclo/run.sh
Icon=/opt/autociclo/iconos/autociclo.png
Terminal=false
Type=Application
Categories=Office;Database;
```

### Paso 2: Crear run.sh

```bash
#!/bin/bash
cd /opt/autociclo
java -jar AutoCiclo.jar
```

### Paso 3: Crear postinstall.sh

```bash
#!/bin/bash
chmod +x /opt/autociclo/run.sh
cp /opt/autociclo/autociclo.desktop /usr/share/applications/
update-desktop-database /usr/share/applications/ 2>/dev/null || true
```

### Paso 4: InstallBuilder para Linux

1. Duplicar proyecto Windows
2. Cambiar plataforma a Linux x64
3. Installation Directory: /opt/autociclo
4. Anadir .desktop, run.sh, postinstall.sh
5. Post-installation action: ejecutar postinstall.sh
6. Build

---

## 8. FASE 6: PREPARAR ENTREGA FINAL

### Paso 1: Limpiar proyecto

```bash
rm -rf app/build
```

### Paso 2: Exportar BBDD

```bash
mysqldump -u root -p autociclo_db > autociclo_db.sql
```

### Paso 3: Estructura final

```
FASEFINAL_YalilMusa/
├── AutoCiclo_DIST.zip
├── Pruebas_YalilMusa_AutoCiclo.pdf
├── autociclo_db.sql
└── Instaladores/
    ├── CarpetaInstalacion/
    ├── AutoCiclo-1.0-windows-x64-installer.exe
    ├── AutoCiclo-1.0-linux-x64-installer.run
    └── Proyectos InstallBuilder (.xml)
```

---

## CHECKLIST FINAL

### Fallos 1er Trimestre corregidos:
- [ ] Icono en ventana de salida
- [ ] Tooltips en TODOS los botones
- [ ] PromptText en buscadores
- [ ] MnemonicParsing en botones

### PDF de Pruebas:
- [ ] Seccion fallos corregidos con capturas
- [ ] Pruebas Funcionales
- [ ] Pruebas de Sistema
- [ ] Pruebas Alfa con usuarios reales
- [ ] Conclusiones

### Distribucion:
- [ ] ip.properties configurado
- [ ] BBDD en servidor remoto
- [ ] Instalador Windows funcional
- [ ] Instalador Linux funcional
- [ ] Algo en Advanced (15%)
- [ ] APP funciona tras instalar

### Ficheros:
- [ ] AutoCiclo_DIST.zip (sin build)
- [ ] Pruebas_YalilMusa_AutoCiclo.pdf
- [ ] autociclo_db.sql
- [ ] Carpeta Instaladores completa

---

**Buena suerte!**
