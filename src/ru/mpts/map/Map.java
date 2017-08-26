package ru.mpts.map;

import ru.mpts.engine.Display;
import ru.mpts.engine.Engine;

import java.awt.*;
import java.util.Random;

public class Map {
    private static int WightMap = 128;
    private static int HeightMap = 128;
    private static int IndentX = 0;
    private static int IndentY = 0;
    private static int scale;
    private static Object[][] mapObjects;
    private static Object[][] mapGrounds;
    private static Graphics2D graphics;

    public Map() {
        mapObjects = new Object[WightMap][HeightMap];
        mapGrounds = new Object[WightMap][HeightMap];

        setScale();
        InitializationMap();
        graphics = Engine.graphics2D;
    }

    public static void setObject(Location location, int objectType) {
        mapObjects[location.getX()][location.getY()] = new Object(location, objectType);
        mapObjects[location.getX()][location.getY()].getSprite().draw();
    }

    public static void setGround(Location location, int groundType) {
        mapGrounds[location.getX()][location.getY()] = new Object(location, groundType);
        mapGrounds[location.getX()][location.getY()].getSprite().draw();
    }

    public static Object getObject(Location location) {
        return mapObjects[location.getX()][location.getY()];
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

        setObject(new Location(0, 0, 0), MapObjectType.STONE);
        setObject(new Location(29, 0, 0), MapObjectType.STONE);
        setObject(new Location(15, 29, 0), MapObjectType.STONE);

        for (int i = 0; i < 10; i++) {
            for (int x = 0; x < WightMap; x++) {
                for (int y = 0; y < WightMap; y++) {
                    if (getObject(new Location(x, y, 0)).getType() == MapObjectType.STONE) {

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
                if (mapObjects[x][y].getType() != MapObjectType.AIR) {
                    mapObjects[x][y].getSprite().draw();
                }
            }
        }
    }
}
