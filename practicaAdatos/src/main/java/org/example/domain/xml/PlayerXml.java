package org.example.domain.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlayerXml {

    @XmlElement(name = "id")
    private Long id;

    @XmlElement(name = "cmission")
    private List<CmissionXml> cmissions = new ArrayList<>();

    public PlayerXml() {
    }

    public PlayerXml(Long id, List<CmissionXml> cmissions) {
        this.id = id;
        this.cmissions = cmissions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CmissionXml> getCmissions() {
        return cmissions;
    }

    public void setCmissions(List<CmissionXml> cmissions) {
        this.cmissions = cmissions;
    }
}