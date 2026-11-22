package org.example.dao.model;

import lombok.*;

import java.time.LocalDate;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MisionCompletada {
    private Long misionId;
    private Long personajeId;
    private LocalDate fechaCompletado;
}
