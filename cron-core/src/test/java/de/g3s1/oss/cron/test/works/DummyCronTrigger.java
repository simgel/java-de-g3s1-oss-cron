package de.g3s1.oss.cron.test.works;

import de.g3s1.oss.cron.works.trigger.AbstractTrigger;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

public class DummyCronTrigger extends AbstractTrigger {
    private final List<Instant> executions;

    public DummyCronTrigger() {
        executions = new LinkedList<>();
    }

    @Override
    public Instant nextExecution(Instant instant) {
        return roundToNextSecond(instant.plusSeconds(1));
    }

    @Override
    public void wasTriggered(Instant instant) {
        executions.add(instant);
    }

    public List<Instant> getExecutions() {
        return executions;
    }

    @Override
    public boolean shouldExecute(Instant instant) {
        return true;
    }
}
