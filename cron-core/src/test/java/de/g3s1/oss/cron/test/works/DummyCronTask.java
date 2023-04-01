package de.g3s1.oss.cron.test.works;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

public class DummyCronTask implements Runnable {
    private final List<Instant> executions;
    private final Object waitMon;

    public DummyCronTask() {
        executions = new LinkedList<>();
        waitMon = new Object();
    }

    @Override
    public void run() {
        executions.add(Instant.now());

        synchronized (waitMon) {
            waitMon.notifyAll();
        }
    }

    public List<Instant> getExecutions() {
        return executions;
    }

    public void waitForExecution(long timeoutMillis) throws InterruptedException {
        synchronized (waitMon) {
            waitMon.wait(timeoutMillis);
        }
    }
}
