package org.example.domain.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "CompletedMission")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompletedMissionXml {

    @XmlElement(name = "player")
    private List<PlayerXml> players = new ArrayList<>();

    public List<PlayerXml> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerXml> players) {
        this.players = players;
    }
}