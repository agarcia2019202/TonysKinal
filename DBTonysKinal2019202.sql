drop database if exists DBTonysKinal2019202;
create database DBTonysKinal2019202;

use DBTonysKinal2019202;

create table empresas(
	codigoEmpresa int primary key auto_increment,
    nombreEmpresa varchar(150) not null,
    direccion varchar(150) not null,
    telefono varchar(10) not null
);

create table tipoEmpleado(
	codigoTipoEmpleado int primary key auto_increment,
    descripcion varchar(100) not null
);

create table tipoPlato(
	codigoTipoPlato int primary key auto_increment,
    descripcionTipo varchar(100) not null
);

create table productos(
	codigoProducto int primary key auto_increment,
    nombreProducto varchar(150) not null,
    cantidad int not null
);

create table empleados(
	codigoEmpleado int primary key auto_increment,
    numeroEmpleado int not null unique,
    apellidosEmpleado varchar(150) not null,
    nombresEmpleado varchar (150) not null,
    direccionEmpleado varchar(150) not null,
    telefonoContacto varchar(10) not null,
    gradoCocinero varchar(50),
    codigoTipoEmpleado int not null,
    foreign key (codigoTipoEmpleado) references tipoEmpleado(codigoTipoEmpleado)
);

create table presupuesto(
	codigoPresupuesto int primary key auto_increment,
	fechaSolicitud date not null,
    cantidadPresupuesto decimal(10,2) not null,
    codigoEmpresa int not null,
    foreign key (codigoEmpresa) references empresas(codigoEmpresa)
);

create table servicios(
	codigoServicio int primary key auto_increment,
    fechaServicio date not null,
    tipoServicio varchar(100) not null,
    horaServicio time not null,
    lugarServicio varchar(100) not null,
    telefonoContacto varchar(10) not null,
    codigoEmpresa int,
    foreign key (codigoEmpresa) references empresas(codigoEmpresa)
);

create table platos(
	codigoPlato int primary key auto_increment,
    cantidad int not null,
    nombrePlato varchar(50) not null,
    descripcionPlato varchar(150) not null,
    precioPlato decimal(10,2) not null,
    codigoTipoPlato int,
    foreign key (codigoTipoPlato) references tipoPlato(codigoTipoPlato)
);

create table servicios_has_Empleados(
	codigoServicioEmpleados int primary key auto_increment,
	servicios_codigoServicio int not null,
    empleados_codigoEmpleado int not null,
    fechaEvento date not null,
    horaEvento time not null,
    lugarEvento varchar(150) not null,
	foreign key (servicios_codigoServicio) references servicios(codigoServicio),
    foreign key (empleados_codigoEmpleado) references empleados(codigoEmpleado)
);

create table servicios_has_Platos(
	servicios_codigoServicio int not null,
    platos_codigoPlato int not null,
    foreign key (servicios_codigoServicio) references servicios(codigoServicio),
    foreign key (platos_codigoPlato) references platos(codigoPlato)
);

create table productos_has_Platos(
	productos_codigoProducto int not null,
    platos_codigoPlato int not null,
    foreign key (productos_codigoProducto) references productos(codigoProducto),
    foreign key (platos_codigoPlato) references platos(codigoPlato)
);


/*-----------------------------------------------------------------*/
-- tipo empleado
DELIMITER $$ 
CREATE PROCEDURE sp_AgregarTipoEmpleado(in descripcionx varchar(100))
BEGIN
	INSERT INTO tipoempleado(descripcion) 
    VALUES (descripcionx);
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ListarTipoEmpleado()
BEGIN
	SELECT * FROM tipoempleado;

END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ActualizarTipoEmpleado(in codigoTipoEmpleadox int, descripcionx varchar(100))
BEGIN
	UPDATE tipoempleado 
    SET descripcion = descripcionx
    WHERE codigoTipoEmpleado = codigoTipoEmpleadox;
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_EliminarTipoEmpleado(in codigoTipoEmpleadox int)
BEGIN 
	DELETE FROM tipoempleado WHERE codigoTipoEmpleado = codigoTipoEmpleadox; 
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_BuscarTipoEmpleado(in codigoTipoEmpleadox int)
BEGIN 
	SELECT*FROM tipoempleado WHERE codigoTipoEmpleado = codigoTipoEmpleadox; 
END$$
delimiter ;


/*-----------------------------------------------------------------*/
-- tipo plato
DELIMITER $$ 
CREATE PROCEDURE sp_AgregarTipoPlato(in descripcionTipox varchar(100))
BEGIN
	INSERT INTO tipoplato(descripcionTipo) 
    VALUES (descripcionTipox);
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ListarTipoPlato()
BEGIN
	SELECT * FROM tipoplato;

END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ActualizarTipoPlato(in codigoTipoPlatox int, descripcionTipox varchar(100))
BEGIN
	UPDATE tipoplato 
    SET descripcionTipo = descripcionTipox
    WHERE codigoTipoPlato = codigoTipoPlatox;
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_EliminarTipoPlato(in codigoTipoPlatox int)
BEGIN 
	DELETE FROM tipoplato WHERE codigoTipoPlato = codigoTipoPlatox; 
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_BuscarTipoPlato(in codigoTipoPlatox int)
BEGIN 
	SELECT*FROM tipoplato WHERE codigoTipoPlato = codigoTipoPlatox; 
END$$
delimiter ;

/*-----------------------------------------------------------------*/
-- productos
DELIMITER $$ 
CREATE PROCEDURE sp_AgregarProductos(in nombreProductox varchar(150), cantidadx int)
BEGIN
	INSERT INTO productos(nombreProducto, cantidad) 
    VALUES (nombreProductox, cantidadx);
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ListarProductos()
BEGIN
	SELECT * FROM productos;

END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ActualizarProductos(in codigoProductox int, nombreProductox varchar(150), cantidadx int)
BEGIN
	UPDATE productos 
    SET nombreProducto = nombreProductox, cantidad = cantidadx
    WHERE codigoProducto = codigoProductox;
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_EliminarProductos(in codigoProductox int)
BEGIN 
	DELETE FROM productos WHERE codigoProducto = codigoProductox; 
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_BuscarProductos(in codigoProductox int)
BEGIN 
	SELECT*FROM productos WHERE codigoProducto = codigoProductox; 
END$$
delimiter ;

/*-----------------------------------------------------------------*/
-- empresas
DELIMITER $$ 
CREATE PROCEDURE sp_AgregarEmpresas(in nombreEmpresax varchar(150), direccionx varchar(150), telefonox varchar(10))
BEGIN
	INSERT INTO empresas(nombreEmpresa, direccion, telefono) 
    VALUES (nombreEmpresax, direccionx, telefonox);
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ListarEmpresas()
BEGIN
	SELECT * FROM empresas;
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ActualizarEmpresas(in codigoEmpresax int, nombreEmpresax varchar(150), direccionx varchar(150), telefonox varchar(10))
BEGIN
	UPDATE empresas 
    SET nombreEmpresa = nombreEmpresax , direccion = direccionx , telefono = telefonox 
    WHERE codigoEmpresa = codigoEmpresax;
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_EliminarEmpresas(in codigoEmpresax int)
BEGIN 
	DELETE FROM empresas WHERE codigoEmpresa = codigoEmpresax; 
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_BuscarEmpresas(in codigoEmpresax int)
BEGIN 
	SELECT*FROM empresas WHERE codigoEmpresa = codigoEmpresax; 
END$$
delimiter ;

/*-----------------------------------------------------------------*/
-- empleados
DELIMITER $$ 
CREATE PROCEDURE sp_AgregarEmpleados(in numeroEmpleadox int, apellidosEmpleadox varchar(150), nombresEmpleadox varchar (150),
direccionEmpleadox varchar(150), telefonoContactox varchar(10), gradoCocinerox varchar(50), codigoTipoEmpleadox int)
BEGIN
	INSERT INTO empleados(numeroEmpleado, apellidosEmpleado, nombresEmpleado, direccionEmpleado, telefonoContacto, gradoCocinero, codigoTipoEmpleado) 
    VALUES (numeroEmpleadox, apellidosEmpleadox, nombresEmpleadox, direccionEmpleadox, telefonoContactox, gradoCocinerox, codigoTipoEmpleadox);
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ListarEmpleados()
BEGIN
	SELECT * FROM empleados;

END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ActualizarEmpleados(in codigoEmpleadox int, numeroEmpleadox int, apellidosEmpleadox varchar(150),
nombresEmpleadox varchar (150), direccionEmpleadox varchar(150), telefonoContactox varchar(10), gradoCocinerox varchar(50))
BEGIN
	UPDATE empleados 
	SET numeroEmpleado = numeroEmpleadox , apellidosEmpleado = apellidosEmpleadox , nombresEmpleado = nombresEmpleadox , 
	direccionEmpleado = direccionEmpleadox , telefonoContacto = telefonoContactox , gradoCocinero = gradoCocinerox
	WHERE codigoEmpleado = codigoEmpleadox;
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_EliminarEmpleados(in codigoEmpleadox int)
BEGIN 
	DELETE FROM empleados WHERE codigoEmpleado = codigoEmpleadox; 
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_BuscarEmpleados(in codigoEmpleadox int)
BEGIN 
	SELECT*FROM empleados WHERE codigoEmpleado = codigoEmpleadox; 
END$$
delimiter ;

/*-----------------------------------------------------------------*/
-- platos
DELIMITER $$ 
CREATE PROCEDURE sp_AgregarPlatos(in cantidadx int, nombrePlatox varchar(50), descripcionPlatox varchar(150), precioPlatox decimal(10,2), codigoTipoPlatox int)
BEGIN
	INSERT INTO platos(cantidad, nombrePlato, descripcionPlato, precioPlato, codigoTipoPlato) 
    VALUES (cantidadx, nombrePlatox, descripcionPlatox, precioPlatox, codigoTipoPlatox);
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ListarPlatos()
BEGIN
	SELECT * FROM platos;

END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ActualizarPlatos(in codigoPlatox int, cantidadx int, nombrePlatox varchar(50), descripcionPlatox varchar(150), 
precioPlatox decimal(10,2))
BEGIN
	UPDATE platos 
    SET cantidad = cantidadx , nombrePlato = nombrePlatox , descripcionPlato = descripcionPlatox , precioPlato = precioPlatox 
    WHERE codigoPlato = codigoPlatox;
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_EliminarPlatos(in codigoPlatox int)
BEGIN 
	DELETE FROM platos WHERE codigoPlato = codigoPlatox; 
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_BuscarPlatos(in codigoPlatox int)
BEGIN 
	SELECT*FROM platos WHERE codigoPlato = codigoPlatox; 
END$$
delimiter ;

/*-----------------------------------------------------------------*/
-- presupuesto
DELIMITER $$ 
CREATE PROCEDURE sp_AgregarPresupuesto(in fechaSolicitudx date, cantidadPresupuestox decimal(10,2), codigoEmpresax int)
BEGIN
	INSERT INTO presupuesto(fechaSolicitud, cantidadPresupuesto, codigoEmpresa) 
    VALUES (fechaSolicitudx, cantidadPresupuestox, codigoEmpresax);
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ListarPresupuesto()
BEGIN
	SELECT * FROM presupuesto;

END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ActualizarPresupuesto(in codigoPresupuestox int, fechaSolicitudx date, cantidadPresupuestox decimal(10,2))
BEGIN
	UPDATE presupuesto 
    SET fechaSolicitud = fechaSolicitudx, cantidadPresupuesto = cantidadPresupuestox
    WHERE codigoPresupuesto = codigoPresupuestox;
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_EliminarPresupuesto(in codigoPresupuestox int)
BEGIN 
	DELETE FROM presupuesto WHERE codigoPresupuesto = codigoPresupuestox; 
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_BuscarPresupuesto(in codigoPresupuestox int)
BEGIN 
	SELECT*FROM presupuesto WHERE codigoPresupuesto = codigoPresupuestox; 
END$$
delimiter ;

/*-----------------------------------------------------------------*/
-- servicios
DELIMITER $$ 
CREATE PROCEDURE sp_AgregarServicios(in fechaServiciox date, tipoServiciox varchar(100), horaServiciox time, lugarServiciox varchar(100), 
telefonoContactox varchar(10),  codigoEmpresax int)
BEGIN
	INSERT INTO servicios(fechaServicio, tipoServicio, horaServicio, lugarServicio, telefonoContacto, codigoEmpresa) 
    VALUES (fechaServiciox, tipoServiciox, horaServiciox, lugarServiciox, telefonoContactox, codigoEmpresax);
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ListarServicios()
BEGIN
	SELECT * FROM servicios;

END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ActualizarServicios(in codigoServiciox int, fechaServiciox date, tipoServiciox varchar(100), horaServiciox time,
lugarServiciox varchar(100), telefonoContactox varchar(10))
BEGIN
	UPDATE servicios 
    SET fechaServicio = fechaServiciox , tipoServicio = tipoServiciox , horaServicio = horaServiciox , lugarServicio = lugarServiciox ,
    telefonoContacto = telefonoContactox
    WHERE codigoServicio = codigoServiciox;
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_EliminarServicios(in codigoServiciox int)
BEGIN 
	DELETE FROM servicios WHERE codigoServicio = codigoServiciox; 
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_BuscarServicios(in codigoServiciox int)
BEGIN 
	SELECT*FROM servicios WHERE codigoServicio = codigoServiciox; 
END$$
delimiter ;

/*-----------------------------------------------------------------*/
-- Servicios_has_Empleados
DELIMITER $$
CREATE PROCEDURE sp_AgregarServiciosHasEmpleados(in servicios_codigoServiciox int, empleados_codigoEmpleadox int, fechaEventox date, horaEventox time, lugarEventox varchar(150))
BEGIN
	INSERT INTO servicios_has_empleados(servicios_codigoServicio, empleados_codigoEmpleado, fechaEvento, horaEvento, lugarEvento)
    VALUES(servicios_codigoServiciox, empleados_codigoEmpleadox, fechaEventox, horaEventox, lugarEventox);
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ListarServiciosHasEmpleados()
BEGIN 
	SELECT * FROM servicios_has_empleados;
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ActualizarServiciosHasEmpleados(in codigoServicioEmpleadosx int, fechaEventox date, horaEventox time, lugarEventox varchar(150))
BEGIN
	UPDATE servicios_has_empleados
	SET fechaEvento = fechaEventox, horaEvento = horaEventox, lugarEvento =lugarEventox
	WHERE codigoServicioEmpleados = codigoServicioEmpleadosx;
END $$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_EliminarServiciosHasEmpleados(in codigoServicioEmpleadosx int)
BEGIN
	DELETE FROM servicios_has_empleados WHERE codigoServicioEmpleados = codigoServicioEmpleadosx;
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_BuscarServiciosHasEmpleados(in codigoServicioEmpleadosx int)
BEGIN
	SELECT * FROM servicios_has_empleados WHERE codigoServicioEmpleados = codigoServicioEmpleadosx;
END$$
delimiter ;

/*-----------------------------------------------------------------*/
-- Servicios_has_Platos
DELIMITER $$
CREATE PROCEDURE sp_AgregarServiciosHasPlatos(in servicios_codigoServiciox int, platos_codigoPlatox int)
BEGIN
	INSERT INTO servicios_has_platos(servicios_codigoServicio, platos_codigoPlato)
		VALUES (servicios_codigoServiciox, platos_codigoPlatox);
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ListarServiciosHasPlatos()
BEGIN
	SELECT * FROM servicios_has_platos;
END$$
delimiter ;

/*-----------------------------------------------------------------*/
-- Productos_has_Platos
DELIMITER $$
CREATE PROCEDURE sp_AgregarProductosHasPlatos(in productos_codigoProductox int, platos_codigoPlatox int)
BEGIN
	INSERT INTO productos_has_platos(productos_codigoProducto, platos_codigoPlato)
		VALUES (productos_codigoProductox, platos_codigoPlatox);
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ListarProductosHasPlatos()
BEGIN
	SELECT * FROM productos_has_platos;
END$$
delimiter ;

/*-----------------------------------------------------------------*/
-- Stored Procedure Reporte Presupuesto

DELIMITER $$
CREATE PROCEDURE sp_ListarReporte(in codEmpresa int)
BEGIN
	SELECT * FROM empresas E inner join presupuesto P on
		E.codigoEmpresa = P.codigoEmpresa 
        where E.codigoEmpresa = codEmpresa group by P.cantidadPresupuesto;
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ListarReporteDos(in codEmpresa int)
BEGIN
	SELECT * FROM empresas E inner join servicios S on
		E.codigoEmpresa = S.codigoEmpresa 
        where E.codigoEmpresa = codEmpresa;
END$$
delimiter ;

/*-----------------------------------------------------------------*/
-- Stored Procedure Reporte Servicio

DELIMITER $$
CREATE PROCEDURE sp_ListarReporteServicio(in codServicio int)
BEGIN
	SELECT * FROM tipoplato 
    inner join platos on
		platos.codigoTipoPlato = tipoplato.codigoTipoPlato
    inner join servicios_has_platos on
		platos.codigoPlato = servicios_has_platos.platos_codigoPlato
    where platos.codigoPlato = servicios_has_platos.platos_codigoPlato and servicios_has_platos.servicios_codigoServicio = codServicio;
END$$
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_ListarReporteServicioDos(in codServicio int)
BEGIN
	SELECT * FROM productos_has_platos
    inner join platos on 
		platos.codigoPlato = productos_has_platos.platos_codigoPlato
	inner join productos on
		productos.codigoProducto = productos_has_platos.productos_codigoProducto
	inner join servicios_has_platos on 
		platos.codigoPlato = servicios_has_platos.platos_codigoPlato
    where platos.codigoPlato = servicios_has_platos.platos_codigoPlato and servicios_has_platos.servicios_codigoServicio = codServicio;
END$$
delimiter ;



/*-------------------------------------------------------------------------------------------------*/
-- tipo empleado
CALL sp_AgregarTipoEmpleado('contador');
CALL sp_AgregarTipoEmpleado('cocinero');
CALL sp_AgregarTipoEmpleado('mesero');
CALL sp_AgregarTipoEmpleado('organizador');
-- tipo plato
CALL sp_AgregarTipoPlato('Mediterraneo');
CALL sp_AgregarTipoPlato('Mariscos');
CALL sp_AgregarTipoPlato('Vegano');
CALL sp_AgregarTipoPlato('Típica Guatemalteca');
CALL sp_AgregarTipoPlato('Asiática');
-- productos
CALL sp_AgregarProductos('zanahoria', '50');
CALL sp_AgregarProductos('tomate', '50');
CALL sp_AgregarProductos('cebolla', '50');
CALL sp_AgregarProductos('ajo', '30');
CALL sp_AgregarProductos('arroz', '25');
CALL sp_AgregarProductos('queso feta', '10');
CALL sp_AgregarProductos('carne de cerdo', '50');
CALL sp_AgregarProductos('langosta', '20'); 
CALL sp_AgregarProductos('camaron', '50');
CALL sp_AgregarProductos('elote', '50');
-- empresas
CALL sp_AgregarEmpresas('BANCO INDUSTRIAL', 'MIXCO', '11111111');
CALL sp_AgregarEmpresas('TIGO', 'GUATEMALA', '22222222');
CALL sp_AgregarEmpresas('ALIMENTOS MARAVILLA', 'VILLA NUEVA', '33333333');
CALL sp_AgregarEmpresas('GIMNASIO EL BICHO', 'FRAIJANES', '44444444');
CALL sp_AgregarEmpresas('DHL', 'SAN JOSE PINULA', '55555555');
-- empleados
CALL sp_AgregarEmpleados('1', 'Messi Cuccitini', 'Leonel Andres', 'Direccion 1', '12123212', 'Grado 1', 2);
CALL sp_AgregarEmpleados('2', 'dos Santos Aveiro', 'Cristiano Ronaldo', 'Direccion 2', '36596894', 'Grado 1', 2);
CALL sp_AgregarEmpleados('3', 'Garcia Perez', 'Gazzy Huncho', 'Direccion 3', '65623849', 'Grado 2', 3);
CALL sp_AgregarEmpleados('4', 'Montenegro Alvarado', 'Austin Post', 'Direccion 4', '32897825', 'Grado 3', 1);
CALL sp_AgregarEmpleados('5', 'Perez Castañeda', 'Grecia Mariela', 'Direccion 5', '36699391', 'Grado 5', 4);
-- platos
CALL sp_AgregarPlatos('69', 'Paella', 'Arroz con mariscos', '40', 1);
CALL sp_AgregarPlatos('50', 'Camarones al Ajillo', 'Camarones sofreidos con ajo', '55', 2);
CALL sp_AgregarPlatos('100', 'Ensalada Griega Vegana', 'Verduras con queso Feta vegano', '50', 3);
CALL sp_AgregarPlatos('70', 'Pepián', 'Recado con carne de cerdo', '50', 4);
CALL sp_AgregarPlatos('80', 'Arroz Chino', 'Arroz con camaron y carne de cerdo', '40', 5);
-- presupuesto
CALL sp_AgregarPresupuesto('2020-06-20', '2000', 1);
CALL sp_AgregarPresupuesto('2020-06-15', '10000', 2);
CALL sp_AgregarPresupuesto('2020-06-18', '2500', 3);
CALL sp_AgregarPresupuesto('2020-06-28', '1800', 4);
CALL sp_AgregarPresupuesto('2020-06-23', '3000', 5);
-- servicios
CALL sp_AgregarServicios('2020-07-20', 'BODA', '18:00:00', 'PUERTO BARRIOS', '11111111', 1);
CALL sp_AgregarServicios('2020-07-15', 'CONVIVIO', '20:30:00', 'COBAN', '22222222', 2);
CALL sp_AgregarServicios('2020-07-18', 'CUMPLEAÑOS', '12:00:00', 'TECPAN', '33333333', 3);
CALL sp_AgregarServicios('2020-07-28', 'QUINCEAÑOS', '12:00:00', 'CHIQUIMULA', '44444444', 4);
CALL sp_AgregarServicios('2020-07-23', 'BUFFET', '08:00:00', 'ANTIGUA GUATEMALA', '55555555', 5);
-- servicios has empleados
CALL sp_AgregarServiciosHasEmpleados(1, 1, '2020-07-20', '18:00:00', 'PUERTO BARRIOS');
CALL sp_AgregarServiciosHasEmpleados(1, 2, '2020-07-20', '18:00:00', 'PUERTO BARRIOS');
CALL sp_AgregarServiciosHasEmpleados(1, 3, '2020-07-20', '18:00:00', 'PUERTO BARRIOS');
CALL sp_AgregarServiciosHasEmpleados(1, 4, '2020-07-20', '18:00:00', 'PUERTO BARRIOS');
CALL sp_AgregarServiciosHasEmpleados(1, 5, '2020-07-20', '18:00:00', 'PUERTO BARRIOS');
CALL sp_AgregarServiciosHasEmpleados(2, 1, '2020-07-15', '20:30:00', 'COBAN');
CALL sp_AgregarServiciosHasEmpleados(2, 3, '2020-07-15', '20:30:00', 'COBAN');
CALL sp_AgregarServiciosHasEmpleados(2, 5, '2020-07-15', '20:30:00', 'COBAN');
CALL sp_AgregarServiciosHasEmpleados(3, 2, '2020-07-18', '12:00:00', 'TECPAN');
CALL sp_AgregarServiciosHasEmpleados(3, 3, '2020-07-18', '12:00:00', 'TECPAN');
CALL sp_AgregarServiciosHasEmpleados(3, 5, '2020-07-18', '12:00:00', 'TECPAN');
CALL sp_AgregarServiciosHasEmpleados(4, 1, '2020-07-28', '12:00:00', 'CHIQUIMULA');
CALL sp_AgregarServiciosHasEmpleados(4, 2, '2020-07-28', '12:00:00', 'CHIQUIMULA');
CALL sp_AgregarServiciosHasEmpleados(4, 3, '2020-07-28', '12:00:00', 'CHIQUIMULA');
CALL sp_AgregarServiciosHasEmpleados(4, 4, '2020-07-28', '12:00:00', 'CHIQUIMULA');
CALL sp_AgregarServiciosHasEmpleados(4, 5, '2020-07-28', '12:00:00', 'CHIQUIMULA');
CALL sp_AgregarServiciosHasEmpleados(5, 1, '2020-07-23', '08:00:00', 'ANTIGUA GUATEMALA');
CALL sp_AgregarServiciosHasEmpleados(5, 2, '2020-07-23', '08:00:00', 'ANTIGUA GUATEMALA');
CALL sp_AgregarServiciosHasEmpleados(5, 5, '2020-07-23', '08:00:00', 'ANTIGUA GUATEMALA');
-- servicios has platos
CALL sp_AgregarServiciosHasPlatos('1','3');
CALL sp_AgregarServiciosHasPlatos('2','2');
CALL sp_AgregarServiciosHasPlatos('3','1');
CALL sp_AgregarServiciosHasPlatos('4','5');
CALL sp_AgregarServiciosHasPlatos('5','4');
-- productos has platos
CALL sp_AgregarProductosHasPlatos(1, 1);
CALL sp_AgregarProductosHasPlatos(3, 1);
CALL sp_AgregarProductosHasPlatos(4, 1);
CALL sp_AgregarProductosHasPlatos(5, 1);
CALL sp_AgregarProductosHasPlatos(8, 1);
CALL sp_AgregarProductosHasPlatos(9, 1);
CALL sp_AgregarProductosHasPlatos(10, 1);
CALL sp_AgregarProductosHasPlatos(4, 2);
CALL sp_AgregarProductosHasPlatos(5, 2);
CALL sp_AgregarProductosHasPlatos(9, 2);
CALL sp_AgregarProductosHasPlatos(1, 3);
CALL sp_AgregarProductosHasPlatos(2, 3);
CALL sp_AgregarProductosHasPlatos(3, 3);
CALL sp_AgregarProductosHasPlatos(6, 3);
CALL sp_AgregarProductosHasPlatos(10, 3);
CALL sp_AgregarProductosHasPlatos(1, 4);
CALL sp_AgregarProductosHasPlatos(2, 4);
CALL sp_AgregarProductosHasPlatos(3, 4);
CALL sp_AgregarProductosHasPlatos(5, 4);
CALL sp_AgregarProductosHasPlatos(7, 4);
CALL sp_AgregarProductosHasPlatos(10, 4);
CALL sp_AgregarProductosHasPlatos(3, 5);
CALL sp_AgregarProductosHasPlatos(4, 5);
CALL sp_AgregarProductosHasPlatos(5, 5);
CALL sp_AgregarProductosHasPlatos(7, 5);
CALL sp_AgregarProductosHasPlatos(9, 5);

