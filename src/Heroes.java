import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Heroes {
    private int HeroesX;
    private int HeroesY;
    public int StageHeroes = 0;     // 0 - ничего не делает(инициализация) берет задачу,
    // 1 - искать путь,ожидает находа пути,
    // 2 - путь найдеn, ходить,
    // 4 - добывает,
    // 5 - получен ответ от TaskPlayers, начать обработку задания
    private int[][] mapWay;

    private int TaskX = 0;
    private int TaskY = 0;
    private int TaskNumAction = 0;      // 0 - задачи нет, 1 - mine

    private Graphics2D graphics;

    private int ID;

    Timer timerHero;

    public Heroes(int x, int y, int ID) {
        graphics = Engine.g;

        HeroesX = x;
        HeroesY = y;
        this.ID = ID;

        mapWay = new int[Map.WightMap][Map.HeightMap];
        Map.map[x][y] = 10;
        CleanMapAll();
        mapWay[HeroesX][HeroesY] = 0;

        timerHero = new Timer();
        update();
    }

    public void update() {

        //  System.out.println("ID:"+ID+" St"+StageHeroes);

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

    private void TakeTask() {
        // StageHeroes = 5;
        TaskPlayers.getTask(ID, HeroesX, HeroesY);
    }

    private void MineResource() {
        //System.out.println("Ress %"+Map.map[xRess][yRess]+" " + Map.mapStageRess[xRess][yRess]);

        if (Map.mapStageRess[TaskX][TaskY] <= 0) {
            if (Map.map[TaskX][TaskY] != 10) {
                Map.map[TaskX][TaskY] = 0;
                StageHeroes = 0;
            }
            TaskPlayers.RemoveTask(TaskX, TaskY);

            TaskX = 0;
            TaskY = 0;
            StageHeroes = 0;

        } else {
            Map.mapStageRess[TaskX][TaskY] -= 0.5;
        }
    }

    private void CleanMapAll() {
        for (int x = 0; x < Map.WightMap; x++) {
            for (int y = 0; y < Map.HeightMap; y++) {
                mapWay[x][y] = 0;
            }
        }
        mapWay[HeroesX][HeroesY] = 1;
    }

    private void MoveOnMap() {
        if ((((HeroesX - TaskX) == 1 || (HeroesX - TaskX) == -1) && ((HeroesY - TaskY) == 0)) ||
                (((HeroesY - TaskY) == 1 || (HeroesY - TaskY) == -1) && ((HeroesX - TaskX) == 0))) {

            StageHeroes = 4;
            return;
        }
        if ((HeroesX - 1) >= 0 && mapWay[HeroesX - 1][HeroesY] == -10 && Map.map[HeroesX - 1][HeroesY] != 10) {
            Map.map[HeroesX][HeroesY] = 0;
            Map.map[HeroesX - 1][HeroesY] = 10;
            HeroesX--;
            System.out.println("move");
        } else if ((HeroesX + 1) < Map.WightMap && mapWay[HeroesX + 1][HeroesY] == -10 && Map.map[HeroesX + 1][HeroesY] != 10) {
            Map.map[HeroesX][HeroesY] = 0;
            Map.map[HeroesX + 1][HeroesY] = 10;
            HeroesX++;
            System.out.println("move");
        } else if ((HeroesY - 1) >= 0 && mapWay[HeroesX][HeroesY - 1] == -10 && Map.map[HeroesX][HeroesY - 1] != 10) {
            Map.map[HeroesX][HeroesY] = 0;
            Map.map[HeroesX][HeroesY - 1] = 10;
            HeroesY--;
            System.out.println("move");
        } else if ((HeroesY + 1) < Map.HeightMap && mapWay[HeroesX][HeroesY + 1] == -10 && Map.map[HeroesX][HeroesY + 1] != 10) {
            Map.map[HeroesX][HeroesY] = 0;
            Map.map[HeroesX][HeroesY + 1] = 10;
            HeroesY++;
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
                    // graphics.drawLine((int)((Map.scale*x)+5+(Map.scale/2)),(int)((Map.scale*y)+5+(Map.scale/2)),);
                    graphics.fillRect((int) ((Map.scale * x) + Map.IndentX + 2), (int) ((Map.scale * y) + Map.IndentY + 2), (int) (Map.scale - 4), (int) (Map.scale - 4));
                }

            }
        }
    }

    // дать герою задния из списка
    public void setTask(int x, int y, int numAction) {
        TaskX = x;
        TaskY = y;
        this.TaskNumAction = numAction;
        System.out.println("Task:" + x + " " + y + " IdD:" + ID);
        StageHeroes = 5;
    }

    public void removeTask() {
        TaskX = 0;
        TaskY = 0;
        TaskNumAction = 0;
        StageHeroes = 0;
        CleanMapAll();
    }

    public int getTaskX() {
        return TaskX;
    }

    public int getTaskY() {
        return TaskY;
    }

    private void FindWay() {

        Thread threadFindWay = new Thread(new Runnable() {
            @Override
            public void run() {
                StageHeroes = 1;
                CleanMapAll();
                int inc = 2;
                mapWay[HeroesX][HeroesY] = inc;
                boolean boolWhile = true;
                boolean FindRout = false;

                exitWhile:
                while (boolWhile) {
                    boolean AliveTide = false;

                    for (int x = 0; x < Map.WightMap; x++) {
                        for (int y = 0; y < Map.HeightMap; y++) {
                            //System.out.println(taskAction.get(a)[0]+" - "+x+" "+taskAction.get(a)[1]+" - " + y + "  |"+mapWay[x][y] + " - " + inc);
                            if (x == TaskX && y == TaskY && (mapWay[x][y] == (inc - 1) || mapWay[x][y] == inc)) {
                                FindRout = true;
                                break exitWhile;
                            } else if (mapWay[x][y] == inc) {

                                if ((x + 1) < Map.WightMap && (Map.map[x + 1][y] == 0 || ((x + 1) == TaskX && y == TaskY)) && mapWay[x + 1][y] == 0) {
                                    mapWay[x + 1][y] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((x - 1) >= 0 && (Map.map[x - 1][y] == 0 || ((x - 1) == TaskX && y == TaskY)) && mapWay[x - 1][y] == 0) {
                                    mapWay[x - 1][y] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((y + 1) < Map.HeightMap && (Map.map[x][y + 1] == 0 || (x == TaskX && (y + 1) == TaskY)) && mapWay[x][y + 1] == 0) {
                                    mapWay[x][y + 1] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((y - 1) >= 0 && (Map.map[x][y - 1] == 0 || (x == TaskX && (y - 1) == TaskY)) && mapWay[x][y - 1] == 0) {
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

                    System.out.println("ID:" + ID + "  x:" + HeroesX + "  y:" + HeroesY + "  Long:" + (inc - 2));
                    int xWay = TaskX;
                    int yWay = TaskY;
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

            char RandomWay(int Mx, int My, int inc) {
                ArrayList<Character> AvailableMove = new ArrayList<>();
                Random random = new Random();

                if ((Mx - 1) >= 0 && mapWay[Mx - 1][My] == inc) {
                    AvailableMove.add('L');
                }
                if ((My - 1) >= 0 && mapWay[Mx][My - 1] == inc) {
                    AvailableMove.add('U');
                }
                if ((Mx + 1) < Map.WightMap && mapWay[Mx + 1][My] == inc) {
                    AvailableMove.add('R');
                }
                if ((My + 1) < Map.HeightMap && mapWay[Mx][My + 1] == inc) {
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
