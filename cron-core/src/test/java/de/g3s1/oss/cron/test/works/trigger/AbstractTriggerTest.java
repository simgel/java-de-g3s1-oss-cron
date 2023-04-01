package de.g3s1.oss.cron.test.works.trigger;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public abstract class AbstractTriggerTest {
    protected Instant getBaseTime() {
        LocalDateTime localDateTime = LocalDateTime.parse("2023-04-01T12:00:00");
        return localDateTime.toInstant(ZoneOffset.UTC);
    }
}
