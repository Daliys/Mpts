package ru.mpts.units;

import ru.mpts.engine.Engine;
import ru.mpts.map.Location;
import ru.mpts.map.Map;
import ru.mpts.timers.Timer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Heroes {
    private Location location;
    public int StageHeroes = TaskHeroType.NONE;
    private int[][] mapWay;

    private Location taskLocation;
    private int TaskNumAction = 0;      // 0 - задачи нет, 1 - mine

    private Graphics2D graphics;

    private int id;

    Timer timerHero;

    public Heroes(Location location, int id) {
        graphics = Engine.g;
        this.location = location;
        
        this.id = id;

        mapWay = new int[Map.WightMap][Map.HeightMap];
        Map.map[location.getX()][location.getY()] = 10;
        CleanMapAll();
        mapWay[location.getX()][location.getY()] = 0;

        timerHero = new Timer();
        update();
    }

    public void update() {

        //  System.out.println("id:"+id+" St"+StageHeroes);

        if (StageHeroes == 2) {      // движится только тогда когда нашел путь
            if (timerHero.getTimeHeroes()) {
                MoveOnMap();
            }
        } else if (StageHeroes == 4) {
            CleanMapAll();
            MineResource();
        } else if (StageHeroes == 0) {
            if (timerHero.getTimeHeroes()) {
                TakeTask();
            }
        } else if (StageHeroes == 5) {
            if (timerHero.getTimeHeroes()) {
                FindWay();
                StageHeroes = 5;
            }
        }
    }

    public Location getLocation() {
        return location;
    }

    private void TakeTask() {
        // StageHeroes = 5;
        TaskPlayers.getTask(id, location);
    }

    private void MineResource() {
        //System.out.println("Ress %"+ru.mpts.map.Map.map[xRess][yRess]+" " + ru.mpts.map.Map.mapStageRess[xRess][yRess]);

        if (Map.mapStageRess[taskLocation.getX()][taskLocation.getY()] <= 0) {
            if (Map.map[taskLocation.getX()][taskLocation.getY()] != 10) {
                Map.map[taskLocation.getX()][taskLocation.getY()] = 0;
                StageHeroes = 0;
            }
            TaskPlayers.RemoveTask(taskLocation);

            taskLocation.setX(0);
            taskLocation.setY(0);
            StageHeroes = 0;

        } else {
            Map.mapStageRess[taskLocation.getX()][taskLocation.getY()] -= 0.5;
        }
    }

    private void CleanMapAll() {
        for (int x = 0; x < Map.WightMap; x++) {
            for (int y = 0; y < Map.HeightMap; y++) {
                mapWay[x][y] = 0;
            }
        }
        mapWay[location.getX()][location.getY()] = 1;
    }

    private void MoveOnMap() {
        if ((((location.getX() - taskLocation.getX()) == 1 || (location.getX() - taskLocation.getX()) == -1) && ((location.getY() - taskLocation.getY()) == 0)) ||
                (((location.getY() - taskLocation.getY()) == 1 || (location.getY() - taskLocation.getY()) == -1) && ((location.getX() - taskLocation.getX()) == 0))) {

            StageHeroes = 4;
            return;
        }
        if ((location.getX() - 1) >= 0 && mapWay[location.getX() - 1][location.getY()] == -10 && Map.map[location.getX() - 1][location.getY()] != 10) {
            Map.map[location.getX()][location.getY()] = 0;
            Map.map[location.getX() - 1][location.getY()] = 10;
            location.setX(location.getX()-1);
            System.out.println("move");
        } else if ((location.getX() + 1) < Map.WightMap && mapWay[location.getX() + 1][location.getY()] == -10 && Map.map[location.getX() + 1][location.getY()] != 10) {
            Map.map[location.getX()][location.getY()] = 0;
            Map.map[location.getX() + 1][location.getY()] = 10;
            location.setX(location.getX()+1);
            System.out.println("move");
        } else if ((location.getY() - 1) >= 0 && mapWay[location.getX()][location.getY() - 1] == -10 && Map.map[location.getX()][location.getY() - 1] != 10) {
            Map.map[location.getX()][location.getY()] = 0;
            Map.map[location.getX()][location.getY() - 1] = 10;
            location.setY(location.getY()-1);
            System.out.println("move");
        } else if ((location.getY() + 1) < Map.HeightMap && mapWay[location.getX()][location.getY() + 1] == -10 && Map.map[location.getX()][location.getY() + 1] != 10) {
            Map.map[location.getX()][location.getY()] = 0;
            Map.map[location.getX()][location.getY() + 1] = 10;
            location.setY(location.getY()+1);
            System.out.println("move");
        }
        StageHeroes = 5;
    }

    private void CleanMapWay(int numR) {
        for (int x = 0; x < Map.WightMap; x++) {
            for (int y = 0; y < Map.HeightMap; y++) {
                if (mapWay[x][y] != numR) {
                    mapWay[x][y] = 0;
                }
            }
        }

    }

    public void render() {
        for (int x = 0; x < Map.WightMap; x++) {
            for (int y = 0; y < Map.HeightMap; y++) {
                if (mapWay[x][y] == -10) {
                    graphics.setColor(new Color(0x267775));
                    // graphics.drawLine((int)((ru.mpts.map.Map.scale*x)+5+(ru.mpts.map.Map.scale/2)),(int)((ru.mpts.map.Map.scale*y)+5+(ru.mpts.map.Map.scale/2)),);
                    graphics.fillRect((int) ((Map.scale * x) + Map.IndentX + 2), (int) ((Map.scale * y) + Map.IndentY + 2), (int) (Map.scale - 4), (int) (Map.scale - 4));
                }

            }
        }
    }

    // дать герою задния из списка
    public void setTask(Location location, int numAction) {
        taskLocation = location;
        this.TaskNumAction = numAction;
        System.out.println("Task:" + location.getX() + " " + location.getY() + " idD:" + id);
        StageHeroes = 5;
    }

    public void removeTask() {
        taskLocation.setX(0);
        taskLocation.setY(0);
        TaskNumAction = 0;
        StageHeroes = 0;
        CleanMapAll();
    }

    public Location getTaskLocation() {
        return taskLocation;
    }

    public int getId() {
        return id;
    }

    private void FindWay() {

        Thread threadFindWay = new Thread(new Runnable() {
            @Override
            public void run() {
                StageHeroes = 1;
                CleanMapAll();
                int inc = 2;
                mapWay[location.getX()][location.getY()] = inc;
                boolean boolWhile = true;
                boolean FindRout = false;

                exitWhile:
                while (boolWhile) {
                    boolean AliveTide = false;

                    for (int x = 0; x < Map.WightMap; x++) {
                        for (int y = 0; y < Map.HeightMap; y++) {
                            //System.out.println(taskAction.get(a)[0]+" - "+x+" "+taskAction.get(a)[1]+" - " + y + "  |"+mapWay[x][y] + " - " + inc);
                            if (x == taskLocation.getX() && y ==taskLocation.getY() && (mapWay[x][y] == (inc - 1) || mapWay[x][y] == inc)) {
                                FindRout = true;
                                break exitWhile;
                            } else if (mapWay[x][y] == inc) {

                                if ((x + 1) < Map.WightMap && (Map.map[x + 1][y] == 0 || ((x + 1) == taskLocation.getX() && y == taskLocation.getY())) && mapWay[x + 1][y] == 0) {
                                    mapWay[x + 1][y] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((x - 1) >= 0 && (Map.map[x - 1][y] == 0 || ((x - 1) == taskLocation.getX() && y == taskLocation.getY())) && mapWay[x - 1][y] == 0) {
                                    mapWay[x - 1][y] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((y + 1) < Map.HeightMap && (Map.map[x][y + 1] == 0 || (x == taskLocation.getX() && (y + 1) == taskLocation.getY())) && mapWay[x][y + 1] == 0) {
                                    mapWay[x][y + 1] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((y - 1) >= 0 && (Map.map[x][y - 1] == 0 || (x == taskLocation.getX() && (y - 1) == taskLocation.getY())) && mapWay[x][y - 1] == 0) {
                                    mapWay[x][y - 1] = (inc + 1);
                                    AliveTide = true;
                                }
                            }
                        }
                    }

                    if (!AliveTide) {
                        boolWhile = false;
                        FindRout = false;
                        StageHeroes = 0;
                    }
                    inc++;
                }

                if (FindRout) {

                    System.out.println("id:" + id + "  x:" + location.getX() + "  y:" + location.getY() + "  Long:" + (inc - 2));
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
                    StageHeroes = 0;
                }
                StageHeroes = 2;
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
                if ((mX + 1) < Map.WightMap && mapWay[mX + 1][mY] == inc) {
                    AvailableMove.add('R');
                }
                if ((mY + 1) < Map.HeightMap && mapWay[mX][mY + 1] == inc) {
                    AvailableMove.add('D');
                }
                if (AvailableMove.size() == 0) {
                    return 'N';
                }

                return AvailableMove.get(random.nextInt(AvailableMove.size()));
            }
        });
        threadFindWay.start();

    }


}
