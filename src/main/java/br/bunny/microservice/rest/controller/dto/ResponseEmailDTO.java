package br.bunny.microservice.rest.controller.dto;

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
}
