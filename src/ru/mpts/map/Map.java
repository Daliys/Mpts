package ru.mpts.map;

import ru.mpts.engine.Display;
import ru.mpts.engine.Engine;
import ru.mpts.sprite.Sprite;

import java.awt.*;

public class Map {
    private static int WightMap = 30;
    private static int HeightMap = 30;
    private static int IndentX = 0;
    private static int IndentY = 0;
    private static float scale;
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

    private void setScale() {
        if ((float) (Display.WIGHT / WightMap) > (float) (Display.HIGHT / HeightMap)) {
            scale = (float) ((Display.HIGHT - 10) / (HeightMap));
        } else {
            scale = (float) ((Display.WIGHT - 10) / (WightMap));
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
    }

    public static void addObject(Location location, int objectType) {
        switch (objectType) {
            case MapObjectType.AIR: {
                mapObjects[location.getX()][location.getY()] = new Object(location, objectType, new Sprite("air.png", graphics));
                break;
            }
            case MapObjectType.GRASS: {
                mapObjects[location.getX()][location.getY()] = new Object(location, objectType, new Sprite("grass(1).png", graphics));
                mapObjects[location.getX()][location.getY()].getSprite().draw((int) ((scale * location.getX()) + IndentX), (int) ((scale * location.getY()) + IndentX), scale);
                break;
            }
            case MapObjectType.HERO: {
                mapObjects[location.getX()][location.getY()] = new Object(location, objectType, new Sprite("hero(1).png", graphics));
                mapObjects[location.getX()][location.getY()].getSprite().draw((int) ((scale * location.getX()) + IndentX), (int) ((scale * location.getY()) + IndentX), scale);
                break;
            }
            case MapObjectType.IRON_ORE: {
                mapObjects[location.getX()][location.getY()] = new Object(location, objectType, new Sprite("ironOreBlockCenter(1).png", graphics));
                mapObjects[location.getX()][location.getY()].getSprite().draw((int) ((scale * location.getX()) + IndentX), (int) ((scale * location.getY()) + IndentX), scale);
                break;
            }
        }
    }

    public static void addGround(Location location, int groundType) {
        switch (groundType) {
            case MapObjectType.GRASS: {
                mapGrounds[location.getX()][location.getY()] = new Object(location, groundType, new Sprite("grass(1).png", graphics));
                mapGrounds[location.getX()][location.getY()].getSprite().draw((int) ((scale * location.getX()) + IndentX), (int) ((scale * location.getY()) + IndentX), scale);
                break;
            }
        }
    }

    public static Object getObject(Location location) {
        return mapObjects[location.getX()][location.getY()];
    }

    public void update() {

    }

    public void render() {

        for (int x = 0; x < WightMap; x++) {
            for (int y = 0; y < HeightMap; y++) {
                mapGrounds[x][y].getSprite().draw((int) ((scale * x) + IndentX), (int) ((scale * y) + IndentY), scale);
                if(mapObjects[x][y].getType() != MapObjectType.AIR) {
                    mapObjects[x][y].getSprite().draw((int) ((scale * x) + IndentX), (int) ((scale * y) + IndentY), scale);
                }
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
