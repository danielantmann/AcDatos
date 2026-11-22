package es.iesquevedo.dao.repositories;

import es.iesquevedo.dao.model.HistoriaClinica;
import java.util.List;

public interface HistoriaRepository {
    List<HistoriaClinica> findByPacienteId(Long pacienteId);
    Long save(HistoriaClinica historia);
    void update(HistoriaClinica historia);
    void delete(Long id);
}