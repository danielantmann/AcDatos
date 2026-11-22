package es.iesquevedo.dao.repositories.springJdbc;

import es.iesquevedo.dao.common.SQLQueries;
import es.iesquevedo.dao.mappers.HistoriaRowMapper;
import es.iesquevedo.dao.model.HistoriaClinica;
import es.iesquevedo.dao.repositories.HistoriaRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SpringJdbcHistoriaRepository implements HistoriaRepository {

    private final JdbcClient jdbcClient;
    private final HistoriaRowMapper historiaRowMapper;

    public SpringJdbcHistoriaRepository(JdbcClient jdbcClient, HistoriaRowMapper historiaRowMapper) {
        this.jdbcClient = jdbcClient;
        this.historiaRowMapper = historiaRowMapper;
    }

    @Override
    public List<HistoriaClinica> findByPacienteId(Long pacienteId) {
        List<HistoriaClinica> historias = jdbcClient.sql(SQLQueries.FIND_HISTORIAS_BY_PACIENTE)
                .param(pacienteId)
                .query(historiaRowMapper)
                .list();

        // Rellenar las recetas asociadas a cada historia clínica
        for (HistoriaClinica historia : historias) {
            List<String> recetas = jdbcClient.sql(SQLQueries.FIND_RECETAS_BY_HISTORIA)
                    .param(historia.getHistoriaId())
                    .query(String.class)
                    .list();
            historia.setRecetas(recetas);
        }

        return historias;
    }

    @Override
    @Transactional
    public Long save(HistoriaClinica historia) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(SQLQueries.INSERT_HISTORIA)
                .params(historia.getPacienteId(), historia.getMedicoId(), historia.getDiagnostico(), historia.getFechaAdmision())
                .update(keyHolder);

        Long historiaId = keyHolder.getKey().longValue();

        // Guardar las recetas asociadas
        if (historia.getRecetas() != null && !historia.getRecetas().isEmpty()) {
            for (String medicamento : historia.getRecetas()) {
                jdbcClient.sql(SQLQueries.INSERT_RECETA)
                        .params(historiaId, medicamento, "N/A")
                        .update();
            }
        }

        return historiaId;
    }

    @Override
    @Transactional
    public void update(HistoriaClinica historia) {
        jdbcClient.sql(SQLQueries.UPDATE_HISTORIA)
                .params(historia.getMedicoId(), historia.getDiagnostico(), historia.getFechaAdmision(), historia.getHistoriaId())
                .update();

        // Sincronizar recetas: borrar antiguas y meter las nuevas
        jdbcClient.sql(SQLQueries.DELETE_RECETAS_BY_HISTORIA)
                .param(historia.getHistoriaId())
                .update();

        if (historia.getRecetas() != null && !historia.getRecetas().isEmpty()) {
            for (String medicamento : historia.getRecetas()) {
                jdbcClient.sql(SQLQueries.INSERT_RECETA)
                        .params(historia.getHistoriaId(), medicamento, "N/A")
                        .update();
            }
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // 1. Borrar primero las recetas hijas asociadas
        jdbcClient.sql(SQLQueries.DELETE_RECETAS_BY_HISTORIA)
                .param(id)
                .update();

        // 2. Borrar la historia clínica
        jdbcClient.sql(SQLQueries.DELETE_HISTORIA)
                .param(id)
                .update();
    }
}