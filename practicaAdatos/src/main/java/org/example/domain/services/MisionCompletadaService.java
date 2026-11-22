package org.example.domain.services;

import jakarta.inject.Inject;
import org.example.dao.model.Jugador;
import org.example.dao.model.Mision;
import org.example.dao.model.MisionCompletada;
import org.example.dao.model.Personaje;
import org.example.dao.repositories.JugadorRepository;
import org.example.dao.repositories.MisionCompletadaRepository;
import org.example.dao.repositories.MisionRepository;
import org.example.dao.repositories.PersonajeRepository;
import org.example.dao.utils.DBConnection;
import org.example.domain.dto.MisionCompletadaDTO;
import org.example.domain.mappers.JugadorDTOMapper;
import org.example.domain.mappers.MisionCompletadaDTOMapper;
import org.example.domain.mappers.MisionDTOMapper;
import org.example.domain.mappers.PersonajeDTOMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MisionCompletadaService {
    private final MisionCompletadaRepository misionCompletadaRepository;
    private final DBConnection dbConnection;
    private final PersonajeRepository personajeRepository;
    private final JugadorRepository jugadorRepository;
    private final MisionRepository misionRepository;
    private final JugadorDTOMapper jugadorMapper = new JugadorDTOMapper();
    private final MisionCompletadaDTOMapper mcMapper = new MisionCompletadaDTOMapper();
    private final MisionDTOMapper misionMapper = new MisionDTOMapper();
    private final PersonajeDTOMapper personajeMapper  = new PersonajeDTOMapper();

    @Inject
    public MisionCompletadaService(MisionCompletadaRepository misionCompletadaRepository,
                                   JugadorRepository jugadorRepository,
                                   DBConnection dbConnection,
                                   PersonajeRepository personajeRepository,
                                   MisionRepository misionRepository
                                   ){
        this.misionCompletadaRepository = misionCompletadaRepository;
        this.jugadorRepository = jugadorRepository;
        this.dbConnection = dbConnection;
        this.personajeRepository = personajeRepository;
        this.misionRepository = misionRepository;
    }


    public boolean completarMision(String nombreJugador, String nombrePersonaje, String nombreMision, LocalDate fecha) {
        Optional<Jugador> jugadorOpt = jugadorRepository.findByName(nombreJugador);
        if (jugadorOpt.isEmpty()) {
            throw new IllegalArgumentException("Jugador no encontrado: " + nombreJugador);
        }

        Optional<Personaje> personajeOpt = personajeRepository.findByName(nombrePersonaje);
        if (personajeOpt.isEmpty()) {
            throw new IllegalArgumentException("Personaje no encontrado: " + nombrePersonaje);
        }

        Optional<Mision> misionOpt = misionRepository.findByName(nombreMision);
        if (misionOpt.isEmpty()) {
            throw new IllegalArgumentException("Misión no encontrada: " + nombreMision);
        }

        Jugador jugador = jugadorOpt.get();
        Personaje personaje = personajeOpt.get();
        Mision mision = misionOpt.get();

        // Comprobar que el personaje pertenece al jugador
        if (!jugador.getJugadorId().equals(personaje.getJugador())) {
            throw new IllegalArgumentException("El personaje " + nombrePersonaje + " no pertenece a " + nombreJugador);
        }

        // Comprobar si tiene tokens suficientes
        if (jugador.getTokens() < mision.getTokens()) {
            throw new IllegalStateException("El jugador " + nombreJugador + " no tiene tokens suficientes ("
                    + jugador.getTokens() + " disponibles, se requieren " + mision.getTokens() + ")");
        }

        return misionCompletadaRepository.save(mision.getMisionId(), personaje.getPersonajeId(), fecha);
    }


    public List<MisionCompletadaDTO> getMisionesCompletadasByPersonajeId(Long personajeId) {
        return misionCompletadaRepository.findByPersonajeId(personajeId).stream()
                .map(mcMapper::toDTO)
                .collect(Collectors.toList());
    }

    public boolean deleteMisionesCompletadasByPersonajeId(Long personajeId) {
        return misionCompletadaRepository.deleteByPersonajeId(personajeId);
    }

}
