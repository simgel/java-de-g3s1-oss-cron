package de.g3s1.oss.cron.works;

import de.g3s1.oss.cron.api.CronEntry;
import de.g3s1.oss.cron.api.CronTrigger;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public final class CronTaskScheduler {
    private final Thread thread;
    private final AtomicBoolean running;

    private final AtomicBoolean waitingForTask;

    private final Executor executor;

    private final Set<InternalSchedulerEntry> schedulerEntries;
    private final Object initialWaitMonitor;

    private final Object entrySetMonitor;

    public CronTaskScheduler(Executor executor) {
        this.executor = executor;

        thread = new Thread(this::run);
        thread.setDaemon(true);

        running = new AtomicBoolean(true);
        waitingForTask = new AtomicBoolean();

        schedulerEntries = new HashSet<>();
        initialWaitMonitor = new Object();
        entrySetMonitor = new Object();

    }

    public void startThread() {
        running.set(true);
        thread.start();
    }

    public void stopThread() {
        running.set(false);
        thread.interrupt();
    }

    public CronEntry submit(CronTrigger trigger, Runnable task) {
        InternalSchedulerEntry entry = new InternalSchedulerEntry(trigger, task, this);

        synchronized (entrySetMonitor) {
            boolean empty = schedulerEntries.isEmpty();
            schedulerEntries.add(entry);

            if(empty) {
                synchronized (initialWaitMonitor) {
                    initialWaitMonitor.notifyAll();
                }
            }

            if(waitingForTask.get()) {
                // interrupt thread to evaluate timings again
                thread.interrupt();
            }
        }

        return entry.getEntryWrapper();
    }

    private void run() {
        Instant lastJobTime = null;

        while (running.get()) {
            // check if entries is empty
            boolean wait = false;
            synchronized (entrySetMonitor) {
                wait = schedulerEntries.isEmpty();
            }

            if(wait) {
                synchronized (initialWaitMonitor) {
                    try {
                        initialWaitMonitor.wait();
                    } catch (InterruptedException ignored) {
                    }
                }
                continue;
            }

            // at least one entry exist
            // perform tasks
            if(lastJobTime == null) {
                lastJobTime = Instant.now();
            }

            Instant nextJobTime = getNextExecutionTime(lastJobTime);
            if(nextJobTime == null) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {
                }

                continue;
            }

            // increase time if a trigger returns a time smaller 1 sec
            if(Duration.between(lastJobTime, nextJobTime).toMillis() < 1000) {
                nextJobTime = nextJobTime.plusSeconds(1);
                System.err.println("Job trigger contract violation");
            }

            // wait for execution
            Duration duration = Duration.between(Instant.now(), nextJobTime);
            try {
                waitingForTask.set(true);
                Thread.sleep(Math.max(duration.toMillis(), 0));
            } catch (InterruptedException ignored) {
                continue;
            } finally {
                waitingForTask.set(false);
            }

            lastJobTime = nextJobTime;

            synchronized (entrySetMonitor) {
                for(InternalSchedulerEntry entry: schedulerEntries) {
                    if(entry.getTrigger().shouldExecute(lastJobTime)) {
                        entry.getTrigger().wasTriggered(lastJobTime);
                        entry.setLastExecutionTime(lastJobTime);

                        executor.execute(entry.getTask());
                    }
                }
            }
        }
    }

    /**
     * Get the smallest execution time from all jobs. Removes job with no further trigger times.
     * @param last Current timestamp
     * @return next execution time
     */
    private Instant getNextExecutionTime(Instant last) {
        Instant next = null;

        synchronized (entrySetMonitor) {
            Set<InternalSchedulerEntry> orphaned = null;

            for(InternalSchedulerEntry entry: schedulerEntries) {
                Instant jobNext = entry.getTrigger().nextExecution(last);
                if(jobNext == null) {
                    if(orphaned == null) {
                        orphaned = new HashSet<>();
                    }

                    orphaned.add(entry);
                    continue;
                }

                if(next == null) {
                    next = jobNext;
                    continue;
                }

                if(jobNext.isBefore(next)) {
                    next = jobNext;
                }
            }

            if(orphaned != null) {
                schedulerEntries.removeAll(orphaned);
            }
        }

        return next;
    }

    void cancel(InternalSchedulerEntry entry) {
        synchronized (entrySetMonitor) {
            if(schedulerEntries.remove(entry)) {
                thread.interrupt();
            }
        }
    }
}
