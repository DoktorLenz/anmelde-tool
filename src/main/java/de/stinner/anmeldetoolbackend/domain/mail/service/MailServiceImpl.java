package de.stinner.anmeldetoolbackend.domain.mail.service;

import de.stinner.anmeldetoolbackend.domain.auth.persistence.RegistrationEntity;
import de.stinner.anmeldetoolbackend.domain.auth.persistence.RegistrationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.File;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


@EnableAsync
@Service("MailService")
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private static final String NOREPLY_ADDRESS = "laurenz.stinner@ymail.com";

    private final JavaMailSender mailSender;

    private final SimpleMailMessage template;

    private final SpringTemplateEngine thymeleafTemplateEngine;

    private final RegistrationRepository registrationRepository;

    @Value("classpath:/mail/assets/mail-logo.jpg")
    private Resource logo;

    @Value("classpath:/mail/assets/instagram.png")
    private Resource instagramLogo;

    @Value("classpath:/mail/assets/google.png")
    private Resource googleLogo;

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(NOREPLY_ADDRESS);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
        } catch (MailException e) {
            log.error("SimpleMail to \"{}\" with subject \"{}\" could not be sent: {}", to, subject, e.getMessage());
        }
    }

    @Override
    public void sendSimpleMessageUsingTemplate(String to, String subject, String... templateModel) {
        String text = String.format(template.getText(), templateModel);
        sendSimpleMessage(to, subject, text);
    }

    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(NOREPLY_ADDRESS);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);

            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("AttachmentMail to \"{}\" with subject \"{}\" could not be sent: {}", to, subject, e.getMessage());
        }
    }

    @Override
    public void sendMessageUsingThymeleafTemplate(
            String to, String subject, String templatePath, Map<String, Object> templateModel)
            throws MessagingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);

        String htmlBody = thymeleafTemplateEngine.process(templatePath, thymeleafContext);

        sendHtmlMessage(to, subject, htmlBody);
    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(NOREPLY_ADDRESS);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        helper.addInline("mail-logo.jpg", logo);
        helper.addInline("instagram.png", instagramLogo);
        helper.addInline("google.png", googleLogo);
        mailSender.send(message);
    }

    public void sendPendingRegistrationEmails() {
        registrationRepository.findByEmailSentIsNull().forEach(this::sendRegistrationEmail);
    }


    @Transactional()
    protected void registrationEmailSent(RegistrationEntity registration) {
        registration.setEmailSent(Instant.now());
        registrationRepository.save(registration);
    }

    @Async
    public void sendRegistrationEmail(RegistrationEntity registration) {
        Map<String, Object> templateModel = new HashMap<>();
        String recipientName = String.format("%s %s", registration.getFirstname(), registration.getLastname());
        String registrationLink = String.format(
                "https://anmeldung.dpsgkolbermoor.de/auth/finish-registration?id=%s",
                registration.getRegistrationId()
        );
        templateModel.put("recipientName", recipientName);
        templateModel.put("registrationLink", registrationLink);
        try {
            sendMessageUsingThymeleafTemplate(
                    registration.getEmail(),
                    "Registrierung Abschließen",
                    "template-registration.html",
                    templateModel
            );
            registrationEmailSent(registration);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
