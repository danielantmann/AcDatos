package org.example.domain.services;

import jakarta.inject.Inject;
import org.example.dao.model.MisionCompletada;
import org.example.dao.model.Personaje;
import org.example.dao.repositories.JugadorRepository;
import org.example.dao.repositories.MisionCompletadaRepository;
import org.example.dao.repositories.PersonajeRepository;
import org.example.dao.utils.DBConnection;
import org.example.domain.dto.JugadorDTO;
import org.example.domain.dto.PersonajeDTO;
import org.example.domain.mappers.JugadorDTOMapper;
import org.example.domain.mappers.PersonajeDTOMapper;
import org.example.domain.xml.CmissionXml;
import org.example.domain.xml.PlayerXml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonajeService {
    private final PersonajeRepository personajeRepository;
    private final DBConnection dbConnection;
    private final PersonajeDTOMapper mapper = new PersonajeDTOMapper();
    private final MisionCompletadaRepository misionCompletadaRepository;

    @Inject
    public PersonajeService(PersonajeRepository personajeRepository, DBConnection dbConnection, MisionCompletadaRepository misionCompletadaRepository){
        this.personajeRepository = personajeRepository;
        this.dbConnection = dbConnection;
        this.misionCompletadaRepository = misionCompletadaRepository;
    }

    public Long savePersonaje(PersonajeDTO personajeDTO){
        return personajeRepository.save(mapper.toEntity(personajeDTO));
    }

    public Optional<PersonajeDTO> findPersonajeByNombre(String name) {
        return personajeRepository.findByName(name).map(mapper::toDto); // o el nombre de tu mapper
    }

    public boolean deletePersonajeByNombre(String name){
        Optional<Personaje> personajeOpt = personajeRepository.findByName(name);

        if (personajeOpt.isEmpty()){
            return false;
        }

        Personaje personaje = personajeOpt.get();

        List<MisionCompletada> mc = misionCompletadaRepository.findByPersonajeId(personaje.getPersonajeId());

        if (!mc.isEmpty()) {
            misionCompletadaRepository.deleteByPersonajeId(personaje.getPersonajeId());
        }

        return personajeRepository.deleteByName(name);
    }

    public PlayerXml buildPlayerXmlByJugadorId(Long jugadorId) {
        List<Personaje> personajes = personajeRepository.findByJugadorId(jugadorId);
        List<CmissionXml> cmissions = new ArrayList<>();

        for (Personaje personaje : personajes) {
            List<MisionCompletada> completadas =
                    misionCompletadaRepository.findByPersonajeId(personaje.getPersonajeId());

            for (MisionCompletada mc : completadas) {
                cmissions.add(new CmissionXml(mc.getMisionId(), personaje.getNombre()));
            }
        }

        return new PlayerXml(jugadorId, cmissions);
    }

}
