package de.stinner.anmeldetool.base;

import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.ContentType;
import jakarta.mail.internet.MimeMultipart;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailTestUtils {
    @SneakyThrows
    public static String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart) {

        int count = mimeMultipart.getCount();
        if (count == 0)
            throw new MessagingException("Multipart with no body parts not supported.");
        boolean multipartAlt = new ContentType(mimeMultipart.getContentType()).match("multipart/alternative");
        if (multipartAlt)
            // alternatives appear in an order of increasing
            // faithfulness to the original content. Customize as req'd.
            return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));
        String result = "";
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            result += getTextFromBodyPart(bodyPart);
        }
        return result;
    }

    @SneakyThrows
    private static String getTextFromBodyPart(
            BodyPart bodyPart) {

        String result = "";
        if (bodyPart.isMimeType("text/plain") || bodyPart.isMimeType("text/html")) {
            result = (String) bodyPart.getContent();
        } else if (bodyPart.getContent() instanceof MimeMultipart) {
            result = getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
        }
        return result;
    }

    @SneakyThrows
    public static String getRegistrationIdFromMessage(String message) {
        Document doc = Jsoup.parse(message);
        Element element = doc.getElementById("registration-link");

        String url = element.html();

        String regex = "id=(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new Exception();
    }
}
