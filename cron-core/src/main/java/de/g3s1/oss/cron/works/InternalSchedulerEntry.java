package de.g3s1.oss.cron.works;

import de.g3s1.oss.cron.api.CronEntry;
import de.g3s1.oss.cron.api.CronTrigger;

import java.time.Instant;

final class InternalSchedulerEntry {
    private final CronTrigger trigger;
    private final Runnable task;

    private final CronTaskScheduler scheduler;

    private Instant lastExecutionTime;

    public InternalSchedulerEntry(CronTrigger trigger, Runnable task, CronTaskScheduler scheduler) {
        this.trigger = trigger;
        this.task = task;
        this.scheduler = scheduler;
    }

    CronEntry getEntryWrapper() {
        return new CronEntryWrapper(this, scheduler);
    }

    CronTrigger getTrigger() {
        return trigger;
    }

    Runnable getTask() {
        return task;
    }

    Instant getLastExecutionTime() {
        return lastExecutionTime;
    }

    void setLastExecutionTime(Instant lastExecutionTime) {
        this.lastExecutionTime = lastExecutionTime;
    }

    private static final class CronEntryWrapper implements CronEntry {
        private final InternalSchedulerEntry entry;
        private final CronTaskScheduler scheduler;

        private CronEntryWrapper(InternalSchedulerEntry entry, CronTaskScheduler scheduler) {
            this.entry = entry;
            this.scheduler = scheduler;
        }

        @Override
        public void cancel() {
            scheduler.cancel(entry);
        }

        @Override
        public Instant lastExecutionTime() {
            return entry.getLastExecutionTime();
        }

        @Override
        public Instant nextExecutionTime() {
            Instant last = entry.getLastExecutionTime();
            if(last == null) {
                last = Instant.now();
            }

            return entry.getTrigger().nextExecution(last);
        }
    }
}
