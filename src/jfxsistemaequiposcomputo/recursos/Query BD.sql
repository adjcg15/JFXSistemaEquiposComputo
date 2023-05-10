CREATE DATABASE gestionequipos;

CREATE USER 'adminSEC'@'localhost' IDENTIFIED BY 'adminSEC090523';
GRANT ALL PRIVILEGES ON gestionequipos . * TO 'adminSEC'@'localhost';

USE gestionequipos;

CREATE TABLE usuarios (
	idUsuario INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(45),
    apellidoPaterno VARCHAR(45),
    apellidoMaterno VARCHAR(45),
    telefono VARCHAR(45),
    direccion VARCHAR(45),
    correo VARCHAR(45),
    contrasenia VARCHAR(20),
    privilegiado BOOLEAN,
    PRIMARY KEY (idUsuario)
);

CREATE TABLE equiposDeComputo (
	idEquipoDeComputo INT AUTO_INCREMENT NOT NULL,
    tipo VARCHAR(15),
    incluyeCargador VARCHAR(5),
    modelo VARCHAR(45),
    so VARCHAR(45),
    tamanioPantalla DOUBLE,
    contraseniaEquipo VARCHAR(20),
    procesador VARCHAR(20),
    memoria VARCHAR(45),
    marca VARCHAR(45),
    fechaRegistro DATE,
    fotoDelEquipo BLOB,
    usuarioSO VARCHAR(45),
    idUsuario INT,
    idSolicitudDiagnostico INT,
    PRIMARY KEY (idEquipoDeComputo)
);

CREATE TABLE solicitudesDiagnostico (
	idSolicitudDiagnostico INT AUTO_INCREMENT NOT NULL,
    observaciones LONGTEXT,
    PRIMARY KEY (idSolicitudDiagnostico)
);
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
    tipoDeMantenimiento VARCHAR(45),
    fechaAtencion DATE,
    diagnosticoPreliminar LONGTEXT,
    fechaSolicitud DATE,
    costoEstimado DOUBLE,
    propuestaSolucion LONGTEXT,
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
<<<<<<< HEAD

CREATE TABLE tipoRefacciones(
	idTipoRefaccion INT AUTO_INCREMENT NOT NULL,
    nombre VARCHAR(100),
    PRIMARY KEY(idTipoRefaccion)
);

ALTER TABLE refacciones ADD CONSTRAINT FK_Refaccion_TipoRefaccion
FOREIGN KEY(idTipoRefaccion)
REFERENCES tipoRefacciones(idTipoRefaccion) ON DELETE CASCADE;

=======
>>>>>>> 2586a979aded5918b2953b81e308e8877b15d289
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
VALUES ('pedro', 'lopez', 'Gomez', 2281814657, 'centro', 'pedro@gmail.com', 123456, true);