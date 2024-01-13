package de.stinner.anmeldetool.application.rest.logging;

import lombok.experimental.UtilityClass;
import org.slf4j.MDC;

import java.util.UUID;

@UtilityClass
public class LoggingTracker {

    private static final String MDC_KEY = "trackingCode";


    public static void start() {
        MDC.put(MDC_KEY, UUID.randomUUID().toString());
    }

    public static void stop() {
        MDC.clear();
    }

}
