package es.iesquevedo.ui;

import es.iesquevedo.domain.dto.PacienteDTO;
import es.iesquevedo.domain.dto.PacienteDTOAlta;
import es.iesquevedo.domain.services.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class RestPaciente {
  private final PacienteService pacienteService;

  public RestPaciente(PacienteService pacienteService) {
    this.pacienteService = pacienteService;
  }

  @GetMapping("/pacientes")
  public List<PacienteDTO> getAll() {
    return pacienteService.getAll();
  }

  @PostMapping("/pacientes")
  public Long nuevoPaciente(@RequestBody PacienteDTOAlta pacienteDTO) {
    return pacienteService.add(pacienteDTO);
  }

  @PutMapping({"/pacientes/{id}", "/pacientes"})
  public void actualizarPaciente(@PathVariable(required = false) String id,
                                 @RequestBody PacienteDTO pacienteDTO) {

    // Si la URL trae un número de ID válido, lo actualizamos.
    // Si trae "undefined", ignoramos la URL y dejamos que coja el ID que ya viaja dentro del JSON (el body).
    if (id != null && !id.equals("undefined")) {
      try {
        pacienteDTO.setId(Long.parseLong(id));
      } catch (NumberFormatException ignored) {
        // Si no se puede parsear, mantiene el id que viene en el cuerpo
      }
    }

    pacienteService.update(pacienteDTO);
  }
  @DeleteMapping("/pacientes/{id}")
  public ResponseEntity<String> eliminarPaciente(@PathVariable Long id,
                                                 @RequestParam(defaultValue = "false") boolean confirm) {
    try {
      pacienteService.delete(id, confirm);
      return ResponseEntity.noContent().build(); // 204 No Content cuando se borra correctamente
    } catch (Exception e) {
      // Devuelve texto plano limpio para que el confirm() del navegador muestre la frase correcta
      return ResponseEntity.status(409)
              .body("El paciente tiene registros médicos asociados. ¿Desea eliminarlo de todos modos?");
    }
  }
}