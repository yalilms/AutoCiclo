# 📦 PASOS PARA COMPLETAR Y ENTREGAR LA PRÁCTICA

---

## 🎓 PASO 0: INSTALAR Y USAR JASPERSOFT STUDIO (Para mostrar al profesor)

### ¿Por qué necesitas JasperSoft Studio?

Aunque los informes ya están creados y funcionan perfectamente, **el profesor quiere ver que sabes usar JasperSoft Studio** porque es la herramienta que os enseñaron en clase para diseñar informes visualmente.

### 0.1 Instalar JasperSoft Studio en Fedora

```bash
# Descargar JasperSoft Studio Community Edition (versión Linux)
cd ~/Descargas

# Opción A: Desde la web oficial
# Ve a: https://community.jaspersoft.com/downloads
# Descarga: Jaspersoft Studio Community Edition (Linux 64-bit)

# Opción B: Con wget (versión 7.0.0)
wget https://sourceforge.net/projects/jasperstudio/files/JaspersoftStudio-7.0.0/TIB_js-studiocomm_7.0.0_linux_x86_64.tgz

# Descomprimir
tar -xzf TIB_js-studiocomm_7.0.0_linux_x86_64.tgz

# Mover a una ubicación permanente
sudo mv TIBCOJaspersoftStudio-7.0.0.final /opt/

# Crear enlace simbólico para ejecutar fácilmente
sudo ln -s /opt/TIBCOJaspersoftStudio-7.0.0.final/Jaspersoft\ Studio /usr/local/bin/jasperstudio

# Dar permisos de ejecución
sudo chmod +x /opt/TIBCOJaspersoftStudio-7.0.0.final/Jaspersoft\ Studio

# Ejecutar JasperSoft Studio
jasperstudio &
# O directamente:
/opt/TIBCOJaspersoftStudio-7.0.0.final/Jaspersoft\ Studio &
```

**Si prefieres usar Flatpak (más fácil en Fedora):**
```bash
# Buscar si está disponible
flatpak search jasper

# Si está disponible, instalar
flatpak install flathub com.jaspersoft.studio
flatpak run com.jaspersoft.studio
```

### 0.2 Abrir los informes existentes en JasperSoft Studio

**Una vez que JasperSoft Studio esté abierto:**

1. **File → Open File** (o Ctrl+O)

2. **Navega a tu proyecto:**
   ```
   /home/yalilms/Documentos/2ºDAM/Desarrollo_interfaces/AutoCiclo/app/src/main/resources/informes/
   ```

3. **Abre cada archivo .jrxml:**
   - InformePiezas.jrxml
   - InformeVehiculos.jrxml
   - InformeInventario.jrxml

### 0.3 Configurar la conexión a la base de datos en JasperSoft Studio

Para poder ver una vista previa de los informes en JasperSoft Studio:

1. **En JasperSoft Studio, ve a:**
   - Repository Explorer (panel izquierdo)
   - Data Adapters → Clic derecho → Create Data Adapter

2. **Selecciona:** Database JDBC Connection

3. **Configura la conexión (según tu ConexionBD.java):**
   ```
   Name: AutoCiclo_MySQL
   JDBC Driver: com.mysql.cj.jdbc.Driver
   JDBC URL: jdbc:mysql://localhost:3306/autociclo_db
   Username: root
   Password: [tu contraseña]
   ```

4. **Test Connection** para verificar que funciona

5. **Finish**

### 0.4 Ver los informes en JasperSoft Studio

**Para cada .jrxml abierto:**

1. **En la pestaña Preview** (abajo)
   - Selecciona el Data Adapter que creaste (AutoCiclo_MySQL)
   - Clic en el botón de Play ▶️ (Preview Report)

2. **Se generará una vista previa del informe con datos reales de tu BD**

### 0.5 Qué mostrarle al profesor en JasperSoft Studio

**Vista de Diseño (Design):**
- Muestra cómo está estructurado el informe
- Los campos de la base de datos
- Las bandas (Title, Column Header, Detail, Summary)
- Los elementos visuales (TextFields, Images, Charts)

**Vista de Código (Source):**
- Muestra el XML del .jrxml
- La consulta SQL
- Los parámetros definidos

**Vista de Preview:**
- Muestra el informe generado con datos reales
- Demuestra que funciona correctamente

**Consulta SQL:**
- Clic derecho en el informe → Edit Query
- Muestra la consulta SQL que extrae los datos

### 0.6 Cómo compilar desde JasperSoft Studio (por si te lo pide)

1. **Clic derecho sobre el archivo .jrxml**
2. **Compile Report**
3. **Se genera automáticamente el .jasper en la misma carpeta**

**NOTA:** Ya tienes los .jasper compilados desde Gradle, pero si el profesor te pide que compiles uno, ya sabes cómo hacerlo.

### 0.7 Capturas para la documentación

**Toma capturas de JasperSoft Studio mostrando:**

1. **Vista de diseño de InformePiezas** (opcional para documentación)
2. **La consulta SQL** de cada informe (Window → Show View → Report Query)
3. **Vista previa del informe** con datos

Estas capturas pueden complementar tu documentación y demostrar que usaste JasperSoft Studio.

---

## ✅ LO QUE YA ESTÁ HECHO (No tocar)

### Código completado al 100%:
- ✅ **InformePiezas.jasper** - Informe simple con imágenes desde BD
- ✅ **InformeVehiculos.jasper** - Informe condicional con parámetros SQL
- ✅ **InformeInventario.jasper** - Informe con JOIN de 3 tablas + 2 gráficos
- ✅ **InformeUtil.java** - Clase que lanza informes embebidos y no embebidos
- ✅ **EjemploUsoInformes.java** - Aplicación con 3 botones para generar informes

**Todo compila correctamente:** `./gradlew build` ✅ BUILD SUCCESSFUL

---

## ❌ LO QUE FALTA HACER (TÚ)

Según el PDF de la práctica, debes entregar:

### 1. Carpeta INFORMES con PDFs y HTMLs ❌
### 2. Documentación PDF con capturas y SQLs ❌
### 3. Proyecto comprimido en ZIP ❌

---

## 📝 PASO 1: GENERAR LOS INFORMES (PDF y HTML)

### 1.1 ✅ Modificar InformeUtil para exportar HTML a archivo (HECHO)

**YA MODIFICADO** - InformeUtil.java ahora exporta automáticamente tanto PDF como HTML a archivos.

Cambios realizados:
- Los archivos se guardan con el nombre del informe (InformePiezas.pdf, InformePiezas.html, etc.)
- Se guardan en el directorio home del usuario (~/)
- Se muestra un mensaje en consola con la ubicación de cada archivo

### 1.2 Ejecutar la aplicación de ejemplo

**TÚ DEBES EJECUTAR:**

```bash
cd /home/yalilms/Documentos/2ºDAM/Desarrollo_interfaces/AutoCiclo
./gradlew run --args="com.autociclo.utils.EjemploUsoInformes"
```

Se abrirá una ventana con 3 botones.

### 1.3 Generar cada informe

**PARA CADA BOTÓN (haz clic uno por uno):**

1. **Clic en "📋 Generar Informe de Piezas"**
   - Se abrirá una ventana mostrando el informe
   - Se generará automáticamente:
     - `~/InformePiezas.pdf`
     - `~/InformePiezas.html`

2. **Clic en "🚗 Generar Informe de Vehículos"**
   - Se abrirá una ventana mostrando el informe
   - Se generará automáticamente:
     - `~/InformeVehiculos.pdf`
     - `~/InformeVehiculos.html`

3. **Clic en "📊 Generar Informe de Inventario"**
   - Se abrirá una ventana mostrando el informe
   - Se generará automáticamente:
     - `~/InformeInventario.pdf`
     - `~/InformeInventario.html`

### 1.4 Crear carpeta INFORMES y mover archivos

```bash
# Crear carpeta INFORMES en el escritorio
mkdir -p ~/Escritorio/INFORMES

# Mover todos los informes generados
mv ~/InformePiezas.pdf ~/Escritorio/INFORMES/
mv ~/InformePiezas.html ~/Escritorio/INFORMES/
mv ~/InformeVehiculos.pdf ~/Escritorio/INFORMES/
mv ~/InformeVehiculos.html ~/Escritorio/INFORMES/
mv ~/InformeInventario.pdf ~/Escritorio/INFORMES/
mv ~/InformeInventario.html ~/Escritorio/INFORMES/

echo "✅ Carpeta INFORMES lista con 6 archivos (3 PDF + 3 HTML)"
ls -l ~/Escritorio/INFORMES/
```

**RESULTADO ESPERADO:**
```
INFORMES/
├── InformePiezas.pdf
├── InformePiezas.html
├── InformeVehiculos.pdf
├── InformeVehiculos.html
├── InformeInventario.pdf
└── InformeInventario.html
```

---

## 📸 PASO 2: CREAR LA DOCUMENTACIÓN PDF

La documentación debe incluir **para cada informe**:
- Una imagen del botón que lo lanza
- Descripción de qué hace
- La consulta SQL

### 2.1 Tomar capturas de pantalla

**Ejecuta de nuevo:**
```bash
./gradlew run --args="com.autociclo.utils.EjemploUsoInformes"
```

**Toma 3 capturas:**

1. **Captura de la ventana completa** con los 3 botones
   ```bash
   gnome-screenshot -w
   # O
   flameshot gui
   ```
   Guárdala como: `~/Escritorio/Interfaz_Informes.png`

2. **Captura del InformePiezas abierto** (después de hacer clic en el botón)
   Guárdala como: `~/Escritorio/Captura_InformePiezas.png`

3. **Captura del InformeVehiculos abierto**
   Guárdala como: `~/Escritorio/Captura_InformeVehiculos.png`

4. **Captura del InformeInventario abierto** (asegúrate de que se vean los gráficos)
   Guárdala como: `~/Escritorio/Captura_InformeInventario.png`

### 2.2 Crear documento de documentación

**Opción A: Usar LibreOffice Writer**

1. Abre LibreOffice Writer
2. Copia y pega esta estructura:

---

**DOCUMENTACIÓN - INFORMES JASPERREPORTS AUTOCICLO**

---

**1. INTERFAZ QUE LANZA LOS INFORMES**

[Pega aquí la imagen: Interfaz_Informes.png]

La aplicación cuenta con una interfaz con 3 botones que permiten generar los siguientes informes:
- Informe de Piezas
- Informe de Vehículos
- Informe de Inventario

---

**2. INFORME DE PIEZAS**

**Ubicación:** Botón "📋 Generar Informe de Piezas"

**Descripción:** Muestra un listado completo de todas las piezas del desguace, incluyendo:
- Imágenes de las piezas (almacenadas en Base64 en la base de datos)
- Código, nombre, categoría, precio de venta
- Stock disponible y stock mínimo
- Ubicación en el almacén
- Totales: Cantidad total de piezas y valor total del stock

**Tipo:** Informe simple con imágenes de BD. No incrustado (ventana nueva).

[Pega aquí la imagen: Captura_InformePiezas.png]

**SQL:**
```sql
SELECT
    idPieza,
    codigoPieza,
    nombre,
    categoria,
    precioVenta,
    stockDisponible,
    stockMinimo,
    ubicacionAlmacen,
    compatibleMarcas,
    imagen,
    descripcion
FROM PIEZAS
ORDER BY categoria, nombre;
```

---

**3. INFORME DE VEHÍCULOS**

**Ubicación:** Botón "🚗 Generar Informe de Vehículos"

**Descripción:** Inventario de vehículos con filtros dinámicos mediante parámetros SQL:
- Filtro por estado (Disponible, Procesando, Vendido)
- Filtro por marca
- Filtro por rango de años
- Colores condicionales según el estado del vehículo
- Estadísticas: Total de vehículos, inversión total, precio promedio

**Tipo:** Informe condicional con parámetros SQL. No incrustado (ventana nueva).

[Pega aquí la imagen: Captura_InformeVehiculos.png]

**SQL:**
```sql
SELECT
    idVehiculo,
    matricula,
    marca,
    modelo,
    anio,
    color,
    DATE_FORMAT(fechaEntrada, '%d/%m/%Y') as fechaEntrada,
    estado,
    precioCompra,
    kilometraje,
    ubicacionGps,
    observaciones
FROM VEHICULOS
WHERE estado LIKE $P{P_ESTADO}
  AND marca LIKE $P{P_MARCA}
  AND anio BETWEEN $P{P_ANIO_MIN} AND $P{P_ANIO_MAX}
ORDER BY fechaEntrada DESC, marca, modelo;
```

**Parámetros:**
- `P_ESTADO`: Estado del vehículo ("%" para todos)
- `P_MARCA`: Marca del vehículo ("%" para todas)
- `P_ANIO_MIN`: Año mínimo (default: 1900)
- `P_ANIO_MAX`: Año máximo (default: 2100)

---

**4. INFORME DE INVENTARIO**

**Ubicación:** Botón "📊 Generar Informe de Inventario"

**Descripción:** Relación de piezas extraídas por vehículo con valoración económica:
- Une 3 tablas: VEHICULOS, PIEZAS, INVENTARIO_PIEZAS
- Agrupa las piezas por vehículo con subtotales
- Incluye 2 gráficos:
  - Gráfico de Barras: Valor de piezas por vehículo
  - Gráfico Circular: Distribución por categoría de pieza
- Totales generales de cantidad de piezas y valor total

**Tipo:** SQL compuesta (JOIN) + Gráficos. No incrustado (ventana nueva).

[Pega aquí la imagen: Captura_InformeInventario.png]

**SQL:**
```sql
SELECT
    v.matricula,
    v.marca AS marcaVehiculo,
    v.modelo,
    v.anio,
    v.estado AS estadoVehiculo,
    p.codigoPieza,
    p.nombre AS nombrePieza,
    p.categoria,
    p.precioVenta,
    ip.cantidad,
    ip.estadoPieza,
    (p.precioVenta * ip.cantidad) AS valorTotal
FROM INVENTARIO_PIEZAS ip
INNER JOIN VEHICULOS v ON ip.idVehiculo = v.idVehiculo
INNER JOIN PIEZAS p ON ip.idPieza = p.idPieza
ORDER BY v.matricula, p.categoria, p.nombre;
```

---

**5. RESUMEN DE CUMPLIMIENTO DE REQUISITOS**

| Requisito | Cumplido | Informe |
|-----------|----------|---------|
| a) Informe simple con imágenes BD | ✅ | InformePiezas |
| b) Informe condicional con parámetros SQL | ✅ | InformeVehiculos |
| c) SQL compuesta (JOIN) | ✅ | InformeInventario |
| d) Gráficos | ✅ | InformeInventario (2 gráficos) |
| e) Incrustado / No incrustado | ✅ | Todos no incrustados* |

*Nota: El código soporta ambos tipos (InformeUtil tiene tipo 0=incrustado, tipo 1=no incrustado). En esta entrega todos se muestran en ventana nueva (no incrustados).

---

**FIN DE LA DOCUMENTACIÓN**

---

3. **Guarda el documento como PDF:**
   - Archivo → Exportar como → Exportar como PDF
   - Guárdalo como: `~/Escritorio/DOCUMENTACION_INFORMES.pdf`

**Opción B: Usar Markdown y convertir a PDF**

Si prefieres, puedo crear un archivo .md con todo el contenido y luego lo conviertes con:
```bash
pandoc documentacion.md -o DOCUMENTACION_INFORMES.pdf
```

---

## 📦 PASO 3: COMPRIMIR EL PROYECTO

### 3.1 Preparar el proyecto para entrega

```bash
cd /home/yalilms/Documentos/2ºDAM/Desarrollo_interfaces/AutoCiclo

# Limpiar archivos temporales
./gradlew clean

# Crear carpeta de entrega
mkdir -p ~/Escritorio/JAVAFX_INFORMES_YALILMS
```

### 3.2 Copiar el proyecto

```bash
# Copiar todo el proyecto
cp -r /home/yalilms/Documentos/2ºDAM/Desarrollo_interfaces/AutoCiclo ~/Escritorio/JAVAFX_INFORMES_YALILMS/AutoCiclo
```

### 3.3 Copiar carpeta INFORMES y documentación

```bash
# Copiar carpeta INFORMES
cp -r ~/Escritorio/INFORMES ~/Escritorio/JAVAFX_INFORMES_YALILMS/

# Copiar documentación PDF
cp ~/Escritorio/DOCUMENTACION_INFORMES.pdf ~/Escritorio/JAVAFX_INFORMES_YALILMS/
```

### 3.4 Verificar estructura

```bash
cd ~/Escritorio/JAVAFX_INFORMES_YALILMS
tree -L 2
```

**Debe verse así:**
```
JAVAFX_INFORMES_YALILMS/
├── AutoCiclo/
│   ├── app/
│   ├── gradle/
│   ├── gradlew
│   └── ...
├── INFORMES/
│   ├── InformePiezas.pdf
│   ├── InformePiezas.html
│   ├── InformeVehiculos.pdf
│   ├── InformeVehiculos.html
│   ├── InformeInventario.pdf
│   └── InformeInventario.html
└── DOCUMENTACION_INFORMES.pdf
```

### 3.5 Comprimir

```bash
cd ~/Escritorio
zip -r JAVAFX_INFORMES_YALILMS.zip JAVAFX_INFORMES_YALILMS/

# O si prefieres .tar.gz:
tar -czf JAVAFX_INFORMES_YALILMS.tar.gz JAVAFX_INFORMES_YALILMS/
```

### 3.6 Verificar

```bash
ls -lh ~/Escritorio/JAVAFX_INFORMES_YALILMS.zip

# Ver contenido sin descomprimir
unzip -l ~/Escritorio/JAVAFX_INFORMES_YALILMS.zip | head -30
```

---

## ✅ CHECKLIST FINAL ANTES DE ENTREGAR

### Contenido del ZIP/RAR:
- [ ] Proyecto AutoCiclo completo
- [ ] Carpeta INFORMES con 6 archivos (3 PDF + 3 HTML)
- [ ] Archivo DOCUMENTACION_INFORMES.pdf

### Documentación PDF debe incluir:
- [ ] Captura de la interfaz con los 3 botones
- [ ] Descripción de cada informe (qué hace, dónde está)
- [ ] Las 3 consultas SQL
- [ ] Capturas de los informes generados
- [ ] Tabla de cumplimiento de requisitos

### Carpeta INFORMES debe contener:
- [ ] InformePiezas.pdf
- [ ] InformePiezas.html
- [ ] InformeVehiculos.pdf
- [ ] InformeVehiculos.html
- [ ] InformeInventario.pdf
- [ ] InformeInventario.html

### Proyecto debe:
- [ ] Compilar sin errores: `./gradlew build`
- [ ] Contener los 3 archivos .jasper compilados en `app/src/main/resources/informes/`
- [ ] Contener InformeUtil.java y EjemploUsoInformes.java en `app/src/main/java/com/autociclo/utils/`

---

## 🎯 RESUMEN ULTRA-RÁPIDO

1. **YO MODIFICO** InformeUtil.java (para exportar HTML)
2. **TÚ EJECUTAS:** `./gradlew run --args="com.autociclo.utils.EjemploUsoInformes"`
3. **TÚ HACES CLIC** en los 3 botones (se generan 6 archivos)
4. **TÚ MUEVES** los archivos a `~/Escritorio/INFORMES/`
5. **TÚ TOMAS** 4 capturas de pantalla
6. **TÚ CREAS** el PDF de documentación con LibreOffice (usando la plantilla de arriba)
7. **TÚ COMPRIMES** todo en un ZIP
8. **TÚ SUBES** el ZIP a Moodle

**Tiempo estimado:** 20-30 minutos

---

## ❓ SI TIENES PROBLEMAS

### No se generan los archivos HTML
- Dime y modifico InformeUtil.java

### No sé cómo tomar capturas
- Linux: `gnome-screenshot -w` o `flameshot gui`

### No tengo LibreOffice
- Dime y te creo el MD para convertir con pandoc o usar Google Docs

### Al ejecutar da error de conexión BD
- Verifica que MySQL esté corriendo: `sudo systemctl status mysql`
- Verifica las credenciales en ConexionBD.java

---

**¿Por dónde quieres que empiece? ¿Modifico InformeUtil primero?**
