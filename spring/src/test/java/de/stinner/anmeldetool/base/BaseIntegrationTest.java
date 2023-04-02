package de.stinner.anmeldetool.base;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("integration-test")
@SpringBootTest()
public abstract class BaseIntegrationTest {

    @Value("${spring.mail.host}")
    static private String mailServerHost;

    @Value("${spring.mail.username}")
    static private String mailServerUsername;

    @Value("${spring.mail.password}")
    static private String mailServerPassword;

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP_IMAP)
            .withConfiguration(GreenMailConfiguration
                    .aConfig()
                    .withUser(mailServerUsername + "@" + mailServerHost, mailServerUsername, mailServerPassword));
}