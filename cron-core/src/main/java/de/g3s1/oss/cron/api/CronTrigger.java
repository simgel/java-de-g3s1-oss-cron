package de.g3s1.oss.cron.api;

import java.time.Instant;

/**
 * Internal Trigger interface. <br>
 * Used by the CronTaskScheduler to get the next execution time.
 */
public interface CronTrigger {
    /**
     * Calculates the next trigger execution. <br>
     * The time difference between the returned instant and the provided instance must be at least 1 sek (1000 ms).
     * @param instant Last execution time
     * @return next execution time or null if the trigger will not trigger again
     */
    Instant nextExecution(Instant instant);

    /**
     * Inform the trigger of an execution
     * @param instant Time of execution.
     */
    void wasTriggered(Instant instant);
}
