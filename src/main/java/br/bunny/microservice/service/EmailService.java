package br.bunny.microservice.service;

import br.bunny.microservice.model.Email;
import br.bunny.microservice.model.enums.EmailStatus;
import br.bunny.microservice.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class EmailService {

    final EmailRepository emailRepository;
    final private JavaMailSender emailSender;

    @Transactional
    public Email sendEmail(Email email) {
        email.setSendDateEmail(LocalDateTime.now());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());

            emailSender.send(message);

            email.setEmailStatus(EmailStatus.SENT);
        } catch (MailException e) {
            email.setEmailStatus(EmailStatus.ERROR);
        }

        return emailRepository.save(email);
    }
}
