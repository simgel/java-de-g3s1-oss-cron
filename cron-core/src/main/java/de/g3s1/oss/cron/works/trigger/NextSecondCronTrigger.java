package de.g3s1.oss.cron.works.trigger;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

public class NextSecondCronTrigger extends AbstractTrigger {
    private final ZoneOffset zoneOffset;

    private Instant lastTriggerTime;

    public NextSecondCronTrigger(ZoneOffset zoneOffset) {
        this.zoneOffset = zoneOffset;
    }


    @Override
    public Instant nextExecution(Instant instant) {
        if(lastTriggerTime == null) {
            Instant next = roundToNextSecond(instant, zoneOffset);
            if(Duration.between(instant, next).toMillis() < 1000) {
                return next.plusSeconds(1);
            }

            return next;
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
