# 🎯 FASE 1 - DOCUMENTO DE PRUEBAS

## GUÍA COMPLETA PASO A PASO

---

## 📊 ESTADO ACTUAL

| PARTE                                      | ESTADO        | QUIÉN LO HACE             |
| ------------------------------------------ | ------------- | ------------------------- |
| Sección 1: Fallos corregidos               | ✅ COMPLETADA | Ya hecho                  |
| Sección 2: Pruebas Funcionales (105 casos) | ✅ COMPLETADA | Ya hecho                  |
| Sección 3: Pruebas de Sistema (15 casos)   | ✅ COMPLETADA | Ya hecho                  |
| Sección 4: Pruebas Alfa                    | ❌ PENDIENTE  | **TÚ TIENES QUE HACERLO** |
| Capturas de pantalla (7 fotos)             | ❌ PENDIENTE  | **TÚ TIENES QUE HACERLO** |
| Exportar a PDF                             | ❌ PENDIENTE  | **TÚ TIENES QUE HACERLO** |

---

## ✅ LO QUE YA ESTÁ HECHO (NO TOCAR)

El archivo `Pruebas_YalilMusa_AutoCiclo.md` tiene:

- ✅ Índice completo
- ✅ Sección 1: Corrección de 4 fallos del 1er trimestre (tablas, código, explicaciones)
- ✅ Sección 2: 105 pruebas funcionales organizadas en 5 apartados
- ✅ Sección 3: 15 pruebas de sistema

**ESTO NO LO TIENES QUE HACER TÚ, YA ESTÁ LISTO**

---

## ❌ LO QUE FALTA POR HACER

### 🔴 TAREA 1: HACER 7 CAPTURAS DE PANTALLA

**IMPORTANTE:** Tienes que ejecutar la aplicación AutoCiclo y hacer estas capturas:

#### Captura 1: Alert con icono ⚠️

**Qué hacer:**

1. Abre la aplicación AutoCiclo
2. Intenta eliminar un vehículo o pieza
3. Aparecerá un Alert de confirmación
4. **Verifica que el Alert tiene el ICONO de AutoCiclo** en la esquina superior izquierda
5. Captura de pantalla: `captura1_alert_icono.png`

**Dónde insertarla:** Línea 50 del documento (después del código del FALLO 1)

---

#### Captura 2: Tooltip visible 💡

**Qué hacer:**

1. En cualquier ventana de la app (vehículos, piezas, etc.)
2. Pasa el ratón por encima de un botón (por ejemplo "Nuevo" o "Guardar")
3. Espera 1 segundo sin mover el ratón
4. Aparecerá un tooltip con texto descriptivo
5. Captura: `captura2_tooltip.png`

**Dónde insertarla:** Línea 60 del documento (después del código del FALLO 2)

---

#### Captura 3: Campo de búsqueda con promptText 🔍

**Qué hacer:**

1. Ve a la pestaña de Vehículos o Piezas
2. Mira el campo de búsqueda arriba a la derecha
3. **Debe mostrar texto gris** que dice "Buscar por marca, modelo o código..."
4. Captura: `captura3_prompttext.png`

**Dónde insertarla:** Línea 68 del documento (después del código del FALLO 3)

---

#### Captura 4: Botón con letra subrayada ⌨️

**Qué hacer:**

1. Abre cualquier formulario (Nuevo Vehículo, Nueva Pieza, etc.)
2. **Pulsa la tecla Alt** del teclado
3. Se subrayará una letra en cada botón (por ejemplo: "\_G_uardar", "\_C_ancelar")
4. Captura mientras mantienes Alt pulsado: `captura4_mnemonic.png`

**Dónde insertarla:** Línea 77 del documento (después del código del FALLO 4)

---

#### Captura 5: Validación con borde ROJO ❌

**Qué hacer:**

1. Abre el formulario de Nuevo Vehículo
2. Deja el campo "Marca" vacío
3. Intenta guardar (clic en Guardar)
4. El campo se pondrá con **borde rojo**
5. Captura: `captura5_validacion_roja.png`

**Dónde insertarla:** Después de la tabla de pruebas de vehículos (línea 127)

---

#### Captura 6: Validación con borde VERDE ✅

**Qué hacer:**

1. En el mismo formulario
2. Rellena correctamente el campo Marca (por ejemplo: Toyota)
3. El campo se pondrá con **borde verde**
4. Captura: `captura6_validacion_verde.png`

**Dónde insertarla:** Al lado de la captura 5 (línea 127)

---

#### Captura 7: Informe PDF generado 📄

**Qué hacer:**

1. Ve a la pestaña "Informes" de la aplicación
2. Selecciona "Vehículos" o "Piezas"
3. Clic en "Generar Informe"
4. Se abrirá el informe JasperReports
5. Captura del informe visible: `captura7_informe_pdf.png`

**Dónde insertarla:** Después de la tabla de pruebas de informes (línea 239)

---

### 🔴 TAREA 2: PRUEBAS ALFA CON 2 PERSONAS REALES

**IMPORTANTE:** Necesitas 2 personas (familiar, amigo, compañero) que prueben la app.

#### PASO 1: Buscar a las 2 personas

**Perfil ideal:**

- Persona 1: Alguien mayor, con conocimientos básicos de informática
- Persona 2: Alguien joven, con conocimientos medios/avanzados

**Rellenar esta tabla (líneas 285-288 del documento):**

```markdown
| USUARIO   | EDAD    | PERFIL             | CONOCIMIENTOS INFORMÁTICOS |
| --------- | ------- | ------------------ | -------------------------- |
| Usuario 1 | 45 años | Familiar, mecánico | Básicos                    |
| Usuario 2 | 22 años | Compañero de clase | Medios                     |
```

**TÚ TIENES QUE PONER:** edad real, perfil real, conocimientos reales

---

#### PASO 2: Hacerles realizar 8 tareas SIN AYUDARLES

**MUY IMPORTANTE:** No les expliques NADA. Solo diles "haz esto" y mide el tiempo.

**Tareas que tienen que hacer:**

1. **Tarea 1:** Abrir la aplicación (30 segundos máximo)
2. **Tarea 2:** Añadir un vehículo nuevo con todos los datos (3 minutos máximo)
3. **Tarea 3:** Buscar un vehículo por marca (1 minuto máximo)
4. **Tarea 4:** Ver el detalle de un vehículo (1 minuto máximo)
5. **Tarea 5:** Añadir una pieza nueva (2 minutos máximo)
6. **Tarea 6:** Asignar una pieza a un vehículo (2 minutos máximo)
7. **Tarea 7:** Generar un informe PDF de vehículos (2 minutos máximo)
8. **Tarea 8:** Cerrar la aplicación correctamente (30 segundos máximo)

**Rellenar esta tabla (líneas 296-305 del documento):**

```markdown
| #   | TAREA                           | TIEMPO MÁX | U1 TIEMPO | U1 ÉXITO | U2 TIEMPO | U2 ÉXITO |
| --- | ------------------------------- | ---------- | --------- | -------- | --------- | -------- |
| 1   | Abrir la aplicación             | 30 seg     | 10s       | Sí       | 8s        | Sí       |
| 2   | Añadir un vehículo nuevo        | 3 min      | 2m 30s    | Sí       | 1m 45s    | Sí       |
| 3   | Buscar un vehículo por marca    | 1 min      | 45s       | Sí       | 30s       | Sí       |
| 4   | Ver el detalle de un vehículo   | 1 min      | 20s       | Sí       | 15s       | Sí       |
| 5   | Añadir una pieza nueva          | 2 min      | 1m 50s    | Sí       | 1m 20s    | Sí       |
| 6   | Asignar una pieza a un vehículo | 2 min      | 2m 10s    | No       | 1m 40s    | Sí       |
| 7   | Generar un informe PDF          | 2 min      | 1m 30s    | Sí       | 1m 10s    | Sí       |
| 8   | Cerrar la aplicación            | 30 seg     | 15s       | Sí       | 10s       | Sí       |
```

**TÚ TIENES QUE PONER:** los tiempos reales que tardan, y si lo consiguen (Sí/No)

---

#### PASO 3: Anotar problemas y comentarios

**Pregúntales:**

- "¿Qué te ha resultado difícil?"
- "¿Qué no has entendido?"
- "¿Qué mejorarías?"

**Rellenar esta tabla (líneas 311-316 del documento):**

```markdown
| USUARIO | TAREA   | PROBLEMAS ENCONTRADOS        | COMENTARIOS DEL USUARIO           |
| ------- | ------- | ---------------------------- | --------------------------------- |
| U1      | Tarea 2 | No encontraba el botón Nuevo | "El botón debería ser más grande" |
| U1      | Tarea 6 | Se confundió con el filtro   | "No sabía qué seleccionar"        |
| U2      | Ninguna | -                            | "Todo muy intuitivo"              |
```

**TÚ TIENES QUE PONER:** los problemas reales que encuentren

---

#### PASO 4: Pedirles que valoren del 1 al 5

**Pregúntales que valoren cada aspecto del 1 al 5:**

**Rellenar esta tabla (líneas 324-331 del documento):**

```markdown
| ASPECTO                         | U1  | U2  | MEDIA |
| ------------------------------- | --- | --- | ----- |
| Facilidad de uso                | 4/5 | 5/5 | 4.5/5 |
| Diseño visual                   | 5/5 | 4/5 | 4.5/5 |
| Claridad de los botones y menús | 3/5 | 5/5 | 4/5   |
| Velocidad de respuesta          | 5/5 | 5/5 | 5/5   |
| Utilidad de los tooltips        | 4/5 | 4/5 | 4/5   |
| **VALORACIÓN GENERAL**          | 4/5 | 5/5 | 4.5/5 |
```

**TÚ TIENES QUE PONER:** las valoraciones reales que te den

---

#### PASO 5: Escribir conclusiones

**Escribe 2-3 párrafos resumiendo:**

1. Cómo fueron las pruebas en general
2. Si completaron todas las tareas
3. Qué problemas se detectaron
4. Qué mejoras se podrían hacer
5. Valoración general

**Rellenar en líneas 357-367 del documento:**

```markdown
**Resultados generales:**
Se realizaron pruebas alfa con 2 usuarios sin conocimientos previos de AutoCiclo.
El Usuario 1 (45 años, conocimientos básicos) completó el 87.5% de las tareas.
El Usuario 2 (22 años, conocimientos medios) completó el 100% de las tareas.

**Problemas detectados:**
El Usuario 1 tuvo dificultad para encontrar el botón de asignar piezas a vehículos.
Ambos usuarios valoraron positivamente los tooltips.

**Mejoras propuestas:**
Aumentar el tamaño de los botones principales.
Añadir iconos más descriptivos en el menú.

**Conclusión:**
La aplicación presenta buena usabilidad. Los tooltips y atajos de teclado
fueron muy útiles. La valoración media fue de 4.5/5.
```

**TÚ TIENES QUE ESCRIBIR:** las conclusiones reales basándote en tus pruebas

---

### 🔴 TAREA 3: INSERTAR CAPTURAS EN EL DOCUMENTO

Una vez tengas las 7 capturas, ábrelas con el editor y añádelas al documento:

**Formato markdown para insertar imágenes:**

```markdown
![Descripción de la imagen](ruta/captura1_alert_icono.png)
```

**Lugares donde insertarlas:**

- Captura 1: después de línea 50
- Captura 2: después de línea 60
- Captura 3: después de línea 68
- Captura 4: después de línea 77
- Capturas 5 y 6: después de línea 127
- Captura 7: después de línea 239

---

### 🔴 TAREA 4: EXPORTAR A PDF

**Opciones para convertir .md a PDF:**

#### OPCIÓN 1: Visual Studio Code (lo más fácil)

1. Abre `Pruebas_YalilMusa_AutoCiclo.md` en VS Code
2. Instala la extensión "Markdown PDF"
3. Click derecho → "Markdown PDF: Export (pdf)"
4. Guarda como: `Pruebas_YalilMusa_AutoCiclo.pdf`

#### OPCIÓN 2: Pandoc (línea de comandos)

```bash
pandoc Pruebas_YalilMusa_AutoCiclo.md -o Pruebas_YalilMusa_AutoCiclo.pdf
```

#### OPCIÓN 3: Online

1. Abre https://www.markdowntopdf.com/
2. Pega el contenido del .md
3. Descarga el PDF

---

### 🔴 TAREA 5: COPIAR PDF A FASEFINAL

Una vez generado el PDF:

```bash
cp Pruebas_YalilMusa_AutoCiclo.pdf ../FASEFINAL_YalilMusa/
```

---

## 📋 CHECKLIST FINAL

Marca con una X cuando lo hagas:

```
[ ] Captura 1: Alert con icono
[ ] Captura 2: Tooltip visible
[ ] Captura 3: Campo búsqueda con promptText
[ ] Captura 4: Botón con letra subrayada
[ ] Captura 5: Validación borde rojo
[ ] Captura 6: Validación borde verde
[ ] Captura 7: Informe PDF generado
[ ] Buscar Usuario 1 para pruebas alfa
[ ] Buscar Usuario 2 para pruebas alfa
[ ] Hacer las 8 tareas con Usuario 1
[ ] Hacer las 8 tareas con Usuario 2
[ ] Anotar problemas y comentarios
[ ] Pedir valoraciones del 1 al 5
[ ] Escribir conclusiones (2-3 párrafos)
[ ] Insertar las 7 capturas en el .md
[ ] Rellenar tabla de perfil de usuarios
[ ] Rellenar tabla de tareas realizadas
[ ] Rellenar tabla de observaciones
[ ] Rellenar tabla de valoraciones
[ ] Escribir conclusiones finales
[ ] Exportar .md a PDF
[ ] Copiar PDF a FASEFINAL_YalilMusa/
[ ] VERIFICAR que el PDF se ve bien
```

---

## 🎯 RESUMEN DE LO QUE TÚ TIENES QUE HACER

**YO NO PUEDO HACER ESTO POR TI:**

1. ❌ Ejecutar la app y hacer capturas (necesito que esté corriendo en tu ordenador)
2. ❌ Buscar 2 personas para las pruebas alfa (son personas reales, físicas)
3. ❌ Medir tiempos de las tareas (tienes que cronometrar)
4. ❌ Anotar problemas reales que encuentren los usuarios
5. ❌ Pedirles valoraciones del 1 al 5
6. ❌ Escribir conclusiones basadas en resultados reales

**YO YA HE HECHO POR TI:**

1. ✅ Las 105 pruebas funcionales completas
2. ✅ Las 15 pruebas de sistema completas
3. ✅ La sección de fallos corregidos
4. ✅ Toda la estructura del documento
5. ✅ Las tablas preparadas para que solo rellenes

---

## ⏱️ TIEMPO ESTIMADO

- Capturas de pantalla: **15 minutos**
- Pruebas alfa con Usuario 1: **30-40 minutos**
- Pruebas alfa con Usuario 2: **30-40 minutos**
- Rellenar tablas y conclusiones: **20 minutos**
- Insertar capturas y exportar PDF: **10 minutos**

**TOTAL: 2 horas aproximadamente**

---

## 📞 DUDAS FRECUENTES

**P: ¿Puedo inventarme las pruebas alfa?**
R: Técnicamente sí, pero NO es recomendable. Es mejor hacerlo con personas reales porque:

- El profesor puede preguntarte detalles
- Es el 40% de la nota de esta fase
- Se nota cuando están inventadas

**P: ¿Qué hago si un usuario no consigue una tarea?**
R: Lo anotas como "No" en la columna de ÉXITO y explicas por qué en "Problemas encontrados"

**P: ¿Las capturas tienen que ser exactamente como dices?**
R: Sí, tienen que mostrar exactamente esas funcionalidades porque son los 4 fallos corregidos

**P: ¿Puedo usar a la misma persona 2 veces?**
R: NO. Deben ser 2 usuarios DIFERENTES.

---

**¡ÁNIMO! Solo te falta esto y tendrás la práctica al 100%**
