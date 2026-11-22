package org.example.domain.services;

import jakarta.inject.Inject;
import org.example.dao.model.Jugador;
import org.example.dao.model.MisionCompletada;
import org.example.dao.model.Personaje;
import org.example.dao.repositories.JugadorRepository;
import org.example.dao.repositories.MisionCompletadaRepository;
import org.example.dao.repositories.PersonajeRepository;
import org.example.dao.utils.DBConnection;
import org.example.domain.dto.InformeJugadorDTO;
import org.example.domain.dto.JugadorDTO;
import org.example.domain.mappers.JugadorDTOMapper;

import java.util.*;

public class JugadorService {
    private final JugadorRepository jugadorRepository;
    private final DBConnection dbConnection;
    private final JugadorDTOMapper mapper = new JugadorDTOMapper();
    private final PersonajeRepository personajeRepository;
    private final MisionCompletadaRepository misionCompletadaRepository;

    @Inject
    public JugadorService(JugadorRepository jugadorRepository,DBConnection dbConnection, PersonajeRepository personajeRepository, MisionCompletadaRepository misionCompletadaRepository){
        this.jugadorRepository = jugadorRepository;

        this.dbConnection = dbConnection;
        this.personajeRepository = personajeRepository;
        this.misionCompletadaRepository = misionCompletadaRepository;
    }

    public Long saveJugador(JugadorDTO jugadorDTO){
        return jugadorRepository.save(mapper.toEntity(jugadorDTO));
    }

    public InformeJugadorDTO obtenerInformeJugador(String nombreJugador){
        Optional<Jugador> jugadorOpt = jugadorRepository.findByName(nombreJugador);
        if (jugadorOpt.isEmpty()) {
            throw new IllegalArgumentException("Jugador no encontrado: " + nombreJugador);
        }
        Jugador jugador = jugadorOpt.get();
        Long jugadorId = jugador.getJugadorId();

        List<Personaje> personajes  = personajeRepository.findByJugadorId(jugadorId);

        if (personajes.isEmpty()){
            throw new IllegalArgumentException("Personajes no encontrados en la lista de : " + nombreJugador);
        }

        Map<Personaje, List<MisionCompletada>> mapa = new LinkedHashMap<>();

        for (Personaje p : personajes){
            Long id = p.getPersonajeId();
            List<MisionCompletada> mc = misionCompletadaRepository.findByPersonajeId(id);
                mapa.put(p,mc);
        }

        return InformeJugadorDTO.builder()
                .nombre(jugador.getNombre())
                .tokens(jugador.getTokens())
                .listaMcPersonajes(mapa)
                .build();
    }
}
