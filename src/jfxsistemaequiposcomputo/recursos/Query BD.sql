CREATE DATABASE IF NOT EXISTS gestionequipos;

CREATE USER IF NOT EXISTS 'adminSEC'@'localhost' IDENTIFIED BY 'adminSEC090523';
GRANT ALL PRIVILEGES ON gestionequipos . * TO 'adminSEC'@'localhost';

USE gestionequipos;

CREATE TABLE usuarios (
	idUsuario INT AUTO_INCREMENT ,
    nombre VARCHAR(45),
    apellidoPaterno VARCHAR(45),
    apellidoMaterno VARCHAR(45),
    telefono CHAR(10),
    direccion VARCHAR(45),
    correo VARCHAR(45),
    contrasenia VARCHAR(20),
    privilegiado BOOLEAN,
    PRIMARY KEY (idUsuario)
);

CREATE TABLE equiposDeComputo (
	idEquipoDeComputo INT AUTO_INCREMENT NOT NULL,
    tipo VARCHAR(15) NOT NULL,
    incluyeCargador BOOLEAN NOT NULL,
    modelo VARCHAR(45) NOT NULL,
    sistemaOperativo VARCHAR(45) NOT NULL,
    tamanioPantalla FLOAT,
    contraseniaEquipo VARCHAR(20) NOT NULL,
    procesador VARCHAR(20),
    memoria INT,
    marca VARCHAR(45) NOT NULL,
    fechaRegistro DATE,
    fotoEquipo LONGBLOB NOT NULL,
    usuarioSO VARCHAR(45) NOT NULL,
    idUsuario INT,
    idSolicitudDiagnostico INT,
    PRIMARY KEY (idEquipoDeComputo)
);

CREATE TABLE solicitudesDiagnostico (
	idSolicitudDiagnostico INT AUTO_INCREMENT NOT NULL,
    observaciones LONGTEXT,
    idUsuario INT,
    PRIMARY KEY (idSolicitudDiagnostico)
);

ALTER TABLE solicitudesDiagnostico ADD CONSTRAINT FK_Solicitud_Usuario
FOREIGN KEY(idUsuario)
REFERENCES usuarios(idUsuario) ON DELETE CASCADE;

CREATE TABLE estados (
	idEstado INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(15),
    PRIMARY KEY (idEstado)
);
CREATE TABLE solicitudEstados (
	idsolicitudEstado INT AUTO_INCREMENT NOT NULL,
    fechaInicio DATE,
    fechaFin DATE,
    activo TINYINT,
    idSolicitudDiagnostico INT,
    idEstado INT,
    PRIMARY KEY (idsolicitudEstado)
);
CREATE TABLE diagnosticos (
	idDiagnostico INT AUTO_INCREMENT NOT NULL,
    tipoDeMantenimiento VARCHAR(45) NOT NULL,
    fechaAtencion DATE NOT NULL,
    diagnosticoPreliminar LONGTEXT NOT NULL,
    fechaSolicitud DATE NOT NULL,
    costoEstimado FLOAT NOT NULL,
    propuestaSolucion LONGTEXT NOT NULL,
    idSolicitudDiagnostico INT,
    PRIMARY KEY (idDiagnostico)
);
CREATE TABLE mantenimientos (
	idMantenimiento INT AUTO_INCREMENT NOT NULL,
    comentario LONGTEXT,
    idDiagnostico INT,
    PRIMARY KEY (idMantenimiento)
);
CREATE TABLE refacciones (
	idRefaccion INT AUTO_INCREMENT NOT NULL,
    stock INT,
    nombre VARCHAR(45),
    idTipoRefaccion INT,
    PRIMARY KEY (idRefaccion)
);

CREATE TABLE tipoRefacciones(
	idTipoRefaccion INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(100),
    PRIMARY KEY(idTipoRefaccion)
);

ALTER TABLE refacciones ADD CONSTRAINT FK_Refaccion_TipoRefaccion
FOREIGN KEY(idTipoRefaccion)
REFERENCES tipoRefacciones(idTipoRefaccion) ON DELETE CASCADE;

CREATE TABLE refaccionesmantenimientos (
	idRefaccionMantenimiento INT AUTO_INCREMENT NOT NULL,
    idMantenimiento INT,
    idRefaccion INT,
    PRIMARY KEY (idRefaccionMantenimiento)
);
ALTER TABLE equiposDeComputo ADD CONSTRAINT FK_EquipoDeComputo_Usuario
FOREIGN KEY(idUsuario)
REFERENCES usuarios(idUsuario) ON DELETE CASCADE;

ALTER TABLE equiposDeComputo ADD CONSTRAINT FK_EquipoDeComputo_SolicitudDiagnostico
FOREIGN KEY(idSolicitudDiagnostico)
REFERENCES solicitudesDiagnostico(idSolicitudDiagnostico) ON DELETE CASCADE;

ALTER TABLE solicitudEstados ADD CONSTRAINT FK_SolicitudEstado_SolicitudDiagnostico
FOREIGN KEY(idSolicitudDiagnostico)
REFERENCES solicitudesDiagnostico(idSolicitudDiagnostico) ON DELETE CASCADE;

ALTER TABLE solicitudEstados ADD CONSTRAINT FK_SolicitudEstado_Estado
FOREIGN KEY(idEstado)
REFERENCES estados(idEstado) ON DELETE CASCADE;

ALTER TABLE diagnosticos ADD CONSTRAINT FK_Diagnostico_SolicitudDiagnostico
FOREIGN KEY(idSolicitudDiagnostico)

REFERENCES solicitudesDiagnostico(idSolicitudDiagnostico) ON DELETE CASCADE;
ALTER TABLE mantenimientos ADD CONSTRAINT FK_Mantenimiento_Diagnostico
FOREIGN KEY(idDiagnostico)
REFERENCES diagnosticos(idDiagnostico) ON DELETE CASCADE;

ALTER TABLE refaccionesmantenimientos ADD CONSTRAINT FK_RefaccionMantenimiento_Mantenimiento
FOREIGN KEY(idMantenimiento)
REFERENCES mantenimientos(idMantenimiento) ON DELETE CASCADE;

ALTER TABLE refaccionesmantenimientos ADD CONSTRAINT FK_RefaccionMantenimiento_Refaccion
FOREIGN KEY(idRefaccion)
REFERENCES refacciones(idRefaccion) ON DELETE CASCADE;

INSERT INTO usuarios (nombre, apellidoPaterno, apellidoMaterno, telefono, direccion, correo, contrasenia, privilegiado)
VALUES ('pedro', 'lopez', 'Gomez', 2281814657, 'centro', 'pedro@gmail.com', 123456, true), 
		('1', '1', '1', 1, '1', '1', 1, true);
INSERT INTO estados (nombre) VALUES ('PENDIENTE'), ('ACEPTADA'), ('RECHAZADA'), ('DIAGNOSTICO'), ('REVISION'), ('FINALIZADO');
INSERT INTO tiporefacciones (nombre) VALUES ('Memoria RAM'), ('SSD'), ('HDD'), ('Tarjeta Madre'), ('Procesador'), ('Monitor');
INSERT INTO refacciones (stock, nombre, idTipoRefaccion) VALUES 
	(10, 'Sony 16GB', 1),
    (4, 'Samsung 8GB', 1),
    (6, 'HP 16GB', 1),
    (7, 'Lenovo 256GB', 2),
    (4, 'Samsung 256GB', 2),
    (2, 'Alcatel 256GB', 2),
    (8, 'Apple 256GB', 2),
    (6, 'Nose 1TB', 3),
    (10, 'Asus', 4),
    (3, 'Rysen 5500u', 5),
    (4, 'Sony 14in', 6);
    
    
    
    