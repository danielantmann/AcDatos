package org.example.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonajeDTO {
    private Long personajeId;
    private String nombre;
    private String tipo;
    private int nivel;
    private Long jugador;
}
