# AutoCiclo 🚗

Sistema de gestión integral para desguaces de automóviles desarrollado con JavaFX.

## 📋 Descripción

**AutoCiclo** es una aplicación de escritorio que permite gestionar el inventario completo de un desguace de automóviles. El sistema facilita el control de vehículos, piezas extraídas y su asignación, ofreciendo una interfaz moderna e intuitiva.

## 🎯 Funcionalidades Principales

### Gestión de Vehículos

- Alta, baja y modificación de vehículos
- Registro de marca, modelo, matrícula, año, color y estado
- Control de ubicación física en el desguace
- Estados: Completo, Desguazando, Desguazado

### Gestión de Piezas

- Inventario completo de piezas extraídas
- Categorización: Motor, Carrocería, Interior, Electrónica, Ruedas, Otros
- Control de stock disponible y stock mínimo
- **Almacenamiento de imágenes en Base64** directamente en la base de datos
- Ubicación en almacén con desplegable predefinido

### Asignación de Inventario

- Vinculación de piezas a vehículos de origen
- Registro de fecha de extracción, cantidad, estado y precio

### Estadísticas Visuales

- Gráficos de barras y circulares con datos en tiempo real
- Visualización del estado del inventario

### Otras Características

- Búsqueda y filtrado en tiempo real
- Formularios con validación de datos
- Atajos de teclado (Ctrl+N, Ctrl+E, Ctrl+D, etc.)
- Pantalla de carga animada
- Diálogo "Acerca de" con información del desarrollador

## 🛠️ Tecnologías Utilizadas

| Tecnología    | Versión | Uso                           |
| ------------- | ------- | ----------------------------- |
| Java          | 21      | Lenguaje principal            |
| JavaFX        | 21      | Framework de interfaz gráfica |
| MySQL         | 8.0     | Base de datos                 |
| Gradle        | 8.8     | Sistema de construcción       |
| Gson          | 2.10.1  | Parseo de JSON                |
| Ikonli        | 12.3.1  | Iconos Material Design        |
| Scene Builder | -       | Diseño de FXML                |

## 📁 Estructura del Proyecto

```
AutoCiclo/
├── app/
│   └── src/main/
│       ├── java/com/autociclo/
│       │   ├── Main.java                 # Punto de entrada
│       │   ├── controllers/              # Controladores FXML
│       │   ├── database/                 # Conexión a BD
│       │   ├── models/                   # Clases de modelo
│       │   └── utils/                    # Utilidades
│       └── resources/
│           ├── css/                      # Estilos CSS
│           ├── fxml/                     # Vistas FXML
│           ├── imagenes/                 # Recursos gráficos
│           ├── ubicaciones.json          # Ubicaciones predefinidas
│           └── vehiculos.json            # Marcas y modelos
├── autociclo_db.sql                      # Script de base de datos
├── build.gradle                          # Configuración Gradle
└── README.md
```

## 🚀 Instalación y Ejecución

### Requisitos Previos

- JDK 21 o superior
- MySQL 8.0 (puede usarse con Docker)
- Gradle 8.x

### Pasos de Instalación

1. **Clonar el repositorio:**

```bash
git clone https://github.com/yalilms/AutoCiclo.git
cd AutoCiclo
```

2. **Configurar la base de datos:**

```bash
# Usando Docker (recomendado)
docker-compose up -d

# O importar manualmente en MySQL
mysql -u root -p < autociclo_db.sql
```

3. **Configurar conexión (si es necesario):**

   Editar `app/src/main/java/com/autociclo/database/ConexionBD.java`:

   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/autociclo_db";
   private static final String USER = "root";
   private static final String PASSWORD = "tu_password";
   ```

4. **Compilar y ejecutar:**

```bash
./gradlew run
```

## 🗄️ Base de Datos

### Diagrama de Tablas

```
VEHICULOS (1) ──────< (N) INVENTARIO_PIEZAS (N) >────── (1) PIEZAS
```

### Tablas Principales

- **VEHICULOS**: Almacena vehículos con matrícula, marca, modelo, estado, ubicación
- **PIEZAS**: Catálogo de piezas con código, categoría, precio, stock, ubicación, imagen (LONGTEXT para Base64)
- **INVENTARIO_PIEZAS**: Relación N:M entre vehículos y piezas con datos de extracción

## ⌨️ Atajos de Teclado

| Atajo           | Función               |
| --------------- | --------------------- |
| Ctrl + N        | Nuevo registro        |
| Ctrl + E        | Editar seleccionado   |
| Ctrl + D / Supr | Eliminar seleccionado |
| Ctrl + F        | Enfocar búsqueda      |
| Ctrl + R        | Refrescar listado     |
| Ctrl + 1/2/3/4  | Cambiar vista         |
| Esc             | Cancelar operación    |

## 📸 Capturas de Pantalla

_La aplicación incluye una interfaz moderna con tema oscuro, formularios validados y gráficos estadísticos._

## 👤 Autor

**Yalil Musa Talhaoui**

- GitHub: [@yalilms](https://github.com/yalilms)
- Curso: 2º DAM - Desarrollo de Aplicaciones Multiplataforma
- Asignatura: Desarrollo de Interfaces

## 📄 Licencia

Proyecto académico desarrollado para el IES Hermenegildo Lanz (Granada) - Curso 2024/2025

© 2025 AutoCiclo - Todos los derechos reservados
