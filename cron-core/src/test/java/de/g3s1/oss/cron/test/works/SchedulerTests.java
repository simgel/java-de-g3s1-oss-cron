package de.g3s1.oss.cron.test.works;

import de.g3s1.oss.cron.api.CronEntry;
import de.g3s1.oss.cron.works.CronTaskScheduler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Executors;

public class SchedulerTests {
    @Test
    public void simpleTest() throws InterruptedException {
        CronTaskScheduler scheduler = new CronTaskScheduler(Executors.newSingleThreadExecutor());
        scheduler.startThread();

        DummyCronTask dummyCronTask = new DummyCronTask();
        DummyCronTrigger trigger = new DummyCronTrigger(ZoneOffset.UTC);

        Instant t1 = Instant.now();
        scheduler.submit(trigger, dummyCronTask);

        dummyCronTask.waitForExecution(2000);
        Instant t2 = Instant.now();
        scheduler.stopThread();

        Assertions.assertTrue((t2.toEpochMilli() - t1.toEpochMilli()) < 2000);
        Assertions.assertEquals(1, dummyCronTask.getExecutions().size());

        System.out.println(LocalDateTime.ofInstant(dummyCronTask.getExecutions().get(0), ZoneOffset.UTC));
    }

    @Test
    public void testCancellation() throws InterruptedException {
        CronTaskScheduler scheduler = new CronTaskScheduler(Executors.newSingleThreadExecutor());
        scheduler.startThread();

        DummyCronTask dummyCronTask = new DummyCronTask();
        DummyCronTrigger trigger = new DummyCronTrigger(ZoneOffset.UTC);

        Instant t1 = Instant.now();
        CronEntry entry = scheduler.submit(trigger, dummyCronTask);
        dummyCronTask.waitForExecution(2000);
        Instant t2 = Instant.now();

        entry.cancel();

        Assertions.assertTrue((t2.toEpochMilli() - t1.toEpochMilli()) < 2000);
        Assertions.assertEquals(1, dummyCronTask.getExecutions().size());
    }
}
