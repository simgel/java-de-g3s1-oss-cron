package de.g3s1.oss.cron;

import de.g3s1.oss.cron.api.CronEntry;
import de.g3s1.oss.cron.api.CronTrigger;
import de.g3s1.oss.cron.works.CronTaskScheduler;

import java.util.concurrent.Executor;

/**
 * Cron scheduler
 */
public final class Cron {
    private final CronTaskScheduler taskScheduler;
    private Cron(CronTaskScheduler taskScheduler) {
        this.taskScheduler = taskScheduler;
    }

    public void stop() {
        this.taskScheduler.stopThread();
    }

    public CronEntry add(CronTrigger trigger, Runnable task) {
        return taskScheduler.submit(trigger, task);
    }

    public static Cron create(Executor executor) {
        var scheduler = new CronTaskScheduler(executor);
        scheduler.startThread();

        return new Cron(scheduler);
    }
}
