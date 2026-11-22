package org.example.dao.model;

import lombok.*;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mision {
    private Long misionId;
    private String nombre;
    private String descripcion;
    private int tokens;
}
