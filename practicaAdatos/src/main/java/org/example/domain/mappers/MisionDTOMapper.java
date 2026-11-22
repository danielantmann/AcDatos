package org.example.domain.mappers;

import org.example.dao.model.Mision;
import org.example.domain.dto.MisionDTO;

public class MisionDTOMapper {

    public MisionDTO toDTO(Mision mision) {
        if (mision == null) {
            return null;
        }
        return MisionDTO.builder()
                .misionId(mision.getMisionId())
                .nombre(mision.getNombre())
                .descripcion(mision.getDescripcion())
                .tokens(mision.getTokens())
                .build();
    }

    public Mision toEntity(MisionDTO dto) {
        if (dto == null) {
            return null;
        }
        return Mision.builder()
                .misionId(dto.getMisionId())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .tokens(dto.getTokens())
                .build();
    }
}
