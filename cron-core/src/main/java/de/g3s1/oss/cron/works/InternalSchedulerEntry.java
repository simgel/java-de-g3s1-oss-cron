package de.g3s1.oss.cron.works;

import de.g3s1.oss.cron.api.CronEntry;

import java.time.Instant;

final class InternalSchedulerEntry {

    CronEntry getEntryWrapper() {
        //TODO
        return new CronEntryWrapper();
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
