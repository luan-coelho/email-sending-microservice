package br.bunny.microservice.service;

import br.bunny.microservice.domain.model.Email;
import br.bunny.microservice.domain.model.enums.EmailStatus;
import br.bunny.microservice.repository.EmailRepository;
import br.bunny.microservice.util.EmailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

import static br.bunny.microservice.util.EmailUtils.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender emailSender;

    @Transactional
    public Email sendEmail(Email email) {
        log.info("Sending email");

        String fileName = null;
        try {
            MimeMessage message = emailSender.createMimeMessage();

            var helper = new MimeMessageHelper(message, true);

            helper.setTo(email.getEmailTo());
            helper.setSubject(email.getSubject());
            helper.setText(replaceWithTheEmailData(email, EmailUtils.htmlToStringConverter()), true);

            if (email.getFileUrl() != null) {
                fileName = buildAttachmentFile(email.getFileUrl());

                FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/".concat(fileName)));
                helper.addAttachment(fileName, file);

                emailSender.send(message);
                log.info("Email sent successfully");

                EmailUtils.deleteFile("src/main/resources/static/".concat(fileName));
            } else {
                emailSender.send(message);
            }

            email.setEmailStatus(EmailStatus.SENT);
        } catch (MessagingException e) {
            log.error("Error sending email");
            email.setEmailStatus(EmailStatus.ERROR);
        } finally {
            if (email.getFileUrl() != null) {
                assert fileName != null;
                EmailUtils.deleteFile("src/main/resources/static/".concat(fileName));
            }
        }
        return emailRepository.save(email);
    }

    public static void main(String[] args) {
        deleteFile("src/main/resources/static/file.png");
    }
}
