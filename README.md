## About

Simple Java cron scheduler


## Usage

```groovy
Cron cron = Cron.create(Executors.newSingleThreadExecutor());
CronEntry entry = cron.add(Triggers.nextSecond(), () -> {
    System.out.println("Cron execution");
});
```