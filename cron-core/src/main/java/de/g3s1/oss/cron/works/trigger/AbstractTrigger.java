package de.g3s1.oss.cron.works.trigger;

import de.g3s1.oss.cron.api.CronTrigger;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public abstract class AbstractTrigger implements CronTrigger {
    protected Instant roundToNextSecond(Instant instant, ZoneOffset offset) {
        return LocalDateTime.ofInstant(instant, offset)
                .truncatedTo(ChronoUnit.SECONDS)
                .plusSeconds(1)
                .toInstant(offset);
    }
}
