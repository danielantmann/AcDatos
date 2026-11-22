package es.iesquevedo.dao.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medico {
    private Long medicoId;
    private String nombre;
    private String especialidad; // O String si lo tienes como texto plano
    private String telefono;
}