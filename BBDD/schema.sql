USE master;
GO

---------- Borrado previo de la BBDD en caso de existir ----------
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'miAccion')
BEGIN
	ALTER DATABASE miAccion SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE miAccion; 
END

----------- Crear la BBDD ----------
CREATE DATABASE miAccion; 
GO

---------- Usar la BBDD ----------
USE miAccion; 
GO

---------- Eliminar las tablas si existen ----------
IF EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'usuario')
    DROP TABLE usuario; 
	GO
	
IF EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'empresa')
    DROP TABLE empresa; 
	GO
	
IF EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'broker')
    DROP TABLE broker; 
	GO
	
IF EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'movimientos_broker')
    DROP TABLE movimientos_broker; 
	GO
	
IF EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'transacciones')
    DROP TABLE transacciones; 
	GO
	
IF EXISTS (SELECT * FROM information_schema.tables WHERE table_name = 'dividendos')
    DROP TABLE dividendos; 
	GO

---------- Creacion de tablas ----------
CREATE TABLE usuario (
    id INT IDENTITY(1,1) PRIMARY KEY,
	email NVARCHAR(30) NOT NULL,
    nombre NVARCHAR(30),
    apellidos NVARCHAR(30),
	edad INT,
    password VARBINARY(32) NOT NULL
); 
GO

/* Divisas comunes de diferentes países:

Dólar estadounidense: $, Euro: €, Libra esterlina (Reino Unido): £, Yen japonés: ¥
Yuan chino (Renminbi): ¥, Dólar canadiense: $, Dólar australiano: $, Franco suizo: Fr.
Dólar neozelandés: $, Rublo ruso: ₽, Peso mexicano: $, Real brasileño: R$, Rupia india: ₹
Won surcoreano: ₩, Peso argentino: $, Rand sudafricano: R, Lira turca: ₺, Corona sueca: kr
Corona noruega: kr, Dólar de Singapur: $, Dólar de Hong Kong: HK$, Baht tailandés: ฿
*/
CREATE TABLE empresa (
    simbolo NVARCHAR(5) PRIMARY KEY NOT NULL,
    nombre NVARCHAR(100),
	sector NVARCHAR(100),
    cotizacion DECIMAL(10, 2),
    divisa NVARCHAR(5),
	PER DECIMAL(10, 2) DEFAULT 0,
	BPA DECIMAL(10, 2) DEFAULT 0,
	activo BIT
); 
GO

CREATE TABLE broker (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(100),
	activo BIT
); 
GO

/* Diferentes tipos de operacion:
1 - Ingreso. 2 - Retirada. 3 - Compra. 4 - Venta. 5 - Dividendo.
*/
CREATE TABLE movimientos_broker (
    id INT IDENTITY(1,1) PRIMARY KEY,
	idbroker INT NOT NULL FOREIGN KEY REFERENCES broker(id),
    idusuario INT NOT NULL FOREIGN KEY REFERENCES usuario(id) ON DELETE CASCADE,
	concepto NVARCHAR(100) DEFAULT '',
	operacion INT NOT NULL,
	fecha DATE,
    importe DECIMAL(10, 2) DEFAULT 0,
	comision DECIMAL(10, 2),
	idoperacion INT DEFAULT 0 -- campo para relacionar dividendos o transacciones con el movimiento
); 
GO

CREATE TABLE transacciones (
    id INT IDENTITY(1,1) PRIMARY KEY,
	idbroker INT NOT NULL FOREIGN KEY REFERENCES broker(id),
	idusuario INT NOT NULL FOREIGN KEY REFERENCES usuario(id) ON DELETE CASCADE,
	simbolo NVARCHAR(5) NOT NULL FOREIGN KEY REFERENCES empresa(simbolo),
	operacion INT,
	fecha DATE,
	numacciones INT,
    precioaccion DECIMAL(10, 2) NOT NULL   
); 
GO

CREATE TABLE dividendos (
    id INT IDENTITY(1,1) PRIMARY KEY,
	simbolo NVARCHAR(5) NOT NULL FOREIGN KEY REFERENCES empresa(simbolo),
    idbroker INT NOT NULL FOREIGN KEY REFERENCES broker(id),
	idusuario INT NOT NULL FOREIGN KEY REFERENCES usuario(id) ON DELETE CASCADE,
	fecha DATE,
    precioaccion DECIMAL(10, 2) NOT NULL,  
	retencion DECIMAL(5, 2) DEFAULT 0
); 
GO

CREATE TABLE seguimiento (
    simbolo NVARCHAR(5) NOT NULL,
    idUsuario INT NOT NULL,
    PRIMARY KEY (simbolo, idUsuario),
    FOREIGN KEY (simbolo) REFERENCES empresa(simbolo),
    FOREIGN KEY (idUsuario) REFERENCES usuario(id) ON DELETE CASCADE,
);
GO

---------- Crear desencadenadores ----------

CREATE TRIGGER trg_dividendos_movimientos
ON dividendos
AFTER INSERT
AS
BEGIN
    -- Insertar registro			
	INSERT INTO movimientos_broker (idbroker, idusuario, concepto, operacion, fecha, importe, comision, idoperacion)
	SELECT
		ins.idbroker,
		ins.idusuario,
		emp.nombre,
		5,
		ins.fecha,
		ISNULL((SELECT SUM(numacciones) from transacciones WHERE idusuario = ins.idusuario AND simbolo = ins.simbolo) * ins.precioaccion, 0),
		0,
		ins.id
	FROM
		inserted ins
	INNER JOIN empresa emp ON ins.simbolo = emp.simbolo;
END;
GO

CREATE TRIGGER trg_dividendos_movimientos_upt
ON dividendos
AFTER UPDATE
AS
BEGIN
    -- Actualizar registro			
	UPDATE movimientos_broker 
	SET 
		idbroker = ins.idbroker,
		idusuario = ins.idusuario,
		concepto = emp.nombre,
		operacion = 5,
		fecha = ins.fecha,
		importe = ISNULL((SELECT SUM(numacciones) from transacciones WHERE idusuario = ins.idusuario AND simbolo = ins.simbolo) * ins.precioaccion, 0),
		comision = 0,
		idoperacion = ins.id
	FROM
		inserted ins
	INNER JOIN empresa emp ON ins.simbolo = emp.simbolo
	WHERE movimientos_broker.idoperacion = ins.id AND movimientos_broker.operacion = 5;
END;
GO

CREATE TRIGGER trg_dividendos_movimientos_del
ON dividendos
AFTER DELETE
AS
BEGIN
    -- Borrar registro
	DELETE mov
	FROM movimientos_broker mov
	INNER JOIN deleted del ON mov.idbroker = del.idbroker 
                     AND mov.idusuario = del.idusuario 
                     AND mov.idoperacion = del.id
	WHERE mov.operacion = 5;
END;
GO

CREATE TRIGGER trg_transacciones_movimientos
ON transacciones
AFTER INSERT
AS
BEGIN
    -- Insertar registro			
	INSERT INTO movimientos_broker (idbroker, idusuario, concepto, operacion, fecha, importe, comision, idoperacion)
	SELECT
		ins.idbroker,
		ins.idusuario,
		emp.nombre,
		ins.operacion,
		ins.fecha,
		ins.numacciones * ins.precioaccion,
		0,
		ins.id
	FROM
		inserted ins
	INNER JOIN empresa emp ON ins.simbolo = emp.simbolo;
END;
GO

CREATE TRIGGER trg_transacciones_movimientos_upt
ON transacciones
AFTER UPDATE
AS
BEGIN
    -- Actualizar registro			
	UPDATE movimientos_broker
	SET
		idbroker = ins.idbroker,
		idusuario = ins.idusuario, 
		concepto = emp.nombre,
		operacion = ins.operacion, 
		fecha = ins.fecha,
		importe = ins.numacciones * ins.precioaccion,
		comision = 0,
		idoperacion = ins.id
	FROM
		inserted ins
	INNER JOIN empresa emp ON ins.simbolo = emp.simbolo
	WHERE movimientos_broker.idoperacion = ins.id AND movimientos_broker.operacion = ins.operacion;
END;
GO

CREATE TRIGGER trg_transacciones_movimientos_del
ON transacciones
AFTER DELETE
AS
BEGIN
    -- Borrar registro			
	DELETE mov
	FROM movimientos_broker mov
	INNER JOIN deleted del ON mov.idbroker = del.idbroker 
                     AND mov.idusuario = del.idusuario 
                     AND mov.idoperacion = del.id 
	WHERE mov.operacion = del.operacion;
END;
GO

CREATE TRIGGER trg_empresa_movimientos_upt
ON empresa
AFTER UPDATE
AS
BEGIN
    -- Actualizar registros
    UPDATE mb
    SET mb.concepto = emp.nombre
    FROM movimientos_broker mb
    INNER JOIN empresa emp ON mb.idoperacion IN (
        SELECT t.id
        FROM transacciones t
        WHERE t.simbolo = emp.simbolo
    )
    OR mb.idoperacion IN (
        SELECT d.id
        FROM dividendos d
        WHERE d.simbolo = emp.simbolo
    )
    INNER JOIN inserted i ON emp.simbolo = i.simbolo
    WHERE mb.operacion IN (3, 4, 5);
END;
GO
