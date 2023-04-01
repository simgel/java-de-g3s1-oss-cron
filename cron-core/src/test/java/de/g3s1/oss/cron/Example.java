package de.g3s1.oss.cron;

import de.g3s1.oss.cron.api.CronEntry;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Executors;

public class Example {
    public static void main(String[] args) {
        final Object sync = new Object();
        Runtime.getRuntime().addShutdownHook(new RuntimeHookThread(sync));

        Cron cron = Cron.create(Executors.newSingleThreadExecutor());
        CronEntry entry = cron.add(Triggers.everyMinute(), () -> {
            System.out.println(LocalDateTime.now(ZoneOffset.UTC));
        });


        synchronized (sync) {
            try {
                sync.wait();
            } catch (InterruptedException ignored) {
            }
        }

        cron.stop();
    }

    private static final class RuntimeHookThread extends Thread {
        private final Object monitor;

        private RuntimeHookThread(Object monitor) {
            this.monitor = monitor;
        }

        @Override
        public void run() {
            synchronized (monitor) {
                monitor.notifyAll();
            }
        }
    }
}
