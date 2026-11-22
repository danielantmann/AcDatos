package org.example.dao.repositories.jdbc;

import jakarta.inject.Inject;
import org.example.dao.common.SQLQueries;
import org.example.dao.model.Jugador;
import org.example.dao.model.Personaje;
import org.example.dao.repositories.PersonajeRepository;
import org.example.dao.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCPersonajeRepository  implements PersonajeRepository {
    private static final Logger logger = Logger.getLogger(JDBCPersonajeRepository.class.getName());
    private final DBConnection dbConnection;

    @Inject
    public JDBCPersonajeRepository(DBConnection dbConnection ){
        this.dbConnection = dbConnection;
    }

    @Override
    public Long save(Personaje personaje) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQueries.INSERT_PERSONAJE, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, personaje.getNombre());
            ps.setString(2, personaje.getTipo());
            ps.setInt(3, personaje.getNivel());
            ps.setLong(4, personaje.getJugador());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1); // Devuelve el ID generado
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al guardar personaje", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Optional<Personaje> findByName(String name) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_PERSONAJE_BY_NOMBRE)){
            logger.log(Level.INFO, SQLQueries.SELECT_PERSONAJE_BY_NOMBRE);
            preparedStatement.setString(1,name);

            try (ResultSet rs = preparedStatement.executeQuery()){
                if (rs.next()){

                    Personaje personaje = Personaje.builder()

                            .personajeId(rs.getLong("Personaje_ID"))
                            .nombre(rs.getString("Nombre"))
                            .tipo(rs.getString("Tipo"))
                            .nivel(rs.getInt("Nivel"))
                            .jugador(rs.getLong("Jugador_ID"))
                            .build();
                    return Optional.of(personaje);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar peronaje por nombre: " + name, e);
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteByName(String name) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQueries.DELETE_PERSONAJE_BY_NOMBRE)) {

            ps.setString(1, name);
            return ps.executeUpdate() > 0;

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            logger.log(Level.WARNING, "No se pueden borrar el personaje con nombre:  " + name, e);
            return false;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al borrar el personaje con nombre:  " + name, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Personaje> findByJugadorId(Long jugadorId) {
        List<Personaje> personajes = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_PERSONAJES_BY_JUGADOR_ID)) {
            logger.log(Level.INFO, SQLQueries.SELECT_PERSONAJES_BY_JUGADOR_ID);
            preparedStatement.setLong(1, jugadorId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Personaje personaje = Personaje.builder()
                            .personajeId(rs.getLong("Personaje_ID"))
                            .nombre(rs.getString("Nombre"))
                            .tipo(rs.getString("Tipo"))
                            .nivel(rs.getInt("Nivel"))
                            .jugador(rs.getLong("Jugador_ID"))
                            .build();
                    personajes.add(personaje);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar personajes por jugador id: " + jugadorId, e);
            throw new RuntimeException(e);
        }
        return personajes;
    }


}

