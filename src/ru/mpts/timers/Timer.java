package ru.mpts.timers;

public class Timer {
    public long lastTimeHero;
    private int interval;

    public Timer(int interval) {
        lastTimeHero = System.currentTimeMillis();
        this.interval = interval;
    }

    public Timer() {
        lastTimeHero = System.currentTimeMillis();
    }

    public boolean getTimeHero() {
        if (System.currentTimeMillis() - lastTimeHero >= 100) {
            lastTimeHero = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public boolean getTime() {
        if (System.currentTimeMillis() - lastTimeHero >= interval) {
            lastTimeHero = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}
