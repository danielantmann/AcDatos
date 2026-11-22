package es.iesquevedo.dao.mappers;

import es.iesquevedo.dao.model.HistoriaClinica;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

@Component
public class HistoriaRowMapper implements RowMapper<HistoriaClinica> {
    @Override
    public HistoriaClinica mapRow(ResultSet rs, int rowNum) throws SQLException {
        Date fechaSql = rs.getDate("fecha_admision");
        return HistoriaClinica.builder()
                .historiaId(rs.getLong("historia_id"))
                .pacienteId(rs.getLong("paciente_id"))
                .medicoId(rs.getLong("medico_id"))
                .diagnostico(rs.getString("diagnostico"))
                .fechaAdmision(fechaSql != null ? fechaSql.toLocalDate() : null)
                .build();
    }
}