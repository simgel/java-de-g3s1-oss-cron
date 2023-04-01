package de.g3s1.oss.cron.works;

import de.g3s1.oss.cron.api.CronEntry;
import de.g3s1.oss.cron.api.CronTrigger;

import java.time.Instant;

final class InternalSchedulerEntry {
    private final CronTrigger trigger;
    private final Runnable task;

    public InternalSchedulerEntry(CronTrigger trigger, Runnable task) {
        this.trigger = trigger;
        this.task = task;
    }

    CronEntry getEntryWrapper() {
        //TODO
        return new CronEntryWrapper();
    }

    CronTrigger getTrigger() {
        return trigger;
    }

    Runnable getTask() {
        return task;
    }

    private static final class CronEntryWrapper implements CronEntry {
        @Override
        public void cancel() {
            //TODO
        }

        @Override
        public Instant lastExecutionTime() {
            //TODO
            return null;
        }

        @Override
        public Instant nextExecutionTime() {
            //TODO
            return null;
        }
    }
}
