package org.example.ui;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.example.domain.services.PersonajeService;
import org.example.domain.xml.CompletedMissionXml;
import org.example.domain.xml.PlayerXml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class MainEj1 {
    private static final Long JUGADOR_ID = 2L;
    private static final Path XML_PATH = Path.of("src/main/resources/misiones_completadas.xml");

    private final PersonajeService personajeService;

    @Inject
    public MainEj1(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            MainEj1 mainEj1 = container.select(MainEj1.class).get();
            mainEj1.exportarMisionesJugador2();
        }
    }

    private void exportarMisionesJugador2() {
        try {
            JAXBContext context = JAXBContext.newInstance(CompletedMissionXml.class);

            // 1. Leer el XML existente con NIO + JAXB
            CompletedMissionXml completedMission;
            if (Files.exists(XML_PATH)) {
                Unmarshaller unmarshaller = context.createUnmarshaller();
                try (InputStream in = Files.newInputStream(XML_PATH)) {
                    completedMission = (CompletedMissionXml) unmarshaller.unmarshal(in);
                }
            } else {
                completedMission = new CompletedMissionXml();
            }

            // 2. Obtener las misiones completadas del jugador 2 desde la BD
            PlayerXml jugador2Xml = personajeService.buildPlayerXmlByJugadorId(JUGADOR_ID);

            // Evitar duplicar el jugador 2 si ya existía en el XML
            completedMission.getPlayers().removeIf(p -> JUGADOR_ID.equals(p.getId()));
            completedMission.getPlayers().add(jugador2Xml);

            // 3. Guardar el XML actualizado con JAXB + NIO
            if (XML_PATH.getParent() != null) {
                Files.createDirectories(XML_PATH.getParent());
            }
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            try (OutputStream out = Files.newOutputStream(XML_PATH)) {
                marshaller.marshal(completedMission, out);
            }

            IO.println("Misiones completadas del jugador " + JUGADOR_ID + " añadidas al XML en: " + XML_PATH);
        } catch (JAXBException | IOException e) {
            throw new RuntimeException("Error al procesar el XML del ejercicio 1", e);
        }
    }
}