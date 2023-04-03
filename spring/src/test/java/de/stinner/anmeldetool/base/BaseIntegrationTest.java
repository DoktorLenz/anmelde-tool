package de.stinner.anmeldetool.base;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


@ActiveProfiles("integration-test")
@SpringBootTest()
@Slf4j
@ContextConfiguration(initializers = BaseIntegrationTest.TestEnvInitializer.class)
public abstract class BaseIntegrationTest {

    public static final String mailServerHost = "localhost";

    public static final String mailServerUsername = "foo";

    public static final String mailServerPassword = "foo-pwd";

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

    /**
     * Overrides the specified properties.
     */
    static class TestEnvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            final TestPropertyValues values = TestPropertyValues.of(
                    "spring.mail.host=" + mailServerHost,
                    "spring.mail.port=3025",
                    "spring.mail.user=" + mailServerUsername,
                    "spring.mail.password=" + mailServerPassword
            );

            values.applyTo(applicationContext);
        }

    }
}