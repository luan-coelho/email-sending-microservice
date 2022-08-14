package br.bunny.microservice.rest.controller.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class CreateEmailDTO {

    @NotBlank(message = "")
    private String ownerRef;
    @NotBlank
    @Email(message = "Enter recipient's email")
    private String emailTo;
    @NotBlank(message = "Enter the subject of the email")
    private String subject;
    @NotBlank(message = "Enter the message text")
    private String text;
    private String fileUrl;
}
