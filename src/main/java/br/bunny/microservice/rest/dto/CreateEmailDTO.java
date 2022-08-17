package br.bunny.microservice.rest.dto;

import br.bunny.microservice.domain.model.enums.EmailType;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotNull(message = "Enter the type of email - DEFAULT, RESET-PASSWORD")
    private EmailType emailType;
}
