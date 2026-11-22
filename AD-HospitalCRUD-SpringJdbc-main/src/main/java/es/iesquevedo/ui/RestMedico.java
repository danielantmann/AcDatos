package es.iesquevedo.ui;

import es.iesquevedo.domain.dto.MedicoDTO;
import es.iesquevedo.domain.services.MedicoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class RestMedico {

    private final MedicoService medicoService;

    public RestMedico(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping("/medicos")
    public List<MedicoDTO> getAllMedicos() {
        return medicoService.getAll();
    }
}