package es.iesquevedo.dao.repositories.springJdbc;

import es.iesquevedo.dao.common.SQLQueries;
import es.iesquevedo.dao.mappers.PacienteRowMapper;
import es.iesquevedo.dao.model.Paciente;
import es.iesquevedo.dao.repositories.PacienteRepository;
import es.iesquevedo.domain.error.PacienteDuplicadoError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Repository
public class SpringJdbcPacienteRepository implements PacienteRepository {

    private final JdbcClient jdbcClient;
    private final PacienteRowMapper pacienteRowMapper;

    public SpringJdbcPacienteRepository(JdbcClient jdbcClient,
                                        PacienteRowMapper pacienteRowMapper) {
        this.jdbcClient = jdbcClient;
        this.pacienteRowMapper = pacienteRowMapper;
    }

    @Override
    public List<Paciente> findAll() {
        return jdbcClient.sql(SQLQueries.FIND_ALL_PACIENTES)
                .query(pacienteRowMapper)
                .list();
    }

    @Override
    @Transactional
    public Long save(Paciente paciente) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try {
            jdbcClient.sql(SQLQueries.ADD_PACIENTE)
                    .param(paciente.getNombre())
                    .param(paciente.getFechaNacimiento())
                    .param(paciente.getTelefono())
                    .update(keyHolder, "paciente_id");

            Long idPaciente = keyHolder.getKeyAs(BigInteger.class).longValue();

            jdbcClient.sql(SQLQueries.ADD_USUARIO)
                    .param(paciente.getUsuario().getUsername())
                    .param(paciente.getUsuario().getPassword())
                    .param(idPaciente)
                    .update();

            return idPaciente;
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw new PacienteDuplicadoError();
        }
    }

    @Override
    public void update(Paciente paciente) {
        jdbcClient.sql(SQLQueries.UPDATE_PACIENTE)
                .params(paciente.getNombre(), paciente.getFechaNacimiento(), paciente.getTelefono(), paciente.getId())
                .update();
    }

    @Override
    @Transactional
    public void delete(Long id, boolean confirm) {
        try {
            // 1. Borrar primero el usuario asociado
            jdbcClient.sql(SQLQueries.DELETE_USUARIO_BY_PACIENTE)
                    .param(id)
                    .update();

            if (confirm) {
                // 2. Si se confirmó el borrado forzoso, limpiamos TODAS las tablas hijas en orden:

                // Borrar facturas del paciente
                jdbcClient.sql(SQLQueries.DELETE_FACTURAS_BY_PACIENTE)
                        .param(id)
                        .update();

                // Borrar citas del paciente
                jdbcClient.sql(SQLQueries.DELETE_CITAS_BY_PACIENTE)
                        .param(id)
                        .update();

                // Borrar recetas asociadas a las historias clínicas del paciente
                jdbcClient.sql(SQLQueries.DELETE_RECETAS_BY_PACIENTE)
                        .param(id)
                        .update();

                // Borrar la historia clínica del paciente
                jdbcClient.sql(SQLQueries.DELETE_HISTORIA_BY_PACIENTE)
                        .param(id)
                        .update();
            }

            // 3. Finalmente borrar al paciente de la tabla principal
            jdbcClient.sql(SQLQueries.DELETE_PACIENTE)
                    .param(id)
                    .update();

        } catch (DataIntegrityViolationException e) {
            log.error("No se puede borrar el paciente porque tiene registros asociados: {}", e.getMessage());
            throw e;
        }
    }
}