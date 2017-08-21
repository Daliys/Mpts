package ru.mpts.listener.Events;

import ru.mpts.engine.Display;
import ru.mpts.engine.Engine;
import ru.mpts.map.Location;
import ru.mpts.map.Map;
import ru.mpts.map.MapObjectType;
import ru.mpts.units.TaskPlayers;
import ru.mpts.units.TaskType;

import java.awt.*;

public class HandlingMouseEvent {
    private static String mouseStage = MouseTypeAction.MOUSE;
    private static Location locationStartSelect;
    private static Location locationNowSelect;
    private  boolean isSelect;
    private static Graphics2D graphics;
    private static boolean isPressMouse = false;

    public HandlingMouseEvent() {
        locationStartSelect = new Location(0, 0, 0);
        locationNowSelect = new Location(0, 0, 0);
        isSelect = false;
        graphics = Engine.g;
    }

    public static void setMouseStage(String mouseStage) {
        HandlingMouseEvent.mouseStage = mouseStage;
    }

    public static String getMouseStage() {
        return mouseStage;
    }

    public Location getLocationStartSelect() {
        return locationStartSelect;
    }

    public Location getLocationNowSelect() {
        return locationNowSelect;
    }

    public void setIsSelect(boolean isSelect) {

        this.isSelect = isSelect;
        if (!isSelect) {
            addInTask();
        }
    }

    public static boolean isPressMouse() {
        return isPressMouse;
    }

    public static void setIsPressMouse(boolean isPressMouse) {
        HandlingMouseEvent.isPressMouse = isPressMouse;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void addInTask() {
        int minX;
        int maxX;
        int minY;
        int maxY;

        if (locationStartSelect.getX() > locationNowSelect.getX()) {
            minX = locationNowSelect.getX();
            maxX = locationStartSelect.getX();
        } else {
            maxX = locationNowSelect.getX();
            minX = locationStartSelect.getX();
        }
        if (locationStartSelect.getY() > locationNowSelect.getY()) {
            minY = locationNowSelect.getY();
            maxY = locationStartSelect.getY();
        } else {
            maxY = locationNowSelect.getY();
            minY = locationStartSelect.getY();
        }


        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if(mouseStage == MouseTypeAction.MINE) {
                    if (Map.getObject(new Location(x, y, 0)).getType() == MapObjectType.IRON_ORE) {
                        TaskPlayers.AddTask(new Location(x, y, 0), TaskType.MINE);
                    }
                }
            }
        }
    }


    public static void render() {
        int countSelect = 0;
        Display.MenuTextSelect.setText(Integer.toString(countSelect)+" Iron");
        if(!isPressMouse()){
            return;

        }
        //System.out.println("Render");
        int minX;
        int maxX;
        int minY;
        int maxY;

        if (locationStartSelect.getX() > locationNowSelect.getX()) {
            minX = locationNowSelect.getX();
            maxX = locationStartSelect.getX();
        } else {
            maxX = locationNowSelect.getX();
            minX = locationStartSelect.getX();
        }
        if (locationStartSelect.getY() > locationNowSelect.getY()) {
            minY = locationNowSelect.getY();
            maxY = locationStartSelect.getY();
        } else {
            maxY = locationNowSelect.getY();
            minY = locationStartSelect.getY();
        }


        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                Engine.g.setColor(new Color(0xFF00EA));

                    Engine.g.drawRect((int) (x * Map.getScale() + Map.getIndentX()), (int) (y * Map.getScale() + Map.getIndentY()), (int) Map.getScale(), (int) Map.getScale());
               if(Map.getObject(new Location(x,y,0)).getType() == MapObjectType.IRON_ORE) {
                   countSelect++;
                   Display.MenuTextSelect.setText(Integer.toString(countSelect)+" Iron");
                }
            }
        }
    }
}
