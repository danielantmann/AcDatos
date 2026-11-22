package org.example.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MisionCompletadaDTO {
    private Long misionId;
    private Long personajeId;
    private LocalDate fechaCompletado;
}
