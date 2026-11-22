package org.example.domain.mappers;

import org.example.dao.model.Jugador;
import org.example.domain.dto.JugadorDTO;

public class JugadorDTOMapper {

    public JugadorDTO toDto(Jugador jugador){
        if (jugador == null){
            return  null;
        }

        return JugadorDTO.builder()
                .jugadorId(jugador.getJugadorId())
                .nombre(jugador.getNombre())
                .email(jugador.getEmail())
                .constraseña(jugador.getConstraseña())
                .tokens(jugador.getTokens())
                .build();
    }

    public Jugador toEntity(JugadorDTO dto){
        if (dto == null){
            return  null;
        }

        return Jugador.builder()
                .jugadorId(dto.getJugadorId())
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .constraseña(dto.getConstraseña())
                .tokens(dto.getTokens())
                .build();
    }
}
