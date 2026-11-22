package org.example.domain.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class MisionCompletadaBackupDTO {

    @XmlElement(name = "misionId")
    private Long misionId;

    @XmlElement(name = "personajeId")
    private Long personajeId;

    @XmlElement(name = "fechaCompletado")
    private String fechaCompletado;
}