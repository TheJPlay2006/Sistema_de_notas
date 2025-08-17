-- Crear base de datos
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'NotasDB')
    CREATE DATABASE NotasDB;
GO

USE NotasDB;
GO

-- Crear tabla
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Notas')
BEGIN
    CREATE TABLE Notas (
        id INT IDENTITY(1,1) PRIMARY KEY,
        titulo NVARCHAR(100) NOT NULL,
        descripcion NVARCHAR(500),
        calificacion DECIMAL(3,1) CHECK (calificacion >= 0.0 AND calificacion <= 10.0),
        categoria NVARCHAR(20) CHECK (categoria IN ('Tarea', 'Examen', 'Proyecto')),
        fecha DATE NOT NULL
    );
END
GO

USE NotasDB;
GO

-- Limpiar tabla (opcional: si quieres partir de cero)
DELETE FROM Notas;
DBCC CHECKIDENT (Notas, RESEED, 0);  -- Reinicia el ID a 1
GO

-- Insertar notas de ejemplo
INSERT INTO Notas (titulo, descripcion, calificacion, categoria, fecha) VALUES
('Examen Parcial Álgebra', 'Primer examen del semestre, temas: matrices y determinantes', 7.8, 'Examen', '2025-03-15'),
('Tarea 1 - Física', 'Ejercicios sobre cinemática y movimiento rectilíneo uniforme', 9.2, 'Tarea', '2025-03-10'),
('Proyecto Grupal - Programación', 'Desarrollo de una app de gestión de notas en Java', 8.5, 'Proyecto', '2025-04-05'),
('Quiz 2 - Química', 'Evaluación corta sobre enlaces químicos', 6.4, 'Examen', '2025-03-22'),
('Tarea 3 - Historia', 'Resumen del siglo XX en Europa', 9.0, 'Tarea', '2025-03-18'),
('Presentación Final - Inglés', 'Exposición sobre cultura británica', 8.7, 'Proyecto', '2025-04-10');

-- Verificar que los datos se insertaron
SELECT * FROM Notas ORDER BY fecha;