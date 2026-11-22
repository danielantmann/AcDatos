package es.iesquevedo.domain.services;

import es.iesquevedo.dao.repositories.HistoriaRepository;
import es.iesquevedo.domain.dto.HistoriaClinicaDTO;
import es.iesquevedo.domain.mappers.HistoriaDTOMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoriaService {
    private final HistoriaRepository historiaRepository;
    private final HistoriaDTOMapper historiaDTOMapper;

    public HistoriaService(HistoriaRepository historiaRepository, HistoriaDTOMapper historiaDTOMapper) {
        this.historiaRepository = historiaRepository;
        this.historiaDTOMapper = historiaDTOMapper;
    }

    public List<HistoriaClinicaDTO> getByPacienteId(Long pacienteId) {
        return historiaDTOMapper.toDTOList(historiaRepository.findByPacienteId(pacienteId));
    }

    public Long add(HistoriaClinicaDTO dto) {
        return historiaRepository.save(historiaDTOMapper.toEntity(dto));
    }

    public void update(HistoriaClinicaDTO dto) {
        historiaRepository.update(historiaDTOMapper.toEntity(dto));
    }

    public void delete(Long id) {
        historiaRepository.delete(id);
    }
}