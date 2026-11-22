package org.example.dao.repositories.jdbc;

import jakarta.inject.Inject;
import org.example.dao.common.SQLQueries;
import org.example.dao.model.Jugador;
import org.example.dao.repositories.JugadorRepository;
import org.example.dao.utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JDBCJugadorRepository implements JugadorRepository {
    private static final Logger logger = Logger.getLogger(JDBCJugadorRepository.class.getName());
    private final DBConnection dbConnection;

    @Inject
    public JDBCJugadorRepository(DBConnection dbConnection ){
        this.dbConnection = dbConnection;
    }

    @Override
    public Long save(Jugador jugador) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQueries.INSERT_JUGADOR, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, jugador.getNombre());
            ps.setString(2, jugador.getEmail());
            ps.setString(3, jugador.getConstraseña());
            ps.setInt(4, jugador.getTokens());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1); // Devuelve el ID generado
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al guardar jugador", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Optional<Jugador> findByName(String name) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_JUGADOR_BY_NOMBRE)){
            logger.log(Level.INFO, SQLQueries.SELECT_JUGADOR_BY_NOMBRE);
            preparedStatement.setString(1,name);

            try (ResultSet rs = preparedStatement.executeQuery()){
                if (rs.next()){

                    Jugador  jugador= Jugador.builder()

                            .jugadorId(rs.getLong("Jugador_ID"))
                            .nombre(rs.getString("Nombre"))
                            .email(rs.getString("Email"))
                            .constraseña(rs.getString("Contraseña"))
                            .tokens(rs.getInt("Tokens"))
                            .build();
                    return Optional.of(jugador);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar jugador por nombre: " + name, e);
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Jugador> findById(Long id) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_JUGADOR_BY_ID)){
            logger.log(Level.INFO, SQLQueries.SELECT_JUGADOR_BY_ID);
            preparedStatement.setLong(1,id);

            try (ResultSet rs = preparedStatement.executeQuery()){
                if (rs.next()){

                    Jugador  jugador= Jugador.builder()

                            .jugadorId(rs.getLong("Jugador_ID"))
                            .nombre(rs.getString("Nombre"))
                            .email(rs.getString("Email"))
                            .constraseña(rs.getString("Contraseña"))
                            .tokens(rs.getInt("Tokens"))
                            .build();
                    return Optional.of(jugador);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar jugador por id: " + id, e);
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

}
