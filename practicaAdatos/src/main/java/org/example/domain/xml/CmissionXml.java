package org.example.domain.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CmissionXml {

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "personaje")
    private String personaje;

    public CmissionXml() {
    }

    public CmissionXml(Long id, String personaje) {
        this.id = id;
        this.personaje = personaje;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonaje() {
        return personaje;
    }

    public void setPersonaje(String personaje) {
        this.personaje = personaje;
    }
}