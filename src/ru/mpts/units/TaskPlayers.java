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

    /// добавить разные списки для разных типов заданией (для копания , строительства)
    public static volatile List<Action> taskActionMine = new ArrayList<Action>();
    public static volatile List<Action> taskActionBuild = new ArrayList<Action>();

    public static void AddTask(Location location, int action) {
        if (CheckListTask(location)) {     // проверка на то ести ли в заднияx эта задача(х,у),если есть то удалить ее
            return;
        }
        taskActionMine.add(new Action(location, action));
        Display.MenuTextTask.setText("Task: " + taskActionMine.size());
    }

    // добовляет выделенную область задания в спискиs
    public static void AddSelectionTask(Location location, int action) {
        if (!getFoundLocation(location)) {
            if(action == TaskType.MINE) {
                taskActionMine.add(new Action(location, action));
            }else if(action == TaskType.BUILD){
                taskActionBuild.add(new Action(location, action));
            }
            Display.MenuTextTask.setText("Task: " + taskActionMine.size());
        }
    }




    // удаляет задания из героя и делает это здание достуным для других героев
    public static void RemoveTaskFromHero(Location location) {
        for (int i = 0; i < taskActionMine.size(); i++) {
            if (location.getX() == taskActionMine.get(i).getLocation().getX() && location.getY() == taskActionMine.get(i).getLocation().getY()) {
                taskActionMine.get(i).setTaken(false);
            }
        }
    }


    // проверят на наличие задания, если в этой клетке нет задания то добавить, иначе удалить это задание
    private static boolean CheckListTask(Location location) {
        for (int i = 0; i < taskActionMine.size(); i++) {
            if (taskActionMine.get(i).getLocation().getX() == location.getX() && taskActionMine.get(i).getLocation().getY() == location.getY()) {
                if (!taskActionMine.get(i).isTaken()) {
                    taskActionMine.remove(i);

                } else if (taskActionMine.get(i).isTaken()) {
                    Units.removeHeroesTask(location);
                    taskActionMine.remove(i);

                }
                Display.MenuTextTask.setText("Task: " + taskActionMine.size());

                return true;
            }
        }
        Display.MenuTextTask.setText("Task: " + taskActionMine.size());
        return false;
    }

    // проверяет есть ли задание в списках
    public static boolean getFoundLocation(Location location) {
        for (int i = 0; i < taskActionMine.size(); i++) {
            if (taskActionMine.get(i).getLocation().getX() == location.getX() && taskActionMine.get(i).getLocation().getY() == location.getY()) {
                return true;
            }
        }
        return false;
    }

    // удали задание только из списка заданий
    public static void RemoveTask(Location location) {
        for (int a = 0; a < taskActionMine.size(); a++) {
            if (taskActionMine.get(a).getLocation().getX() == location.getX() && taskActionMine.get(a).getLocation().getY() == location.getY()) {
                taskActionMine.remove(a);
                Display.MenuTextTask.setText("Task: " + taskActionMine.size());
            }
        }
    }

    // удаляет задание из списка и из героя
    public static void RemoveTaskFormListAndHeroes(Location location) {
        RemoveTask(location);
        Units.removeHeroesTask(location);
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


                int ThreadMapWay[][] = new int[Map.getWightMap()][Map.getHeightMap()];
                int inc = 2;
                ThreadMapWay[location.getX()][location.getY()] = inc;
                boolean boolWhile = true;
                boolean FindRout = false;
                int numGetTask = -1;

                exitWhile:
                while (boolWhile) {
                    boolean AliveTide = false;
                    for (int x = 0; x < Map.getWightMap(); x++) {
                        for (int y = 0; y < Map.getHeightMap(); y++) {
                            //System.out.println(taskActionMine.get(a).getLocation().getX()+" - "+x+" "+taskActionMine.get(a).getLocation().getY()+" - " + y + "  |"+mapWay[x][y] + " - " + inc);
                            for (int i = 0; i < taskActionMine.size(); i++) {
                                if(!taskActionMine.get(i).isTaken()) {
                                    if (taskActionMine.size() > i && x == taskActionMine.get(i).getLocation().getX() && y == taskActionMine.get(i).getLocation().getY() && (ThreadMapWay[x][y] == (inc - 1) || ThreadMapWay[x][y] == inc)) {
                                        FindRout = true;
                                        numGetTask = i;
                                        break exitWhile;
                                    }
                                }
                            }
                            if (ThreadMapWay[x][y] == inc) {

                                if ((x + 1) < Map.getWightMap() && (Map.getObject(new Location(x + 1, y, 0)).getType() == MapObjectType.AIR || (getFoundLocation(new Location((x + 1),y,0)))) && ThreadMapWay[x + 1][y] == 0) {
                                    ThreadMapWay[x + 1][y] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((x - 1) >= 0 && (Map.getObject(new Location(x - 1, y, 0)).getType() == MapObjectType.AIR || (getFoundLocation(new Location((x - 1),y,0)))) && ThreadMapWay[x - 1][y] == 0) {
                                    ThreadMapWay[x - 1][y] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((y + 1) < Map.getHeightMap() && (Map.getObject(new Location(x, y + 1, 0)).getType() == MapObjectType.AIR || (getFoundLocation(new Location(x, (y + 1),0)))) && ThreadMapWay[x][y + 1] == 0) {
                                    ThreadMapWay[x][y + 1] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((y - 1) >= 0 && (Map.getObject(new Location(x, y - 1, 0)).getType() == MapObjectType.AIR || (getFoundLocation(new Location(x, (y - 1),0)))) && ThreadMapWay[x][y - 1] == 0) {
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
                    if (numGetTask < taskActionMine.size() && numGetTask != -1 && !taskActionMine.get(numGetTask).isTaken()) {
                        if (taskActionMine.get(numGetTask).getLocation().getX() != -1 && taskActionMine.get(numGetTask).getLocation().getY() != -1) {
                            Units.setHeroesTask(taskActionMine.get(numGetTask).getLocation(), taskActionMine.get(numGetTask).getAction(), heroId);
                            taskActionMine.get(numGetTask).setTaken(true);
                        }

                    }
                    ThreadTask = true;

                    return;
                }



            ThreadTask = true;


        });
        FindWays.start();

    }

    public static void render() {
        for (int i = 0; i < taskActionMine.size(); i++) {
            if (!taskActionMine.get(i).isTaken()) {
                Engine.graphics2D.setColor(new Color(0xAC5800));
            } else {
                Engine.graphics2D.setColor(new Color(0x00A8BB));
                Engine.graphics2D.fillRect(taskActionMine.get(i).getLocation().getX() * Map.getScale() + Map.getIndentX() + 2, taskActionMine.get(i).getLocation().getY() * Map.getScale() + Map.getIndentY() + 2,
                        Map.getScale() - 5, Map.getScale() - 5);
                Engine.graphics2D.setColor(new Color(0xAF0063));
            }
            Engine.graphics2D.drawRect(taskActionMine.get(i).getLocation().getX() * Map.getScale() + Map.getIndentX() + 2, taskActionMine.get(i).getLocation().getY() * Map.getScale() + Map.getIndentY() + 2,
                    Map.getScale() - 5, Map.getScale() - 5);

        }
    }

}