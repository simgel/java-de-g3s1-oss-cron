package de.g3s1.oss.cron.test.works.trigger;

import de.g3s1.oss.cron.works.trigger.NextSecondCronTrigger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class NextSecondTriggerTest extends AbstractTriggerTest {
    @Test
    public void testTrigger() {
        NextSecondCronTrigger trigger = new NextSecondCronTrigger(zoneOffset);

        Instant baseTime = getBaseTime();
        Instant next = trigger.nextExecution(baseTime);

        long diff = next.toEpochMilli() - baseTime.toEpochMilli();
        Assertions.assertTrue(diff >= 1000);
    }
}
