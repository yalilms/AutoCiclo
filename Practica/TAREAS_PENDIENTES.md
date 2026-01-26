# TAREAS PENDIENTES - Práctica Final Tema 4
## AutoCiclo - Yalil Musa

---

## FASE 0: Corregir fallos del 1er Trimestre
| Tarea | Estado |
|-------|--------|
| Icono en ventana de salida | ✅ HECHO |
| Tooltips en todos los botones | ✅ HECHO |
| PromptText en buscadores | ✅ HECHO |
| MnemonicParsing en botones | ✅ HECHO |

---

## FASE 1: Documento de Pruebas (PDF)

### ✅ Ya completado:
- [x] Sección 1: Fallos corregidos
- [x] Sección 2: Pruebas Funcionales (105 casos con resultados)
- [x] Sección 3: Pruebas de Sistema (15 casos con resultados)
- [x] Plantilla de Pruebas Alfa preparada

### ❌ Pendiente (lo tienes que hacer tú):

#### 1. Pruebas Alfa con 2 usuarios reales (40% de la nota)
- [ ] Buscar 2 personas para probar la app (familiar, amigo, compañero...)
- [ ] Pedirles que hagan las 8 tareas de la tabla SIN ayudarles
- [ ] Apuntar los tiempos que tardan en cada tarea
- [ ] Apuntar si lo consiguen (Sí/No)
- [ ] Anotar problemas y comentarios que hagan
- [ ] Pedirles que valoren del 1 al 5 cada aspecto
- [ ] Escribir las conclusiones (2-3 párrafos)

#### 2. Capturas de pantalla
- [ ] Captura 1: Alert de confirmación mostrando el ICONO de AutoCiclo
- [ ] Captura 2: Tooltip visible al pasar el ratón por un botón
- [ ] Captura 3: Campo de búsqueda mostrando el promptText "Buscar por..."
- [ ] Captura 4: Botón con letra subrayada (pulsa Alt para verlo)
- [ ] Captura 5: Validación con borde rojo en campo incorrecto
- [ ] Captura 6: Validación con borde verde en campo correcto
- [ ] Captura 7: Informe PDF generado

#### 3. Exportar a PDF
- [ ] Abrir `Practica/Pruebas_YalilMusa_AutoCiclo.md`
- [ ] Insertar las capturas en los lugares indicados
- [ ] Exportar a PDF: `Pruebas_YalilMusa_AutoCiclo.pdf`

---

## FASE 2: Configurar ip.properties
- [ ] Crear archivo `app/src/main/resources/ip.properties`
- [ ] Configurar variables: db.host, db.port, db.name, db.user, db.password
- [ ] Modificar `ConexionBD.java` para que lea del properties

---

## FASE 3: Desplegar BBDD en remoto
- [ ] Crear cuenta en Railway (https://railway.app/) o similar
- [ ] Crear base de datos MySQL
- [ ] Importar el SQL de AutoCiclo
- [ ] Copiar credenciales de conexión
- [ ] Actualizar ip.properties con la IP remota
- [ ] Probar que la app conecta al servidor remoto

---

## FASE 4: Crear Instalador Windows
- [ ] Ejecutar `./gradlew clean build jar` para generar el JAR
- [ ] Crear carpeta `CarpetaInstalacion/` con:
  - [ ] AutoCiclo.jar
  - [ ] lib/ (dependencias)
  - [ ] iconos/ (autociclo.ico, autociclo.png)
  - [ ] run.bat
- [ ] Abrir InstallBuilder
- [ ] Crear proyecto nuevo para Windows x64
- [ ] Añadir los ficheros
- [ ] Crear acceso directo con icono
- [ ] **IMPORTANTE (15% nota):** Añadir algo en Advanced:
  - [ ] Verificar Java instalado
  - [ ] Splash screen
  - [ ] Licencia personalizada
- [ ] Build del instalador .exe
- [ ] Guardar proyecto como `ProyectoInstaladorWindows.xml`

---

## FASE 5: Crear Instalador Linux
- [ ] Crear `autociclo.desktop` (acceso directo Linux)
- [ ] Crear `run.sh` (script de ejecución)
- [ ] Crear `postinstall.sh` (script post-instalación)
- [ ] Duplicar proyecto de InstallBuilder
- [ ] Cambiar plataforma a Linux x64
- [ ] Configurar directorio: `/opt/autociclo`
- [ ] Build del instalador .run
- [ ] Guardar proyecto como `ProyectoInstaladorLinux.xml`

---

## FASE 6: Preparar entrega final
- [ ] Eliminar carpeta `app/build/`
- [ ] Exportar base de datos: `mysqldump -u root -p autociclo_db > autociclo_db.sql`
- [ ] Crear ZIP del proyecto: `AutoCiclo_DIST.zip`
- [ ] Organizar carpeta final:

```
FASEFINAL_YalilMusa/
├── AutoCiclo_DIST.zip
├── Pruebas_YalilMusa_AutoCiclo.pdf
├── autociclo_db.sql
└── Instaladores/
    ├── CarpetaInstalacion/
    ├── AutoCiclo-1.0-windows-x64-installer.exe
    ├── AutoCiclo-1.0-linux-x64-installer.run
    ├── ProyectoInstaladorWindows.xml
    └── ProyectoInstaladorLinux.xml
```

- [ ] Subir a Moodle antes de la fecha límite

---

## RESUMEN RÁPIDO

| FASE | ESTADO | PRIORIDAD |
|------|--------|-----------|
| FASE 0 | ✅ Completada | - |
| FASE 1 | 🔶 Falta: pruebas alfa + capturas + PDF | ALTA |
| FASE 2 | ❌ Pendiente | MEDIA |
| FASE 3 | ❌ Pendiente | MEDIA |
| FASE 4 | ❌ Pendiente | ALTA |
| FASE 5 | ❌ Pendiente | ALTA |
| FASE 6 | ❌ Pendiente | ALTA |

---

*Última actualización: Enero 2026*
