-- Usamos la BBDD miAccion
USE miAccion; 
GO

-- Inserción de datos en la tabla Usuario
INSERT INTO usuario (email, nombre, apellidos, edad, password)
VALUES 
    ('usuario1@example.com', 'Usuario', 'Uno', 30, HASHBYTES('SHA2_256', 'contraseña1')),
    ('usuario2@example.com', 'Usuario', 'Dos', 25, HASHBYTES('SHA2_256', 'contraseña2')),
    ('usuario3@example.com', 'Usuario', 'Tres', 35, HASHBYTES('SHA2_256', 'contraseña3'));

-- Inserción de datos en la tabla Empresa

/* Divisas comunes de diferentes países:

Dólar estadounidense: $
Euro: €
Libra esterlina (Reino Unido): £
Yen japonés: ¥
Yuan chino (Renminbi): ¥
Dólar canadiense: $
Dólar australiano: $
Franco suizo: Fr.
Dólar neozelandés: $
Rublo ruso: ₽
Peso mexicano: $
Real brasileño: R$
Rupia india: ₹
Won surcoreano: ₩
Peso argentino: $
Rand sudafricano: R
Lira turca: ₺
Corona sueca: kr
Corona noruega: kr
Dólar de Singapur: $
Dólar de Hong Kong: HK$
Baht tailandés: ฿
*/
INSERT INTO empresa (simbolo, nombre, sector, cotizacion, divisa, activo)
VALUES 
    ('EMP1', 'Empresa 1', 'Tecnología', 50.00, '$', 1),
    ('EMP2', 'Empresa 2', 'Salud', 30.00, '¥', 1),
    ('EMP3', 'Empresa 3', 'Finanzas', 70.00, 'R$', 1);

-- Inserción de datos en la tabla Broker
INSERT INTO broker (nombre, activo)
VALUES 
    ('Broker A', 1),
    ('Broker B', 1),
    ('Broker C', 1);

-- Inserción de datos en la tabla MovimientosBroker

/* Diferentes tipos de operacion:
1 - Ingreso.
2 - Retirada.
3 - Compra.
4 - Venta.
5 - Dividendo.
*/
INSERT INTO movimientos_broker (idbroker, idusuario, operacion, fecha, importe, comision)
VALUES 
    (1, 1, 1, '2023-10-25', 500.00, 10.00),
    (2, 2, 2, '2023-10-26', 700.00, 15.00),
    (3, 3, 1, '2023-10-27', 1000.00, 20.00);

-- Inserción de datos en la tabla Transacciones
INSERT INTO transacciones (idbroker, idusuario, simbolo, operacion, fecha, numacciones, precioaccion)
VALUES 
    (1, 1, 'EMP1', 3,  '2023-10-25', 100, 45.00),
    (2, 2, 'EMP2', 3, '2023-10-26', 50, 32.00),
	(2, 2, 'EMP2', 4, '2023-10-26', 50, 30.00),
    (3, 3, 'EMP3', 3, '2023-10-27', 75, 65.00);

-- Inserción de datos en la tabla Dividendos
INSERT INTO dividendos (simbolo, idbroker, idusuario, fecha, precioaccion)
VALUES 
    ('EMP1', 1, 1, '2023-10-25', 5.00),
    ('EMP2', 2, 2, '2023-10-26', 3.00),
    ('EMP3', 3, 3, '2023-10-27', 4.00);
