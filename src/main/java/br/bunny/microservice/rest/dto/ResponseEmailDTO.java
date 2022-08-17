package br.bunny.microservice.rest.dto;

import br.bunny.microservice.domain.model.enums.EmailType;
import lombok.Data;

import java.util.UUID;

@Data
public class ResponseEmailDTO {

    private UUID id;
    private String ownerRef;
    private String emailTo;
    private String subject;
    private String text;
    private String fileUrl;
    private EmailType emailType;
}
