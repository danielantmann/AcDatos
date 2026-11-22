package org.example.dao.repositories;

import org.example.dao.model.Jugador;
import org.example.dao.model.Personaje;

import java.util.List;
import java.util.Optional;

public interface PersonajeRepository {
    Long save(Personaje personaje);
    Optional<Personaje> findByName(String name);
    boolean deleteByName(String name);
    List<Personaje> findByJugadorId(Long jugadorId);
}
