package ru.mpts.map;

import ru.mpts.engine.Display;
import ru.mpts.engine.Engine;

import java.awt.*;

public class Map {
    private static int WightMap = 30;
    private static int HeightMap = 30;
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

    public static void addObject(Location location, int objectType) {
        mapObjects[location.getX()][location.getY()] = new Object(location, objectType);
        mapObjects[location.getX()][location.getY()].getSprite().draw();
    }

    public static void addGround(Location location, int groundType) {
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

    private void InitializationMap() {
        for (int x = 0; x < WightMap; x++) {
            for (int y = 0; y < WightMap; y++) {
                addGround(new Location(x, y, 0), MapGroundType.GRASS);
                addObject(new Location(x, y, 0), MapObjectType.AIR);
            }
        }

        addObject(new Location(15, 25, 0), MapObjectType.IRON_ORE);
        addObject(new Location(16, 25, 0), MapObjectType.IRON_ORE);
        addObject(new Location(17, 25, 0), MapObjectType.IRON_ORE);
        addObject(new Location(18, 25, 0), MapObjectType.IRON_ORE);
        addObject(new Location(19, 25, 0), MapObjectType.IRON_ORE);
        addObject(new Location(20, 25, 0), MapObjectType.IRON_ORE);
        addObject(new Location(21, 25, 0), MapObjectType.IRON_ORE);

        addObject(new Location(15, 24, 0), MapObjectType.IRON_ORE);
        addObject(new Location(16, 24, 0), MapObjectType.IRON_ORE);
        addObject(new Location(17, 24, 0), MapObjectType.IRON_ORE);
        addObject(new Location(18, 24, 0), MapObjectType.IRON_ORE);
        addObject(new Location(19, 24, 0), MapObjectType.IRON_ORE);
        addObject(new Location(20, 24, 0), MapObjectType.IRON_ORE);
        addObject(new Location(21, 24, 0), MapObjectType.IRON_ORE);

        addObject(new Location(15, 23, 0), MapObjectType.IRON_ORE);
        addObject(new Location(16, 23, 0), MapObjectType.IRON_ORE);
        addObject(new Location(17, 23, 0), MapObjectType.IRON_ORE);
        addObject(new Location(18, 23, 0), MapObjectType.IRON_ORE);
        addObject(new Location(19, 23, 0), MapObjectType.IRON_ORE);
        addObject(new Location(20, 23, 0), MapObjectType.IRON_ORE);
        addObject(new Location(21, 23, 0), MapObjectType.IRON_ORE);

        addObject(new Location(15, 22, 0), MapObjectType.IRON_ORE);
        addObject(new Location(16, 22, 0), MapObjectType.IRON_ORE);
        addObject(new Location(17, 22, 0), MapObjectType.IRON_ORE);
        addObject(new Location(18, 22, 0), MapObjectType.IRON_ORE);
        addObject(new Location(19, 22, 0), MapObjectType.IRON_ORE);
        addObject(new Location(20, 22, 0), MapObjectType.IRON_ORE);
        addObject(new Location(21, 22, 0), MapObjectType.IRON_ORE);

        addObject(new Location(15, 21, 0), MapObjectType.IRON_ORE);
        addObject(new Location(16, 21, 0), MapObjectType.IRON_ORE);
        addObject(new Location(17, 21, 0), MapObjectType.IRON_ORE);
        addObject(new Location(18, 21, 0), MapObjectType.IRON_ORE);
        addObject(new Location(19, 21, 0), MapObjectType.IRON_ORE);
        addObject(new Location(20, 21, 0), MapObjectType.IRON_ORE);
        addObject(new Location(21, 21, 0), MapObjectType.IRON_ORE);


        addObject(new Location(11, 1, 0), MapObjectType.IRON_ORE);
        addObject(new Location(21, 10, 0), MapObjectType.IRON_ORE);
        addObject(new Location(1, 21, 0), MapObjectType.IRON_ORE);
        addObject(new Location(3, 16, 0), MapObjectType.IRON_ORE);
        addObject(new Location(8, 28, 0), MapObjectType.IRON_ORE);

        addObject(new Location(17, 5, 0), MapObjectType.IRON_ORE);
        addObject(new Location(21, 27, 0), MapObjectType.IRON_ORE);
        addObject(new Location(4, 24, 0), MapObjectType.IRON_ORE);
        addObject(new Location(19, 25, 0), MapObjectType.IRON_ORE);
        addObject(new Location(18, 28, 0), MapObjectType.IRON_ORE);

        addObject(new Location(17, 17, 0), MapObjectType.IRON_ORE);
        addObject(new Location(6, 20, 0), MapObjectType.IRON_ORE);
        addObject(new Location(9, 20, 0), MapObjectType.IRON_ORE);
        addObject(new Location(19, 19, 0), MapObjectType.IRON_ORE);
        addObject(new Location(13, 21, 0), MapObjectType.IRON_ORE);
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
