package de.g3s1.oss.cron.works;

import de.g3s1.oss.cron.api.CronEntry;
import de.g3s1.oss.cron.api.CronTrigger;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public final class CronTaskScheduler {
    private final Thread thread;
    private final AtomicBoolean running;

    private final Executor executor;

    public CronTaskScheduler(Executor executor) {
        this.executor = executor;

        thread = new Thread(this::run);
        thread.setDaemon(true);

        running = new AtomicBoolean(true);
    }

    public void startThread() {
        running.set(true);
        thread.start();
    }

    public void stopThread() {
        running.set(false);
    }

    public CronEntry submit(CronTrigger trigger, Runnable task) {
        // TODO
        InternalSchedulerEntry entry = new InternalSchedulerEntry();
        return entry.getEntryWrapper();
    }

    private void run() {
        while (running.get()) {
            // TODO
        }
    }
}
