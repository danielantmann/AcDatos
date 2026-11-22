DROP DATABASE IF EXISTS misiones_bd;
CREATE DATABASE misiones_bd;
USE misiones_bd;

CREATE TABLE jugadores (
                           Jugador_ID INT auto_increment PRIMARY KEY,
                           Nombre VARCHAR(100),
                           Email VARCHAR(100),
                           Contraseña VARCHAR(100),
                           Tokens INT
);

CREATE TABLE personajes (
                            Personaje_ID INT auto_increment PRIMARY KEY,
                            Nombre VARCHAR(100),
                            Tipo VARCHAR(100),
                            Nivel INT,
                            Jugador_ID INT,
                            FOREIGN KEY (Jugador_ID) REFERENCES jugadores(Jugador_ID)
);

CREATE TABLE misiones (
                          Mision_ID INT PRIMARY KEY,
                          Nombre VARCHAR(50),
                          Descripcion VARCHAR(150),
                          Tokens INT
);

CREATE TABLE misiones_completadas (
                                      Mision_ID INT,
                                      Personaje_ID INT,
                                      Fecha_Completado DATE,
                                      PRIMARY KEY (Mision_ID, Personaje_ID),
                                      FOREIGN KEY (Mision_ID) REFERENCES misiones(Mision_ID),
                                      FOREIGN KEY (Personaje_ID) REFERENCES personajes(Personaje_ID)
);

-- Insertando datos en la tabla jugadores
INSERT INTO jugadores (Jugador_ID, Nombre, Email, Tokens)
VALUES (1, 'John Doe', 'johndoe@example.com', 100),
       (2, 'Jane Doe', 'janedoe@example.com', 200),
       (3, 'Jim Doe', 'jimdoe@example.com', 300);

-- Insertando datos en la tabla personajes
INSERT INTO personajes (Personaje_ID, Nombre, Tipo, Nivel, Jugador_ID)
VALUES (1, 'Guerrero', 'Elfo', 10, 1),
       (2, 'Mago', 'Humano', 15, 2),
       (3, 'Pícaro', 'Enano', 20, 2),
       (4, 'Lucas', 'Perro', 5, 3);

-- Insertando datos en la tabla misiones
INSERT INTO misiones (Mision_ID, Nombre, Descripcion, Tokens)
VALUES (1, 'Primera Misión', 'Esta es la primera misión.', 50),
       (2, 'Segunda Misión', 'Esta es la segunda misión.', 75),
       (3, 'Tercera Misión', 'Esta es la tercera misión.', 100),
       (4, 'Caza del Dragon', 'Misión de caza del dragón.', 100);

-- Insertando datos en la tabla misiones_completadas
INSERT INTO misiones_completadas (Mision_ID, Personaje_ID, Fecha_Completado)
VALUES (1, 1, '2025-11-13'),
       (2, 2, '2025-11-14'),
       (3, 3, '2025-11-15'),
       (3, 4, '2025-11-15');