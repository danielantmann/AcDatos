package org.example.dao.model;

import lombok.*;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Jugador {
    private Long jugadorId;
    private String nombre;
    private String email;
    private String constraseña;
    private int tokens;
}
