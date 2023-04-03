package de.stinner.anmeldetool.base;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static de.stinner.anmeldetool.base.TestEnvInitializer.*;


@ActiveProfiles("integration-test")
@SpringBootTest()
@Slf4j
@ContextConfiguration(initializers = TestEnvInitializer.class)
public abstract class BaseIntegrationTest {


    @RegisterExtension
    public static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP_IMAP)
            .withConfiguration(GreenMailConfiguration
                    .aConfig()
                    .withUser(
                            mailServerUsername + "@" + mailServerHost,
                            mailServerUsername,
                            mailServerPassword
                    )
            );
}