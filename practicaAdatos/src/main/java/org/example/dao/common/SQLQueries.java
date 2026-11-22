package org.example.dao.common;

public final class SQLQueries {

  private SQLQueries() {
  }

  // --- JUGADORES ---
  public static final String SELECT_JUGADOR_BY_ID =
          "SELECT Jugador_ID, Nombre, Email, Contraseña, Tokens FROM jugadores WHERE Jugador_ID = ?";
  public static final String SELECT_JUGADOR_BY_NOMBRE =
          "SELECT Jugador_ID, Nombre, Email, Contraseña, Tokens FROM jugadores WHERE LOWER(Nombre) = LOWER(?)";

  public static final String INSERT_JUGADOR =
          "INSERT INTO jugadores (Nombre, Email, Contraseña, Tokens) VALUES (?, ?, ?, ?)";

  public static final String UPDATE_JUGADOR_TOKENS =
          "UPDATE jugadores SET Tokens = ? WHERE Jugador_ID = ?";

  // --- PERSONAJES ---
  public static final String SELECT_PERSONAJE_BY_ID =
          "SELECT Personaje_ID, Nombre, Tipo, Nivel, Jugador_ID FROM personajes WHERE Personaje_ID = ?";

  public static final String SELECT_PERSONAJE_BY_NOMBRE =
          "SELECT Personaje_ID, Nombre, Tipo, Nivel, Jugador_ID FROM personajes WHERE LOWER(Nombre) = LOWER(?)";

  public static final String SELECT_PERSONAJES_BY_JUGADOR_ID =
          "SELECT Personaje_ID, Nombre, Tipo, Nivel, Jugador_ID FROM personajes WHERE Jugador_ID = ?";

  public static final String INSERT_PERSONAJE =
          "INSERT INTO personajes (Nombre, Tipo, Nivel, Jugador_ID) VALUES (?, ?, ?, ?)";

  public static final String DELETE_PERSONAJE_BY_ID =
          "DELETE FROM personajes WHERE Personaje_ID = ?";

  public static final String DELETE_PERSONAJE_BY_NOMBRE =
          "DELETE FROM personajes WHERE LOWER(Nombre) = LOWER(?)";

  // --- MISIONES ---
  public static final String SELECT_MISION_BY_ID =
          "SELECT Mision_ID, Nombre, Descripcion, Recompensa, Tokens FROM misiones WHERE Mision_ID = ?";

  public static final String SELECT_MISION_BY_NOMBRE =
          "SELECT Mision_ID, Nombre, Descripcion, Tokens FROM misiones WHERE LOWER(Nombre) = LOWER(?)";

  // --- MISIONES COMPLETADAS ---
  public static final String INSERT_MISION_COMPLETADA =
          "INSERT INTO misiones_completadas (Mision_ID, Personaje_ID, Fecha_Completado) VALUES (?, ?, ?)";

  public static final String COUNT_MISIONES_COMPLETADAS_BY_PERSONAJE =
          "SELECT COUNT(*) FROM misiones_completadas WHERE Personaje_ID = ?";

  public static final String SELECT_MISIONES_COMPLETADAS_BY_PERSONAJE =
          "SELECT Mision_ID, Personaje_ID, Fecha_Completado FROM misiones_completadas WHERE Personaje_ID = ?";

  public static final String SELECT_MISIONES_COMPLETADAS_BY_ID =
          "SELECT Mision_ID, Personaje_ID, Fecha_Completado FROM misiones_completadas WHERE Mision_ID = ?";

  public static final String DELETE_MISIONES_COMPLETADAS_BY_PERSONAJE =
          "DELETE FROM misiones_completadas WHERE Personaje_ID = ?";

  // Consulta útil para el Ejercicio 1 (misiones completadas del jugador con id de misión y nombre de personaje)
  public static final String SELECT_MISIONES_COMPLETADAS_BY_JUGADOR =
          "SELECT mc.Mision_ID, p.Nombre AS Nombre_Personaje, mc.Fecha_Completado " +
                  "FROM misiones_completadas mc " +
                  "JOIN personajes p ON mc.Personaje_ID = p.Personaje_ID " +
                  "WHERE p.Jugador_ID = ?";


  // --- EXTRA JUGADORES ---
  public static final String SELECT_ALL_JUGADORES =
          "SELECT Jugador_ID, Nombre, Email, Contraseña, Tokens FROM jugadores ORDER BY Jugador_ID";

  public static final String DELETE_JUGADOR_BY_ID =
          "DELETE FROM jugadores WHERE Jugador_ID = ?";

  // --- EXTRA PERSONAJES ---
  public static final String SELECT_ALL_PERSONAJES =
          "SELECT Personaje_ID, Nombre, Tipo, Nivel, Jugador_ID FROM personajes ORDER BY Personaje_ID";

  public static final String UPDATE_PERSONAJE_NIVEL =
          "UPDATE personajes SET Nivel = ? WHERE Personaje_ID = ?";

  // --- EXTRA MISIONES ---
  public static final String SELECT_ALL_MISIONES =
          "SELECT Mision_ID, Nombre, Descripcion, Recompensa, Tokens FROM misiones ORDER BY Mision_ID";

  public static final String INSERT_MISION =
          "INSERT INTO misiones (Nombre, Descripcion, Recompensa, Tokens) VALUES (?, ?, ?, ?)";

  // --- EXTRA MISIONES COMPLETADAS / REPORTES ---
  public static final String EXISTS_MISION_COMPLETADA =
          "SELECT COUNT(*) FROM misiones_completadas WHERE Mision_ID = ? AND Personaje_ID = ?";

  public static final String DELETE_MISION_COMPLETADA_BY_MISION_Y_PERSONAJE =
          "DELETE FROM misiones_completadas WHERE Mision_ID = ? AND Personaje_ID = ?";

  // Misiones completadas por un personaje concreto con datos de la misión
  public static final String SELECT_DETALLE_MISIONES_BY_PERSONAJE =
          "SELECT mc.Mision_ID, m.Nombre AS Nombre_Mision, m.Recompensa, m.Tokens, mc.Fecha_Completado " +
                  "FROM misiones_completadas mc " +
                  "JOIN misiones m ON mc.Mision_ID = m.Mision_ID " +
                  "WHERE mc.Personaje_ID = ? " +
                  "ORDER BY mc.Fecha_Completado DESC";

  // Personajes que han completado una misión concreta (útil si piden por misión en vez de por jugador)
  public static final String SELECT_PERSONAJES_BY_MISION_ID =
          "SELECT p.Personaje_ID, p.Nombre AS Nombre_Personaje, p.Tipo, p.Nivel, j.Nombre AS Nombre_Jugador, mc.Fecha_Completado " +
                  "FROM misiones_completadas mc " +
                  "JOIN personajes p ON mc.Personaje_ID = p.Personaje_ID " +
                  "JOIN jugadores j ON p.Jugador_ID = j.Jugador_ID " +
                  "WHERE mc.Mision_ID = ? " +
                  "ORDER BY mc.Fecha_Completado DESC";
}