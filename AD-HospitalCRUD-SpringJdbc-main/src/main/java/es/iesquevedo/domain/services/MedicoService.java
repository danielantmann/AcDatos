package es.iesquevedo.domain.services;

import es.iesquevedo.dao.repositories.MedicoRepository;
import es.iesquevedo.domain.dto.MedicoDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public List<MedicoDTO> getAll() {
        return medicoRepository.findAll().stream()
                .map(medico -> MedicoDTO.builder()
                        .medico_id(medico.getMedicoId()) // 👈 Usamos medico_id aquí también
                        .nombre(medico.getNombre())
                        .build())
                .collect(Collectors.toList());
    }
}