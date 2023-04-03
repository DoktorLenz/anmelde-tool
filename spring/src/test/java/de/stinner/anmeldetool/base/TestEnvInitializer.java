package de.stinner.anmeldetool.base;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Overrides the specified properties.
 */
public class TestEnvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final String mailServerHost = "localhost";

    public static final String mailServerUsername = "foo";

    public static final String mailServerPassword = "foo-pwd";

    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        final TestPropertyValues values = TestPropertyValues.of(
                "spring.mail.host=" + mailServerHost,
                "spring.mail.port=3025",
                "spring.mail.username=" + mailServerUsername,
                "spring.mail.password=" + mailServerPassword
        );

        values.applyTo(applicationContext);
    }

}