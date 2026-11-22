package es.iesquevedo.domain.mappers;

import es.iesquevedo.dao.model.HistoriaClinica;
import es.iesquevedo.domain.dto.HistoriaClinicaDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HistoriaDTOMapper {

    public HistoriaClinicaDTO toDTO(HistoriaClinica historia) {
        return HistoriaClinicaDTO.builder()
                .historia_id(historia.getHistoriaId())
                .paciente_id(historia.getPacienteId())
                .medico_id(historia.getMedicoId())
                .diagnostico(historia.getDiagnostico())
                .fecha_admision(historia.getFechaAdmision())
                .recetas(historia.getRecetas() != null ? historia.getRecetas() : Collections.emptyList()) // 👈 Mapea las recetas reales de la entidad
                .build();
    }

    public HistoriaClinica toEntity(HistoriaClinicaDTO dto) {
        return HistoriaClinica.builder()
                .historiaId(dto.getHistoria_id())
                .pacienteId(dto.getPaciente_id())
                .medicoId(dto.getMedico_id())
                .diagnostico(dto.getDiagnostico())
                .fechaAdmision(dto.getFecha_admision())
                .recetas(dto.getRecetas()) // 👈 Mapea las recetas del DTO hacia la entidad al guardar/actualizar
                .build();
    }

    public List<HistoriaClinicaDTO> toDTOList(List<HistoriaClinica> list) {
        return list.stream().map(this::toDTO).collect(Collectors.toList());
    }
}