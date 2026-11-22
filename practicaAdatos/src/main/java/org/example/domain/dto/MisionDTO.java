package org.example.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MisionDTO {
    private Long misionId;
    private String nombre;
    private String descripcion;
    private int tokens;
}
