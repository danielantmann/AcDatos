package es.iesquevedo.ui;

import es.iesquevedo.domain.dto.HistoriaClinicaDTO;
import es.iesquevedo.domain.services.HistoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class RestHistoria {

    private final HistoriaService historiaService;

    public RestHistoria(HistoriaService historiaService) {
        this.historiaService = historiaService;
    }

    @GetMapping("/pacientes/{id}/historias")
    public List<HistoriaClinicaDTO> getHistoriasByPaciente(@PathVariable Long id) {
        return historiaService.getByPacienteId(id);
    }

    @PostMapping("/historias")
    public Long nuevaHistoria(@RequestBody HistoriaClinicaDTO dto) {
        return historiaService.add(dto);
    }

    @PutMapping("/historias")
    public void actualizarHistoria(@RequestBody HistoriaClinicaDTO dto) {
        historiaService.update(dto);
    }

    @DeleteMapping("/historias/{id}")
    public void eliminarHistoria(@PathVariable Long id) {
        historiaService.delete(id);
    }
}