package br.bunny.microservice.service;

import br.bunny.microservice.domain.model.Email;
import br.bunny.microservice.domain.model.enums.EmailStatus;
import br.bunny.microservice.repository.EmailRepository;
import br.bunny.microservice.util.EmailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDateTime;

import static br.bunny.microservice.util.EmailUtils.replaceWithTheEmailData;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender emailSender;

    @Transactional
    public Email sendEmail(Email email) {
        log.info("Sending simple email.");
        email.setSendDateEmail(LocalDateTime.now());

        try {
            MimeMessage message = emailSender.createMimeMessage();

            var helper = new MimeMessageHelper(message);

            helper.setTo(email.getEmailTo());
            helper.setSubject(email.getSubject());
            helper.setText(replaceWithTheEmailData(email, EmailUtils.htmlToStringConverter()), true);

            emailSender.send(message);
            log.info("Simple email sent successfully.");

            email.setEmailStatus(EmailStatus.SENT);
        } catch (MessagingException e) {
            log.error("Error sending email.");
            email.setEmailStatus(EmailStatus.ERROR);
        }

        return emailRepository.save(email);
    }

    @Transactional
    public Email sendEmailWithFile(Email email) {
        log.info("Sending simple email with file.");

        try {
            MimeMessage message = emailSender.createMimeMessage();

            var helper = new MimeMessageHelper(message, true);

            helper.setTo(email.getEmailTo());
            helper.setSubject(email.getSubject());
            helper.setText(replaceWithTheEmailData(email, EmailUtils.htmlToStringConverter()), true);

            String fileUrl = email.getFileUrl();
            String fileName = "file".concat(EmailUtils.getFileExtesionByUrl(fileUrl));

            String destinationFile = "src/main/resources/static/".concat(fileName);
            EmailUtils.saveFile(fileUrl, destinationFile);

            helper.addAttachment(fileName, new ClassPathResource("/static/".concat(fileName)));

            emailSender.send(message);
            log.info("Email with attachment sent successfully");

            EmailUtils.deleteFile(destinationFile);

            email.setEmailStatus(EmailStatus.SENT);
        } catch (MessagingException e) {
            log.error("Error sending email");
            email.setEmailStatus(EmailStatus.ERROR);
        } catch (IOException e) {
            log.error("Failed to attach file");
            email.setEmailStatus(EmailStatus.ERROR);
        }

        return emailRepository.save(email);
    }
}
