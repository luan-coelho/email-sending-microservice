package br.bunny.microservice.util;

import br.bunny.microservice.model.Email;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
public class EmailUtils {

    private static final String CONTENT_PATH = "src/main/resources/static";

    public static String htmlToStringConverter() {
        log.info("Converter Html to String...");

        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(CONTENT_PATH.concat("/default-message.html")));
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

    public static String replaceWithTheEmailData(Email email, String html){
        html = html.replace("***subject***", email.getSubject());
        html = html.replace("***text***", email.getText());
        return html;
    }
}

