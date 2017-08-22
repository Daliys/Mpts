package ru.mpts.timers;

public class Timer {
    public long lastTimeHero;
    public static float GAME_SPEED = 1000.0f;


    public static float GAME_SPEED_MINE = 500.0f;
    public static float GAME_SPEED_MOVE = 50.0f;

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

    /// 0.9
    public boolean getHeroTimeMove(float heroMoveSpeed) {
       return getTime(GAME_SPEED_MOVE/heroMoveSpeed);
    }

    public boolean getHeroTimeMine(float heroMineSpeed) {
       return getTime(GAME_SPEED_MINE/heroMineSpeed);
    }
}
