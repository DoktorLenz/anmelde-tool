package de.stinner.anmeldetool.hexagonal.application.rest.logging;

import java.time.Duration;

public final class StopWatch {

    private final long startTime;

    private StopWatch() {
        startTime = System.nanoTime();
    }

    /**
     * Returns a started stopwatch instance.
     */
    public static StopWatch start() {
        return new StopWatch();
    }

    /**
     * Returns time in ms since the stopWatch has been started. can be called multiple times,
     * each call returning the duration from start of stopwatch to stop.
     */
    public long stop() {
        return Duration
                .ofNanos(System.nanoTime() - startTime)
                .toMillis();
    }

}
