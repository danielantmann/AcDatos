package org.example.dao.model;

import lombok.*;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Personaje {
    private Long personajeId;
    private String nombre;
    private String tipo;
    private int nivel;
    private Long jugador;
}
