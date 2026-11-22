package es.iesquevedo.dao.repositories.springJdbc;

import es.iesquevedo.dao.common.SQLQueries;
import es.iesquevedo.dao.mappers.MedicoRowMapper;
import es.iesquevedo.dao.model.Medico;
import es.iesquevedo.dao.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class SpringJdbcMedicoRepository implements MedicoRepository {

    private final JdbcClient jdbcClient;
    private final MedicoRowMapper medicoRowMapper;

    public SpringJdbcMedicoRepository(JdbcClient jdbcClient, MedicoRowMapper medicoRowMapper) {
        this.jdbcClient = jdbcClient;
        this.medicoRowMapper = medicoRowMapper;
    }

    @Override
    public List<Medico> findAll() {
        return jdbcClient.sql(SQLQueries.FIND_ALL_MEDICOS)
                .query(medicoRowMapper)
                .list();
    }

    @Override
    public Optional<Medico> findById(Long id) {
        return jdbcClient.sql(SQLQueries.FIND_MEDICO_BY_ID)
                .param(id)
                .query(medicoRowMapper)
                .optional();
    }
}