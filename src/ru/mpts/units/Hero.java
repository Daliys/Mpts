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
        System.out.println("move speed===" + speedMove);
        taskLocation = new Location(0, 0, 0);
        TaskNumAction = 0;

        mapWay = new int[Map.getWightMap()][Map.getHeightMap()];

        Map.addObject(InitHeroLocation, MapObjectType.HERO);
        graphics = Engine.graphics2D;

        timerHero = new Timer();
    }

    public void render() {
        for (int x = 0; x < Map.getWightMap(); x++) {
            for (int y = 0; y < Map.getHeightMap(); y++) {
                if (mapWay[x][y] == -10) {
                    graphics.setColor(new Color(0x267775));
                    // graphics.drawLine((int)((ru.mpts.map.Map.scale*x)+5+(ru.mpts.map.Map.scale/2)),(int)((ru.mpts.map.Map.scale*y)+5+(ru.mpts.map.Map.scale/2)),);
                    graphics.fillRect((int) ((Map.getScale() * x) + Map.getIndentX() + 2), (int) ((Map.getScale() * y) + Map.getIndentY() + 2), (int) (Map.getScale() - 4), (int) (Map.getScale() - 4));

                   /* graphics.setColor(new Color(0xAD0001));
                    // graphics.setStroke(mapWay[x][y]);
                    graphics.drawString(mapWay[x][y] + "", (int) ((Map.getScale() * x) + Map.getIndentX() + 2 + 5 + 2), (int) ((Map.getScale() * y) + Map.getIndentY() + 2 + 15));
             */
                }
            }
        }
    }

    private void TakeTask() {
        TaskPlayers.getTask(id, heroLocation);
    }

    public void update() {

        //  System.out.println("id:"+id+" St"+StageHero);

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
        }
    }

    private void MineResource() {
        if (Map.getObject(taskLocation).getDurability() <= 0) {
            if (Map.getObject(taskLocation).getType() == MapObjectType.IRON_ORE) {
                Map.addObject(taskLocation, MapObjectType.AIR);
                StageHero = TaskType.NONE;
            }
            TaskPlayers.RemoveTask(taskLocation);

            taskLocation.setX(0);
            taskLocation.setY(0);
            StageHero = TaskType.NONE;
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


    private void MoveOnMap() {
        if ((((heroLocation.getX() - taskLocation.getX()) == 1 || (heroLocation.getX() - taskLocation.getX()) == -1) && ((heroLocation.getY() - taskLocation.getY()) == 0)) ||
                (((heroLocation.getY() - taskLocation.getY()) == 1 || (heroLocation.getY() - taskLocation.getY()) == -1) && ((heroLocation.getX() - taskLocation.getX()) == 0))) {
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            StageHero = TaskType.MINE;
            return;
        }

        if ((heroLocation.getX() - 1) >= 0 && mapWay[(heroLocation.getX() - 1)][heroLocation.getY()] == -10  // лево
                && Map.getObject(new Location((heroLocation.getX() - 1), heroLocation.getY(), 0)).getType() != MapObjectType.HERO) {
            Map.addObject(heroLocation, MapObjectType.AIR);
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            heroLocation.setX(heroLocation.getX() - 1);
            Map.addObject(heroLocation, MapObjectType.HERO);

        } else if ((heroLocation.getX() + 1) < Map.getWightMap() && mapWay[(heroLocation.getX() + 1)][heroLocation.getY()] == -10      // право
                && Map.getObject(new Location((heroLocation.getX() + 1), heroLocation.getY(), 0)).getType() != MapObjectType.HERO) {
            Map.addObject(heroLocation, MapObjectType.AIR);
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            heroLocation.setX(heroLocation.getX() + 1);
            Map.addObject(heroLocation, MapObjectType.HERO);

        } else if ((heroLocation.getY() - 1) >= 0 && mapWay[heroLocation.getX()][(heroLocation.getY() - 1)] == -10
                && Map.getObject(new Location(heroLocation.getX(), (heroLocation.getY() - 1), 0)).getType() != MapObjectType.HERO) { // верх
            Map.addObject(heroLocation, MapObjectType.AIR);
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            heroLocation.setY(heroLocation.getY() - 1);
            Map.addObject(heroLocation, MapObjectType.HERO);

        } else if ((heroLocation.getY() + 1) < Map.getHeightMap() && mapWay[heroLocation.getX()][(heroLocation.getY() + 1)] == -10         // низ
                && Map.getObject(new Location(heroLocation.getX(), (heroLocation.getY() + 1), 0)).getType() != MapObjectType.HERO) {
            Map.addObject(heroLocation, MapObjectType.AIR);
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            heroLocation.setY(heroLocation.getY() + 1);
            Map.addObject(heroLocation, MapObjectType.HERO);

        } else if ((heroLocation.getX() + 1) < Map.getWightMap() && (heroLocation.getY() - 1) >= 0 && mapWay[(heroLocation.getX() + 1)][heroLocation.getY() - 1] == -10
                && Map.getObject(new Location((heroLocation.getX() + 1), (heroLocation.getY() - 1), 0)).getType() != MapObjectType.HERO) {  // право верх
            Map.addObject(heroLocation, MapObjectType.AIR);
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            heroLocation.setX(heroLocation.getX() + 1);
            heroLocation.setY(heroLocation.getY() - 1);
            Map.addObject(heroLocation, MapObjectType.HERO);

        } else if ((heroLocation.getX() - 1) < Map.getWightMap() && (heroLocation.getY() - 1) >= 0 && mapWay[(heroLocation.getX() - 1)][heroLocation.getY() - 1] == -10
                && Map.getObject(new Location((heroLocation.getX() - 1), (heroLocation.getY() - 1), 0)).getType() != MapObjectType.HERO) {  // лево верх
            Map.addObject(heroLocation, MapObjectType.AIR);
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            heroLocation.setX(heroLocation.getX() - 1);
            heroLocation.setY(heroLocation.getY() - 1);
            Map.addObject(heroLocation, MapObjectType.HERO);

        } else if ((heroLocation.getX() + 1) < Map.getWightMap() && (heroLocation.getY() + 1) >= 0 && mapWay[(heroLocation.getX() + 1)][heroLocation.getY() + 1] == -10
                && Map.getObject(new Location((heroLocation.getX() + 1), (heroLocation.getY() + 1), 0)).getType() != MapObjectType.HERO) {  // право низ
            Map.addObject(heroLocation, MapObjectType.AIR);
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            heroLocation.setX(heroLocation.getX() + 1);
            heroLocation.setY(heroLocation.getY() + 1);
            Map.addObject(heroLocation, MapObjectType.HERO);
        } else if ((heroLocation.getX() - 1) < Map.getWightMap() && (heroLocation.getY() + 1) >= 0 && mapWay[(heroLocation.getX() - 1)][heroLocation.getY() + 1] == -10
                && Map.getObject(new Location((heroLocation.getX() - 1), (heroLocation.getY() + 1), 0)).getType() != MapObjectType.HERO) {      //лево низ
            Map.addObject(heroLocation, MapObjectType.AIR);
            mapWay[heroLocation.getX()][heroLocation.getY()] = 0;
            heroLocation.setX(heroLocation.getX() - 1);
            heroLocation.setY(heroLocation.getY() + 1);
            Map.addObject(heroLocation, MapObjectType.HERO);
        }

            StageHero = TaskType.MOVE;
        }

     /*  private void MoveOnMap() {        /// old version
         if ((((heroLocation.getX() - taskLocation.getX()) == 1 || (heroLocation.getX() - taskLocation.getX()) == -1) && ((heroLocation.getY() - taskLocation.getY()) == 0)) ||
                 (((heroLocation.getY() - taskLocation.getY()) == 1 || (heroLocation.getY() - taskLocation.getY()) == -1) && ((heroLocation.getX() - taskLocation.getX()) == 0))) {

             StageHero = TaskType.MINE;
             return;
         }
         if ((location.getX() - 1) >= 0 && mapWay[location.getX() - 1][location.getY()] == -10 && Map.getObject(new Location(location.getX()-1, location.getY(), 0)).getType() != MapObjectType.HERO) {
             Map.addObject(location, MapObjectType.AIR);
             location.setX(location.getX()-1);
             Map.addObject(location, MapObjectType.HERO);
             System.out.println("move");
         } else if ((location.getX() + 1) < Map.getWightMap() && mapWay[location.getX() + 1][location.getY()] == -10 && Map.getObject(new Location(location.getX()+1, location.getY(), 0)).getType() != MapObjectType.HERO) {
             Map.addObject(location, MapObjectType.AIR);
             location.setX(location.getX()+1);
             Map.addObject(location, MapObjectType.HERO);
             System.out.println("move");
         } else if ((location.getY() - 1) >= 0 && mapWay[location.getX()][location.getY() - 1] == -10 && Map.getObject(new Location(location.getX(), location.getY()-1, 0)).getType() != MapObjectType.HERO) {
             Map.addObject(location, MapObjectType.AIR);
             location.setY(location.getY()-1);
             Map.addObject(location, MapObjectType.HERO);
             System.out.println("move");
         } else if ((location.getY() + 1) < Map.getHeightMap() && mapWay[location.getX()][location.getY() + 1] == -10 && Map.getObject(new Location(location.getX(), location.getY()+1, 0)).getType() != MapObjectType.HERO) {
             Map.addObject(location, MapObjectType.AIR);
             location.setY(location.getY()+1);
             Map.addObject(location, MapObjectType.HERO);
             System.out.println("move");
         }
         StageHero = TaskType.FIND_WAY;
     }*/

    private void CleanMapWay(int numR) {
        for (int x = 0; x < Map.getWightMap(); x++) {
            for (int y = 0; y < Map.getHeightMap(); y++) {
                if (mapWay[x][y] != numR) {
                    mapWay[x][y] = 0;
                }
            }
        }

    }/*private void CleanMapWay(int numR) {         //OLD Versions
        for (int x = 0; x < Map.getWightMap(); x++) {
            for (int y = 0; y < Map.getHeightMap(); y++) {
                if (mapWay[x][y] != numR) {
                    mapWay[x][y] = 0;
                }
            }
        }

    }*/

    public void setTask(Location location, int numAction) {
        taskLocation = location;
        this.TaskNumAction = numAction;
        System.out.println("Task:" + taskLocation.getX() + " " + taskLocation.getY() + " idD:" + id);
        StageHero = TaskType.FIND_WAY;
    }

    public void removeTask() {
        taskLocation.setX(0);
        taskLocation.setY(0);
        TaskNumAction = 0;
        StageHero = TaskType.NONE;
        CleanMapAll();

    }

 /*   private void FindWay() {

        Thread threadFindWay = new Thread(new Runnable() {
            @Override
            public void run() {
                StageHero = TaskType.WAIT_FIND_WAY;
                CleanMapAll();
                int inc = 2;
                mapWay[heroLocation.getX()][heroLocation.getY()] = inc;
                boolean boolWhile = true;
                boolean FindRout = false;

                exitWhile:
                while (boolWhile) {
                    boolean AliveTide = false;

                    for (int x = 0; x < Map.getWightMap(); x++) {
                        for (int y = 0; y < Map.getHeightMap(); y++) {
                            //System.out.println(taskAction.get(a)[0]+" - "+x+" "+taskAction.get(a)[1]+" - " + y + "  |"+mapWay[x][y] + " - " + inc);
                            if (x == taskLocation.getX() && y == taskLocation.getY() && (mapWay[x][y] == (inc - 1) || mapWay[x][y] == inc)) {
                                FindRout = true;
                                break exitWhile;
                            } else if (mapWay[x][y] == inc) {

                                if ((x + 1) < Map.getWightMap() && (Map.getObject(new Location(x+1, y, 0)).getType() == MapObjectType.AIR || ((x + 1) == taskLocation.getX() && y == taskLocation.getY())) && mapWay[x + 1][y] == 0) {
                                    mapWay[x + 1][y] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((x - 1) >= 0 && (Map.getObject(new Location(x-1, y, 0)).getType() == MapObjectType.AIR || ((x - 1) == taskLocation.getX() && y == taskLocation.getY())) && mapWay[x - 1][y] == 0) {
                                    mapWay[x - 1][y] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((y + 1) < Map.getHeightMap() && (Map.getObject(new Location(x, y+1, 0)).getType() == MapObjectType.AIR || (x == taskLocation.getX() && (y + 1) == taskLocation.getY())) && mapWay[x][y + 1] == 0) {
                                    mapWay[x][y + 1] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((y - 1) >= 0 && (Map.getObject(new Location(x, y-1, 0)).getType() == MapObjectType.AIR || (x == taskLocation.getX() && (y - 1) == taskLocation.getY())) && mapWay[x][y - 1] == 0) {
                                    mapWay[x][y - 1] = (inc + 1);
                                    AliveTide = true;
                                }
                            }
                        }
                    }

                    if (!AliveTide) {
                        boolWhile = false;
                        FindRout = false;
                        StageHero = TaskType.NONE;
                        // дописать удаления из списка и из героя
                    }
                    inc++;
                }

                if (FindRout) {

                    System.out.println("id:" + id + "  x:" + heroLocation.getX() + "  y:" + heroLocation.getY() + "  Long:" + (inc - 2));
                    int xWay = taskLocation.getX();
                    int yWay = taskLocation.getY();
                    inc = mapWay[xWay][yWay];
                    mapWay[xWay][yWay] = -10;

                    while (inc > 3) {
                        inc--;
                        char rand = RandomWay(xWay, yWay, inc);
                        if (rand == 'L') {
                            xWay--;
                        } else if (rand == 'R') {
                            xWay++;
                        } else if (rand == 'U') {
                            yWay--;
                        } else if (rand == 'D') {
                            yWay++;
                        }
                        mapWay[xWay][yWay] = -10;
                    }
                    CleanMapWay(-10);
                } else {
                    StageHero = TaskType.NONE;
                }
                StageHero = TaskType.MOVE;
            }

            char RandomWay(int mX, int mY, int inc) {
                ArrayList<Character> AvailableMove = new ArrayList<>();
                Random random = new Random();

                if ((mX - 1) >= 0 && mapWay[mX - 1][mY] == inc) {
                    AvailableMove.add('L');
                }
                if ((mY - 1) >= 0 && mapWay[mX][mY - 1] == inc) {
                    AvailableMove.add('U');
                }
                if ((mX + 1) < Map.getWightMap() && mapWay[mX + 1][mY] == inc) {
                    AvailableMove.add('R');
                }
                if ((mY + 1) < Map.getHeightMap() && mapWay[mX][mY + 1] == inc) {
                    AvailableMove.add('D');
                }
                if (AvailableMove.size() == 0) {
                    return 'N';
                }

                return AvailableMove.get(random.nextInt(AvailableMove.size()));
            }
        });
        threadFindWay.start();

    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return heroLocation;
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