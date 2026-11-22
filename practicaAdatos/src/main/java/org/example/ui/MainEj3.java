package org.example.ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import org.example.dao.model.MisionCompletada;
import org.example.dao.model.Personaje;
import org.example.domain.dto.MisionCompletadaDTO;
import org.example.domain.dto.PersonajeDTO;
import org.example.domain.error.DataBaseError;
import org.example.domain.services.MisionCompletadaService;
import org.example.domain.services.PersonajeService;

import java.util.List;
import java.util.Optional;

public class MainEj3 {
    private final MisionCompletadaService misionCompletadaService;
    private final PersonajeService personajeService;

    @Inject
    public MainEj3(MisionCompletadaService misionCompletadaService,
                   PersonajeService personajeService){
        this.misionCompletadaService = misionCompletadaService;
        this.personajeService = personajeService;
    }

    static void main() {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            MainEj3 mainEj3 = container.select(MainEj3.class).get();
            mainEj3.elimnarJugador();
        }
    }

    private void elimnarJugador(){
        try {
            IO.println("Introduce el nombre del personaje: ");
            String nombrePersonaje = IO.readln();

            Optional<PersonajeDTO> personajeOpt = personajeService.findPersonajeByNombre(nombrePersonaje);

            if (personajeOpt.isEmpty()){
                IO.println("No existe personaje con ese nombre en la base de datos.");
                return;
            }

            PersonajeDTO personaje = personajeOpt.get();

            List<MisionCompletadaDTO> mcOpt = misionCompletadaService.getMisionesCompletadasByPersonajeId(personaje.getPersonajeId());

            if (mcOpt.size() > 0){
                IO.println("El personaje " + personaje.getNombre()+ " tiene " + mcOpt.size()+ " completadas" );
                IO.println("Desea borrar el personaje igualmente. (s/n)");
                String respuesta = IO.readln().trim();

                if (respuesta.equalsIgnoreCase("s")){

                    personajeService.deletePersonajeByNombre(nombrePersonaje);
                }else {
                    IO.println("Ha decidido no borrar el personaje, si desea borrarlo intentelo nuevamente");
                }

            }else {
                personajeService.deletePersonajeByNombre(nombrePersonaje);
            }

        } catch (IllegalArgumentException e) {
            IO.println("Error: " + e.getMessage());
        } catch (DataBaseError e) {
            IO.println("Error de base de datos: " + e.getMessage());
        }
    }
}
