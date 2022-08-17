package br.bunny.microservice.util;

import br.bunny.microservice.domain.model.Email;
import br.bunny.microservice.domain.model.enums.EmailType;
import br.bunny.microservice.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
public class EmailUtils {

    private static final String CONTENT_PATH = "src/main/resources/static/";

    public static String buildEmailBody(EmailType emailType) {
        log.info("Converter Html to String...");

        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(CONTENT_PATH.concat(emailType.getHtmlFileName())));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
            return contentBuilder.toString();
        } catch (IOException e) {
            log.error("Error converting default-message.txt file to string");
        }
        return null;
    }

    public static String replaceWithTheEmailData(Email email, String html) {
        html = html.replace("***subject***", email.getSubject());
        html = html.replace("***text***", email.getText());
        return html;
    }

    /* If successful return the file name  */
    public static String buildAttachmentFile(String fileUrl) {
        String fileExtension = EmailUtils.getFileExtensionByUrl(fileUrl);
        String fileName = "file.".concat(fileExtension);
        String destinationFile = "src/main/resources/static/".concat(fileName);

        try {
            EmailUtils.saveFile(fileUrl, destinationFile);
            log.info("Attachment file saved successfully");
        } catch (IOException e) {
            throw new RuntimeException("Error saving attachment file");
        }

        return fileName;
    }

    private static void saveFile(String fileUrl, String destinationFile) throws IOException {
        InputStream in = new URL(fileUrl).openStream();
        Files.copy(in, Paths.get(destinationFile), StandardCopyOption.REPLACE_EXISTING);
        in.close();
    }

    private static String getFileExtensionByUrl(String url) {
        url = url.trim().replaceAll(".*/[^.]+\\.([^?]+)\\??.*", "$1");
        if (url.length() == 0)
            throw new BadRequestException("File extension not found as the url is in an invalid format");
        return url;
    }

    public static void deleteFile(String fileUrl) {
        File file = new File(fileUrl);
        if (file.delete())
            log.info("Attachment file deleted successfully");
        else
            log.error("Failed to delete attachment file");
    }
}

