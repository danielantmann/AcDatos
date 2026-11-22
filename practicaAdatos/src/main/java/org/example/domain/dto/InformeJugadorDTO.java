package org.example.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dao.model.MisionCompletada;
import org.example.dao.model.Personaje;

import java.util.List;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformeJugadorDTO {
    private String nombre;
    private int tokens;
    Map<Personaje, List<MisionCompletada>> listaMcPersonajes;
}
