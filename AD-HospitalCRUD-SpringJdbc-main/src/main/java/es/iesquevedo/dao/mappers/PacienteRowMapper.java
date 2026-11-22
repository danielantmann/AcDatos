package es.iesquevedo.dao.mappers;

import es.iesquevedo.dao.model.Paciente;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date; // Asegúrate de importar java.sql.Date si hace falta

@Component
public class PacienteRowMapper implements RowMapper<Paciente> {

    @Override
    public Paciente mapRow(ResultSet rs, int rowNum) throws SQLException {
        Date fechaSql = rs.getDate("fecha_nacimiento");

        return Paciente.builder()
                .id(rs.getLong("paciente_id"))
                .nombre(rs.getString("nombre"))
                .fechaNacimiento(fechaSql != null ? fechaSql.toLocalDate() : null)
                .telefono(rs.getString("telefono"))
                .build();
    }
}