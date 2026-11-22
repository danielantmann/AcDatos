package org.example.ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import org.example.dao.model.MisionCompletada;
import org.example.dao.model.Personaje;
import org.example.domain.dto.InformeJugadorDTO;
import org.example.domain.services.JugadorService;

import java.util.List;
import java.util.Map;


public class MainEj5 {
    private final JugadorService jugadorService;

    @Inject
    public MainEj5(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }
    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            MainEj5 mainEj5 = container.select(MainEj5.class).get();
            mainEj5.obtenerInfome();
        }
    }

    private void obtenerInfome() {

        try {
            IO.println("Introduce el nombre del jugador: ");
            String nombreJugador = IO.readln();

            InformeJugadorDTO informe = jugadorService.obtenerInformeJugador(nombreJugador);

            IO.println("Jugador: " + informe.getNombre());
            IO.println("Tokens:  " + informe.getTokens());

            Map<Personaje, List<MisionCompletada>> mapa = informe.getListaMcPersonajes();

            for (Personaje personaje : mapa.keySet()) {
                IO.println("Personaje:  " + personaje.getNombre());
                IO.println("Tipo:  " + personaje.getTipo());
                IO.println("Nivel:  " + personaje.getNivel());
                List<MisionCompletada> misiones = mapa.get(personaje);
                if (misiones.size() <= 0){
                    IO.println(personaje.getNombre() + " no tiene misiones completadas");
                }else{
                    IO.println(personaje.getNombre() + " completo " + misiones.size() + " misiones");
                    for (MisionCompletada m : misiones){
                        IO.println("Mision : " + m.getMisionId());
                        IO.println("Completado el: " + m.getFechaCompletado());
                    }

                }
                IO.println("------------------");

            }




        }catch (IllegalArgumentException e) {
            IO.println("Error: " + e.getMessage());
        }
    }
}
