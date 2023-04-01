package de.g3s1.oss.cron.works.trigger;

import java.time.Instant;

public class NextSecondCronTrigger extends AbstractTrigger {
    private Instant lastTriggerTime;

    @Override
    public Instant nextExecution(Instant instant) {
        if(lastTriggerTime == null) {
            return instant.plusSeconds(1);
        } else {
            return null;
        }
    }

    @Override
    public void wasTriggered(Instant instant) {
        lastTriggerTime = instant;
    }

    @Override
    public boolean shouldExecute(Instant instant) {
        return lastTriggerTime == null;
    }
}
