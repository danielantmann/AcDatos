package org.example.domain.mappers;

import org.example.dao.model.Jugador;
import org.example.dao.model.Personaje;
import org.example.domain.dto.JugadorDTO;
import org.example.domain.dto.PersonajeDTO;

public class PersonajeDTOMapper {

    public PersonajeDTO toDto(Personaje personaje){
        if (personaje == null){
            return  null;
        }

        return PersonajeDTO.builder()
                .personajeId(personaje.getPersonajeId())
                .nombre(personaje.getNombre())
                .tipo(personaje.getTipo())
                .nivel(personaje.getNivel())
                .jugador(personaje.getJugador())
                .build();
    }

    public Personaje toEntity(PersonajeDTO dto){
        if (dto == null){
            return  null;
        }

        return Personaje.builder()
                .personajeId(dto.getPersonajeId())
                .nombre(dto.getNombre())
                .tipo(dto.getTipo())
                .nivel(dto.getNivel())
                .jugador(dto.getJugador())
                .build();
    }
}
