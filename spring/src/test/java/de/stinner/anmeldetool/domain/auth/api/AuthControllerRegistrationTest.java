package de.stinner.anmeldetool.domain.auth.api;

import de.stinner.anmeldetool.application.rest.ApiEndpoints;
import de.stinner.anmeldetool.base.BaseControllerTest;
import de.stinner.anmeldetool.base.MailTestUtils;
import de.stinner.anmeldetool.domain.auth.api.model.FinishRegistrationDto;
import de.stinner.anmeldetool.domain.auth.api.model.RegistrationRequestDto;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.Store;
import jakarta.mail.internet.MimeMultipart;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthControllerRegistrationTest extends BaseControllerTest {


    private final String password = "validpassword";
    private String username;
    @Value("${spring.mail.host}")
    private String mailServerHost;
    @Value("${spring.mail.username}")
    private String mailServerUsername;
    @Value("${spring.mail.password}")
    private String mailServerPassword;

    @Test
    @SneakyThrows
    public void testSuccessfulRegistrationFlow() {
        username = mailServerUsername + "@" + mailServerHost;
        String messageContent = testRegisterAndReturnMailMessageContent();
        String id = MailTestUtils.getRegistrationIdFromMessage(messageContent);
        testFinishRegistration(UUID.fromString(id));
    }

    @SneakyThrows
    private String testRegisterAndReturnMailMessageContent() {
        RegistrationRequestDto dto = new RegistrationRequestDto();
        dto.setEmail(username);
        dto.setFirstname("Firstname");
        dto.setLastname("Lastname");

        performPostRequest(dto, ApiEndpoints.V1.Auth.REGISTER).andExpect(status().isNoContent());

        greenMail.waitForIncomingEmail(1);

        try (final Store store = greenMail.getImap().createStore()) {
            store.connect(mailServerUsername, mailServerPassword);
            final Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message message = inbox.getMessages()[0];
            assertThat(inbox.getMessageCount()).isEqualTo(1);
            assertThat(message.getSubject()).isEqualTo("Registrierung Abschließen");
            return MailTestUtils.getTextFromMimeMultipart((MimeMultipart) message.getContent());
        }
    }

    @SneakyThrows
    public void testFinishRegistration(UUID registrationId) {
        FinishRegistrationDto dto = new FinishRegistrationDto();
        dto.setRegistrationId(registrationId);
        dto.setPassword(password);

        performPostRequest(dto, ApiEndpoints.V1.Auth.FINISH_REGISTRATION).andExpect(status().isNoContent());

        performGetRequestWithAuth(ApiEndpoints.V1.Auth.LOGIN, username, password).andExpect(status().isNoContent());
    }
}
