package ru.mpts.units;

import com.sun.org.glassfish.external.arc.Taxonomy;
import ru.mpts.engine.Engine;
import ru.mpts.inventory.Inventory;
import ru.mpts.map.Location;
import ru.mpts.map.Map;
import ru.mpts.map.MapObjectType;
import ru.mpts.timers.Timer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Hero {
    private int id;
    private int healthPoints;
    private int[][] mapWay;
    private int StageHero = TaskType.NONE;
    private int TaskNumAction = 0;
    private float speedMove;
    private Action action;
    private Location heroLocation;
    private Location taskLocation;
    private Inventory inventory;
    private Graphics2D graphics;
    private Timer timerHero;

    public Hero(int id, Location InitHeroLocation, Inventory inventory, float speedMove, int healthPoints) {
        this.heroLocation = InitHeroLocation;
        this.inventory = inventory;
        this.speedMove = speedMove;
        this.healthPoints = healthPoints;
        this.action = new Action();
        this.id = id;
        taskLocation = new Location(-1, -1, 0);
        TaskNumAction = 0;

        mapWay = new int[Map.getWightMap()][Map.getHeightMap()];

        Map.setObject(InitHeroLocation, MapObjectType.HERO);
        graphics = Engine.graphics2D;

        timerHero = new Timer();
    }

    public void render() {
        for (int x = 0; x < Map.getWightMap(); x++) {
            for (int y = 0; y < Map.getHeightMap(); y++) {
                if (mapWay[x][y] == -10 && Map.getObject(new Location(x, y, 0)).getType() != MapObjectType.HERO) {
                    graphics.setColor(new Color(0x267775));
                    // graphics.drawLine((int)((ru.mpts.map.Map.scale*x)+5+(ru.mpts.map.Map.scale/2)),(int)((ru.mpts.map.Map.scale*y)+5+(ru.mpts.map.Map.scale/2)),);
                    graphics.fillRect((int) ((Map.getScale() * x) + Map.getIndentX() + 2), (int) ((Map.getScale() * y) + Map.getIndentY() + 2), (int) (Map.getScale() - 4), (int) (Map.getScale() - 4));

                }
            }
        }
    }

    private void TakeTask() {
        TaskPlayers.getTask(id, heroLocation);
    }

    public void update() {
        switch (StageHero) {
            case TaskType.NONE: {
                StageHero = TaskType.GET_TASK;
            }
            case TaskType.GET_TASK: {
                if (timerHero.getTime(Timer.GAME_SPEED_GET_TASK)) {
                    TakeTask();
                }
                break;
            }
            case TaskType.MOVE: {
                if (timerHero.getHeroTimeMove(speedMove)) {
                    MoveOnMap();
                }
                break;
            }
            case TaskType.MINE: {
                if (timerHero.getHeroTimeMine(1.0f)) {
                    CleanMapAll();
                    MineResource();
                }
                break;
            }
            case TaskType.FIND_WAY: {

                FindWayTask();
                break;
            }
            case TaskType.CHECK_WAY_MOVE: {
                CheckWayMove();
            }
        }
    }

    private void MineResource() {
        if (Map.getObject(taskLocation).getDurability() <= 0) {
            Map.setObject(taskLocation, MapObjectType.AIR);

            TaskPlayers.RemoveTaskFormListAndHeroes(taskLocation);
        } else {
            Map.getObject(taskLocation).setDurability(Map.getObject(taskLocation).getDurability() - 5);
        }
    }

    private void CleanMapAll() {
        for (int x = 0; x < Map.getWightMap(); x++) {
            for (int y = 0; y < Map.getHeightMap(); y++) {
                mapWay[x][y] = 0;
            }
        }
        mapWay[heroLocation.getX()][heroLocation.getY()] = 1;
    }


    private void FindWayTask() {
        StageHero = TaskType.WAIT_FIND_WAY;
        Thread threadFineWay = new Thread(new Runnable() {
            @Override
            public void run() {
                CleanMapAll();
                boolean boolWhile = false;
                boolean boolFindWay = false;
                int increment = 1;
                if (TaskNumAction == 0 && (taskLocation.getX() != -1 && taskLocation.getY() != -1)) {
                    StageHero = TaskType.NONE;
                    TaskPlayers.RemoveTaskFromHero(new Location(taskLocation.getX(), taskLocation.getY(), 0));
                    removeTask();
                    return;
                }

                exitWhile:

                while (!boolWhile) {
                    boolean AliveTide = false;
                    for (int x = 0; x < Map.getWightMap(); x++) {
                        for (int y = 0; y < Map.getHeightMap(); y++) {

                            if (CheckTaskCell(new Location(x, y, 0)) && mapWay[x][y] == increment) {

                                boolWhile = true;
                                boolFindWay = true;
                                break exitWhile;
                            } else if (mapWay[x][y] == increment) {

                                if (((x - 1) >= 0) && (CheckFreeMap(new Location((x - 1), y, 0)) || CheckTaskCell(new Location((x - 1), y, 0))) && mapWay[(x - 1)][y] == 0) {
                                    mapWay[x - 1][y] = (increment + 1);
                                    AliveTide = true;
                                }
                                if (((x + 1) < Map.getWightMap()) && (CheckFreeMap(new Location((x + 1), y, 0)) || CheckTaskCell(new Location((x + 1), y, 0))) && mapWay[(x + 1)][y] == 0) {
                                    mapWay[x + 1][y] = (increment + 1);
                                    AliveTide = true;
                                }
                                if (((y - 1) >= 0) && (CheckFreeMap(new Location(x, (y - 1), 0)) || CheckTaskCell(new Location(x, (y - 1), 0))) && mapWay[x][(y - 1)] == 0) {
                                    mapWay[x][y - 1] = (increment + 1);
                                    AliveTide = true;
                                }
                                if (((y + 1) < Map.getHeightMap()) && (CheckFreeMap(new Location(x, (y + 1), 0)) || CheckTaskCell(new Location(x, (y + 1), 0))) && mapWay[x][(y + 1)] == 0) {
                                    mapWay[x][y + 1] = (increment + 1);
                                    AliveTide = true;
                                }
                            }


                        }
                    }

                    increment++;

                    if (!AliveTide) {
                        StageHero = TaskType.NONE;
                        boolWhile = true;
                        boolFindWay = false;
                    }
                }

                if (boolFindWay) {
                    mapWay[taskLocation.getX()][taskLocation.getY()] = -10;
                    increment--;
                    int x = taskLocation.getX();
                    int y = taskLocation.getY();
                    while (increment >= 1) {
                        if ((x + 1) < Map.getWightMap() && (y - 1) >= 0 && mapWay[(x + 1)][(y - 1)] == (increment - 1) &&     // правый верхний
                                !CheckTaskCell(new Location(x, y, 0)) && (increment - 1) > 0 && mapWay[(x + 1)][y] != 0 && mapWay[x][(y - 1)] != 0) {

                            mapWay[x + 1][y - 1] = -10;
                            x++;
                            y--;
                            increment--;

                        } else if ((x - 1) >= 0 && (y - 1) >= 0 && mapWay[(x - 1)][(y - 1)] == (increment - 1) &&       // левый верхний
                                !CheckTaskCell(new Location(x, y, 0)) && (increment - 1) > 0 && mapWay[(x - 1)][y] != 0 && mapWay[x][(y - 1)] != 0) {

                            mapWay[x - 1][y - 1] = -10;
                            x--;
                            y--;
                            increment--;

                        } else if ((x + 1) < Map.getWightMap() && (y + 1) < Map.getHeightMap() && mapWay[(x + 1)][(y + 1)] == (increment - 1) &&      // правый нижний
                                !CheckTaskCell(new Location(x, y, 0)) && (increment - 1) > 0 && mapWay[(x + 1)][y] != 0 && mapWay[x][(y + 1)] != 0) {

                            mapWay[x + 1][y + 1] = -10;
                            x++;
                            y++;
                            increment--;

                        } else if ((x - 1) >= 0 && (y + 1) < Map.getHeightMap() && mapWay[(x - 1)][(y + 1)] == (increment - 1) &&       // левый нижний
                                !CheckTaskCell(new Location(x, y, 0)) && (increment - 1) > 0 && mapWay[(x - 1)][y] != 0 && mapWay[x][(y + 1)] != 0) {

                            mapWay[x - 1][y + 1] = -10;
                            x--;
                            y++;
                            increment--;

                        } else if ((x + 1) < Map.getWightMap() && mapWay[(x + 1)][y] == increment) {    //право

                            mapWay[x + 1][y] = -10;
                            x++;

                        } else if ((x - 1) >= 0 && mapWay[(x - 1)][y] == increment) { // лево

                            mapWay[x - 1][y] = -10;
                            x--;

                        } else if ((y + 1) < Map.getHeightMap() && mapWay[x][(y + 1)] == increment) {   // низ

                            mapWay[x][y + 1] = -10;
                            y++;

                        } else if ((y - 1) >= 0 && mapWay[x][(y - 1)] == increment) {       // верх

                            mapWay[x][y - 1] = -10;
                            y--;

                        }

                        increment--;
                    }
                    CleanMapWay(-10);
                    StageHero = TaskType.MOVE;


                } else {
                    TaskPlayers.RemoveTaskFromHero(new Location(taskLocation.getX(), taskLocation.getY(), 0));
                    removeTask();
                }
            }

            private boolean CheckFreeMap(Location location) {
                if (Map.getObject(location).getType() == MapObjectType.AIR) {
                    return true;
                } else {
                    return false;
                }
            }

            private boolean CheckTaskCell(Location location) {
                if (taskLocation.getX() == location.getX() && taskLocation.getY() == location.getY()) {
                    return true;
                } else {
                    return false;
                }
            }

        });
        threadFineWay.start();
    }

    private void CheckWayMove() {       // проверка пути на возможность прохождение персонажем этого пути без проблем если не так то поиск нового пути
        StageHero = TaskType.WAIT_FIND_WAY;
        Location nowCellWay = new Location(heroLocation.getX(), heroLocation.getY(), 0);
        Location lastCellWay = new Location(-1, -1, 0);
        boolean flagWhile = true;
        while (flagWhile) {
            // если найдет последнюю клетку то выходит
            if ((taskLocation.getX() == (nowCellWay.getX()) && taskLocation.getY() == (nowCellWay.getY()))) {
                StageHero = TaskType.MOVE;
                return;
            }
            //System.out.println("check");
            boolean freeCell = false;

            if ((nowCellWay.getX() + 1) < Map.getWightMap() && mapWay[(nowCellWay.getX() + 1)][nowCellWay.getY()] == -10
                    && !(lastCellWay.getX() == (nowCellWay.getX() + 1) && lastCellWay.getY() == (nowCellWay.getY()))) {    //  право
                if (Map.getObject(new Location((nowCellWay.getX() + 1), (nowCellWay.getY()), 0)).getType() == MapObjectType.AIR
                        || (taskLocation.getX() == (nowCellWay.getX() + 1) && taskLocation.getY() == (nowCellWay.getY()))) {
                    freeCell = true;
                    lastCellWay.setY(nowCellWay.getY());
                    lastCellWay.setX(nowCellWay.getX());
                    nowCellWay.setX((nowCellWay.getX() + 1));
                }
            } else if ((nowCellWay.getX() - 1) >= 0 && mapWay[(nowCellWay.getX() - 1)][nowCellWay.getY()] == -10
                    && !(lastCellWay.getX() == (nowCellWay.getX() - 1) && lastCellWay.getY() == (nowCellWay.getY()))) {     // лево
                if (Map.getObject(new Location((nowCellWay.getX() - 1), (nowCellWay.getY()), 0)).getType() == MapObjectType.AIR
                        || (taskLocation.getX() == (nowCellWay.getX() - 1) && taskLocation.getY() == (nowCellWay.getY()))) {
                    freeCell = true;
                    lastCellWay.setY(nowCellWay.getY());
                    lastCellWay.setX(nowCellWay.getX());
                    nowCellWay.setX((nowCellWay.getX() - 1));
                }
            } else if ((nowCellWay.getY() + 1) < Map.getHeightMap() && mapWay[nowCellWay.getX()][(nowCellWay.getY() + 1)] == -10
                    && !(lastCellWay.getX() == nowCellWay.getX() && lastCellWay.getY() == (nowCellWay.getY() + 1))) {  //низ
                if (Map.getObject(new Location((nowCellWay.getX()), (nowCellWay.getY() + 1), 0)).getType() == MapObjectType.AIR
                        || (taskLocation.getX() == (nowCellWay.getX()) && taskLocation.getY() == (nowCellWay.getY() + 1))) {
                    freeCell = true;
                    lastCellWay.setY(nowCellWay.getY());
                    lastCellWay.setX(nowCellWay.getX());
                    nowCellWay.setY((nowCellWay.getY() + 1));
                }
            } else if ((nowCellWay.getY() - 1) >= 0 && mapWay[nowCellWay.getX()][(nowCellWay.getY() - 1)] == -10
                    && !(lastCellWay.getX() == (nowCellWay.getX()) && lastCellWay.getY() == (nowCellWay.getY() - 1))) {         // верх
                //System.out.println("pred");
                if (Map.getObject(new Location((nowCellWay.getX()), (nowCellWay.getY() - 1), 0)).getType() == MapObjectType.AIR
                        || (taskLocation.getX() == (nowCellWay.getX()) && taskLocation.getY() == (nowCellWay.getY() - 1))) {
                    //System.out.println("VERX");
                    freeCell = true;
                    lastCellWay.setY(nowCellWay.getY());
                    lastCellWay.setX(nowCellWay.getX());
                    nowCellWay.setY((nowCellWay.getY() - 1));
                }
            } else if ((nowCellWay.getX() + 1) < Map.getWightMap() && (nowCellWay.getY() - 1) >= 0      // право верх
                    && mapWay[(nowCellWay.getX() + 1)][(nowCellWay.getY() - 1)] == -10
                    && !(lastCellWay.getX() == (nowCellWay.getX() + 1) && lastCellWay.getY() == (nowCellWay.getY() - 1))) {

                if (Map.getObject(new Location((nowCellWay.getX() + 1), (nowCellWay.getY() - 1), 0)).getType() == MapObjectType.AIR
                        || (taskLocation.getX() == (nowCellWay.getX() + 1) && taskLocation.getY() == (nowCellWay.getY() - 1))) {
                    freeCell = true;
                    lastCellWay.setY(nowCellWay.getY());
                    lastCellWay.setX(nowCellWay.getX());
                    nowCellWay.setX((nowCellWay.getX() + 1));
                    //nowCellWay.setY((nowCellWay.getY()));
                    nowCellWay.setY((nowCellWay.getY() - 1));
                }
            } else if ((nowCellWay.getX() - 1) >= 0 && (nowCellWay.getY() - 1) >= 0     // лево верх
                    && mapWay[(nowCellWay.getX() - 1)][(nowCellWay.getY() - 1)] == -10
                    && !(lastCellWay.getX() == (nowCellWay.getX() - 1) && lastCellWay.getY() == (nowCellWay.getY() - 1))) {

                if (Map.getObject(new Location((nowCellWay.getX() - 1), (nowCellWay.getY() - 1), 0)).getType() == MapObjectType.AIR
                        || (taskLocation.getX() == (nowCellWay.getX() - 1) && taskLocation.getY() == (nowCellWay.getY() - 1))) {
                    freeCell = true;
                    lastCellWay.setY(nowCellWay.getY());
                    lastCellWay.setX(nowCellWay.getX());
                    nowCellWay.setX((nowCellWay.getX() - 1));
                    nowCellWay.setY((nowCellWay.getY() - 1));
                }
            } else if ((nowCellWay.getX() + 1) < Map.getWightMap() && (nowCellWay.getY() + 1) < Map.getHeightMap()      // право низ
                    && mapWay[(nowCellWay.getX() + 1)][(nowCellWay.getY() + 1)] == -10
                    && !(lastCellWay.getX() == (nowCellWay.getX() + 1) && lastCellWay.getY() == (nowCellWay.getY() + 1))) {

                if (Map.getObject(new Location((nowCellWay.getX() + 1), (nowCellWay.getY() + 1), 0)).getType() == MapObjectType.AIR
                        || (taskLocation.getX() == (nowCellWay.getX() + 1) && taskLocation.getY() == (nowCellWay.getY() + 1))) {
                    freeCell = true;
                    lastCellWay.setY(nowCellWay.getY());
                    lastCellWay.setX(nowCellWay.getX());
                    nowCellWay.setX((nowCellWay.getX() + 1));
                    nowCellWay.setY((nowCellWay.getY() + 1));
                }
            } else if ((nowCellWay.getX() - 1) >= 0 && (nowCellWay.getY() + 1) < Map.getHeightMap()         // лево низ
                    && mapWay[(nowCellWay.getX() - 1)][(nowCellWay.getY() + 1)] == -10
                    && !(lastCellWay.getX() == (nowCellWay.getX() - 1) && lastCellWay.getY() == (nowCellWay.getY() + 1))) {
                if (Map.getObject(new Location((nowCellWay.getX() - 1), (nowCellWay.getY() + 1), 0)).getType() == MapObjectType.AIR
                        || (taskLocation.getX() == (nowCellWay.getX() - 1) && taskLocation.getY() == (nowCellWay.getY() + 1))) {
                    freeCell = true;
                    nowCellWay.setX((nowCellWay.getX() - 1));
                    nowCellWay.setY((nowCellWay.getY() + 1));
                }
            }

            if (!freeCell) {
                StageHero = TaskType.FIND_WAY;
                return;
            }


        }

    }

    // движение героя по карте
    private void MoveOnMap() {
        if ((((heroLocation.getX() - taskLocation.getX()) == 1 || (heroLocation.getX() - taskLocation.getX()) == -1) && ((heroLocation.getY() - taskLocation.getY()) == 0)) ||
                (((heroLocation.getY() - taskLocation.getY()) == 1 || (heroLocation.getY() - taskLocation.getY()) == -1) && ((heroLocation.getX() - taskLocation.getX()) == 0))) {
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            StageHero = TaskType.MINE;
            return;
        }

        if ((heroLocation.getX() - 1) >= 0 && mapWay[(heroLocation.getX() - 1)][heroLocation.getY()] == -10  // лево
                && Map.getObject(new Location((heroLocation.getX() - 1), heroLocation.getY(), 0)).getType() != MapObjectType.HERO) {
            Map.setObject(heroLocation, MapObjectType.AIR);
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            heroLocation.setX(heroLocation.getX() - 1);
            Map.setObject(heroLocation, MapObjectType.HERO);

        } else if ((heroLocation.getX() + 1) < Map.getWightMap() && mapWay[(heroLocation.getX() + 1)][heroLocation.getY()] == -10      // право
                && Map.getObject(new Location((heroLocation.getX() + 1), heroLocation.getY(), 0)).getType() != MapObjectType.HERO) {
            Map.setObject(heroLocation, MapObjectType.AIR);
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            heroLocation.setX(heroLocation.getX() + 1);
            Map.setObject(heroLocation, MapObjectType.HERO);

        } else if ((heroLocation.getY() - 1) >= 0 && mapWay[heroLocation.getX()][(heroLocation.getY() - 1)] == -10
                && Map.getObject(new Location(heroLocation.getX(), (heroLocation.getY() - 1), 0)).getType() != MapObjectType.HERO) { // верх
            Map.setObject(heroLocation, MapObjectType.AIR);
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            heroLocation.setY(heroLocation.getY() - 1);
            Map.setObject(heroLocation, MapObjectType.HERO);

        } else if ((heroLocation.getY() + 1) < Map.getHeightMap() && mapWay[heroLocation.getX()][(heroLocation.getY() + 1)] == -10         // низ
                && Map.getObject(new Location(heroLocation.getX(), (heroLocation.getY() + 1), 0)).getType() != MapObjectType.HERO) {
            Map.setObject(heroLocation, MapObjectType.AIR);
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            heroLocation.setY(heroLocation.getY() + 1);
            Map.setObject(heroLocation, MapObjectType.HERO);

        } else if ((heroLocation.getX() + 1) < Map.getWightMap() && (heroLocation.getY() - 1) >= 0 && mapWay[(heroLocation.getX() + 1)][heroLocation.getY() - 1] == -10
                && Map.getObject(new Location((heroLocation.getX() + 1), (heroLocation.getY() - 1), 0)).getType() != MapObjectType.HERO) {  // право верх
            Map.setObject(heroLocation, MapObjectType.AIR);
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            heroLocation.setX(heroLocation.getX() + 1);
            heroLocation.setY(heroLocation.getY() - 1);
            Map.setObject(heroLocation, MapObjectType.HERO);

        } else if ((heroLocation.getX() - 1) < Map.getWightMap() && (heroLocation.getY() - 1) >= 0 && mapWay[(heroLocation.getX() - 1)][heroLocation.getY() - 1] == -10
                && Map.getObject(new Location((heroLocation.getX() - 1), (heroLocation.getY() - 1), 0)).getType() != MapObjectType.HERO) {  // лево верх
            Map.setObject(heroLocation, MapObjectType.AIR);
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            heroLocation.setX(heroLocation.getX() - 1);
            heroLocation.setY(heroLocation.getY() - 1);
            Map.setObject(heroLocation, MapObjectType.HERO);

        } else if ((heroLocation.getX() + 1) < Map.getWightMap() && (heroLocation.getY() + 1) >= 0 && mapWay[(heroLocation.getX() + 1)][heroLocation.getY() + 1] == -10
                && Map.getObject(new Location((heroLocation.getX() + 1), (heroLocation.getY() + 1), 0)).getType() != MapObjectType.HERO) {  // право низ
            Map.setObject(heroLocation, MapObjectType.AIR);
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            heroLocation.setX(heroLocation.getX() + 1);
            heroLocation.setY(heroLocation.getY() + 1);
            Map.setObject(heroLocation, MapObjectType.HERO);
        } else if ((heroLocation.getX() - 1) < Map.getWightMap() && (heroLocation.getY() + 1) >= 0 && mapWay[(heroLocation.getX() - 1)][heroLocation.getY() + 1] == -10
                && Map.getObject(new Location((heroLocation.getX() - 1), (heroLocation.getY() + 1), 0)).getType() != MapObjectType.HERO) {      //лево низ
            Map.setObject(heroLocation, MapObjectType.AIR);
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            heroLocation.setX(heroLocation.getX() - 1);
            heroLocation.setY(heroLocation.getY() + 1);
            Map.setObject(heroLocation, MapObjectType.HERO);
        }

        StageHero = TaskType.FIND_WAY;
    }

    // очищает карту остовляя только выбранную цифру
    private void CleanMapWay(int numR) {
        for (int x = 0; x < Map.getWightMap(); x++) {
            for (int y = 0; y < Map.getHeightMap(); y++) {
                if (mapWay[x][y] != numR) {
                    mapWay[x][y] = 0;
                }
            }
        }

    }

    public void setTask(Location location, int numAction) {
        taskLocation = location;
        this.TaskNumAction = numAction;
        StageHero = TaskType.FIND_WAY;
    }

    // удаление задание
    public void removeTask() {
        taskLocation.setX(-1);
        taskLocation.setY(-1);
        TaskNumAction = 0;
        StageHero = TaskType.NONE;
        CleanMapAll();

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return heroLocation;
    }

    public int getTaskNumAction() {
        return TaskNumAction;
    }

    public void setLocation(Location location) {
        this.heroLocation = location;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

    public float getSpeedMove() {
        return speedMove;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getStageHero() {
        return StageHero;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Location getTaskLocation() {
        return taskLocation;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setSpeedMove(float speedMove) {
        this.speedMove = speedMove;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setStageHero(int stageHero) {
        StageHero = stageHero;
    }

    public void setTaskLocation(Location taskLocation) {
        this.taskLocation = taskLocation;
    }
}