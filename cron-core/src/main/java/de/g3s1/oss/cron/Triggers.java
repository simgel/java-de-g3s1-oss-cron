package de.g3s1.oss.cron;

import de.g3s1.oss.cron.api.CronTrigger;
import de.g3s1.oss.cron.works.trigger.NextSecondCronTrigger;

import java.time.ZoneOffset;

public final class Triggers {
    private Triggers() {
    }

    public static CronTrigger nextSecond() {
        return new NextSecondCronTrigger(ZoneOffset.UTC);
    }

    public static CronTrigger nextSecond(ZoneOffset offset) {
        return new NextSecondCronTrigger(offset);
    }
}
