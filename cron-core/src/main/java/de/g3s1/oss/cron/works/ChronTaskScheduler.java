package de.g3s1.oss.cron.works;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ChronTaskScheduler {
    private final Thread thread;
    private final AtomicBoolean running;

    private final Executor executor;

    public ChronTaskScheduler(Executor executor) {
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

    private void run() {
        while (running.get()) {
            // TODO
        }
    }
}
