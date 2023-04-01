package de.g3s1.oss.cron;

import de.g3s1.oss.cron.api.CronTrigger;
import de.g3s1.oss.cron.works.trigger.EveryHourCronTrigger;
import de.g3s1.oss.cron.works.trigger.EveryMinuteCronTrigger;
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

    public static CronTrigger everyMinute(ZoneOffset offset, int second) {
        return new EveryMinuteCronTrigger(offset, second);
    }

    public static CronTrigger everyMinute(int second) {
        return everyMinute(ZoneOffset.UTC, second);
    }

    public static CronTrigger everyMinute(ZoneOffset offset) {
        return new EveryMinuteCronTrigger(offset, 0);
    }

    public static CronTrigger everyMinute() {
        return everyMinute(ZoneOffset.UTC, 0);
    }

    public static CronTrigger everyHour(ZoneOffset offset, int minute, int second) {
        return new EveryHourCronTrigger(offset, minute, second);
    }

    public static CronTrigger everyHour(int minute, int second) {
        return everyHour(ZoneOffset.UTC, minute, second);
    }

    public static CronTrigger everyHour(ZoneOffset offset) {
        return everyHour(offset, 0, 0);
    }

    public static CronTrigger everyHour() {
        return everyHour(ZoneOffset.UTC, 0, 0);
    }
}
