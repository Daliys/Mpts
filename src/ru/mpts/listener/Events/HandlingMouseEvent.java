package ru.mpts.listener.Events;

import ru.mpts.engine.Display;
import ru.mpts.engine.Engine;
import ru.mpts.map.Location;
import ru.mpts.map.Map;
import ru.mpts.map.MapObjectType;
import ru.mpts.sprite.Sprite;
import ru.mpts.units.TaskPlayers;
import ru.mpts.units.TaskType;
import ru.mpts.units.Units;
import ru.mpts.map.Object;

import java.awt.*;

public class HandlingMouseEvent {
    private static String mouseStage = MouseTypeAction.MOUSE;
    private static Location locationStartSelect;
    private static Location locationNowSelect;
    private static Graphics2D graphics;
    private static boolean isPressMouse = false;
    private static Sprite spriteSelectCell;
    private boolean isSelect;
    public static int followTheHeroID = -1;
    public static Location followTheBlock = null;

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
        if(followTheHeroID != -1){
            int x = Units.getHero(followTheHeroID).getLocation().getX();
            int y = Units.getHero(followTheHeroID).getLocation().getY();

            String str ="<html>ID:"+followTheHeroID;
            if(Units.getHero(followTheHeroID).getTaskLocation() != null) {
                str += "  Task:" + Units.getHero(followTheHeroID).getTaskLocation().getX() + " " + Units.getHero(followTheHeroID).getTaskLocation().getY();
            }else{
                str+="  Task: null";
            }
            str+="<br>TaskType:"+Units.getHero(followTheHeroID).getTaskNumAction() + " StageHero: "+Units.getHero(followTheHeroID).getStageHero() + "<br>LocationHero: " +
                    Units.getHero(followTheHeroID).getLocation().getX() + " "+Units.getHero(followTheHeroID).getLocation().getY()+"</html>";
            Display.MenuTextInformationHero.setText(str);

            spriteSelectCell.setLocation(new Location(x, y, 0));
            spriteSelectCell.draw();
        }else if(followTheBlock != null){
            String srt = "<html>Block:"+Map.getObject(followTheBlock).getType()+"<br>"+
                    "Location:"+followTheBlock.getX()+" "+followTheBlock.getY()+
                    "<br>"+"durability:"+Map.getObject(followTheBlock).getDurability()+"</html>";
            Display.MenuTextInformationHero.setText(srt);
        }else{
            String str = "<html>";
            for(int i = 0; i < TaskPlayers.taskAction.size(); i++) {
                str+= "Location: " + TaskPlayers.taskAction.get(i).getLocation().getX()+" "+TaskPlayers.taskAction.get(i).getLocation().getY()
                        +" Action:"+TaskPlayers.taskAction.get(i).getAction()+"<br>";
            }
            str += "/html";
            Display.MenuTextInformationHero.setText(str);
        }

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
                }else if(mouseStage == MouseTypeAction.CANCEL){
                    TaskPlayers.RemoveTaskFormListAndHeroes(new Location(x,y,0));
                }
                /*else if(mouseStage == MouseTypeAction.MOUSE){
                    if(((maxX-minX)+(maxY-minY)) == 1){
                        System.out.println("==1");
                        if(Units.getHero(new Location(x,y,0)) != null){

                            try {
                                followTheHeroID = Units.getHero(new Location(x,y,0)).getId();
                                System.out.println(followTheHeroID);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }*/
            }
        }
    }
}
