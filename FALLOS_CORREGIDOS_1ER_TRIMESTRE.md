# FALLOS CORREGIDOS DEL 1ER TRIMESTRE
## AutoCiclo - Yalil Musa

---

## RESUMEN DE FALLOS Y CORRECCIONES

| # | FALLO DETECTADO | TIPO | SOLUCION APLICADA | ARCHIVOS MODIFICADOS |
|---|-----------------|------|-------------------|---------------------|
| 1 | Falta icono en ventana de confirmacion de salida | Diseno/Usabilidad Menor | Se ha anadido el icono de la aplicacion a todos los Alerts mediante listener en sceneProperty | `ValidationUtils.java`, `Main.java`, `ListadoMaestroController.java` |
| 2 | Faltan tooltips en TODOS los botones | Diseno/Usabilidad Menor | Se han anadido tooltips descriptivos a todos los botones de la aplicacion | Todos los archivos FXML |
| 3 | Buscadores sin indicar por que campo buscan | Diseno/Usabilidad Menor | Se ha anadido promptText descriptivo y tooltip a todos los campos de busqueda | `ListadosController.fxml` y formularios |
| 4 | Falta MnemonicParsing en todos los botones | Diseno/Usabilidad Menor | Se ha activado mnemonicParsing con atajos Alt+Letra en todos los botones | Todos los archivos FXML |

---

## DETALLE DE CADA CORRECCION

### FALLO 1: Icono en ventana de confirmacion de salida

**Problema:** La ventana de confirmacion al salir de la aplicacion no mostraba el icono de AutoCiclo.

**Solucion aplicada:**
Se ha modificado el metodo `applyCustomStyle()` en `ValidationUtils.java` para anadir automaticamente el icono de la aplicacion a todos los Alerts. Tambien se ha anadido el icono especificamente a los Alerts de salida en `Main.java` y `ListadoMaestroController.java`.

**Codigo anadido:**
```java
// Listener para anadir icono cuando se crea la escena del Alert
dialogPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
    if (newScene != null && newScene.getWindow() != null) {
        Stage stage = (Stage) newScene.getWindow();
        stage.getIcons().add(new Image(
            ValidationUtils.class.getResourceAsStream("/imagenes/logo_autociclo.png")));
    }
});
```

**Estado:** CORREGIDO

---

### FALLO 2: Tooltips en TODOS los botones

**Problema:** Los botones de la aplicacion no tenian tooltips explicativos.

**Solucion aplicada:**
Se han anadido tooltips a todos los botones de la aplicacion, incluyendo:
- Botones de la barra de herramientas (Nuevo, Ver, Editar, Eliminar, Buscar)
- Botones de navegacion (Vehiculos, Piezas, Inventario, Estadisticas, Informes)
- Botones de formularios (Guardar, Cancelar)
- Botones de detalle (Cerrar)
- Botones de paginacion (Anterior, Siguiente)

**Ejemplo de tooltip anadido:**
```xml
<Button fx:id="btnGuardar" text="_Guardar" mnemonicParsing="true">
    <tooltip><Tooltip text="Guardar los datos del vehiculo (Alt+G)" /></tooltip>
</Button>
```

**Archivos modificados:**
- ListadosController.fxml
- FormularioVehiculo.fxml
- FormularioPieza.fxml
- FormularioInventario.fxml
- DetalleVehiculo.fxml
- DetallePieza.fxml
- DetalleInventario.fxml
- AsignarPiezaVehiculo.fxml
- Informes.fxml
- AcercaDe.fxml

**Estado:** CORREGIDO

---

### FALLO 3: Buscadores sin indicar campo de busqueda

**Problema:** Los campos de busqueda no indicaban por que campo estaban buscando.

**Solucion aplicada:**
Se ha anadido `promptText` descriptivo y tooltip a todos los campos de busqueda.

**Ejemplo:**
```xml
<TextField fx:id="txtBuscar" promptText="Buscar por marca, modelo o codigo...">
    <tooltip><Tooltip text="Escribe para filtrar por marca, modelo, codigo o nombre" /></tooltip>
</TextField>
```

**Campos de busqueda modificados:**
| Pantalla | PromptText |
|----------|------------|
| Listado Principal | "Buscar por marca, modelo o codigo..." |
| Formulario Vehiculo (filtro marca) | "Filtrar por marca..." |
| Asignar Pieza (filtro vehiculo) | "Filtrar por matricula..." |
| Asignar Pieza (filtro pieza) | "Filtrar por codigo de pieza..." |

**Estado:** CORREGIDO

---

### FALLO 4: MnemonicParsing en botones

**Problema:** Los botones no tenian atajos de teclado (Alt + letra).

**Solucion aplicada:**
Se ha activado `mnemonicParsing="true"` en todos los botones y se han anadido los guiones bajos para indicar las letras de acceso rapido.

**Tabla de atajos implementados:**

| Boton | Texto | Atajo |
|-------|-------|-------|
| Guardar | `_Guardar` | Alt+G |
| Cancelar | `_Cancelar` | Alt+C |
| Nuevo | `_Nuevo` | Alt+N |
| Ver | `_Ver` | Alt+V |
| Editar | `E_ditar` | Alt+D |
| Eliminar | `E_liminar` | Alt+L |
| Buscar | `_Buscar` | Alt+B |
| Cerrar | `_Cerrar` | Alt+C |
| Anterior | `_Anterior` | Alt+A |
| Siguiente | `_Siguiente` | Alt+S |
| Vehiculos | `_Vehiculos` | Alt+V |
| Piezas | `_Piezas` | Alt+P |
| Inventario | `_Inventario` | Alt+I |
| Estadisticas | `_Estadisticas` | Alt+E |
| Informes | `Info_rmes` | Alt+R |
| Generar Informe | `_Generar Informe` | Alt+G |
| Exportar PDF | `E_xportar PDF` | Alt+X |
| Asignar | `_Asignar` | Alt+A |

**Menus con mnemonics:**
| Menu | Texto | Atajo |
|------|-------|-------|
| Archivo | `_Archivo` | Alt+A |
| Gestion | `_Gestion` | Alt+G |
| Ayuda | `Ay_uda` | Alt+U |
| Salir | `_Salir` | Alt+S |

**Estado:** CORREGIDO

---

## ARCHIVOS MODIFICADOS (RESUMEN)

### Archivos Java:
1. `app/src/main/java/com/autociclo/utils/ValidationUtils.java`
   - Anadido import de Image y Stage
   - Modificado metodo applyCustomStyle() para anadir icono a Alerts

2. `app/src/main/java/com/autociclo/Main.java`
   - Anadido listener para icono en Alert de salida

3. `app/src/main/java/com/autociclo/controllers/ListadoMaestroController.java`
   - Anadido estilos e icono al Alert de salirAplicacion()

### Archivos FXML:
1. `ListadosController.fxml` - Tooltips + Mnemonics + PromptText
2. `FormularioVehiculo.fxml` - Tooltips + Mnemonics
3. `FormularioPieza.fxml` - Tooltips + Mnemonics
4. `FormularioInventario.fxml` - Tooltips + Mnemonics + PromptText
5. `DetalleVehiculo.fxml` - Tooltips + Mnemonics
6. `DetallePieza.fxml` - Tooltips + Mnemonics
7. `DetalleInventario.fxml` - Tooltips + Mnemonics
8. `AsignarPiezaVehiculo.fxml` - Tooltips + Mnemonics + PromptText
9. `Informes.fxml` - Tooltips + Mnemonics
10. `AcercaDe.fxml` - Tooltips + Mnemonics

---

## CAPTURAS DE PANTALLA (ANADIR AL PDF)

Para completar el PDF de pruebas, se deben incluir las siguientes capturas:

1. **Captura 1 - Icono en Alert de salida:**
   - Mostrar la ventana de confirmacion de salida con el icono de AutoCiclo visible en la barra de titulo

2. **Captura 2 - Tooltip en boton:**
   - Mostrar un tooltip visible al pasar el raton por cualquier boton (ej: boton Guardar)

3. **Captura 3 - PromptText en buscador:**
   - Mostrar el campo de busqueda vacio con el texto "Buscar por marca, modelo o codigo..." visible

4. **Captura 4 - Mnemonic en boton:**
   - Mostrar un boton con la letra subrayada (pulsar Alt para que se muestre)

---

## CONCLUSION

Se han corregido los 4 fallos menores de diseno/usabilidad detectados en la revision del primer trimestre:

1. Todos los Alerts ahora muestran el icono de la aplicacion
2. Todos los botones tienen tooltips explicativos que describen su funcion
3. Los campos de busqueda indican claramente por que campos filtran
4. Todos los botones tienen atajos de teclado mediante MnemonicParsing

La aplicacion ahora cumple con los estandares de usabilidad y accesibilidad requeridos.

---

**Fecha de correccion:** Enero 2026
**Alumno:** Yalil Musa Talhaoui
