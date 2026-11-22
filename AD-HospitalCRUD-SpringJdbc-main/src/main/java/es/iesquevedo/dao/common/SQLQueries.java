package es.iesquevedo.dao.common;

public class SQLQueries {
  // Usuarios y Pacientes
  public static final String FIND_USUARIO_BY_USERNAME = "SELECT * FROM usuarios WHERE username = ?";
  public static final String FIND_ALL_PACIENTES = "SELECT * FROM pacientes";
  public static final String ADD_PACIENTE = "INSERT INTO pacientes (nombre, fecha_nacimiento, telefono) VALUES (?, ?, ?)";
  public static final String ADD_USUARIO = "INSERT INTO usuarios (username, password, paciente_id) VALUES (?, ?, ?)";
  public static final String UPDATE_PACIENTE = "UPDATE pacientes SET nombre = ?, fecha_nacimiento = ?, telefono = ? WHERE paciente_id = ?";
  public static final String DELETE_PACIENTE = "DELETE FROM pacientes WHERE paciente_id = ?";

  // Borrados en cascada para Pacientes
  public static final String DELETE_USUARIO_BY_PACIENTE = "DELETE FROM usuarios WHERE paciente_id = ?";
  public static final String DELETE_FACTURAS_BY_PACIENTE = "DELETE FROM facturas WHERE paciente_id = ?";
  public static final String DELETE_CITAS_BY_PACIENTE = "DELETE FROM citas WHERE paciente_id = ?";
  public static final String DELETE_RECETAS_BY_PACIENTE = """
      DELETE r FROM recetas r 
      JOIN historia_clinica h ON r.historia_id = h.historia_id 
      WHERE h.paciente_id = ?
  """;
  public static final String DELETE_HISTORIA_BY_PACIENTE = "DELETE FROM historia_clinica WHERE paciente_id = ?";

  // Médicos
  public static final String FIND_ALL_MEDICOS = "SELECT * FROM medicos";
  public static final String FIND_MEDICO_BY_ID = "SELECT * FROM medicos WHERE medico_id = ?";

  // Historias Clínicas
  public static final String FIND_HISTORIAS_BY_PACIENTE = "SELECT * FROM historia_clinica WHERE paciente_id = ?";
  public static final String INSERT_HISTORIA = "INSERT INTO historia_clinica (paciente_id, medico_id, diagnostico, fecha_admision) VALUES (?, ?, ?, ?)";
  public static final String UPDATE_HISTORIA = "UPDATE historia_clinica SET medico_id = ?, diagnostico = ?, fecha_admision = ? WHERE historia_id = ?";
  public static final String DELETE_HISTORIA = "DELETE FROM historia_clinica WHERE historia_id = ?";

  // Recetas
  public static final String FIND_RECETAS_BY_HISTORIA = "SELECT medicamento FROM recetas WHERE historia_id = ?";
  public static final String INSERT_RECETA = "INSERT INTO recetas (historia_id, medicamento, dosis) VALUES (?, ?, ?)";
  public static final String DELETE_RECETAS_BY_HISTORIA = "DELETE FROM recetas WHERE historia_id = ?";
}