-- Usamos la BBDD miAccion
USE miAccion; 
GO

-- Insertar empresas
INSERT INTO Empresa (simbolo, nombre, sector, cotizacion, divisa, PER, BPA, activo)
VALUES 
	('ANA', 'Acciona', 'Constructora', 0.00, 'EUR', 0.00, 0.00, 1),
	('ACX', 'Acerinox', 'Metalurgia', 0.00, 'EUR', 0.00, 0.00, 1),
	('ACS', 'ACS', 'Infraestructura', 0.00, 'EUR', 0.00, 0.00, 1),
	('AENA', 'Aena', 'Aeropuertos', 0.00, 'EUR', 0.00, 0.00, 1),
	('ALM', 'Almirall', 'Farmacéutica', 0.00, 'EUR', 0.00, 0.00, 1),
	('AMS', 'Amadeus', 'Tecnología', 0.00, 'EUR', 0.00, 0.00, 1),
	('MTS', 'ArcelorMittal', 'Metalurgia', 0.00, 'EUR', 0.00, 0.00, 1),
	('SAB', 'Banco Sabadell', 'Banca', 0.00, 'EUR', 0.00, 0.00, 1),
	('SAN', 'Banco Santander', 'Banca', 0.00, 'EUR', 0.00, 0.00, 1),
	('BKT', 'Bankinter', 'Banca', 0.00, 'EUR', 0.00, 0.00, 1),
	('BBVA', 'BBVA', 'Banca', 0.00, 'EUR', 0.00, 0.00, 1),
	('CABK', 'CaixaBank', 'Banca', 0.00, 'EUR', 0.00, 0.00, 1),
	('CLNX', 'Cellnex Telecom', 'Telecomunicaciones', 0.00, 'EUR', 0.00, 0.00, 1),
	('COL', 'Colonial', 'Inmobiliaria', 0.00, 'EUR', 0.00, 0.00, 1),
	('ENG', 'Enagás', 'Energía', 0.00, 'EUR', 0.00, 0.00, 1),
	('ELE', 'Endesa', 'Energía', 0.00, 'EUR', 0.00, 0.00, 1),
	('FER', 'Ferrovial', 'Infraestructura', 0.00, 'EUR', 0.00, 0.00, 1),
	('FDR', 'Fluidra', 'Tecnología', 0.00, 'EUR', 0.00, 0.00, 1),
	('GRF', 'Grifols', 'Farmacéutica', 0.00, 'EUR', 0.00, 0.00, 1),
	('IAG', 'IAG', 'Aerolínea', 0.00, 'EUR', 0.00, 0.00, 1),
	('IBE', 'Iberdrola', 'Energía', 0.00, 'EUR', 0.00, 0.00, 1),
	('ITX', 'Inditex', 'Moda', 0.00, 'EUR', 0.00, 0.00, 1),
	('IDR', 'Indra', 'Tecnología', 0.00, 'EUR', 0.00, 0.00, 1),
	('LOG', 'Logista', 'Distribución', 0.00, 'EUR', 0.00, 0.00, 1),
	('MAP', 'Mapfre', 'Seguros', 0.00, 'EUR', 0.00, 0.00, 1),
	('MEL', 'Meliá Hotels', 'Hotelería', 0.00, 'EUR', 0.00, 0.00, 1),
	('MRL', 'Merlin Properties', 'Inmobiliaria', 0.00, 'EUR', 0.00, 0.00, 1),
	('NTGY', 'Naturgy', 'Energía', 0.00, 'EUR', 0.00, 0.00, 1),
	('PHM', 'Pharmamar', 'Farmacéutica', 0.00, 'EUR', 0.00, 0.00, 1),
	('REP', 'Repsol', 'Petróleo', 0.00, 'EUR', 0.00, 0.00, 1),
	('ROVI', 'Rovi', 'Farmacéutica', 0.00, 'EUR', 0.00, 0.00, 1),
	('RED', 'Red Eléctrica', 'Energía', 0.00, 'EUR', 0.00, 0.00, 1),
	('SLR', 'Solaria', 'Energía Renovable', 0.00, 'EUR', 0.00, 0.00, 1),
	('TEF', 'Telefónica', 'Telecomunicaciones', 0.00, 'EUR', 0.00, 0.00, 1),
	('UNI', 'Unicaja Banco', 'Banca', 0.00, 'EUR', 0.00, 0.00, 1);

-- Insertar usuarios
INSERT INTO Usuario (email, nombre, apellidos, edad, password)
VALUES 
    ('usuario1@email.com', 'Juan', 'Pérez', 30,HASHBYTES('SHA2_256', 'contraseña1')),
    ('usuario2@email.com', 'Ana', 'González', 28, HASHBYTES('SHA2_256', 'contraseña2'));

--Brokers más típicos o más usados en la comunidad española:
--DEGIRO, ING, Interactive Brokers, iBroker, IG, xtb
INSERT INTO Broker (nombre, activo) 
VALUES
    ('Bankinter', 1),
	('BBVA', 1),
	('CaixaBank', 1),
    ('DEGIRO', 1),
	('eToro', 1),
	('FREEDOM24', 1),
	('ING', 1),
	('Interactive Brokers', 1),
	('iBroker', 1),
	('IG', 1),
	('renta4', 1),	
	('xtb', 1);

-- Insertar movimientos (10 ingresos por usuario)
-- SE USAN LOS SIGUIENTES VALORES PARA OPERACION:
/*
	1: Ingreso en cuenta
	2: Retiro de cuenta
	3: Compra de acción
	4: Venta de acción
	5: Cobro dividendo
*/

-- Modificando las fechas para que estén más salteadas en el tiempo
DECLARE @i INT = 1;
DECLARE @MES INT = 6;
DECLARE @FECHA1 VARCHAR(10);
DECLARE @FECHA2 VARCHAR(10);

WHILE @i <= 3
BEGIN
	SET @FECHA1 = CONCAT('2023-0', @MES, '-25');
	SET @FECHA2 = CONCAT('2023-0', @MES + 1, '-10');

    INSERT INTO movimientos_broker (idusuario, idbroker, operacion, fecha, importe, comision)
    VALUES 
        (1, 1, 1, @FECHA1, ROUND(RAND() * 1000, 2), ROUND(RAND() * 10, 2)),
        (1, 2, 2, @FECHA2, ROUND(RAND() * 1000, 2), ROUND(RAND() * 10, 2));

    SET @i = @i + 1;
	SET @MES = @MES + 1;
END;

-- Insertar empresas en seguimiento
INSERT INTO Seguimiento (simbolo,idUsuario)
VALUES ('TEF', 1),('RED', 1),('ACS', 1),('BBVA', 1),('BBVA', 2),('SAN', 2),('MEL', 2),('TEF', 2);


-- Insertar transacciones
-- Se comentan las inserciones en movimientos ya que se hacen automaticamente con el trigger en la BBDD

-- Transacción de compra
INSERT INTO Transacciones (idbroker, idusuario, simbolo, operacion, fecha, numacciones, precioaccion)
VALUES (1, 1, 'TEF', 3, '2023-09-05', 100, 12.50);

-- Registro en movimientos_broker para la compra.
-- INSERT INTO movimientos_broker (idbroker, idusuario, operacion, fecha, importe, comision)
-- VALUES (1, 1, 3, '2023-09-05', 1250.00, 5.00);

-- Transacción de venta
INSERT INTO Transacciones (idbroker, idusuario, simbolo, operacion, fecha, numacciones, precioaccion)
VALUES (1, 1, 'TEF', 4, '2023-09-15', 50, 13.00);

-- Registro en MovimientosBroker para la venta
-- INSERT INTO movimientos_broker (idbroker, idusuario, operacion, fecha, importe, comision)
-- VALUES (1, 1, 4, '2023-09-15', 650.00, 3.25);

-- Transacción de compra
INSERT INTO Transacciones (idbroker, idusuario, simbolo, operacion, fecha, numacciones, precioaccion)
VALUES (2, 2, 'ENG', 3, '2023-09-12', 50, 18.00);

-- Registro en MovimientosBroker para la compra
-- INSERT INTO movimientos_broker (idbroker, idusuario, operacion, fecha, importe, comision)
-- VALUES (2, 2, 3, '2023-09-12', 900.00, 4.50);

-- Transacción de venta
INSERT INTO Transacciones (idbroker, idusuario, simbolo, operacion, fecha, numacciones, precioaccion)
VALUES (2, 2, 'ENG', 4, '2023-09-20', 20, 19.00);

-- Registro en MovimientosBroker para la venta
-- INSERT INTO movimientos_broker (idbroker, idusuario, operacion, fecha, importe, comision)
-- VALUES (2, 2, 4, '2023-09-20', 380.00, 1.90);

-- Transacción de compra
INSERT INTO Transacciones (idbroker, idusuario, simbolo, operacion, fecha, numacciones, precioaccion)
VALUES (1, 1, 'RED', 3, '2023-09-10', 150, 19.00);

-- Registro en MovimientosBroker para la compra
-- INSERT INTO movimientos_broker (idbroker, idusuario, operacion, fecha, importe, comision)
-- VALUES (1, 1, 3, '2023-09-10', 2850.00, 9.00);

-- Transacción de venta
INSERT INTO Transacciones (idbroker, idusuario, simbolo, operacion, fecha, numacciones, precioaccion)
VALUES (1, 1, 'RED', 4, '2023-09-25', 150, 21.00);

-- Registro en MovimientosBroker para la venta
-- INSERT INTO movimientos_broker (idbroker, idusuario, operacion, fecha, importe, comision)
-- VALUES (1, 1, 4, '2023-09-25', 3150.00, 15.75);


-- Insertar cobros de dividendos
INSERT INTO Dividendos (simbolo, idbroker, idusuario, fecha, precioaccion, retencion)
VALUES ('RED', 1, 1, '2023-09-30', 2.00, 0.10);

-- Registro en MovimientosBroker 
-- INSERT INTO movimientos_broker (idbroker, idusuario, operacion, fecha, importe, comision)
-- VALUES (1, 1, 5, '2023-09-30', 300.00, 1.50);

-- Cobro de dividendos 
INSERT INTO Dividendos (simbolo, idbroker, idusuario, fecha, precioaccion, retencion)
VALUES ('TEF', 1, 1, '2023-10-05', 2.50, 0.15);

-- Registro en MovimientosBroker 
-- INSERT INTO movimientos_broker (idbroker, idusuario, operacion, fecha, importe, comision)
-- VALUES (1, 1, 5, '2023-09-30', 375.00, 1.87);

-- Cobro de dividendos 
INSERT INTO Dividendos (simbolo, idbroker, idusuario, fecha, precioaccion, retencion)
VALUES ('ENG', 1, 1, '2023-10-15', 3.00, 0.10);

-- Registro en MovimientosBroker 
-- INSERT INTO movimientos_broker (idbroker, idusuario, operacion, fecha, importe, comision)
-- VALUES (1, 1, 5, '2023-09-30', 450.00, 2.25);