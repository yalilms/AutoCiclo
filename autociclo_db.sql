SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

-- Base de datos
CREATE DATABASE IF NOT EXISTS `autociclo_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `autociclo_db`;

-- Estructura tabla INVENTARIO_PIEZAS
CREATE TABLE `INVENTARIO_PIEZAS` (
  `id_vehiculo` int NOT NULL,
  `id_pieza` int NOT NULL,
  `cantidad` int NOT NULL,
  `estado_pieza` enum('nueva','usada','reparada') NOT NULL,
  `fecha_extraccion` date NOT NULL,
  `precio_unitario` decimal(10,2) NOT NULL,
  `notas` varchar(255) DEFAULT NULL
) ;

-- Datos tabla INVENTARIO_PIEZAS
INSERT INTO `INVENTARIO_PIEZAS` (`id_vehiculo`, `id_pieza`, `cantidad`, `estado_pieza`, `fecha_extraccion`, `precio_unitario`, `notas`) VALUES
(1, 4, 1, 'usada', '2024-01-20', 150.00, 'Capó sin golpes'),
(1, 7, 2, 'usada', '2024-01-20', 120.00, 'Asientos en perfecto estado'),
(2, 1, 1, 'usada', '2024-02-25', 2500.00, 'Motor completo revisado'),
(2, 9, 1, 'usada', '2024-02-25', 90.00, 'Alternador funcional'),
(3, 3, 1, 'reparada', '2024-03-15', 200.00, 'Puerta reparada y pintada'),
(3, 6, 1, 'usada', '2024-03-15', 80.00, 'Volante multifunción'),
(4, 5, 1, 'usada', '2024-04-01', 120.00, 'Parachoques con enganche'),
(4, 8, 2, 'nueva', '2024-04-01', 450.00, 'Par de faros LED nuevos'),
(5, 10, 2, 'nueva', '2024-04-10', 150.00, 'Discos Brembo nuevos'),
(5, 11, 1, 'reparada', '2024-04-10', 800.00, 'Turbo certificado con garantía'),
(6, 2, 1, 'usada', '2024-04-15', 1800.00, 'Motor HDI en buen estado'),
(6, 12, 1, 'usada', '2024-04-15', 350.00, 'Escape original completo'),
(7, 11, 4, 'nueva', '2024-04-22', 85.00, 'Neumáticos seminuevos');

-- Estructura tabla PIEZAS
CREATE TABLE `PIEZAS` (
  `id_pieza` int NOT NULL,
  `codigo_pieza` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `categoria` enum('motor','carroceria','interior','electronica','ruedas','otros') NOT NULL,
  `precio_venta` decimal(10,2) NOT NULL,
  `stock_disponible` int NOT NULL DEFAULT '0',
  `stock_minimo` int NOT NULL DEFAULT '1',
  `ubicacion_almacen` varchar(50) DEFAULT NULL,
  `compatible_marcas` text,
  `imagen` varchar(255) DEFAULT NULL,
  `descripcion` text
) ;

-- Datos tabla PIEZAS
INSERT INTO `PIEZAS` (`id_pieza`, `codigo_pieza`, `nombre`, `categoria`, `precio_venta`, `stock_disponible`, `stock_minimo`, `ubicacion_almacen`, `compatible_marcas`, `imagen`, `descripcion`) VALUES
(1, 'MOT-001', 'Motor 1.6 TDI', 'motor', 2500.00, 0, 1, 'Estantería A, nivel 2', 'Volkswagen Golf 2015-2020, Audi A3 2014-2019', '/imagenes/piezas/motor123.png', 'Motor diésel en perfecto estado, 80.000 km reales, completo con accesorios'),
(2, 'MOT-002', 'Motor 2.0 HDI', 'motor', 1800.00, 0, 1, 'Zona motores, pasillo 3', 'Peugeot 308 2012-2018, Citroën C4', NULL, 'Motor diésel 120CV, revisado y garantizado'),
(3, 'CAR-025', 'Puerta delantera izquierda', 'carroceria', 200.00, 0, 2, 'Estantería A, nivel 2', 'Renault Clio III generación', '/img/MOT001.jpg', 'Puerta completa con cristal y mecanismos'),
(4, 'CAR-026', 'Capó delantero', 'carroceria', 150.00, 0, 2, 'Zona carrocería, pasillo 1', 'Seat Ibiza 2012-2016', NULL, 'Capó en buen estado, sin golpes'),
(5, 'CAR-027', 'Parachoques trasero', 'carroceria', 120.00, 0, 3, 'Estantería C, nivel 1', 'Ford Focus 2010-2015, Seat León 2012-2016', NULL, 'Parachoques original, con enganche'),
(6, 'INT-340', 'Volante multifunción', 'interior', 80.00, 0, 5, 'Zona motores, pasillo 3', 'Universal (adaptable)', NULL, 'Volante con controles de audio y crucero'),
(7, 'INT-341', 'Asientos delanteros', 'interior', 120.00, 0, 2, 'Zona interior, pasillo 2', 'Seat Ibiza FR, León FR', NULL, 'Par de asientos deportivos, tapicería en buen estado'),
(8, 'ELE-455', 'Faro delantero LED', 'electronica', 450.00, 0, 1, 'Estantería D, nivel 3', 'Ford Transit 2016-2020', NULL, 'Faro con tecnología LED, lado derecho'),
(9, 'ELE-456', 'Alternador 120A', 'electronica', 90.00, 0, 3, 'Zona electrónica, pasillo 4', 'Universal (Bosch)', NULL, 'Alternador 14V 120A, funciona perfectamente'),
(10, 'RUE-550', 'Disco de freno ventilado 280mm', 'ruedas', 150.00, 0, 5, 'Zona frenos, estante bajo', 'Universal deportivo', NULL, 'Disco de competición Brembo'),
(11, 'RUE-551', 'Neumático 205/55 R16', 'ruedas', 85.00, 0, 10, 'Almacén exterior', 'Universal', NULL, 'Neumático seminuevo con 6mm de profundidad'),
(12, 'OTR-700', 'Turbocompresor', 'otros', 800.00, 0, 1, 'Caja fuerte, estante especial', 'Honda CBR 600-1000', NULL, 'Turbo reparado y certificado, garantía 6 meses'),
(13, 'OTR-701', 'Sistema de escape completo', 'otros', 350.00, 0, 1, 'Almacén exterior', 'Toyota Corolla 2015-2020', NULL, 'Escape original Toyota en buen estado');

-- Estructura tabla VEHICULOS
CREATE TABLE `VEHICULOS` (
  `id_vehiculo` int NOT NULL,
  `matricula` varchar(10) NOT NULL,
  `marca` varchar(50) NOT NULL,
  `modelo` varchar(50) NOT NULL,
  `anio` int NOT NULL,
  `color` varchar(30) DEFAULT NULL,
  `fecha_entrada` date NOT NULL DEFAULT (curdate()),
  `estado` enum('completo','desguazando','desguazado') NOT NULL,
  `precio_compra` decimal(10,2) NOT NULL,
  `kilometraje` int DEFAULT NULL,
  `ubicacion_gps` varchar(50) DEFAULT NULL,
  `observaciones` text
) ;

-- Datos tabla VEHICULOS
INSERT INTO `VEHICULOS` (`id_vehiculo`, `matricula`, `marca`, `modelo`, `anio`, `color`, `fecha_entrada`, `estado`, `precio_compra`, `kilometraje`, `ubicacion_gps`, `observaciones`) VALUES
(1, '1234ABC', 'Seat', 'Ibiza', 2015, 'Rojo', '2024-01-15', 'desguazado', 1500.00, 180000, 'Patio trasero, zona A', 'Motor averiado, carrocería en buen estado'),
(2, '5678DEF', 'Volkswagen', 'Golf', 2018, 'Blanco', '2024-02-20', 'completo', 8500.00, 95000, '37.1773,-3.5985', 'Buen estado general'),
(3, '9012GHI', 'Renault', 'Clio', 2012, 'Negro mate', '2024-03-10', 'desguazando', 2000.00, 220000, 'Estantería B, nivel 2', 'Accidente frontal'),
(4, '3456JKL', 'Ford', 'Focus', 2016, 'Blanco', '2024-03-25', 'completo', 12000.00, 150000, 'Zona motores, pasillo 3', 'Uso comercial'),
(5, '7890MNO', 'Honda', 'CBR', 2019, 'Azul metalizado', '2024-04-05', 'desguazado', 3500.00, 25000, NULL, 'Moto deportiva'),
(6, '2345PQR', 'Toyota', 'Corolla', 2017, 'Gris', '2024-04-12', 'desguazando', 7500.00, 120000, 'Patio principal', 'Motor funcional'),
(7, '6789STU', 'Peugeot', '308', 2014, 'Azul', '2024-04-20', 'completo', 4500.00, 160000, NULL, 'Falta rueda delantera');

-- Índices y claves
ALTER TABLE `INVENTARIO_PIEZAS`
  ADD PRIMARY KEY (`id_vehiculo`,`id_pieza`),
  ADD KEY `id_pieza` (`id_pieza`);

ALTER TABLE `PIEZAS`
  ADD PRIMARY KEY (`id_pieza`),
  ADD UNIQUE KEY `codigo_pieza` (`codigo_pieza`);

ALTER TABLE `VEHICULOS`
  ADD PRIMARY KEY (`id_vehiculo`),
  ADD UNIQUE KEY `matricula` (`matricula`);

-- Auto increment
ALTER TABLE `PIEZAS`
  MODIFY `id_pieza` int NOT NULL AUTO_INCREMENT;

ALTER TABLE `VEHICULOS`
  MODIFY `id_vehiculo` int NOT NULL AUTO_INCREMENT;

-- Relaciones entre tablas
ALTER TABLE `INVENTARIO_PIEZAS`
  ADD CONSTRAINT `INVENTARIO_PIEZAS_ibfk_1` FOREIGN KEY (`id_vehiculo`) REFERENCES `VEHICULOS` (`id_vehiculo`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `INVENTARIO_PIEZAS_ibfk_2` FOREIGN KEY (`id_pieza`) REFERENCES `PIEZAS` (`id_pieza`) ON DELETE CASCADE ON UPDATE CASCADE;

COMMIT;