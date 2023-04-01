package de.g3s1.oss.cron;

import de.g3s1.oss.cron.api.CronTrigger;
import de.g3s1.oss.cron.works.trigger.NextSecondCronTrigger;

public final class Triggers {
    private Triggers() {
    }

    public static CronTrigger nextSecond() {
        return new NextSecondCronTrigger();
    }
}
