package de.g3s1.oss.cron;

import de.g3s1.oss.cron.api.CronEntry;

import java.util.concurrent.Executors;

public class Example {
    void example() {
        Cron cron = Cron.create(Executors.newSingleThreadExecutor());
        CronEntry entry = cron.add(Triggers.nextSecond(), () -> {
            System.out.println("Cron execution");
        });
    }
}
