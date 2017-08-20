package ru.mpts.map;

import ru.mpts.engine.Display;
import ru.mpts.engine.Engine;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Map {
    private static int WightMap = 30;
    private static int HeightMap = 30;
    private static int IndentX = 5;
    private static int IndentY = 5;
    private static float scale;
    private static MapObject[][] map;
    private static float[][] mapStageRess;
    private Graphics2D graphics;

    public Map() {
        map = new MapObject[WightMap][HeightMap];

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
            for (int y = 0; y < WightMap; y++) {
                map[x][y] = new MapObject(new Location(x, y, 0), MapObjectType.GRASS);
            }
        }

        addObject(new Location(15, 25, 0), MapObjectType.IRON_ORE);
    }

    public static void addObject(Location location, int objectType) {
        map[location.getX()][location.getY()] = new MapObject(location, objectType);
    }

    public static MapObject getObject(Location location) {
        return map[location.getX()][location.getY()];
    }

    public void update() {

    }

    public void render() {

        for (int x = 0; x < WightMap; x++) {
            for (int y = 0; y < HeightMap; y++) {
                switch (map[x][y].getType()) {
                    case MapObjectType.GRASS: {
                        graphics.setColor(new Color(0x007707));
                        break;
                    }
                    case MapObjectType.HERO: {
                        graphics.setColor(new Color(0xDA0400));
                        break;
                    }
                    case MapObjectType.IRON_ORE: {
                        graphics.setColor(new Color(0x4A4949));
                        break;
                    }
                }
                graphics.fillRect((int) ((scale * x) + IndentX), (int) ((scale * y) + IndentY), (int) (scale), (int) (scale));
                graphics.setColor(new Color(0x030077));
                graphics.drawRect((int) ((scale * x) + IndentX), (int) ((scale * y) + IndentY), (int) (scale), (int) (scale));
            }
        }
    }

    public static void setHeightMap(int heightMap) {
        HeightMap = heightMap;
    }

    public static void setWightMap(int wightMap) {
        WightMap = wightMap;
    }

    public static void setIndentX(int indentX) {
        IndentX = indentX;
    }

    public static void setIndentY(int indentY) {
        IndentY = indentY;
    }

    public static void setScale(float scale) {
        Map.scale = scale;
    }

    public static int getHeightMap() {
        return HeightMap;
    }

    public static int getWightMap() {
        return WightMap;
    }

    public static int getIndentX() {
        return IndentX;
    }

    public static int getIndentY() {
        return IndentY;
    }

    public static float getScale() {
        return scale;
    }
}
