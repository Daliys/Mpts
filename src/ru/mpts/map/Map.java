package ru.mpts.map;

import ru.mpts.engine.Display;
import ru.mpts.engine.Engine;

import java.awt.*;

public class Map {
    private static int WightMap = 32;
    private static int HeightMap = 32;
    private static int IndentX = 0;
    private static int IndentY = 0;
    private static int scale;
    private static Object[][] mapObjects; // находятся все блоки и герой
    private static Object[][] mapGrounds;   // отоброжения земли (пола)
    private static Object[][] mapDropObject;      // ноходятся все упавший блоки, добытые рессурсы
    private static Graphics2D graphics;

    public Map() {
        mapObjects = new Object[WightMap][HeightMap];
        mapGrounds = new Object[WightMap][HeightMap];
        mapDropObject = new Object[WightMap][HeightMap];

        setScale();
        InitializationMap();
        graphics = Engine.graphics2D;
    }

    public static void setObject(Location location, int objectType) {
        mapObjects[location.getX()][location.getY()] = new Object(location, objectType);
        mapObjects[location.getX()][location.getY()].draw();
    }
    public static void setMapDropObject(Location location, int objectType){
        mapDropObject[location.getX()][location.getY()] = new Object(location, objectType);
        mapDropObject[location.getX()][location.getY()].draw();
    }

    public static void setGround(Location location, int groundType) {
        mapGrounds[location.getX()][location.getY()] = new Object(location, groundType);
        mapGrounds[location.getX()][location.getY()].getSprite().draw();
    }

    public static Object getObject(Location location) {
        if (location.getX() >= 0 && location.getX() < getWightMap() && location.getY() >= 0 && location.getY() < getHeightMap())
            return mapObjects[location.getX()][location.getY()];
        else
            return new Object(location, MapObjectType.NONE);
    }

    public static Object getObject(int x, int y) {
        Location location = new Location(x, y, 0);
        return getObject(location);
    }

    // проверяет можно ли пройти герою по этому блоку
    public static boolean isPassableBlock(Location location){
        if(Map.getObject(location).getType() == MapObjectType.AIR){
            return true;
        }
        return false;
    }

    public static Object getGround(Location location) {
        return mapGrounds[location.getX()][location.getY()];
    }

    public static int getHeightMap() {
        return HeightMap;
    }

    public static void setHeightMap(int heightMap) {
        HeightMap = heightMap;
    }

    public static int getWightMap() {
        return WightMap;
    }

    public static void setWightMap(int wightMap) {
        WightMap = wightMap;
    }

    public static int getIndentX() {
        return IndentX;
    }

    public static void setIndentX(int indentX) {
        IndentX = indentX;
    }

    public static int getIndentY() {
        return IndentY;
    }

    public static void setIndentY(int indentY) {
        IndentY = indentY;
    }

    public static int getScale() {
        return scale;
    }

    public static void setScale(int scale) {
        Map.scale = scale;
    }

    public static Object[][] getMapObjects() {
        return mapObjects;
    }

    public static Object[][] getMapGrounds() {
        return mapGrounds;
    }

    private void setScale() {
        if ((float) (Display.WIGHT / WightMap) > (float) (Display.HIGHT / HeightMap)) {
            scale = (Display.HIGHT - 10) / (HeightMap);
        } else {
            scale = (Display.WIGHT - 10) / (WightMap);
        }
        scale = 20;
    }

    private void generationWorld() {
        int[][] gMap = new int[WightMap][HeightMap];
        int s = (int)(Math.random() * 7) + 3;
        int d = (int)(Math.random() * 6) + 2;

        while (d > 0) {
            for (int x = 0; x < WightMap; x++) {
                if ((int) (Math.random() * 64) == 0) {
                    setObject(new Location(x, 0, 0), MapObjectType.STONE);
                    d--;
                }
            }
            if (d <= 0) break;
            for (int x = 0; x < WightMap; x++) {
                if ((int) (Math.random() * 64) == 0) {
                    setObject(new Location(x, HeightMap-1, 0), MapObjectType.STONE);
                    d--;
                }
            }
            if (d <= 0) break;
            for (int y = 0; y < HeightMap; y++) {
                if ((int) (Math.random() * 64) == 0) {
                    setObject(new Location(0, y, 0), MapObjectType.STONE);
                    d--;
                }
            }
            if (d <= 0) break;
            for (int y = 0; y < HeightMap; y++) {
                if ((int) (Math.random() * 64) == 0) {
                    setObject(new Location(WightMap-1, y, 0), MapObjectType.STONE);
                    d--;
                }
            }
        }

        for (int i = 0; i <= s; i++) {

            for (int x = 0; x < WightMap; x++) {
                for (int y = 0; y < WightMap; y++) {
                    if (getObject(new Location(x, y, 0)).getType() == MapObjectType.STONE) {
                        if (x + 1 < WightMap) {
                            if (i == s){
                                if ((int)(Math.random() * 2) == 1) {
                                    gMap[x + 1][y] = 1;
                                }
                            } else {
                                gMap[x + 1][y] = 1;
                            }
                        }
                        if (x - 1 >= 0) {
                            if (i == s) {
                                if ((int)(Math.random() * 2) == 1) {
                                    gMap[x - 1][y] = 1;
                                }
                            } else {
                                gMap[x - 1][y] = 1;
                            }
                        }
                        if (y + 1 < HeightMap) {
                            if (i == s){
                                if ((int)(Math.random() * 2) == 1) {
                                    gMap[x][y+ 1] = 1;
                                }
                            } else {
                                gMap[x][y + 1] = 1;
                            }
                        }
                        if (y - 1 >= 0) {
                            if (i == s){
                                if ((int)(Math.random() * 2) == 1) {
                                    gMap[x][y - 1] = 1;
                                }
                            } else {
                                gMap[x][y - 1] = 1;
                            }
                        }
                    }
                }
            }

            for (int x = 0; x < WightMap; x++) {
                for (int y = 0; y < WightMap; y++) {
                    if (getObject(new Location(x, y, 0)).getType() != MapObjectType.STONE && gMap[x][y] == 1) {
                        setObject(new Location(x, y, 0), MapObjectType.STONE);
                    }
                }
            }
        }
    }

    private void InitializationMap() {
        for (int x = 0; x < WightMap; x++) {
            for (int y = 0; y < WightMap; y++) {
                setGround(new Location(x, y, 0), MapGroundType.GRASS);
                setObject(new Location(x, y, 0), MapObjectType.AIR);
                setMapDropObject(new Location(x,y,0), MapDropObjectType.NULL);
            }
        }

        generationWorld();
    }

    public void setRectangleObject(Location startLocation, Location endLocation, int objectType) {
        int startLocationX = startLocation.getX();
        int startLocationY = startLocation.getY();
        int endLocationX = endLocation.getX();
        int endLocationY = endLocation.getY();

        for (int x = Math.min(startLocationX, endLocationX); x <= Math.max(startLocationX, endLocationX); x++) {
            for (int y = Math.min(startLocationY, endLocationY); y <= Math.max(startLocationY, endLocationY); y++) {
                setObject(new Location(x, y, 0), objectType);
            }
        }
    }

    public void update() {

    }

    public void render() {
        for (int x = 0; x < WightMap; x++) {
            for (int y = 0; y < HeightMap; y++) {
                mapGrounds[x][y].getSprite().draw();
                mapDropObject[x][y].getSprite().draw();
                if (mapObjects[x][y].getType() != MapObjectType.AIR) {
                    mapObjects[x][y].draw();
                }
            }
        }
    }
}
