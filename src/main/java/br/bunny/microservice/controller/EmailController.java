package br.bunny.microservice.controller;

import br.bunny.microservice.dto.EmailDto;
import br.bunny.microservice.model.Email;
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
    public ResponseEntity<EmailDto> sendingEmail(@RequestBody @Valid EmailDto emailDto) {
        Email email = new Email();
        mapper.map(emailDto, email);
        return new ResponseEntity<>(mapper.map(emailService.sendEmail(email), EmailDto.class), HttpStatus.CREATED);
    }

    @PostMapping("/sending-email-with-file")
    public ResponseEntity<EmailDto> sendingEmailWithFile(@RequestBody @Valid EmailDto emailDto) {
        Email email = new Email();
        mapper.map(emailDto, email);
        return new ResponseEntity<>(mapper.map(emailService.sendEmailWithFile(email), EmailDto.class), HttpStatus.CREATED);
    }
}
