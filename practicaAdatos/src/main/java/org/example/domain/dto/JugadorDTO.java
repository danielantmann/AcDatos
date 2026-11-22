package org.example.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JugadorDTO {
    private Long jugadorId;
    private String nombre;
    private String email;
    private String constraseña;
    private int tokens;
}
