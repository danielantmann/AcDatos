package org.example.dao.repositories.jdbc;

import jakarta.inject.Inject;
import org.example.dao.common.RowMapper;
import org.example.dao.common.SQLQueries;
import org.example.dao.model.MisionCompletada;
import org.example.dao.repositories.MisionCompletadaRepository;
import org.example.dao.utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCMisionCompletadaRepository implements MisionCompletadaRepository {
    private static final Logger logger = Logger.getLogger(JDBCMisionCompletadaRepository.class.getName());
    private final DBConnection dbConnection;

    @Inject
    public JDBCMisionCompletadaRepository(DBConnection dbConnection ){
        this.dbConnection = dbConnection;
    }

    private final RowMapper<MisionCompletada>  mcRowMapper = rs -> {
        Date fecha = rs.getDate("Fecha_Completado");
        LocalDate fechaLocal = (fecha != null) ? fecha.toLocalDate() : null;

        return MisionCompletada.builder()
                .misionId(rs.getLong("Mision_ID"))
                .personajeId(rs.getLong("Personaje_ID"))
                .fechaCompletado(fechaLocal)
                .build();
    };

    @Override
    public boolean save(Long misionId, Long personajeId, LocalDate fechaCompletado) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQueries.INSERT_MISION_COMPLETADA)) {

            ps.setLong(1, misionId);
            ps.setLong(2, personajeId);
            ps.setDate(3, Date.valueOf(fechaCompletado));

            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al guardar misión completada", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean deleteByPersonajeId(Long personajeId) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQueries.DELETE_MISIONES_COMPLETADAS_BY_PERSONAJE)) {

            ps.setLong(1, personajeId);
            return ps.executeUpdate() > 0;

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            logger.log(Level.WARNING, "No se pueden borrar las misiones completadas del personaje ID " + personajeId, e);
            return false;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al borrar misiones completadas del personaje ID: " + personajeId, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MisionCompletada> findByPersonajeId(Long personajeId) {
        List<MisionCompletada> lista = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQueries.SELECT_MISIONES_COMPLETADAS_BY_PERSONAJE)) {

            ps.setLong(1, personajeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mcRowMapper.mapRow(rs));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar mision por ID: " + personajeId, e);
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public List<MisionCompletada> findByMisionId(Long misionId) {
        List<MisionCompletada> lista = new ArrayList<>();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQLQueries.SELECT_MISIONES_COMPLETADAS_BY_ID)) {

            ps.setLong(1, misionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mcRowMapper.mapRow(rs));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar mision por ID: " + misionId, e);
            throw new RuntimeException(e);
        }
        return lista;
    }
}
