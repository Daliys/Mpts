package ru.mpts.map;

import ru.mpts.engine.Display;
import ru.mpts.engine.Engine;

import java.awt.*;

public class Map {
    public static int WightMap = 30;
    public static int HeightMap = 30;
    public static int IndentX = 5;
    public static int IndentY = 5;
    public static float scale;
    public static int[][] map;
    public static float[][] mapStageRess;
    public Graphics2D graphics;


    public Map() {
        map = new int[WightMap][HeightMap];
        mapStageRess = new float[WightMap][HeightMap];

        setScale();
        InitializationMap();
        graphics = Engine.g;
    }

    private void setScale() {
        if ((float) (Display.WIGHT / WightMap) > (float) (Display.HIGHT / HeightMap)) {
            scale = (float) ((Display.HIGHT - 10) / (HeightMap));
        } else {
            scale = (float) ((Display.WIGHT - 10) / (WightMap));
        }
    }


    private void InitializationMap() {
        for (int x = 0; x < WightMap; x++) {
            for (int y = 0; y < HeightMap; y++) {
                map[x][y] = 0;
                mapStageRess[x][y] = 0;

            }
        }

        AddResource(9, 25);
        AddResource(10, 25);
        AddResource(11, 25);
        AddResource(12, 25);
        AddResource(13, 25);
        AddResource(14, 25);
        AddResource(15, 25);
        AddResource(16, 25);
        AddResource(17, 25);
        AddResource(18, 25);
        AddResource(10, 18);
        AddResource(10, 19);
        AddResource(10, 20);
        AddResource(10, 21);
        AddResource(10, 22);
        AddResource(11, 22);
        AddResource(12, 22);
        AddResource(13, 22);
        AddResource(10, 23);
        AddResource(11, 23);
        AddResource(12, 23);
        AddResource(13, 23);
        AddResource(13, 23);
        AddResource(11, 21);
        AddResource(12, 21);
        AddResource(13, 21);
        AddResource(13, 21);
        AddResource(11, 20);
        AddResource(12, 20);
        AddResource(13, 20);
        AddResource(13, 20);


    }

    public void AddResource(int xR, int yR) {
        map[xR][yR] = 1;
        mapStageRess[xR][yR] = 100;
    }

    public void update() {

    }

    public void render() {

        for (int x = 0; x < WightMap; x++) {
            for (int y = 0; y < HeightMap; y++) {
                if (map[x][y] == 0) {
                    graphics.setColor(new Color(0x007707));
                } else if (map[x][y] == 10) {
                    graphics.setColor(new Color(0xDA0400));
                } else if (map[x][y] == 1) {
                    graphics.setColor(new Color(0x4A4949));
                } else if (map[x][y] == 2) {
                    graphics.setColor(new Color(0xFFFF00));
                }
                graphics.fillRect((int) ((scale * x) + IndentX), (int) ((scale * y) + IndentY), (int) (scale), (int) (scale));
                graphics.setColor(new Color(0x030077));
                graphics.drawRect((int) ((scale * x) + IndentX), (int) ((scale * y) + IndentY), (int) (scale), (int) (scale));
            }

        }


        // graphics.setColor(new Color(0x030077));
     /*   for (int x = 0; x <= HeightMap; x++) {
            graphics.drawLine((int) ((scale * 0) + IndentX), (int) ((scale * x) + IndentY), (int) ((scale * (WightMap) + IndentX)), (int) ((scale * x) + IndentY));
        }
        for (int y = 0; y <= WightMap; y++) {
            graphics.drawLine((int) ((scale * y) + IndentX), (int) ((scale * 0) + IndentY), (int) ((scale * y) + IndentX), (int) ((scale * (HeightMap)) + IndentY));
        }

*/
    }


}
