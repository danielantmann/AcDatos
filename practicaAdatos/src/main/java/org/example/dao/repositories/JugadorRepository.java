package org.example.dao.repositories;

import org.example.dao.model.Jugador;

import java.util.Optional;

public interface JugadorRepository {
    Long save(Jugador jugador);
    Optional<Jugador> findByName(String name);
    Optional<Jugador> findById(Long id);
}
