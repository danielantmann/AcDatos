package org.example.ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import org.example.domain.dto.JugadorDTO;
import org.example.domain.dto.PersonajeDTO;
import org.example.domain.services.JugadorService;
import org.example.domain.services.PersonajeService;

public class MainEj2 {
    private final JugadorService jugadorService;
    private final PersonajeService personajeService;

    @Inject
    public MainEj2(JugadorService jugadorService,
                   PersonajeService personajeService) {
        this.jugadorService = jugadorService;
        this.personajeService = personajeService;
    }

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            MainEj2 mainEj2 = container.select(MainEj2.class).get();
            mainEj2.anadirJugadorPersonaje();
        }
    }

    private void anadirJugadorPersonaje() {
        try {
            IO.println("Introduce el nombre del jugador: ");
            String nombre = IO.readln();

            IO.println("Introduce el email: ");
            String email = IO.readln();

            IO.println("Introduce la contraseña (password): ");
            String password = IO.readln();

            IO.println("Introduce los tokens: ");
            String tokenStr = IO.readln();
            int token = Integer.parseInt(tokenStr);

            JugadorDTO jugadorSaveDTO = JugadorDTO.builder()
                    .nombre(nombre)
                    .email(email)
                    .constraseña(password)
                    .tokens(token)
                    .build();

            Long nuevoId = jugadorService.saveJugador(jugadorSaveDTO);

            if (nuevoId != null) {
                IO.println("Jugador dado de alta con ID: " + nuevoId);

                boolean seguir = true;
                while (seguir) {
                    IO.println("Introduce el nombre del personaje: ");
                    String nombrePersonaje = IO.readln();

                    IO.println("Introduce el tipo del personaje: ");
                    String tipoPersonaje = IO.readln();

                    IO.println("Introduce el nivel del personaje: ");
                    int nivelPersonaje = Integer.parseInt(IO.readln());

                    PersonajeDTO personajeDTO = PersonajeDTO.builder()
                            .nombre(nombrePersonaje)
                            .tipo(tipoPersonaje)
                            .nivel(nivelPersonaje)
                            .jugador(nuevoId)
                            .build();

                    Long personajeId = personajeService.savePersonaje(personajeDTO);
                    if (personajeId != null) {
                        IO.println("Personaje dado de alta con ID: " + personajeId);
                    } else {
                        IO.println("Error al dar de alta al personaje.");
                    }

                    IO.println("¿Quieres añadir otro personaje? (s/n): ");
                    String respuesta = IO.readln();
                    seguir = respuesta.equalsIgnoreCase("s");
                }
            } else {
                IO.println("Error al dar de alta al Jugador.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}