package de.g3s1.oss.cron.api;

import java.time.Instant;

public interface CronEntry {
    /**
     * Remove task from cron
     */
    void cancel();

    /**
     * Retrieve last execution time
     * @return last exution time - <pre>null</pre> if the task was not executed yet.
     */
    Instant lastExecutionTime();

    /**
     * Retrieve the next execution time
     * @return next execution time - <pre>null</pre> if the task will not execute again.
     */
    Instant nextExecutionTime();
}
