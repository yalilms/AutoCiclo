# AUTOCICLO

## SISTEMA DE GESTIÓN INTEGRAL PARA DESGUACES DE AUTOMÓVILES

---

**Autor:** Yalil Musa Talhaoui  
**Curso:** 2º Desarrollo de Aplicaciones Multiplataforma  
**Asignatura:** Desarrollo de Interfaces  
**Centro:** IES Politécnico Hermenegildo Lanz (Granada)  
**Año académico:** 2025-2026  
**Fecha:** Enero 2026

---

<div style="page-break-after: always;"></div>

## ÍNDICE

1. [INTRODUCCIÓN](#1-introducción)
   - 1.1. Descripción del proyecto
   - 1.2. Objetivos
   - 1.3. Alcance

2. [TECNOLOGÍAS UTILIZADAS](#2-tecnologías-utilizadas)
   - 2.1. Lenguajes y frameworks
   - 2.2. Herramientas de desarrollo
   - 2.3. Bibliotecas externas

3. [ARQUITECTURA DEL SISTEMA](#3-arquitectura-del-sistema)
   - 3.1. Patrón MVC
   - 3.2. Estructura de capas
   - 3.3. Organización del proyecto

4. [BASE DE DATOS](#4-base-de-datos)
   - 4.1. Modelo entidad-relación
   - 4.2. Descripción de tablas
   - 4.3. Relaciones y restricciones

5. [FUNCIONALIDADES DEL SISTEMA](#5-funcionalidades-del-sistema)
   - 5.1. Gestión de vehículos
   - 5.2. Gestión de piezas
   - 5.3. Gestión de inventario
   - 5.4. Estadísticas visuales
   - 5.5. Generación de informes

6. [INTERFAZ DE USUARIO](#6-interfaz-de-usuario)
   - 6.1. Pantalla de carga
   - 6.2. Pantalla principal
   - 6.3. Formularios de datos
   - 6.4. Vistas de detalle

7. [BIBLIOTECAS JAVAFX UTILIZADAS](#7-bibliotecas-javafx-utilizadas)
   - 7.1. Ikonli Material Design
   - 7.2. Gson
   - 7.3. JasperReports

8. [CONCLUSIONES](#8-conclusiones)
   - 8.1. Resultados obtenidos
   - 8.2. Dificultades encontradas
   - 8.3. Mejoras futuras

9. [REFERENCIAS](#9-referencias)

---

<div style="page-break-after: always;"></div>

## 1. INTRODUCCIÓN

### 1.1. Descripción del proyecto

AutoCiclo es un sistema de gestión integral desarrollado específicamente para desguaces de automóviles. El proyecto surge como respuesta a la necesidad de modernizar y optimizar los procesos de control de inventario en este tipo de establecimientos, donde la gestión eficiente de vehículos y piezas es fundamental para la rentabilidad del negocio.

La aplicación permite llevar un control exhaustivo de los vehículos que ingresan al desguace, las piezas que se extraen de ellos, su ubicación en el almacén, el stock disponible y la generación de informes detallados para la toma de decisiones.

### 1.2. Objetivos

Los objetivos principales del proyecto AutoCiclo son los siguientes:

- Crear una aplicación de escritorio robusta y profesional utilizando JavaFX como framework de interfaz gráfica.
- Implementar un sistema completo de gestión CRUD (Create, Read, Update, Delete) para vehículos, piezas e inventario.
- Diseñar una interfaz de usuario moderna, intuitiva y atractiva que mejore la experiencia del usuario.
- Integrar una base de datos MySQL para el almacenamiento persistente de la información.
- Proporcionar funcionalidades avanzadas como búsqueda en tiempo real, filtrado de datos y estadísticas visuales.
- Generar informes profesionales en formato PDF y HTML utilizando JasperReports.
- Aplicar buenas prácticas de programación orientada a objetos y el patrón de diseño MVC (Modelo-Vista-Controlador).

### 1.3. Alcance

El sistema AutoCiclo abarca las siguientes áreas funcionales:

- **Gestión de vehículos**: Registro completo de vehículos con matrícula, marca, modelo, año, estado (completo, desguazando, desguazado), precio de compra, kilometraje y ubicación física.
- **Gestión de piezas**: Catálogo de piezas con código único, categoría, precio, stock disponible y mínimo, ubicación en almacén e imágenes almacenadas en Base64.
- **Asignación de inventario**: Vinculación de piezas extraídas con sus vehículos de origen, incluyendo cantidad, estado, fecha de extracción y precio unitario.
- **Estadísticas visuales**: Gráficos de barras y circulares que permiten visualizar el estado del inventario de forma rápida e intuitiva.
- **Generación de informes**: Tres tipos de informes profesionales (Piezas, Vehículos e Inventario) exportables a PDF y HTML.

---

<div style="page-break-after: always;"></div>

## 2. TECNOLOGÍAS UTILIZADAS

### 2.1. Lenguajes y frameworks

**Java 24**

Java es el lenguaje de programación principal utilizado en este proyecto. Se ha seleccionado la versión 24 por su estabilidad, rendimiento y compatibilidad con las herramientas modernas de desarrollo. Java proporciona las características de programación orientada a objetos necesarias para estructurar el código de manera eficiente y mantenible.

**JavaFX 25**

JavaFX es el framework de interfaz gráfica de usuario seleccionado para el desarrollo de la aplicación de escritorio. JavaFX ofrece componentes modernos y personalizables, soporte para CSS para el diseño visual, y capacidades multimedia que permiten crear interfaces atractivas y profesionales. La versión 25 incluye mejoras en rendimiento y compatibilidad.

### 2.2. Herramientas de desarrollo

**Gradle 8.8**

Gradle es el sistema de construcción y gestión de dependencias utilizado en el proyecto. Facilita la automatización de tareas como la compilación, el empaquetado y la ejecución de la aplicación, además de gestionar todas las bibliotecas externas de forma eficiente.

**MySQL 8.0**

MySQL es el sistema de gestión de bases de datos relacionales (SGBD) empleado para el almacenamiento persistente de la información. MySQL 8.0 ofrece robustez, rendimiento y características avanzadas como soporte para JSON y mejoras en la seguridad.

**Scene Builder**

Scene Builder es la herramienta visual utilizada para diseñar las interfaces FXML de la aplicación. Permite crear diseños de interfaz de usuario de forma gráfica mediante arrastrar y soltar, lo que acelera el proceso de desarrollo y facilita la creación de interfaces complejas.

### 2.3. Bibliotecas externas

**MySQL Connector/J 8.2.0**

Driver JDBC oficial de MySQL que permite la conexión entre la aplicación Java y la base de datos MySQL.

**Ikonli 12.3.1**

Biblioteca que proporciona acceso a miles de iconos vectoriales de Material Design para enriquecer la interfaz gráfica de la aplicación.

**Gson 2.10.1**

Biblioteca de Google para parsear archivos JSON, utilizada para cargar configuraciones de datos desde archivos como `vehiculos.json` y `ubicaciones.json`.

**JasperReports 6.20.6**

Potente biblioteca para la generación de informes profesionales que permite exportar a múltiples formatos (PDF, HTML, Excel, etc.).

---

<div style="page-break-after: always;"></div>

## 3. ARQUITECTURA DEL SISTEMA

### 3.1. Patrón MVC

AutoCiclo implementa el patrón de diseño Modelo-Vista-Controlador (MVC), que separa la lógica de negocio, la presentación y el control de flujo en tres componentes independientes:

- **Modelo (Model)**: Representado por las clases de dominio ubicadas en el paquete `com.autociclo.models`. Estas clases encapsulan los datos y la lógica de negocio relacionada con vehículos, piezas e inventario.

- **Vista (View)**: Archivos FXML ubicados en `resources/fxml` que definen la estructura visual de la interfaz de usuario. Los estilos CSS complementan estas vistas proporcionando el diseño visual.

- **Controlador (Controller)**: Clases Java ubicadas en `com.autociclo.controllers` que actúan como intermediarios entre el modelo y la vista, gestionando eventos del usuario y actualizando la interfaz según sea necesario.

### 3.2. Estructura de capas

La arquitectura del sistema está organizada en cuatro capas principales:

**CAPA DE PRESENTACIÓN**

Contiene las vistas FXML y los estilos CSS. Es responsable de mostrar la información al usuario y capturar sus interacciones.

**CAPA DE CONTROLADORES**

Gestiona la lógica de presentación y coordina las interacciones entre la vista y el modelo. Incluye controladores como `ListadoMaestroController`, `FormularioVehiculoController`, `FormularioPiezaController` y `EstadisticasController`.

**CAPA DE MODELO**

Define las entidades del dominio: `Vehiculo`, `Pieza` e `InventarioPieza`. Estas clases representan los objetos de negocio con sus atributos y métodos.

**CAPA DE DATOS**

Gestiona el acceso a la base de datos mediante la clase `ConexionBD`, que implementa el patrón Singleton para garantizar una única conexión activa en todo momento.

![Figura 1. Diagrama de arquitectura de AutoCiclo](/home/yalilms/.gemini/antigravity/brain/7eba0d24-1e67-448d-8af9-ec173a5b56f7/arquitectura_proyecto_1768762529447.png)

### 3.3. Organización del proyecto

La estructura de directorios del proyecto sigue las convenciones estándar de Maven/Gradle:

```
AutoCiclo/
├── app/
│   └── src/main/
│       ├── java/com/autociclo/
│       │   ├── Main.java
│       │   ├── controllers/
│       │   ├── database/
│       │   ├── models/
│       │   └── utils/
│       └── resources/
│           ├── css/
│           ├── fxml/
│           ├── imagenes/
│           ├── informes/
│           ├── ubicaciones.json
│           └── vehiculos.json
├── autociclo_db.sql
├── build.gradle
└── README.md
```

---

<div style="page-break-after: always;"></div>

## 4. BASE DE DATOS

### 4.1. Modelo entidad-relación

La base de datos `autociclo_db` ha sido diseñada bajo el modelo relacional y consta de tres tablas principales que se relacionan mediante claves foráneas. El diagrama entidad-relación muestra una relación muchos a muchos (N:M) entre VEHICULOS y PIEZAS, implementada mediante la tabla intermedia INVENTARIO_PIEZAS.

![Figura 2. Diagrama Entidad-Relación de la base de datos](/home/yalilms/.gemini/antigravity/brain/7eba0d24-1e67-448d-8af9-ec173a5b56f7/diagrama_base_datos_1768762496029.png)

### 4.2. Descripción de tablas

**TABLA VEHICULOS**

Almacena la información completa de los vehículos que ingresan al desguace.

| Campo         | Tipo          | Descripción                               |
| ------------- | ------------- | ----------------------------------------- |
| id_vehiculo   | INT (PK)      | Identificador único autoincremental       |
| matricula     | VARCHAR(10)   | Matrícula del vehículo (único)            |
| marca         | VARCHAR(50)   | Marca del vehículo                        |
| modelo        | VARCHAR(50)   | Modelo del vehículo                       |
| anio          | INT           | Año de fabricación                        |
| color         | VARCHAR(30)   | Color del vehículo                        |
| fecha_entrada | DATE          | Fecha de ingreso al desguace              |
| estado        | ENUM          | Estado: completo, desguazando, desguazado |
| precio_compra | DECIMAL(10,2) | Precio pagado por el vehículo             |
| kilometraje   | INT           | Kilómetros recorridos                     |
| ubicacion_gps | VARCHAR(50)   | Ubicación física en el desguace           |
| observaciones | TEXT          | Notas adicionales                         |

**TABLA PIEZAS**

Catálogo de piezas disponibles en el inventario del desguace.

| Campo             | Tipo          | Descripción                                  |
| ----------------- | ------------- | -------------------------------------------- |
| id_pieza          | INT (PK)      | Identificador único autoincremental          |
| codigo_pieza      | VARCHAR(20)   | Código alfanumérico único                    |
| nombre            | VARCHAR(100)  | Nombre descriptivo de la pieza               |
| categoria         | ENUM          | Categoría: motor, carroceria, interior, etc. |
| precio_venta      | DECIMAL(10,2) | Precio de venta al público                   |
| stock_disponible  | INT           | Cantidad disponible en stock                 |
| stock_minimo      | INT           | Nivel mínimo de reposición                   |
| ubicacion_almacen | VARCHAR(50)   | Ubicación física en el almacén               |
| compatible_marcas | TEXT          | Marcas y modelos compatibles                 |
| imagen            | LONGTEXT      | Imagen en formato Base64                     |
| descripcion       | TEXT          | Descripción detallada de la pieza            |

**TABLA INVENTARIO_PIEZAS**

Tabla intermedia que relaciona vehículos con las piezas extraídas de ellos.

| Campo            | Tipo          | Descripción                          |
| ---------------- | ------------- | ------------------------------------ |
| id_vehiculo      | INT (FK)      | Referencia al vehículo de origen     |
| id_pieza         | INT (FK)      | Referencia a la pieza extraída       |
| cantidad         | INT           | Cantidad de piezas extraídas         |
| estado_pieza     | ENUM          | Estado: nueva, usada, reparada       |
| fecha_extraccion | DATE          | Fecha en que se extrajo la pieza     |
| precio_unitario  | DECIMAL(10,2) | Precio unitario de la pieza extraída |
| notas            | VARCHAR(255)  | Observaciones sobre la extracción    |

### 4.3. Relaciones y restricciones

**RELACIONES**

- VEHICULOS (1) ──────< (N) INVENTARIO_PIEZAS
- PIEZAS (1) ──────< (N) INVENTARIO_PIEZAS

Cada vehículo puede tener múltiples piezas extraídas, y cada pieza puede provenir de múltiples vehículos. Esta relación N:M se implementa mediante INVENTARIO_PIEZAS.

**RESTRICCIONES DE INTEGRIDAD REFERENCIAL**

```sql
ALTER TABLE INVENTARIO_PIEZAS
ADD CONSTRAINT INVENTARIO_PIEZAS_ibfk_1
  FOREIGN KEY (id_vehiculo)
  REFERENCES VEHICULOS (id_vehiculo)
  ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE INVENTARIO_PIEZAS
ADD CONSTRAINT INVENTARIO_PIEZAS_ibfk_2
  FOREIGN KEY (id_pieza)
  REFERENCES PIEZAS (id_pieza)
  ON DELETE CASCADE ON UPDATE CASCADE;
```

Estas restricciones garantizan que:

- No se pueden asignar piezas a vehículos inexistentes.
- Al eliminar un vehículo o pieza, se eliminan automáticamente sus registros en el inventario (CASCADE).
- Las actualizaciones de claves primarias se propagan automáticamente.

---

<div style="page-break-after: always;"></div>

## 5. FUNCIONALIDADES DEL SISTEMA

### 5.1. Gestión de vehículos

El módulo de gestión de vehículos permite realizar las siguientes operaciones:

**ALTA DE VEHÍCULOS**

Permite registrar nuevos vehículos en el sistema mediante un formulario que solicita:

- Matrícula (validada con expresión regular española)
- Marca y modelo (cargados desde `vehiculos.json`)
- Año de fabricación (entre 1900 y año actual)
- Color, precio de compra y kilometraje
- Estado inicial (Completo, Desguazando o Desguazado)
- Ubicación física en el desguace (cargada desde `ubicaciones.json`)
- Observaciones opcionales

El sistema valida que la matrícula no esté duplicada y que todos los campos obligatorios estén completos.

**MODIFICACIÓN DE VEHÍCULOS**

Los vehículos existentes pueden ser editados en cualquier momento. El formulario se precarga con los datos actuales y permite modificar cualquier campo excepto el identificador.

**BAJA DE VEHÍCULOS**

La eliminación de un vehículo es una operación crítica que requiere confirmación del usuario. Debido a las restricciones de integridad referencial con cascada, al eliminar un vehículo también se eliminan automáticamente todos sus registros de piezas extraídas en INVENTARIO_PIEZAS.

**CONSULTA Y BÚSQUEDA**

El listado principal de vehículos incluye:

- Tabla con todas las columnas relevantes
- Búsqueda en tiempo real que filtra por cualquier campo
- Ordenación por columnas (matrícula, marca, modelo, estado, etc.)
- Vista detallada al hacer doble clic en un registro

### 5.2. Gestión de piezas

El módulo de piezas ofrece un control completo del catálogo de repuestos:

**CATÁLOGO DE PIEZAS**

Cada pieza se registra con:

- Código único alfanumérico
- Nombre descriptivo
- Categoría (Motor, Carrocería, Interior, Electrónica, Ruedas, Otros)
- Precio de venta
- Stock disponible y stock mínimo
- Ubicación específica en el almacén
- Marcas y modelos compatibles
- Imagen de la pieza (almacenada en Base64)
- Descripción detallada

**ALMACENAMIENTO DE IMÁGENES EN BASE64**

Una característica destacada es el almacenamiento de imágenes directamente en la base de datos mediante codificación Base64. Esto ofrece varias ventajas:

- No se requiere gestión de archivos externos
- Las imágenes quedan integradas en los backups de la base de datos
- Portabilidad total del sistema

El proceso funciona de la siguiente manera:

1. El usuario selecciona una imagen desde el formulario de piezas
2. La aplicación lee el archivo y lo codifica en Base64
3. La cadena Base64 se almacena en el campo `imagen` de tipo LONGTEXT
4. Al recuperar la pieza, se decodifica el Base64 y se muestra la imagen

**CONTROL DE STOCK**

El sistema implementa alertas visuales para el control de stock:

- Piezas con stock por debajo del mínimo se destacan en color rojo
- El stock se actualiza automáticamente al asignar piezas a vehículos
- Se pueden realizar ajustes manuales de inventario

### 5.3. Gestión de inventario

El módulo de inventario relaciona vehículos con las piezas que se extraen de ellos:

**ASIGNACIÓN DE PIEZAS A VEHÍCULOS**

El proceso de asignación requiere:

1. Seleccionar un vehículo de origen
2. Seleccionar la pieza extraída
3. Indicar cantidad extraída
4. Especificar estado de la pieza (nueva, usada, reparada)
5. Registrar fecha de extracción
6. Establecer precio unitario
7. Agregar notas opcionales

**ACTUALIZACIÓN AUTOMÁTICA DE STOCK**

Cuando se asigna una pieza a un vehículo, el sistema incrementa automáticamente el `stock_disponible` de esa pieza en la tabla PIEZAS, manteniendo la coherencia de datos.

**CONSULTAS DE INVENTARIO**

El listado de inventario muestra:

- Matrícula y datos del vehículo
- Código y nombre de la pieza
- Cantidad y estado
- Valor total (precio × cantidad)
- Filtrado por vehículo, pieza, fecha o estado

### 5.4. Estadísticas visuales

AutoCiclo incluye un módulo de estadísticas con gráficos interactivos que facilitan la toma de decisiones:

**GRÁFICO DE BARRAS: VALOR DE PIEZAS POR VEHÍCULO**

Muestra el valor total de las piezas extraídas de cada vehículo, permitiendo identificar rápidamente qué vehículos han sido más rentables.

**GRÁFICO CIRCULAR: DISTRIBUCIÓN POR CATEGORÍA**

Visualiza la distribución del inventario según las categorías de piezas (Motor, Carrocería, Interior, etc.), ayudando a identificar qué tipos de piezas predominan en el stock.

Los gráficos se actualizan en tiempo real al modificar el inventario y se generan utilizando los componentes nativos de JavaFX (BarChart y PieChart).

### 5.5. Generación de informes

El sistema integra JasperReports para la generación de informes profesionales en PDF y HTML:

**INFORME DE PIEZAS**

Informe simple que muestra el catálogo completo de piezas con:

- Imágenes (decodificadas desde Base64)
- Código, nombre, categoría y precios
- Stock disponible y mínimo
- Ubicación y compatibilidades
- Totales: cantidad total y valor del stock

**INFORME DE VEHÍCULOS**

Informe condicional con parámetros SQL que permite filtrar por:

- Estado del vehículo
- Marca específica
- Rango de años

Incluye colores condicionales según el estado y estadísticas finales (total de vehículos, inversión total, precio promedio).

**INFORME DE INVENTARIO**

Informe avanzado con JOIN de tres tablas (VEHICULOS, PIEZAS, INVENTARIO_PIEZAS) que muestra:

- Piezas extraídas agrupadas por vehículo
- Subtotales por vehículo
- Dos gráficos (barras y circular) integrados
- Totales generales

Los tres informes se pueden exportar a PDF para impresión o a HTML para visualización web.

---

<div style="page-break-after: always;"></div>

## 6. INTERFAZ DE USUARIO

### 6.1. Pantalla de carga

La aplicación comienza con una pantalla de carga animada que mejora la percepción de arranque del sistema. Características:

- Fondo personalizado con imagen relacionada con desguaces
- Indicador de progreso circular (ProgressIndicator)
- Logo de AutoCiclo
- Tiempo estimado de carga de 3 segundos
- Transición suave a la pantalla principal

La pantalla de carga está controlada por `PantallaDeCargaController` y se define en `PantallaDeCarga.fxml`.

### 6.2. Pantalla principal

La interfaz principal de AutoCiclo implementa un diseño de navegación por pestañas (TabPane) que organiza las funcionalidades en cuatro secciones:

**PESTAÑA VEHÍCULOS**

- Tabla con listado completo de vehículos
- Barra de búsqueda en tiempo real
- Botones de acción: Nuevo, Editar, Eliminar, Refrescar
- Iconos Material Design para mejorar la usabilidad
- Código de colores según el estado del vehículo

**PESTAÑA PIEZAS**

- Catálogo de piezas con imágenes en miniatura
- Indicadores visuales de stock bajo (alerta roja)
- Búsqueda y filtrado por categoría
- Vista previa de imágenes al seleccionar una pieza

**PESTAÑA INVENTARIO**

- Vista relacional de vehículos y piezas asignadas
- Filtros múltiples (por vehículo, pieza, fecha)
- Cálculo automático de valores totales
- Agrupación por vehículo

**PESTAÑA ESTADÍSTICAS**

- Gráficos interactivos en tiempo real
- Selector de tipo de gráfico (barras o circular)
- Resumen numérico de totales
- Exportación de datos

### 6.3. Formularios de datos

Los formularios de alta y edición siguen un diseño consistente y profesional:

**DISEÑO ESTÁNDAR DE FORMULARIOS**

- Tamaño uniforme: 850x750 píxeles
- Campos organizados en columnas para optimizar el espacio
- Validación en tiempo real con mensajes de error descriptivos
- ComboBox para campos predefinidos (marcas, modelos, ubicaciones)
- DatePicker para selección de fechas
- TextArea para campos de texto extenso

**FORMULARIO DE VEHÍCULOS**

Incluye validaciones específicas:

- Expresión regular para matrícula española
- Año entre 1900 y año actual
- Precio y kilometraje como números positivos
- Desplegables dinámicos cargados desde JSON

**FORMULARIO DE PIEZAS**

Características destacadas:

- Selector de imagen con vista previa
- Codificación automática a Base64
- Generación automática de código de pieza
- Categorización predefinida
- Control de stock con alertas

**FORMULARIO DE INVENTARIO**

Particularidades:

- Selección de vehículo con autocompletar
- Búsqueda de pieza por código o nombre
- Cálculo automático de valor total
- Validación de cantidades disponibles

### 6.4. Vistas de detalle

Al hacer doble clic en un registro de las tablas, se abre una vista de detalle modal que muestra toda la información de forma organizada y legible:

**DETALLE DE VEHÍCULO**

- Información completa en formato ficha
- Estado destacado con código de colores
- Sección de observaciones expandida
- Historial de piezas extraídas de ese vehículo

**DETALLE DE PIEZA**

- Imagen ampliada de la pieza
- Información técnica
- Stock actual y alertas
- Lista de vehículos compatibles
- Estadísticas de uso

**DETALLE DE INVENTARIO**

- Datos del vehículo y la pieza relacionados
- Información de la transacción (fecha, cantidad, estado)
- Valor total calculado
- Notas y observaciones

Todas las vistas utilizan estilos CSS personalizados que implementan un tema oscuro moderno con acentos de color para mejorar la legibilidad y la estética.

---

<div style="page-break-after: always;"></div>

## 7. BIBLIOTECAS JAVAFX UTILIZADAS

### 7.1. Ikonli Material Design

**Descripción general**

Ikonli es una biblioteca que proporciona acceso a iconos vectoriales de diversas colecciones, incluyendo Material Design, Font Awesome, Ionicons, entre otros. En AutoCiclo se utiliza específicamente el paquete Material Design 2.

**Implementación en AutoCiclo**

La biblioteca se ha utilizado extensivamente en toda la interfaz para mejorar la usabilidad y el aspecto visual:

```java
// Dependencias en build.gradle
implementation 'org.kordamp.ikonli:ikonli-javafx:12.3.1'
implementation 'org.kordamp.ikonli:ikonli-materialdesign2-pack:12.3.1'
```

**Casos de uso en el proyecto**

1. **Botones de acción en la barra de herramientas**:
   - Icono de añadir (add-circle) para nuevos registros
   - Icono de editar (pencil) para modificar
   - Icono de eliminar (delete) para borrar
   - Icono de actualizar (refresh) para refrescar datos
   - Icono de búsqueda (magnify) para el campo de búsqueda

2. **Indicadores de estado**:
   - Icono de vehículo completo (car)
   - Icono de proceso en marcha (cog)
   - Icono de completado (check-circle)

3. **Navegación**:
   - Iconos en las pestañas del TabPane
   - Iconos en menús contextuales

**Ventajas de su uso**

- **Escalabilidad**: Los iconos vectoriales se ven perfectos en cualquier resolución
- **Rendimiento**: Carga más rápida que imágenes bitmap
- **Flexibilidad**: Los iconos pueden cambiar de color y tamaño mediante CSS
- **Consistencia**: Diseño uniforme en toda la aplicación

### 7.2. Gson

**Descripción general**

Gson es una biblioteca de Google para serializar y deserializar objetos Java a JSON y viceversa. Proporciona una API sencilla para trabajar con datos en formato JSON.

**Implementación en AutoCiclo**

```java
// Dependencia en build.gradle
implementation 'com.google.code.gson:gson:2.10.1'
```

**Casos de uso en el proyecto**

1. **Carga de marcas y modelos de vehículos** (`vehiculos.json`):

El archivo JSON contiene una estructura con todas las marcas y sus modelos:

```json
{
  "marcas": [
    {
      "nombre": "Volkswagen",
      "modelos": ["Golf", "Polo", "Passat", "Tiguan"]
    },
    {
      "nombre": "Peugeot",
      "modelos": ["208", "308", "3008", "5008"]
    }
  ]
}
```

El código Java utiliza Gson para parsear este archivo:

```java
Gson gson = new Gson();
Reader reader = new FileReader("vehiculos.json");
MarcasWrapper wrapper = gson.fromJson(reader, MarcasWrapper.class);
```

2. **Carga de ubicaciones del desguace** (`ubicaciones.json`):

Define ubicaciones categorizadas para vehículos y piezas:

```json
{
  "vehiculos": ["Patio principal", "Zona A", "Zona B"],
  "piezas": ["Estantería A, nivel 1", "Zona motores, pasillo 3"]
}
```

**Ventajas de su uso**

- **Mantenibilidad**: Los datos de configuración están separados del código
- **Escalabilidad**: Agregar nuevas marcas o ubicaciones no requiere recompilar
- **Legibilidad**: JSON es un formato fácil de leer y editar
- **Interoperabilidad**: Los datos pueden ser utilizados por otras aplicaciones

### 7.3. JasperReports

**Descripción general**

JasperReports es una de las bibliotecas más utilizadas para la generación de informes en Java. Permite diseñar informes complejos con gráficos, imágenes y exportación a múltiples formatos (PDF, HTML, Excel, etc.).

**Implementación en AutoCiclo**

```java
// Dependencia en build.gradle
implementation 'net.sf.jasperreports:jasperreports:6.20.6'
```

**Arquitectura de informes**

El proyecto incluye tres informes profesionales ubicados en `resources/informes/`:

1. **InformePiezas.jasper** - Informe de catálogo de piezas
2. **InformeVehiculos.jasper** - Informe parametrizado de vehículos
3. **InformeInventario.jasper** - Informe con JOIN y gráficos

**Proceso de generación de informes**

1. **Diseño del informe** con JasperSoft Studio (archivo .jrxml)
2. **Compilación** del .jrxml a .jasper (formato binario)
3. **Llenado del informe** con datos de la base de datos
4. **Exportación** a PDF o HTML

**Clase utilitaria InformeUtil.java**

Se ha creado una clase utilitaria que simplifica la generación de informes:

```java
public class InformeUtil {
    public static void generarInforme(
        String rutaJasper,
        Map<String, Object> parametros,
        int tipo
    ) {
        // Conexión a BD
        Connection conn = ConexionBD.getInstance().getConnection();

        // Cargar informe compilado
        JasperReport report = (JasperReport) JRLoader.loadObject(
            new File(rutaJasper)
        );

        // Llenar con datos
        JasperPrint print = JasperFillManager.fillReport(
            report, parametros, conn
        );

        // Exportar a PDF
        JasperExportManager.exportReportToPdfFile(
            print, nombreArchivo + ".pdf"
        );

        // Exportar a HTML
        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(
            new SimpleHtmlExporterOutput(nombreArchivo + ".html")
        );
        exporter.exportReport();
    }
}
```

**Características avanzadas utilizadas**

1. **Parámetros SQL**: El informe de vehículos utiliza parámetros para filtrado dinámico
2. **Imágenes desde Base64**: El informe de piezas decodifica y muestra imágenes almacenadas en la BD
3. **Gráficos integrados**: El informe de inventario incluye BarChart y PieChart
4. **Agrupación de datos**: Subtotales por vehículo en el informe de inventario
5. **Formateo condicional**: Colores dinámicos según el estado del vehículo

**Ventajas de su uso**

- **Profesionalidad**: Los informes tienen calidad de producción
- **Flexibilidad**: Soporte para múltiples formatos de salida
- **Potencia**: Capacidad para manejar grandes volúmenes de datos
- **Personalización**: Control total sobre el diseño y contenido

---

<div style="page-break-after: always;"></div>

## 8. CONCLUSIONES

### 8.1. Resultados obtenidos

El proyecto AutoCiclo ha cumplido satisfactoriamente todos los objetivos planteados al inicio del desarrollo. Se ha logrado crear una aplicación de escritorio completa, funcional y profesional que demuestra el dominio de las siguientes competencias:

**Desarrollo de interfaces gráficas con JavaFX**

Se ha implementado una interfaz moderna y atractiva utilizando FXML para la estructura, CSS para el diseño visual, y controladores Java para la lógica de presentación. El uso de componentes avanzados como TableView, TabPane, ComboBox, DatePicker y Charts ha permitido crear una experiencia de usuario rica e intuitiva.

**Persistencia de datos con bases de datos relacionales**

La integración con MySQL mediante JDBC ha sido exitosa, implementando un esquema relacional normalizado con tres tablas y relaciones de integridad referencial. El manejo de transacciones, consultas SQL complejas con JOIN, y el almacenamiento de imágenes en Base64 demuestran un conocimiento sólido de bases de datos.

**Arquitectura de software**

La aplicación estructura el código siguiendo el patrón MVC y una arquitectura por capas, lo que facilita el mantenimiento, la escalabilidad y la comprensión del sistema. La separación de responsabilidades entre modelos, vistas y controladores es clara y consistente.

**Uso de bibliotecas externas**

La integración de bibliotecas como Ikonli, Gson y JasperReports ha enriquecido significativamente las funcionalidades de la aplicación, demostrando la capacidad de incorporar herramientas de terceros para soluciones específicas.

**Generación de informes profesionales**

Los tres informes implementados con JasperReports cumplen con todos los requisitos solicitados: informe simple con imágenes, informe condicional con parámetros SQL, y SQL compuesta con gráficos. La exportación a PDF y HTML amplía las posibilidades de distribución de la información.

### 8.2. Dificultades encontradas

Durante el desarrollo del proyecto se han encontrado y superado diversos desafíos:

**Almacenamiento de imágenes en Base64**

Uno de los retos técnicos más significativos fue implementar el almacenamiento de imágenes directamente en la base de datos mediante codificación Base64. Fue necesario investigar las mejores prácticas para la codificación y decodificación eficiente, así como el manejo del espacio en disco que requieren los campos LONGTEXT.

**Actualización automática de stock**

Garantizar la coherencia de datos al asignar piezas a vehículos requirió implementar triggers en la base de datos o lógica en la aplicación para actualizar automáticamente el stock disponible. Se optó por la segunda opción para tener mayor control desde Java.

**Diseño de informes con JasperReports**

La curva de aprendizaje de JasperSoft Studio fue pronunciada, especialmente para:

- Configurar correctamente la conexión a la base de datos
- Diseñar consultas SQL con parámetros
- Integrar gráficos y formatear el layout
- Decodificar imágenes Base64 para mostrarlas en el informe

**Validación de formularios**

Implementar validaciones robustas en tiempo real que proporcionen feedback inmediato al usuario sin resultar intrusivas requirió múltiples iteraciones en el diseño de los controladores de formularios.

**Gestión de dependencias**

Configurar correctamente Gradle con todas las dependencias necesarias (JavaFX, MySQL Connector, Ikonli, Gson, JasperReports) y asegurar la compatibilidad entre versiones fue un proceso que requirió atención al detalle.

### 8.3. Mejoras futuras

Aunque AutoCiclo es una aplicación completamente funcional, existen varias áreas donde se podrían implementar mejoras:

**Módulo de clientes y ventas**

Ampliar el sistema para gestionar también la venta de piezas a clientes, incluyendo:

- Registro de clientes
- Generación de presupuestos y facturas
- Histórico de ventas
- Control de cobros

**Panel de administración multiusuario**

Implementar un sistema de usuarios con diferentes niveles de permisos:

- Administrador: acceso total
- Empleado: solo consulta e inventario
- Facturación: solo módulo de ventas

**Integración con APIs externas**

Conectar con servicios externos para:

- Verificación automática de matrículas
- Actualización de precios de mercado de piezas
- Envío de informes por correo electrónico

**Aplicación móvil complementaria**

Desarrollar una aplicación Android/iOS que permita:

- Consultar inventario desde cualquier lugar
- Escaneo de códigos QR de piezas
- Fotografía y alta rápida de nuevas piezas desde el almacén

**Dashboard analytics avanzado**

Ampliar el módulo de estadísticas con:

- Gráficos de tendencias temporales
- Predicción de stock mediante machine learning
- Indicadores KPI del negocio
- Informes automáticos programados

**Exportación de datos**

Implementar funcionalidades de backup y exportación:

- Exportación a Excel de las tablas
- Backup automático de la base de datos
- Importación masiva desde CSV

---

<div style="page-break-after: always;"></div>

## 9. REFERENCIAS

**Documentación oficial**

- Oracle JavaFX Documentation  
  [https://openjfx.io/](https://openjfx.io/)

- MySQL 8.0 Reference Manual  
  [https://dev.mysql.com/doc/refman/8.0/en/](https://dev.mysql.com/doc/refman/8.0/en/)

- Gradle Build Tool  
  [https://gradle.org/](https://gradle.org/)

**Bibliotecas utilizadas**

- Ikonli - Icon packs for JavaFX  
  [https://kordamp.org/ikonli/](https://kordamp.org/ikonli/)

- Gson - JSON Library for Java  
  [https://github.com/google/gson](https://github.com/google/gson)

- JasperReports Community Edition  
  [https://community.jaspersoft.com/](https://community.jaspersoft.com/)

**Herramientas de desarrollo**

- Scene Builder - JavaFX Visual Layout Tool  
  [https://gluonhq.com/products/scene-builder/](https://gluonhq.com/products/scene-builder/)

- JasperSoft Studio  
  [https://community.jaspersoft.com/downloads](https://community.jaspersoft.com/downloads)

**Recursos de aprendizaje**

- JavaFX Tutorial - Jenkov.com  
  [http://tutorials.jenkov.com/javafx/](http://tutorials.jenkov.com/javafx/)

- JasperReports Tutorial - TutorialsPoint  
  [https://www.tutorialspoint.com/jasper_reports/](https://www.tutorialspoint.com/jasper_reports/)

- MySQL Connector/J Documentation  
  [https://dev.mysql.com/doc/connector-j/8.0/en/](https://dev.mysql.com/doc/connector-j/8.0/en/)

---

**FIN DEL DOCUMENTO**

---

© 2026 Yalil Musa Talhaoui - IES Politécnico Hermenegildo Lanz (Granada)  
Desarrollo de Aplicaciones Multiplataforma - Desarrollo de Interfaces
