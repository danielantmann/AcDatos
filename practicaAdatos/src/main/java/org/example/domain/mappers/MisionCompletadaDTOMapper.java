package org.example.domain.mappers;

import org.example.dao.model.MisionCompletada;
import org.example.domain.dto.MisionCompletadaDTO;

public class MisionCompletadaDTOMapper {

    public MisionCompletadaDTO toDTO(MisionCompletada entity) {
        if (entity == null) {
            return null;
        }
        return MisionCompletadaDTO.builder()
                .misionId(entity.getMisionId())
                .personajeId(entity.getPersonajeId())
                .fechaCompletado(entity.getFechaCompletado())
                .build();
    }

    public MisionCompletada toEntity(MisionCompletadaDTO dto) {
        if (dto == null) {
            return null;
        }
        return MisionCompletada.builder()
                .misionId(dto.getMisionId())
                .personajeId(dto.getPersonajeId())
                .fechaCompletado(dto.getFechaCompletado())
                .build();
    }
}
