package org.example.ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import org.example.domain.error.DataBaseError;
import org.example.domain.services.MisionCompletadaService;

import java.time.LocalDate;

public class MainEj4 {
    private final MisionCompletadaService misionCompletadaService;

    @Inject
    public MainEj4(MisionCompletadaService misionCompletadaService) {
        this.misionCompletadaService = misionCompletadaService;
    }

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            MainEj4 mainEj4 = container.select(MainEj4.class).get();
            mainEj4.completarMision();
        }
    }

    private void completarMision() {
        try {
            IO.println("Introduce el nombre del jugador: ");
            String nombreJugador = IO.readln();

            IO.println("Introduce el nombre del personaje: ");
            String nombrePersonaje = IO.readln();

            IO.println("Introduce el nombre de la misión: ");
            String nombreMision = IO.readln();

            boolean completada = misionCompletadaService.completarMision(
                    nombreJugador,
                    nombrePersonaje,
                    nombreMision,
                    LocalDate.now()
            );

            if (completada) {
                IO.println("Misión completada y registrada en la base de datos.");
            } else {
                IO.println("No se pudo registrar la misión completada.");
            }
        } catch (IllegalStateException e) {
            IO.println("Error de tokens: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            IO.println("Error: " + e.getMessage());
        } catch (DataBaseError e) {
            IO.println("Error de base de datos: " + e.getMessage());
        }
    }
}