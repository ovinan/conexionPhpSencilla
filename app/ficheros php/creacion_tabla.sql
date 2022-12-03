CREATE TABLE `usuarios` (
  `id` int(11) DEFAULT NULL,
  `nombre` varchar(20) DEFAULT NULL,
  `apellido` varchar(20) DEFAULT NULL,
  `direccion` varchar(20) DEFAULT NULL,
  `telefono` int(11) DEFAULT NULL,
  `correo` varchar(20) DEFAULT NULL,
  `sexo` varchar(20) DEFAULT NULL,
  `fecha` date DEFAULT NULL
);