package es.iesquevedo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoriaClinicaDTO {
    private Long historia_id;
    private Long paciente_id;
    private Long medico_id;
    private String diagnostico;
    private LocalDate fecha_admision;
    private List<String> recetas; // Lista de nombres de medicamentos
}