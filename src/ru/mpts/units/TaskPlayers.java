package ru.mpts.units;

import ru.mpts.engine.Display;
import ru.mpts.engine.Engine;
import ru.mpts.map.Location;
import ru.mpts.map.Map;
import ru.mpts.map.MapObjectType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class TaskPlayers {

    public static boolean ADD_SELECT_ALL = true;
    public static boolean REMOVE_SELECT_ALL = false;

    public static volatile List<Action> taskAction = new ArrayList<Action>();

    public static void AddTask(Location location, int action) {
        if (CheckListTask(location)) {     // проверка на то ести ли в заднияx эта задача(х,у),если есть то удалить ее
            return;
        }
        taskAction.add(new Action(location, action));
        Display.MenuTextTask.setText("Task: " + taskAction.size());

    }

    public static void AddSelectionTask(Location location, int action){
        if(!getFoundLocation(location)){
            taskAction.add(new Action(location,action));
        }
    }
    public static void RemoveSelectionTask(Location location, int action){
        if(getFoundLocation(location)){
            if(!CheckListTask(location)) {
                RemoveTask(location);
            }
        }
    }

    // проверят на наличие задания, если в этой клетке нет задания то добавить, иначе удалить это задание
    private static boolean CheckListTask(Location location) {
        for (int i = 0; i < taskAction.size(); i++) {
            if (taskAction.get(i).getLocation().getX() == location.getX() && taskAction.get(i).getLocation().getY() == location.getY()) {
                if (!taskAction.get(i).isTaken()) {
                    taskAction.remove(i);

                } else if (taskAction.get(i).isTaken()) {
                    Units.removeHeroesTask(location);
                    taskAction.remove(i);

                }
                Display.MenuTextTask.setText("Task: " + taskAction.size());

                return true;
            }
        }
        Display.MenuTextTask.setText("Task: " + taskAction.size());
        return false;
    }
    public static boolean getFoundLocation(Location location){
        for(int i = 0; i < taskAction.size(); i++){
            if(taskAction.get(i).getLocation().getX() == location.getX() && taskAction.get(i).getLocation().getY() == location.getY()){
                return true;
            }
        }
        return false;
    }
    // удали задание из списка заданий  и из задания героя
    public static void RemoveTask(Location location) {
        for (int a = 0; a < taskAction.size(); a++) {
            if (taskAction.get(a).getLocation().getX() == location.getX() && taskAction.get(a).getLocation().getY() == location.getY()) {
                taskAction.remove(a);
                Display.MenuTextTask.setText("Task: " + taskAction.size());
            }
        }
    }

    private static boolean ThreadTask = true;

    // герой запрашивает задание. Здесь проверяется задания может ли герой до него дойти. Если может то дать задание иначе игнор
    public static synchronized void getTask(int heroId, Location location) {
        if (ThreadTask) {
            ThreadTask = false;
        } else {
            return;
        }
        Thread FindWays = new Thread(() -> {

            for (int a = 0; a < taskAction.size(); a++) {
                if (taskAction.get(a).isTaken()) {
                    continue;
                }

                int ThreadMapWay[][] = new int[Map.getWightMap()][Map.getHeightMap()];
                int inc = 2;
                ThreadMapWay[location.getX()][location.getY()] = inc;
                boolean boolWhile = true;
                boolean FindRout = false;

                exitWhile:
                while (boolWhile) {
                    boolean AliveTide = false;
                    if(taskAction.size() <= a){
                        return;
                    }
                    for (int x = 0; x < Map.getWightMap(); x++) {
                        for (int y = 0; y < Map.getHeightMap(); y++) {
                            //System.out.println(taskAction.get(a).getLocation().getX()+" - "+x+" "+taskAction.get(a).getLocation().getY()+" - " + y + "  |"+mapWay[x][y] + " - " + inc);
                            if (x == taskAction.get(a).getLocation().getX() && y == taskAction.get(a).getLocation().getY() && (ThreadMapWay[x][y] == (inc - 1) || ThreadMapWay[x][y] == inc)) {
                                FindRout = true;
                                //  System.out.println(FindRout);
                                break exitWhile;
                            } else if (ThreadMapWay[x][y] == inc) {

                                if ((x + 1) < Map.getWightMap() && (Map.getObject(new Location(x+1, y, 0)).getType() == MapObjectType.GRASS || ((x + 1) == taskAction.get(a).getLocation().getX() && y == taskAction.get(a).getLocation().getY())) && ThreadMapWay[x + 1][y] == 0) {
                                    ThreadMapWay[x + 1][y] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((x - 1) >= 0 && (Map.getObject(new Location(x-1, y, 0)).getType() == MapObjectType.GRASS || ((x - 1) == taskAction.get(a).getLocation().getX() && y == taskAction.get(a).getLocation().getY())) && ThreadMapWay[x - 1][y] == 0) {
                                    ThreadMapWay[x - 1][y] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((y + 1) < Map.getHeightMap() && (Map.getObject(new Location(x, y+1, 0)).getType() == MapObjectType.GRASS || (x == taskAction.get(a).getLocation().getX() && (y + 1) == taskAction.get(a).getLocation().getY())) && ThreadMapWay[x][y + 1] == 0) {
                                    ThreadMapWay[x][y + 1] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((y - 1) >= 0 && (Map.getObject(new Location(x, y-1, 0)).getType() == MapObjectType.GRASS || (x == taskAction.get(a).getLocation().getX() && (y - 1) == taskAction.get(a).getLocation().getY())) && ThreadMapWay[x][y - 1] == 0) {
                                    ThreadMapWay[x][y - 1] = (inc + 1);
                                    AliveTide = true;
                                }
                            }
                        }
                    }

                    if (!AliveTide) {
                        boolWhile = false;
                        FindRout = false;

                    }
                    inc++;

                }
                if (FindRout) {
                    if (!taskAction.get(a).isTaken()) {
                        Units.setHeroesTask(taskAction.get(a).getLocation(), taskAction.get(a).getAction(), heroId);
                        taskAction.get(a).setTaken(true);
                    }
                    ThreadTask = true;

                    return;
                }

            }

            ThreadTask = true;


        });
        FindWays.start();

    }

    public static void render() {
        for (int i = 0; i < taskAction.size(); i++) {
            if (!taskAction.get(i).isTaken()) {
                Engine.g.setColor(new Color(0xAC5800));
            } else {
                Engine.g.setColor(new Color(0x00A8BB));
                Engine.g.fillRect((int) (taskAction.get(i).getLocation().getX() * Map.getScale() + Map.getIndentX() + 2), (int) (taskAction.get(i).getLocation().getY() * Map.getScale() + Map.getIndentY() + 2),
                        (int) (Map.getScale() - 5), (int) (Map.getScale() - 5));
                Engine.g.setColor(new Color(0xAF0063));
            }
            Engine.g.drawRect((int) (taskAction.get(i).getLocation().getX() * Map.getScale() + Map.getIndentX() + 2), (int) (taskAction.get(i).getLocation().getY() * Map.getScale() + Map.getIndentY() + 2),
                    (int) (Map.getScale() - 5), (int) (Map.getScale() - 5));

        }
    }

}