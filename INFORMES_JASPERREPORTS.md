# Documentación de Informes - AutoCiclo

## 1. Interfaz de Informes

La interfaz de informes se encuentra en la pestaña **"Informes"** de la aplicación principal.



### Controles disponibles:
- **ComboBox "Seleccionar informe"**: Permite elegir entre los 3 tipos de informe
- **ComboBox "Filtrar por"**: Filtro condicional que varía según el informe seleccionado
- **CheckBox "Incrustado"**: Si está marcado, muestra el informe dentro de la ventana principal. Si no, lo abre en una ventana modal
- **Botón "Generar Informe"**: Genera el informe con los filtros seleccionados
- **Botón "Abrir Informe"**: Abre el informe en ventana modal (aparece tras generar)
- **Botón "Exportar PDF"**: Guarda el informe en PDF en la carpeta `informes/exportados/`

---

## 2. Informes Disponibles

### 2.1 Informe de Piezas (Informe Simple con Gráfica)

**Ubicación del botón**: Pestaña Informes → ComboBox → "Informe de Piezas"

**Descripción**: Listado de todas las piezas del catálogo agrupadas por categoría, mostrando el stock real (cantidad en inventario).

**Filtro condicional**: Por **Categoría** (motor, carrocería, interior, electrónica, ruedas, otros)

**SQL asociada**:
```sql
SELECT
    p.id_pieza as idPieza,
    p.codigo_pieza as codigoPieza,
    p.nombre,
    p.categoria,
    p.precio_venta as precioVenta,
    COALESCE(SUM(ip.cantidad), 0) as stockReal,
    p.stock_minimo as stockMinimo,
    p.ubicacion_almacen as ubicacionAlmacen,
    p.compatible_marcas as compatibleMarcas,
    p.imagen,
    p.descripcion
FROM PIEZAS p
LEFT JOIN INVENTARIO_PIEZAS ip ON p.id_pieza = ip.id_pieza
WHERE p.categoria LIKE $P{P_CATEGORIA}
GROUP BY p.id_pieza, p.codigo_pieza, p.nombre, p.categoria, p.precio_venta,
         p.stock_minimo, p.ubicacion_almacen, p.compatible_marcas, p.imagen, p.descripcion
ORDER BY p.categoria, p.nombre
```

**Gráfica incluida**: Gráfico circular (Pie Chart) mostrando distribución de piezas por categoría.

**Tipo**: NO incrustado (puede verse en modal)

---

### 2.2 Informe de Vehículos (Informe Condicional con Gráfica)

**Ubicación del botón**: Pestaña Informes → ComboBox → "Informe de Vehículos"

**Descripción**: Listado de vehículos del desguace con información detallada y estado con colores condicionales.

**Filtro condicional**: Por **Marca** (Renault, Ford, Honda, Toyota, Peugeot, etc.)

**SQL asociada**:
```sql
SELECT
    id_vehiculo as idVehiculo,
    matricula,
    marca,
    modelo,
    anio,
    color,
    fecha_entrada as fechaEntrada,
    estado,
    precio_compra as precioCompra,
    kilometraje,
    ubicacion_gps as ubicacionGps,
    observaciones
FROM VEHICULOS
WHERE estado LIKE $P{P_ESTADO}
  AND marca LIKE $P{P_MARCA}
  AND anio BETWEEN $P{P_ANIO_MIN} AND $P{P_ANIO_MAX}
ORDER BY fecha_entrada DESC, marca, modelo
```

**Comportamiento condicional del estado**:
- Estado **"completo"** → Fondo verde (#4CAF50)
- Estado **"desguazando"** → Fondo naranja (#FFA726)
- Estado **"desguazado"** → Fondo rojo (#E57373)

**Gráfica incluida**: Gráfico de barras mostrando cantidad de vehículos por marca.

**Tipo**: Incrustado (se muestra dentro de la aplicación)

---

### 2.3 Informe de Inventario (SQL Compuesta - 3 Tablas)

**Ubicación del botón**: Pestaña Informes → ComboBox → "Informe de Inventario"

**Descripción**: Relación de piezas extraídas por vehículo con valoración económica. Combina datos de las 3 tablas: VEHICULOS, PIEZAS e INVENTARIO_PIEZAS.

**Filtro condicional**: Por **Estado de la pieza** (nueva, usada, reparada)

**SQL asociada (compuesta con JOIN de 3 tablas)**:
```sql
SELECT
    v.matricula,
    v.marca AS marcaVehiculo,
    v.modelo,
    v.anio,
    v.estado AS estadoVehiculo,
    p.codigo_pieza AS codigoPieza,
    p.nombre AS nombrePieza,
    p.categoria,
    p.precio_venta AS precioVenta,
    ip.cantidad,
    ip.estado_pieza AS estadoPieza,
    (p.precio_venta * ip.cantidad) AS valorTotal
FROM INVENTARIO_PIEZAS ip
INNER JOIN VEHICULOS v ON ip.id_vehiculo = v.id_vehiculo
INNER JOIN PIEZAS p ON ip.id_pieza = p.id_pieza
WHERE ip.estado_pieza LIKE $P{P_ESTADO_PIEZA}
ORDER BY v.matricula, p.categoria, p.nombre
```

**Gráfica incluida**: Ninguna (informe de datos relacionales)

**Tipo**: NO incrustado

---

## 3. Exportación de Informes

Los informes se pueden exportar a PDF haciendo clic en el botón **"Exportar PDF"**.

**Ruta de guardado**: `app/src/main/resources/informes/exportados/`

**Formato del nombre**: `NombreInforme_YYYY-MM-DD_HH-mm-ss.pdf`

Ejemplo: `InformePiezas_2026-01-19_13-30-45.pdf`

---

## 4. Resumen de Requisitos Cumplidos

| Requisito | Informe | Cumplido |
|-----------|---------|----------|
| Informe Simple | Piezas | ✅ |
| Informe Condicional (filtros variables) | Vehículos, Piezas, Inventario | ✅ |
| SQL Compuesta (JOIN 3 tablas) | Inventario | ✅ |
| Gráficas | Piezas (Pie), Vehículos (Barras) | ✅ |
| Incrustado | Vehículos | ✅ |
| No Incrustado (Modal) | Piezas, Inventario | ✅ |

---


**Autor**: Yalil
**Asignatura**: Desarrollo de Interfaces - 2º DAM
**Fecha**: 20 Enero 2026
