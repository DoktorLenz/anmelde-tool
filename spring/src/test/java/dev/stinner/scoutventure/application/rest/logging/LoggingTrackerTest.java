package dev.stinner.scoutventure.application.rest.logging;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.slf4j.MDC;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mockStatic;

/**
 * This test the correct interaction with the MDC of SLF4J
 */
class LoggingTrackerTest {

    @Test
    @SneakyThrows
    void testConstructorIsPrivateAndPreventsInstantiation() {
        Constructor<LoggingTracker> constructor = LoggingTracker.class.getDeclaredConstructor();
        assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();
        constructor.setAccessible(true);
        assertThatThrownBy(constructor::newInstance).isInstanceOf(InvocationTargetException.class);
    }

    @Test
    void testStart() {
        try (MockedStatic<MDC> mdc = mockStatic(MDC.class)) {
            LoggingTracker.start();

            mdc.verify(() -> MDC.put(eq("trackingCode"), anyString()));
        }

    }

    @Test
    void testStop() {
        try (MockedStatic<MDC> mdc = mockStatic(MDC.class)) {
            LoggingTracker.stop();

            mdc.verify(MDC::clear);
        }
    }
}
