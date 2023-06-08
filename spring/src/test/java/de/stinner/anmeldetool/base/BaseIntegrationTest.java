package de.stinner.anmeldetool.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("integration-test")
@SpringBootTest()
@Slf4j
public abstract class BaseIntegrationTest {
}