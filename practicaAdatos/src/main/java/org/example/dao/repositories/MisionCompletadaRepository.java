package org.example.dao.repositories;

import org.example.dao.model.MisionCompletada;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MisionCompletadaRepository {
    boolean save(Long misionId, Long personajeId, LocalDate fechaCompletado);
    boolean deleteByPersonajeId(Long personajeId);
    List<MisionCompletada> findByPersonajeId(Long personajeId);
    List<MisionCompletada> findByMisionId(Long misionId);

}

