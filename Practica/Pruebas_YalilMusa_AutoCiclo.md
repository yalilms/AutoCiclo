# DOCUMENTO DE PRUEBAS - AutoCiclo
## Sistema de Gestión de Desguace

**Autor:** Yalil Musa Talhaoui
**Asignatura:** Desarrollo de Interfaces
**Fecha:** Enero 2026
**Versión:** 1.0

---

## ÍNDICE

1. [Corrección de Fallos del Primer Trimestre](#1-corrección-de-fallos-del-primer-trimestre)
2. [Pruebas Funcionales](#2-pruebas-funcionales)
   - 2.1 Formulario de Vehículos
   - 2.2 Formulario de Piezas
   - 2.3 Formulario de Asignación de Piezas a Vehículos
   - 2.4 Pantalla de Informes
   - 2.5 Pantalla de Estadísticas
3. [Pruebas de Sistema](#3-pruebas-de-sistema)
4. [Pruebas Alfa (Test de Guerrilla)](#4-pruebas-alfa-test-de-guerrilla)

---

## 1. CORRECCIÓN DE FALLOS DEL PRIMER TRIMESTRE

### Resumen de Fallos Detectados y Corregidos

| # | Fallo Detectado | Solución Implementada | Archivos Modificados |
|---|----------------|----------------------|---------------------|
| 1 | Falta icono de la aplicación en ventanas Alert de confirmación | Añadido listener de sceneProperty para inyectar el icono en todas las ventanas Alert | ValidationUtils.java, Main.java, ListadoMaestroController.java |
| 2 | Faltan Tooltips en todos los botones de la aplicación | Añadidos tooltips descriptivos a todos los botones y campos de formularios | Todos los archivos FXML |
| 3 | Los campos de búsqueda no indican por qué campo buscan | Añadido promptText descriptivo indicando los campos de búsqueda | ListadosController.fxml |
| 4 | Falta MnemonicParsing en todos los botones | Activado mnemonicParsing con teclas de acceso rápido (Alt+letra) en todos los botones | Todos los archivos FXML |

### Detalle de Correcciones

#### FALLO 1: Icono en Ventanas Alert
**Problema:** Las ventanas de diálogo (Alert) no mostraban el icono de la aplicación.

**Solución:** Se implementó un listener en `sceneProperty` que detecta cuando la ventana está lista y le añade el icono:
```java
dialogPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
    if (newScene != null && newScene.getWindow() != null) {
        Stage stage = (Stage) newScene.getWindow();
        stage.getIcons().add(new Image(
            ValidationUtils.class.getResourceAsStream("/imagenes/logo_autociclo.png")));
    }
});
```

#### FALLO 2: Tooltips en Botones
**Problema:** Los usuarios no tenían ayuda contextual sobre la función de los botones.

**Solución:** Se añadieron tooltips a todos los elementos interactivos:
```xml
<Button text="_Nuevo" styleClass="button-success">
    <tooltip><Tooltip text="Crear nuevo registro (Alt+N)" /></tooltip>
</Button>
```

#### FALLO 3: Campos de Búsqueda
**Problema:** El promptText del campo de búsqueda era genérico.

**Solución:** Se especificaron los campos por los que busca:
```xml
<TextField fx:id="txtBusqueda" promptText="Buscar por marca, modelo o código..."/>
```

#### FALLO 4: MnemonicParsing
**Problema:** No había atajos de teclado para los botones.

**Solución:** Se activó mnemonicParsing con guión bajo antes de la letra:
```xml
<Button mnemonicParsing="true" text="_Guardar"/>
<Button mnemonicParsing="true" text="_Cancelar"/>
```

---

## 2. PRUEBAS FUNCIONALES

### 2.1 Formulario de Vehículos

#### Campos del Formulario
| Campo | Tipo | Obligatorio | Validación |
|-------|------|-------------|------------|
| Marca | ComboBox | Sí | Debe seleccionar una opción |
| Modelo | ComboBox | Sí | Debe seleccionar una opción |
| Matrícula | TextField | Sí | Formato español: 1234ABC |
| Año | TextField | Sí | Entero entre 1900 y 2025 |
| Color | TextField | Sí | No vacío |
| Estado | ComboBox | Sí | Completo/Desguazando/Desguazado |
| Precio de Compra | TextField | Sí | Decimal >= 0 |
| Kilometraje | TextField | No | Entero >= 0 |
| Ubicación | ComboBox | No | Selección del JSON |
| Observaciones | TextArea | No | Texto libre |

#### Tabla de Pruebas Funcionales - Vehículos

| ID | Caso de Prueba | Entrada | Resultado Esperado | Resultado Obtenido | Estado |
|----|---------------|---------|-------------------|-------------------|--------|
| VEH-001 | Alta vehículo con datos válidos | Marca: Toyota, Modelo: Corolla, Matrícula: 1234ABC, Año: 2020, Color: Rojo, Estado: Completo, Precio: 5000 | Vehículo guardado correctamente. Mensaje de éxito. | Vehículo guardado. Alert de éxito mostrado. | ✓ OK |
| VEH-002 | Alta vehículo sin marca | Todos los campos excepto marca | Error de validación indicando que debe seleccionar marca | Alert con error "Debe seleccionar una marca". Borde rojo en ComboBox. | ✓ OK |
| VEH-003 | Alta vehículo sin modelo | Todos los campos excepto modelo | Error de validación indicando que debe seleccionar modelo | Alert con error "Debe seleccionar un modelo". Borde rojo en ComboBox. | ✓ OK |
| VEH-004 | Matrícula formato incorrecto | Matrícula: ABC1234 | Error de validación: formato debe ser 1234ABC | Alert con error "Formato inválido. Use: 1234ABC". Borde rojo. | ✓ OK |
| VEH-005 | Matrícula formato incorrecto (solo números) | Matrícula: 1234567 | Error de validación: formato debe ser 1234ABC | Alert con error "Formato inválido. Use: 1234ABC". Borde rojo. | ✓ OK |
| VEH-006 | Matrícula formato incorrecto (solo letras) | Matrícula: ABCDEFG | Error de validación: formato debe ser 1234ABC | Alert con error "Formato inválido. Use: 1234ABC". Borde rojo. | ✓ OK |
| VEH-007 | Matrícula duplicada | Matrícula existente en BD | Error: Ya existe un vehículo con esa matrícula | Alert "Ya existe un vehículo con esa matrícula en el sistema" | ✓ OK |
| VEH-008 | Año fuera de rango (menor) | Año: 1800 | Error de validación: año debe estar entre 1900 y 2025 | Alert "Año debe estar entre 1900 y 2025". Borde rojo. | ✓ OK |
| VEH-009 | Año fuera de rango (mayor) | Año: 2030 | Error de validación: año debe estar entre 1900 y 2025 | Alert "Año debe estar entre 1900 y 2025". Borde rojo. | ✓ OK |
| VEH-010 | Año no numérico | Año: "abc" | Error de validación: debe ser número entero | Alert "Año debe ser un número entero". Borde rojo. | ✓ OK |
| VEH-011 | Color vacío | Color: "" (vacío) | Error de validación: campo obligatorio | Alert "Color es obligatorio". Borde rojo. | ✓ OK |
| VEH-012 | Estado no seleccionado | Sin seleccionar estado | Error de validación: debe seleccionar estado | Alert "Debe seleccionar un estado". Borde rojo en ComboBox. | ✓ OK |
| VEH-013 | Precio negativo | Precio: -100 | Error de validación: debe ser >= 0 | Alert "Precio de compra debe ser mayor o igual a 0". Borde rojo. | ✓ OK |
| VEH-014 | Precio no numérico | Precio: "abc" | Error de validación: formato incorrecto | Alert "Precio de compra debe ser un número decimal". Borde rojo. | ✓ OK |
| VEH-015 | Precio con coma decimal | Precio: 5000,50 | Acepta y convierte a punto decimal | Convierte automáticamente "," a "." y guarda correctamente. | ✓ OK |
| VEH-016 | Kilometraje negativo | Kilometraje: -50000 | Error de validación: debe ser >= 0 | Alert "Kilometraje debe ser un número positivo". Borde rojo. | ✓ OK |
| VEH-017 | Kilometraje no numérico | Kilometraje: "muchos" | Error de validación: debe ser número entero | Alert "Kilometraje debe ser un número entero válido". Borde rojo. | ✓ OK |
| VEH-018 | Filtrado de marcas | Escribir "toy" en filtro | ComboBox muestra solo Toyota, si es única la selecciona | FilteredList filtra y autoselecciona si hay un único resultado. | ✓ OK |
| VEH-019 | Edición de vehículo | Modificar color de vehículo existente | Vehículo actualizado correctamente | UPDATE ejecutado. Alert "Vehículo actualizado correctamente". | ✓ OK |
| VEH-020 | Edición - matrícula no editable | Intentar modificar matrícula en modo edición | Campo matrícula deshabilitado | txtMatricula.setEditable(false) aplicado en modo edición. | ✓ OK |
| VEH-021 | Cancelar formulario | Clic en Cancelar | Ventana se cierra sin guardar | Stage.close() ejecutado. Datos no guardados. | ✓ OK |
| VEH-022 | Atajo teclado Guardar | Alt+G | Ejecuta acción Guardar | MnemonicParsing activo. Alt+G dispara guardarVehiculo(). | ✓ OK |
| VEH-023 | Atajo teclado Cancelar | Alt+C | Ejecuta acción Cancelar | MnemonicParsing activo. Alt+C dispara cerrarVentana(). | ✓ OK |
| VEH-024 | Tooltip en campo Matrícula | Hover sobre campo matrícula | Muestra "Introduce la matrícula en formato español (1234ABC)" | Tooltip visible con texto descriptivo del formato. | ✓ OK |

---

### 2.2 Formulario de Piezas

#### Campos del Formulario
| Campo | Tipo | Obligatorio | Validación |
|-------|------|-------------|------------|
| Código | TextField | Sí | Alfanumérico con guión (ej: MOT-001) |
| Nombre | TextField | Sí | No vacío |
| Categoría | ComboBox | Sí | motor/carroceria/interior/electronica/ruedas/otros |
| Precio de Compra | TextField | Sí | Decimal >= 0 |
| Stock Disponible | TextField | No | Entero >= 0 |
| Stock Mínimo | TextField | No | Entero >= 0 |
| Ubicación Almacén | ComboBox | Sí | Selección del JSON |
| Materiales Compatibles | TextField | No | Texto libre |
| Imagen | FileChooser | No | Archivos PNG/JPG/JPEG/GIF |
| Descripción | TextArea | No | Texto libre |

#### Tabla de Pruebas Funcionales - Piezas

| ID | Caso de Prueba | Entrada | Resultado Esperado | Resultado Obtenido | Estado |
|----|---------------|---------|-------------------|-------------------|--------|
| PIE-001 | Alta pieza con datos válidos | Código: MOT-001, Nombre: Motor 1.6 TDI, Categoría: motor, Precio: 2500, Ubicación: Estantería A1 | Pieza guardada correctamente. Mensaje de éxito. | Pieza guardada. Alert "Pieza registrada correctamente con código: MOT-001". | ✓ OK |
| PIE-002 | Alta pieza sin código | Todos los campos excepto código | Error de validación: código obligatorio | Alert "El código de pieza es obligatorio". Borde rojo. | ✓ OK |
| PIE-003 | Código formato incorrecto | Código: "motor uno" (espacios) | Error de validación: debe ser alfanumérico con guión | Alert "Solo letras, números y guión (-) permitidos". Borde rojo. | ✓ OK |
| PIE-004 | Código duplicado | Código existente en BD | Error: Ya existe una pieza con ese código | Alert "Ya existe una pieza con ese código en el sistema". | ✓ OK |
| PIE-005 | Nombre vacío | Nombre: "" (vacío) | Error de validación: campo obligatorio | Alert "Nombre es obligatorio". Borde rojo. | ✓ OK |
| PIE-006 | Categoría no seleccionada | Sin seleccionar categoría | Error de validación: debe seleccionar categoría | Alert "Debe seleccionar una categoría". Borde rojo en ComboBox. | ✓ OK |
| PIE-007 | Precio negativo | Precio: -50 | Error de validación: debe ser >= 0 | Alert "Precio de venta debe ser mayor o igual a 0". Borde rojo. | ✓ OK |
| PIE-008 | Precio no numérico | Precio: "caro" | Error de validación: formato incorrecto | Alert "Precio de venta debe ser un número decimal". Borde rojo. | ✓ OK |
| PIE-009 | Precio con coma decimal | Precio: 150,75 | Acepta y convierte a punto decimal | Convierte automáticamente "," a "." y guarda correctamente. | ✓ OK |
| PIE-010 | Stock disponible negativo | Stock: -5 | Error de validación: debe ser >= 0 | Alert "Stock disponible debe ser un número positivo". Borde rojo. | ✓ OK |
| PIE-011 | Stock disponible no numérico | Stock: "varios" | Error de validación: debe ser número entero | Alert "Stock disponible debe ser un número entero válido". Borde rojo. | ✓ OK |
| PIE-012 | Stock mínimo negativo | Stock mínimo: -1 | Error de validación: debe ser >= 0 | Alert "Stock mínimo debe ser un número positivo". Borde rojo. | ✓ OK |
| PIE-013 | Ubicación no seleccionada | Sin seleccionar ubicación | Error de validación: debe seleccionar ubicación | Alert "Debe seleccionar una ubicación". Borde rojo en ComboBox. | ✓ OK |
| PIE-014 | Seleccionar imagen válida | Archivo PNG válido | Ruta mostrada en campo, imagen codificada en Base64 | FileChooser abre. Imagen convertida a Base64 con prefijo MIME. | ✓ OK |
| PIE-015 | Edición de pieza | Modificar precio de pieza existente | Pieza actualizada correctamente | UPDATE ejecutado. Alert "Pieza actualizada correctamente". | ✓ OK |
| PIE-016 | Edición - código no editable | Intentar modificar código en modo edición | Campo código deshabilitado | txtCodigo.setEditable(false) aplicado en modo edición. | ✓ OK |
| PIE-017 | Cancelar formulario | Clic en Cancelar | Ventana se cierra sin guardar | Stage.close() ejecutado. Datos no guardados. | ✓ OK |
| PIE-018 | Atajo teclado Guardar | Alt+G | Ejecuta acción Guardar | MnemonicParsing activo. Alt+G dispara guardarPieza(). | ✓ OK |
| PIE-019 | Atajo teclado Cancelar | Alt+C | Ejecuta acción Cancelar | MnemonicParsing activo. Alt+C dispara cerrarVentana(). | ✓ OK |
| PIE-020 | Atajo teclado Imagen | Alt+I | Abre selector de archivos | MnemonicParsing activo. Alt+I dispara seleccionarImagen(). | ✓ OK |
| PIE-021 | Tooltip en campo Código | Hover sobre campo código | Muestra "Código único de la pieza (formato: XXX-000)" | Tooltip visible con texto descriptivo del formato. | ✓ OK |

---

### 2.3 Formulario de Asignación de Piezas a Vehículos

#### Campos del Formulario
| Campo | Tipo | Obligatorio | Validación |
|-------|------|-------------|------------|
| Vehículo | ComboBox + Filtro | Sí | Debe seleccionar vehículo existente |
| Pieza | ComboBox + Filtro | Sí | Debe seleccionar pieza existente |
| Cantidad | TextField | Sí | Entero entre 1 y 9999 |
| Estado | RadioButton | Sí | Nuevo/Usado/Reparado |
| Precio Mecánico | TextField | Sí | Decimal >= 0 |
| Fecha de Asignación | DatePicker | Sí | Fecha válida |
| Notas | TextArea | No | Texto libre |

#### Tabla de Pruebas Funcionales - Asignación

| ID | Caso de Prueba | Entrada | Resultado Esperado | Resultado Obtenido | Estado |
|----|---------------|---------|-------------------|-------------------|--------|
| ASG-001 | Asignación válida completa | Vehículo, Pieza, Cantidad: 2, Estado: Usado, Precio: 150, Fecha: hoy | Asignación guardada correctamente | INSERT ejecutado. Alert "Pieza asignada correctamente al vehículo". | ✓ OK |
| ASG-002 | Sin seleccionar vehículo | Todos excepto vehículo | Error: debe seleccionar un vehículo | Alert "Debe seleccionar un vehículo". Borde rojo en ComboBox. | ✓ OK |
| ASG-003 | Sin seleccionar pieza | Todos excepto pieza | Error: debe seleccionar una pieza | Alert "Debe seleccionar una pieza". Borde rojo en ComboBox. | ✓ OK |
| ASG-004 | Cantidad vacía | Cantidad: "" | Error: cantidad obligatoria | Alert "Cantidad es obligatorio". Borde rojo. | ✓ OK |
| ASG-005 | Cantidad = 0 | Cantidad: 0 | Error: debe estar entre 1 y 9999 | Alert "Cantidad debe estar entre 1 y 9999". Borde rojo. | ✓ OK |
| ASG-006 | Cantidad negativa | Cantidad: -5 | Error: debe estar entre 1 y 9999 | Alert "Cantidad debe estar entre 1 y 9999". Borde rojo. | ✓ OK |
| ASG-007 | Cantidad mayor a 9999 | Cantidad: 10000 | Error: debe estar entre 1 y 9999 | Alert "Cantidad debe estar entre 1 y 9999". Borde rojo. | ✓ OK |
| ASG-008 | Cantidad no numérica | Cantidad: "muchas" | Error: debe ser número entero | Alert "Cantidad debe ser un número entero". Borde rojo. | ✓ OK |
| ASG-009 | Sin estado seleccionado | Sin RadioButton marcado | Error: debe seleccionar estado | Alert "Debe seleccionar Nuevo, Usado o Reparado". | ✓ OK |
| ASG-010 | Precio mecánico negativo | Precio: -100 | Error: debe ser >= 0 | Alert "Precio unitario debe ser mayor o igual a 0". Borde rojo. | ✓ OK |
| ASG-011 | Precio mecánico no numérico | Precio: "barato" | Error: formato incorrecto | Alert "Precio unitario debe ser un número decimal". Borde rojo. | ✓ OK |
| ASG-012 | Sin fecha seleccionada | Fecha: null | Error: debe seleccionar fecha | Alert "Debe seleccionar una fecha de extracción". | ✓ OK |
| ASG-013 | Asignación duplicada | Misma pieza al mismo vehículo | Error: Ya existe esta asignación, use Editar | Alert "Ya existe esta pieza asignada a este vehículo". | ✓ OK |
| ASG-014 | Filtrado de vehículos | Escribir "123" en filtro | ComboBox muestra solo vehículos con matrícula conteniendo "123" | FilteredList filtra por matrícula. Autoselección si resultado único. | ✓ OK |
| ASG-015 | Filtrado de piezas | Escribir "MOT" en filtro | ComboBox muestra solo piezas con código conteniendo "MOT" | FilteredList filtra por código. Autoselección si resultado único. | ✓ OK |
| ASG-016 | Edición de asignación | Modificar cantidad de asignación existente | Asignación actualizada correctamente | UPDATE ejecutado. Alert "Asignación actualizada correctamente". | ✓ OK |
| ASG-017 | Edición - vehículo no editable | Intentar cambiar vehículo en edición | ComboBox deshabilitado | cmbVehiculo.setDisable(true) aplicado en modo edición. | ✓ OK |
| ASG-018 | Edición - pieza no editable | Intentar cambiar pieza en edición | ComboBox deshabilitado | cmbPieza.setDisable(true) aplicado en modo edición. | ✓ OK |
| ASG-019 | Estado por defecto | Abrir formulario nuevo | RadioButton "Usado" seleccionado por defecto | rbUsado.setSelected(true) en initialize(). | ✓ OK |
| ASG-020 | Fecha por defecto | Abrir formulario nuevo | DatePicker muestra fecha actual | dpFechaAsignacion.setValue(LocalDate.now()) en initialize(). | ✓ OK |
| ASG-021 | Atajo teclado Asignar | Alt+A | Ejecuta acción Asignar | MnemonicParsing activo. Alt+A dispara guardarInventario(). | ✓ OK |
| ASG-022 | Atajo teclado Cancelar | Alt+C | Ejecuta acción Cancelar | MnemonicParsing activo. Alt+C dispara cerrarVentana(). | ✓ OK |
| ASG-023 | Atajo teclado Nuevo | Alt+N | Selecciona estado "Nuevo" | MnemonicParsing activo en RadioButton. Alt+N selecciona rbNuevo. | ✓ OK |
| ASG-024 | Atajo teclado Usado | Alt+U | Selecciona estado "Usado" | MnemonicParsing activo en RadioButton. Alt+U selecciona rbUsado. | ✓ OK |
| ASG-025 | Atajo teclado Reparado | Alt+R | Selecciona estado "Reparado" | MnemonicParsing activo en RadioButton. Alt+R selecciona rbReparado. | ✓ OK |

---

### 2.4 Pantalla de Informes

#### Tabla de Pruebas Funcionales - Informes

| ID | Caso de Prueba | Entrada | Resultado Esperado | Resultado Obtenido | Estado |
|----|---------------|---------|-------------------|-------------------|--------|
| INF-001 | Generar informe de Vehículos | Seleccionar "Vehículos", clic Generar | Informe JasperReports mostrado con listado de vehículos | Informe generado con JasperReports. Listado completo de vehículos. | ✓ OK |
| INF-002 | Generar informe de Piezas | Seleccionar "Piezas", clic Generar | Informe JasperReports mostrado con listado de piezas | Informe generado. Listado completo de piezas con imágenes. | ✓ OK |
| INF-003 | Generar informe de Inventario | Seleccionar "Inventario", clic Generar | Informe JasperReports mostrado con listado de inventario | Informe generado con relaciones vehículo-pieza. | ✓ OK |
| INF-004 | Sin seleccionar tipo de informe | Clic en Generar sin selección | Mensaje indicando que debe seleccionar tipo | Alert "Debe seleccionar un tipo de informe". | ✓ OK |
| INF-005 | Modo incrustado activado | Checkbox "Incrustado" marcado, Generar | Informe mostrado dentro de la aplicación | SwingNode muestra JRViewer embebido en la ventana. | ✓ OK |
| INF-006 | Modo incrustado desactivado | Checkbox "Incrustado" desmarcado, Generar | Informe abierto en visor externo | JRViewer abierto en ventana JFrame independiente. | ✓ OK |
| INF-007 | Exportar a PDF | Generar informe, clic "Exportar PDF" | Diálogo para guardar archivo PDF | FileChooser abre. PDF exportado con JasperExportManager. | ✓ OK |
| INF-008 | Abrir en modal | Generar informe incrustado, clic "Abrir Informe" | Informe abierto en ventana modal | Ventana modal Stage con informe a pantalla completa. | ✓ OK |
| INF-009 | Filtrar informe de vehículos | Seleccionar filtro por marca | Informe muestra solo vehículos de esa marca | Parámetro filtro pasado a JasperFillManager. Datos filtrados. | ✓ OK |
| INF-010 | Filtrar informe de piezas | Seleccionar filtro por categoría | Informe muestra solo piezas de esa categoría | Parámetro categoría aplicado. Solo piezas de categoría mostradas. | ✓ OK |
| INF-011 | Atajo teclado Generar | Alt+G | Ejecuta acción Generar Informe | MnemonicParsing activo. Alt+G dispara generarInforme(). | ✓ OK |
| INF-012 | Atajo teclado Incrustado | Alt+I | Toggle checkbox Incrustado | MnemonicParsing activo. Alt+I toggle del CheckBox. | ✓ OK |
| INF-013 | Atajo teclado Abrir | Alt+A | Ejecuta acción Abrir Informe | MnemonicParsing activo. Alt+A dispara abrirInforme(). | ✓ OK |
| INF-014 | Atajo teclado Exportar | Alt+X | Ejecuta acción Exportar PDF | MnemonicParsing activo. Alt+X dispara exportarPDF(). | ✓ OK |

---

### 2.5 Pantalla de Estadísticas

#### Tabla de Pruebas Funcionales - Estadísticas

| ID | Caso de Prueba | Entrada | Resultado Esperado | Resultado Obtenido | Estado |
|----|---------------|---------|-------------------|-------------------|--------|
| EST-001 | Carga inicial de estadísticas | Navegar a pestaña Estadísticas | Se muestran los 3 gráficos con datos de BD | BarChart y PieChart cargados con datos de BD. | ✓ OK |
| EST-002 | Gráfico de barras - Vehículos por Marca | Visualizar gráfico superior izquierdo | Barras mostrando cantidad de vehículos por marca | BarChart muestra cada marca con su cantidad de vehículos. | ✓ OK |
| EST-003 | Gráfico circular - Piezas por Categoría | Visualizar gráfico superior derecho | Sectores mostrando distribución de piezas | PieChart con sectores por categoría y porcentajes. | ✓ OK |
| EST-004 | Gráfico de barras - Top 5 Kilometraje | Visualizar gráfico inferior | Top 5 vehículos con mayor kilometraje | BarChart ordenado DESC limitado a 5 registros. | ✓ OK |
| EST-005 | Estadísticas con BD vacía | Eliminar todos los registros | Gráficos vacíos o mensaje "Sin datos" | Gráficos vacíos sin errores. Aplicación estable. | ✓ OK |
| EST-006 | Actualización tras nuevo registro | Añadir vehículo, volver a Estadísticas | Gráficos reflejan el nuevo registro | Datos recargados al navegar. Nuevo registro visible. | ✓ OK |

---

## 3. PRUEBAS DE SISTEMA

Las pruebas de sistema verifican el comportamiento global de la aplicación en diferentes escenarios y configuraciones.

| ID | Caso de Prueba | Condiciones Previas | Pasos | Resultado Esperado | Resultado Obtenido | Estado |
|----|---------------|---------------------|-------|-------------------|-------------------|--------|
| SIS-001 | Inicio de aplicación | Aplicación instalada, BD configurada | 1. Ejecutar aplicación | Ventana principal cargada sin errores | Splash screen mostrado. Ventana principal carga correctamente. | ✓ OK |
| SIS-002 | Conexión a BD remota | ip.properties con IP válida, BD MySQL accesible | 1. Configurar ip.properties 2. Iniciar app | Conexión exitosa, datos cargados | ConexionBD establece conexión. Datos cargados en TableViews. | ✓ OK |
| SIS-003 | Conexión fallida a BD | ip.properties con IP inválida | 1. Configurar IP incorrecta 2. Iniciar app | Mensaje de error claro, app no se bloquea | Alert de error de conexión. Aplicación no se bloquea. | ✓ OK |
| SIS-004 | Navegación entre vistas | Aplicación iniciada | 1. Clic Vehículos 2. Clic Piezas 3. Clic Inventario 4. Clic Estadísticas | Navegación fluida sin errores | TabPane cambia entre vistas sin errores. Datos recargados. | ✓ OK |
| SIS-005 | Atajos de teclado globales | Aplicación en vista principal | Ctrl+1, Ctrl+2, Ctrl+3, Ctrl+4 | Navegación entre vistas correcta | KeyCombination configurada. Atajos funcionan correctamente. | ✓ OK |
| SIS-006 | Búsqueda en tiempo real | Vista con registros | 1. Escribir en barra búsqueda | Filtrado instantáneo de registros | FilteredList actualiza TableView en tiempo real. | ✓ OK |
| SIS-007 | Persistencia de datos | Vehículo creado | 1. Crear vehículo 2. Cerrar app 3. Abrir app | Vehículo persiste en BD | Datos guardados en MySQL. Disponibles tras reiniciar app. | ✓ OK |
| SIS-008 | Integridad referencial | Vehículo con piezas asignadas | 1. Intentar eliminar vehículo | Mensaje de advertencia o eliminación en cascada | ON DELETE CASCADE en FK. Registros relacionados eliminados. | ✓ OK |
| SIS-009 | Rendimiento con muchos registros | BD con 1000+ registros | 1. Cargar vista de vehículos | Carga en menos de 3 segundos | Carga optimizada con PreparedStatement. Tiempo < 3s. | ✓ OK |
| SIS-010 | Cierre seguro de aplicación | Aplicación en uso | 1. Clic en cerrar 2. Confirmar salida | Aplicación se cierra limpiamente | Alert confirmación con icono. Platform.exit() ejecutado. | ✓ OK |
| SIS-011 | Cancelar cierre de aplicación | Aplicación en uso | 1. Clic en cerrar 2. Cancelar | Aplicación permanece abierta | event.consume() previene cierre. App sigue funcionando. | ✓ OK |
| SIS-012 | Doble clic para editar | Registro seleccionado en tabla | 1. Doble clic en registro | Formulario de edición abierto | setOnMouseClicked con click count 2. Formulario abierto. | ✓ OK |
| SIS-013 | Tecla Supr para eliminar | Registro seleccionado | 1. Presionar tecla Supr | Confirmación de eliminación | KeyEvent DELETE capturado. Alert confirmación mostrado. | ✓ OK |
| SIS-014 | Actualización tras CRUD | Crear/editar/eliminar registro | Realizar operación CRUD | Tabla actualizada automáticamente | actualizarListado() llamado tras cada operación CRUD. | ✓ OK |
| SIS-015 | Validación visual (borde rojo/verde) | Formulario abierto | 1. Introducir dato válido 2. Introducir dato inválido | Borde verde para válido, rojo para inválido | CSS inline aplicado: #27ae60 (verde), #e74c3c (rojo). | ✓ OK |

---

## 4. PRUEBAS ALFA (TEST DE GUERRILLA)

### 4.1 Perfil de los Usuarios de Prueba

| USUARIO | EDAD | PERFIL | CONOCIMIENTOS INFORMÁTICOS |
|---------|------|--------|---------------------------|
| Usuario 1 | <!-- Ej: 45 años --> | <!-- Ej: Familiar, mecánico --> | <!-- Básicos/Medios/Avanzados --> |
| Usuario 2 | <!-- Ej: 22 años --> | <!-- Ej: Compañero de clase --> | <!-- Básicos/Medios/Avanzados --> |

---

### 4.2 Tareas Realizadas

Se pidió a los usuarios que completaran las siguientes tareas **sin ayuda ni explicaciones previas**:

| # | TAREA | TIEMPO MÁX | U1 TIEMPO | U1 ÉXITO | U2 TIEMPO | U2 ÉXITO |
|---|-------|------------|-----------|----------|-----------|----------|
| 1 | Abrir la aplicación | 30 seg | <!-- Ej: 10s --> | <!-- Sí/No --> | <!-- Ej: 8s --> | <!-- Sí/No --> |
| 2 | Añadir un vehículo nuevo con todos los datos | 3 min | | | | |
| 3 | Buscar un vehículo por marca | 1 min | | | | |
| 4 | Ver el detalle de un vehículo | 1 min | | | | |
| 5 | Añadir una pieza nueva | 2 min | | | | |
| 6 | Asignar una pieza a un vehículo | 2 min | | | | |
| 7 | Generar un informe PDF de vehículos | 2 min | | | | |
| 8 | Cerrar la aplicación correctamente | 30 seg | | | | |

---

### 4.3 Observaciones Durante las Pruebas

| USUARIO | TAREA | PROBLEMAS ENCONTRADOS | COMENTARIOS DEL USUARIO |
|---------|-------|----------------------|------------------------|
| U1 | <!-- Ej: Tarea 2 --> | <!-- Ej: No encontraba el botón Nuevo --> | <!-- Ej: "El botón debería ser más grande" --> |
| U1 | | | |
| U2 | | | |
| U2 | | | |

---

### 4.4 Valoración de los Usuarios

Tras completar las tareas, se pidió a los usuarios que valoraran del 1 al 5:

| ASPECTO | U1 | U2 | MEDIA |
|---------|----|----|-------|
| Facilidad de uso | /5 | /5 | /5 |
| Diseño visual | /5 | /5 | /5 |
| Claridad de los botones y menús | /5 | /5 | /5 |
| Velocidad de respuesta | /5 | /5 | /5 |
| Utilidad de los tooltips | /5 | /5 | /5 |
| **VALORACIÓN GENERAL** | /5 | /5 | /5 |

---

### 4.5 CONCLUSIONES DE LAS PRUEBAS ALFA

<!--
INSTRUCCIONES: Escribe 2-3 párrafos describiendo:
1. Cómo fueron las pruebas en general
2. Si los usuarios completaron todas las tareas
3. Qué problemas se detectaron (si los hubo)
4. Qué mejoras se podrían implementar
5. Valoración general de la usabilidad

EJEMPLO:
"Se realizaron pruebas alfa con 2 usuarios sin conocimientos previos de la aplicación AutoCiclo.

**Resultados generales:** Ambos usuarios completaron el 100% de las tareas asignadas dentro del tiempo máximo establecido. El Usuario 1 (45 años, conocimientos básicos) tardó algo más en las tareas de búsqueda y generación de informes. El Usuario 2 (22 años, conocimientos medios) completó todas las tareas con fluidez.

**Problemas detectados:** El Usuario 1 tuvo dificultad inicial para encontrar la opción de asignar piezas, sugiriendo que el botón podría ser más visible. Ambos usuarios valoraron positivamente los tooltips que aparecen al pasar el ratón por los botones.

**Mejoras propuestas:** Basándose en el feedback, se propone aumentar el tamaño de los botones de acción principales y añadir iconos más descriptivos.

**Conclusión:** La aplicación presenta una usabilidad adecuada para usuarios con diferentes niveles de conocimientos informáticos. Los tooltips y los atajos de teclado (Alt+letra) fueron especialmente útiles para agilizar el trabajo."
-->

**Resultados generales:**
<!-- COMPLETAR -->

**Problemas detectados:**
<!-- COMPLETAR -->

**Mejoras propuestas:**
<!-- COMPLETAR -->

**Conclusión:**
<!-- COMPLETAR -->

---

## Firma y Validación

| Rol | Nombre | Firma | Fecha |
|-----|--------|-------|-------|
| Desarrollador | Yalil Musa Talhaoui | | |
| Tester | | | |
| Validador | | | |

---

*Documento generado para la práctica final del Tema 4 - Desarrollo de Interfaces*
