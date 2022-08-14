package br.bunny.microservice.util;

import br.bunny.microservice.domain.model.Email;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;

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

    public static String replaceWithTheEmailData(Email email, String html) {
        html = html.replace("***subject***", email.getSubject());
        html = html.replace("***text***", email.getText());
        return html;
    }

    public static void saveFile(String fileUrl, String destinationFile) throws IOException {
        URL url = new URL(fileUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[8192];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }

    public static String getFileExtesionByUrl(String url) {
        return url.substring(url.lastIndexOf("."));
    }

    public static void deleteFile(String fileUrl) {
        File file = new File(fileUrl);
        file.delete();
    }
}

