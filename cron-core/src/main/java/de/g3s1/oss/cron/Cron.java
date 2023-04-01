package de.g3s1.oss.cron;

import de.g3s1.oss.cron.works.ChronTaskScheduler;

/**
 * Cron scheduler
 */
public final class Cron {
    private final ChronTaskScheduler taskScheduler;
    private Cron(ChronTaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void stop() {
        this.taskScheduler.stopThread();
    }

    public static Cron create() {
        var scheduler = new ChronTaskScheduler();
        scheduler.startThread();

        return new Cron(scheduler);
    }
}
