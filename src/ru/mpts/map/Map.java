package ru.mpts.map;

import ru.mpts.engine.Display;
import ru.mpts.engine.Engine;
import ru.mpts.sprite.Sprite;

import java.awt.*;
import java.util.Random;

public class Map {
    private static int WightMap = 30;
    private static int HeightMap = 30;
    private static int IndentX = 0;
    private static int IndentY = 0;
    private static float scale;
    private static Object[][] map;
    private static float[][] mapStageRess;
    private Graphics2D graphics;

    public Map() {
        map = new Object[WightMap][HeightMap];

        setScale();
        InitializationMap();
        graphics = Engine.graphics2D;
    }

    private void setScale() {
       /* if ((float) (Display.WIGHT / WightMap) > (float) (Display.HIGHT / HeightMap)) {
            scale = (float) ((Display.HIGHT - 10) / (HeightMap));
        } else {
            scale = (float) ((Display.WIGHT - 10) / (WightMap));
        }*/
        scale = 20;
    }


    private void InitializationMap() {
        for (int x = 0; x < WightMap; x++) {
            for (int y = 0; y < WightMap; y++) {
                map[x][y] = new Object(new Location(x, y, 0), MapObjectType.GRASS);
            }
        }
        generationMap();

/*x
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
*/
    }

    private void generationMap(){
        Random random = new Random();
        for(int a = 0; a < 10; a++) {
            int x = random.nextInt(30);
            int y = random.nextInt(30);
            addObject(new Location(x, y, 0), MapObjectType.STONE);
        }
    }

    public static void addObject(Location location, int objectType) {
        switch (objectType) {
            case MapObjectType.GRASS: {

            }
            map[location.getX()][location.getY()] = new Object(location, objectType);
        }
    }

    public static Object getObject(Location location) {
        return map[location.getX()][location.getY()];
    }

    public void update() {

    }

    public void render() {

        for (int x = 0; x < WightMap; x++) {
            for (int y = 0; y < HeightMap; y++) {
                map[x][y].getSprite().draw((int)(x*scale+getIndentX()), (int)(y*scale+getIndentY()), (float) scale);
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
