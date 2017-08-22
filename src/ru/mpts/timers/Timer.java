package ru.mpts.timers;

public class Timer {
    public long lastTimeHero;
    public static float GAME_SPEED = 1.0f;
    public static float RENDER_SPEED = 30.0f;

    public Timer() {
        lastTimeHero = System.currentTimeMillis();
    }

    public boolean getTime(float interval) {
        if (System.currentTimeMillis() - lastTimeHero >= interval) {
            lastTimeHero = System.currentTimeMillis();
            return true;
        }
        return false;
    }
    public boolean getHeroTime(float heroSpeed) {
        if (System.currentTimeMillis() - lastTimeHero >= GAME_SPEED*1000/heroSpeed) {
            lastTimeHero = System.currentTimeMillis();
            return true;
        }
        return false;
    }
}
