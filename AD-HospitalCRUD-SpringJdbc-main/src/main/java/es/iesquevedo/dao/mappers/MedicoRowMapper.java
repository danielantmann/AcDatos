package es.iesquevedo.dao.mappers;

import es.iesquevedo.dao.model.Medico;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MedicoRowMapper implements RowMapper<Medico> {
    @Override
    public Medico mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Medico.builder()
                .medicoId(rs.getLong("medico_id"))
                .nombre(rs.getString("nombre"))
                .telefono(rs.getString("telefono"))
                .build();
    }
}