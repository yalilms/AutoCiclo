# AutoCiclo

Proyecto de Desarrollo de Interfaces - 2º DAM

## Qué es

Aplicación de escritorio para gestionar un desguace de coches. Básicamente puedes llevar el control de los vehículos que entran, las piezas que se sacan de cada uno y el inventario del almacén.

## Qué puedes hacer

- Añadir, editar y eliminar vehículos
- Gestionar piezas (con fotos incluidas)
- Asignar piezas a los vehículos de donde vienen
- Ver estadísticas con gráficos

## Tecnologías

- Java 24
- JavaFX 25
- MySQL 8
- Gradle

## Cómo ejecutarlo

1. Tener MySQL instalado (o usar el docker-compose que hay)
2. Importar el archivo `autociclo_db.sql` a la base de datos
3. Revisar la conexión en `app/src/main/java/com/autociclo/database/ConexionBD.java` por si hay que cambiar usuario/contraseña
4. Ejecutar con `./gradlew run`

## Estructura básica

```
app/src/main/java/com/autociclo/
├── controllers/    -> Controladores de las vistas
├── database/       -> Conexión a MySQL
├── models/         -> Clases Vehiculo, Pieza, etc
└── utils/          -> Clases auxiliares
```

## Autor

Yalil Musa Talhaoui - 2º DAM
IES Hermenegildo Lanz (Granada)
Curso 2024/2025
