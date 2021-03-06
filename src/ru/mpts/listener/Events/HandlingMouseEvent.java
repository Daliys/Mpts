package ru.mpts.listener.Events;

import ru.mpts.engine.Display;
import ru.mpts.engine.Engine;
import ru.mpts.map.Location;
import ru.mpts.map.Map;
import ru.mpts.map.MapObjectType;
import ru.mpts.sprite.Sprite;
import ru.mpts.units.TaskPlayers;
import ru.mpts.units.TaskType;

import java.awt.*;

public class HandlingMouseEvent {
    private static String mouseStage = MouseTypeAction.MOUSE;
    private static Location locationStartSelect;
    private static Location locationNowSelect;
    private static Graphics2D graphics;
    private static boolean isPressMouse = false;
    private static Sprite spriteSelectCell;
    private boolean isSelect;

    public HandlingMouseEvent() {
        locationStartSelect = new Location(0, 0, 0);
        locationNowSelect = new Location(0, 0, 0);
        spriteSelectCell = new Sprite("selectCell.png", new Location(1, 1, 1));
        isSelect = false;
        graphics = Engine.graphics2D;
    }

    public static String getMouseStage() {
        return mouseStage;
    }

    public static void setMouseStage(String mouseStage) {
        HandlingMouseEvent.mouseStage = mouseStage;
    }

    public static boolean isPressMouse() {
        return isPressMouse;
    }

    public static void setIsPressMouse(boolean isPressMouse) {
        HandlingMouseEvent.isPressMouse = isPressMouse;
    }

    public static void render() {
        int countSelect = 0;
        Display.MenuTextSelect.setText(Integer.toString(countSelect) + " Iron");
        if (!isPressMouse()) {
            return;
        }
        int minX = Math.min(locationStartSelect.getX(), locationNowSelect.getX());
        int maxX = Math.max(locationStartSelect.getX(), locationNowSelect.getX());
        int minY = Math.min(locationStartSelect.getY(), locationNowSelect.getY());
        int maxY = Math.max(locationStartSelect.getY(), locationNowSelect.getY());

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                spriteSelectCell.setLocation(new Location(x, y, 0));
                spriteSelectCell.draw();

                switch (Map.getObject(new Location(x, y, 0)).getType()) {
                    case MapObjectType.IRON_ORE: {
                        countSelect++;
                        Display.MenuTextSelect.setText(Integer.toString(countSelect) + " Iron");
                        break;
                    }
                    case MapObjectType.STONE: {
                        countSelect++;
                        Display.MenuTextSelect.setText(Integer.toString(countSelect) + " Stone");
                        break;
                    }
                }
            }
        }
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

    public boolean isSelect() {
        return isSelect;
    }

    public void addInTask() {
        int minX = Math.min(locationStartSelect.getX(), locationNowSelect.getX());
        int maxX = Math.max(locationStartSelect.getX(), locationNowSelect.getX());
        int minY = Math.min(locationStartSelect.getY(), locationNowSelect.getY());
        int maxY = Math.max(locationStartSelect.getY(), locationNowSelect.getY());

        boolean boolSelected = true;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (mouseStage == MouseTypeAction.MINE) {
                    int type = Map.getObject(new Location(x, y, 0)).getType();
                    if (type == MapObjectType.IRON_ORE ||
                            type == MapObjectType.STONE) {
                        TaskPlayers.AddSelectionTask(new Location(x, y, 0), TaskType.MINE);
                    }
                }
            }
        }
    }
}
