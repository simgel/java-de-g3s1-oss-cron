package de.g3s1.oss.cron.works.trigger;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class EveryHourCronTrigger extends AbstractTrigger {
    private final ZoneOffset zoneOffset;

    private final int minute;

    private final int second;


    private Instant lastExecution;

    public EveryHourCronTrigger(ZoneOffset zoneOffset, int minute, int second) {
        this.zoneOffset = zoneOffset;
        this.second = Math.abs(second) % 60;
        this.minute = Math.abs(minute) % 60;
    }

    @Override
    public Instant nextExecution(Instant instant) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneOffset);

        LocalDateTime nextExec = localDateTime
                .truncatedTo(ChronoUnit.HOURS)
                .plusMinutes(minute)
                .plusSeconds(second);

        if(!instant.isBefore(nextExec.toInstant(zoneOffset))) {
            nextExec = nextExec.plusHours(1);
        }

        return nextExec.toInstant(zoneOffset);
    }

    @Override
    public void wasTriggered(Instant instant) {
        lastExecution = instant;
    }

    @Override
    public boolean shouldExecute(Instant instant) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneOffset);
        return localDateTime.getSecond() == second && localDateTime.getMinute() == minute;
    }
}
