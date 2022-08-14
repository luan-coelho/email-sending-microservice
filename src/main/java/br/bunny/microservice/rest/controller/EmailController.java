package br.bunny.microservice.rest.controller;

import br.bunny.microservice.rest.controller.dto.CreateEmailDTO;
import br.bunny.microservice.domain.model.Email;
import br.bunny.microservice.rest.controller.dto.ResponseEmailDTO;
import br.bunny.microservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mse")
public class EmailController {

    private final EmailService emailService;
    private final ModelMapper mapper;

    @PostMapping("/sending-email")
    public ResponseEntity<ResponseEmailDTO> sendingEmail(@RequestBody @Valid CreateEmailDTO emailRequest) {
        Email email = new Email();
        mapper.map(emailRequest, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(emailService.sendEmail(email), ResponseEmailDTO.class));
    }
}
