package de.g3s1.oss.cron.works.trigger;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

/**
 * Triggers every minute at the given second
 */
public class EveryMinuteCronTrigger extends AbstractTrigger {

    private final ZoneOffset zoneOffset;

    private final int second;

    public EveryMinuteCronTrigger(ZoneOffset zoneOffset, int second) {
        this.zoneOffset = zoneOffset;
        this.second = Math.abs(second) % 60;
    }

    @Override
    public Instant nextExecution(Instant instant) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneOffset);

        LocalDateTime nextExec = localDateTime
                .truncatedTo(ChronoUnit.MINUTES)
                .plusMinutes(1)
                .plusSeconds(second);

        return nextExec.toInstant(zoneOffset);
    }

    @Override
    public void wasTriggered(Instant instant) {
    }

    @Override
    public boolean shouldExecute(Instant instant) {
        return LocalDateTime.ofInstant(instant, zoneOffset).getSecond() == second;
    }
}
