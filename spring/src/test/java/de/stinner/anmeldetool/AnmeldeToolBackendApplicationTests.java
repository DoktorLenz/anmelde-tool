package de.stinner.anmeldetool;

import de.stinner.anmeldetool.base.TestEnvInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = TestEnvInitializer.class)
class AnmeldeToolBackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
