package es.iesquevedo.dao.model;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoriaClinica {
    private Long historiaId;
    private Long pacienteId;
    private Long medicoId;
    private String diagnostico;
    private LocalDate fechaAdmision;
    private List<String> recetas;
}