package br.bunny.microservice.service;

import br.bunny.microservice.model.Email;
import br.bunny.microservice.model.enums.EmailStatus;
import br.bunny.microservice.repository.EmailRepository;
import br.bunny.microservice.util.EmailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

import static br.bunny.microservice.util.EmailUtils.replaceWithTheEmailData;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    final EmailRepository emailRepository;
    final private JavaMailSender emailSender;

    @Transactional
    public Email sendEmail(Email email) {
        log.info("Sending simple email");
        email.setSendDateEmail(LocalDateTime.now());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());

            emailSender.send(message);
            log.info("Simple email sent successfully.");

            email.setEmailStatus(EmailStatus.SENT);
        } catch (MailException e) {
            email.setEmailStatus(EmailStatus.ERROR);
        }

        return emailRepository.save(email);
    }

    public Email sendEmailWithFile(Email email) {
        log.info("Sending simple email with file");
        try {
            MimeMessage message = emailSender.createMimeMessage();

            var helper = new MimeMessageHelper(message, true);

            helper.setTo(email.getEmailTo());
            helper.setSubject(email.getSubject());
            helper.setText(replaceWithTheEmailData(email, EmailUtils.htmlToStringConverter()), true);

//            helper.addAttachment("image.jpeg", new ClassPathResource(email.getFile()));

            emailSender.send(message);
            log.info("Email with attachment sent successfully.");

            email.setEmailStatus(EmailStatus.SENT);
        } catch (MessagingException e) {
            email.setEmailStatus(EmailStatus.ERROR);
        }

        return emailRepository.save(email);
    }

}
