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
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class AuthControllerRegistrationTest extends BaseControllerTest {

    private String username;
    @Value("${spring.mail.host}")
    private String mailServerHost;
    @Value("${spring.mail.username}")
    private String mailServerUsername;
    @Value("${spring.mail.password}")
    private String mailServerPassword;


    @Test
    @SneakyThrows
    void testSuccessfulRegistrationFlow() {
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

        baseRequest.with().body(dto)
                .when().post(ApiEndpoints.V1.Auth.REGISTER)
                .then().statusCode(204);

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
        String password = "validpassword";
        dto.setPassword(password);

        baseRequest.with().body(dto)
                .when().post(ApiEndpoints.V1.Auth.FINISH_REGISTRATION)
                .then().statusCode(204);

        baseRequest.with().auth().basic(username, password)
                .when().get(ApiEndpoints.V1.Auth.LOGIN)
                .then().statusCode(HttpStatus.NO_CONTENT.value());
    }
}
