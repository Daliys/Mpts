package ru.mpts.engine;

import ru.mpts.listener.Events.HandlingMouseEvent;
import ru.mpts.listener.Events.KeyActionListener;
import ru.mpts.map.Map;
import ru.mpts.units.TaskPlayers;
import ru.mpts.units.Units;
import ru.mpts.timers.Timer;

import java.awt.*;

public class Engine implements Runnable {
    public static Graphics2D graphics2D;
    private static long nanosec = 1000000000;
    private static float UpdateInterval = nanosec / 60.0f; // кол во обновления game
    public Map map;
    Units units;
    Timer timer = new Timer();
    private boolean startGame;
    private float delta;
    private int fps;
    private int upd;
    private int updSkip;            // считает количество пропусков update

    public Engine() {
        Display.CreateBuffer(0xff000000, 2);
        graphics2D = Display.getGraphics();

        int x = 10;
        int width = 100;
        int scale = 64;
        System.out.println((x*scale-((width/(64/scale)-scale)/2)));

        map = new Map();
        units = new Units();

        Thread thread = new Thread(this, "ru.mpts.engine.Engine");
        thread.start();
    }

    public void StartGame() {
        startGame = true;
    }

    public void PauseGame() {
        startGame = false;
    }

    public void render() {
        Display.clear();

        map.render();
        units.render();
        HandlingMouseEvent.render();
        TaskPlayers.render();

        Display.swapBuffer();
        if (timer.getTime(timer.RENDER_SPEED))
            KeyActionListener.render();
    }

    public void update() {
        map.update();
        units.update();
    }

    @Override
    public void run() {

        long lastTime = System.nanoTime();
        long coutSec = 0;

        while (true) {      //Поток работает до конца завершения программы

            while (startGame) {     //  служит что бы остановить(Пауза), возобновить игру (главный цикл игры)
                //видео 6  (46.30)
                long now = System.nanoTime();
                long elapsedTime = now - lastTime;
                lastTime = now;
                coutSec += elapsedTime;
                boolean render = false;
                delta += (elapsedTime / UpdateInterval);
                while (delta > 1) {

                    update();

                    upd++;
                    delta--;
                    if (render) {
                        updSkip++;
                    } else {
                        render = true;
                    }
                }
                if (render) {
                    render();

                    fps++;
                } else {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (coutSec >= nanosec) {
                    coutSec = 0;
                    Display.setTitle("FPS:" + fps + "  UPD:" + upd + "  SKIP:" + updSkip);
                    fps = 0;
                    upd = 0;
                    updSkip = 0;
                }


            }


            try {    //проверка снята ли игра с паузы
                Thread.sleep(300);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Map getMap() {
        return map;
    }
}
