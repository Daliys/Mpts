import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class TaskPlayers {



             // x , y , numAction(1-mine) , The task is taken by the hero?(0/1);*
    public static volatile List<int[]> taskAction = new ArrayList<int[]>();

    public static void AddTask(int x, int y, String action){
        if(CheckListTask(x, y)){     // проверка на то ести ли в задния эта задача(х,у),если есть то удалить ее
            return;
        }
        int[] Action = new int[4];

        if(action.equals("mine")){
            Action[2] = 1;
        }else{
            return;
        }
        Action[0] = x;
        Action[1] = y;
        Action[3] = 0;
        taskAction.add(Action);
        Display.MenuTextTask.setText("Task: " + taskAction.size());
    }

        // проверят на наличие задания, если в этой клетке нет задания то добавить, иначе удалить это задание
    private static boolean CheckListTask(int x, int y){
        for(int i = 0; i < taskAction.size(); i++){
            if(taskAction.get(i)[0] == x && taskAction.get(i)[1] == y){
                if(taskAction.get(i)[3] == 0) {
                    taskAction.remove(i);
                }else if(taskAction.get(i)[3] == 1){
                    Units.removeHeroesTask(x, y);
                    taskAction.remove(i);
                }
                return true;
            }
        }
        return false;
    }

    // удали задание из списка заданий  и из задания героя
    public static void RemoveTask(int x , int y){
        for(int a = 0; a < taskAction.size(); a++){
            if(taskAction.get(a)[0] == x && taskAction.get(a)[1] == y){
                taskAction.remove(a);
                Display.MenuTextTask.setText("Task: " + taskAction.size());
            }
        }
    }
    private static boolean ThreadTask= true;

        // герой запрашивает задание. Здесь проверяется задания может ли герой до него дойти. Если может то дать задание иначе игнор
    public static synchronized void getTask(int ID, int HeroesX, int HeroesY){
        if (ThreadTask){
            ThreadTask = false;
        }else {
            return;
        }
        Thread FindWays = new Thread(() -> {

            for(int a = 0; a < taskAction.size(); a++) {
                if(taskAction.get(a)[3] == 1){
                    continue;
                }

                int ThreadMapWay[][] = new int[Map.WightMap][Map.HeightMap];
                int inc = 2;
                ThreadMapWay[HeroesX][HeroesY] = inc;
                boolean boolWhile = true;
                boolean FindRout = false;

                exitWhile:
                while (boolWhile) {
                    boolean AliveTide = false;

                    for (int x = 0; x < Map.WightMap; x++) {
                        for (int y = 0; y < Map.HeightMap; y++) {
                            //System.out.println(taskAction.get(a)[0]+" - "+x+" "+taskAction.get(a)[1]+" - " + y + "  |"+mapWay[x][y] + " - " + inc);
                            if (x == taskAction.get(a)[0] && y == taskAction.get(a)[1] && (ThreadMapWay[x][y] == (inc - 1) || ThreadMapWay[x][y] == inc)) {
                                FindRout = true;
                              //  System.out.println(FindRout);
                                break exitWhile;
                            } else if (ThreadMapWay[x][y] == inc) {

                                if ((x + 1) < Map.WightMap && (Map.map[x + 1][y] == 0 || ((x+1) == taskAction.get(a)[0] && y == taskAction.get(a)[1])) && ThreadMapWay[x + 1][y] == 0) {
                                    ThreadMapWay[x + 1][y] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((x - 1) >= 0 && (Map.map[x - 1][y] == 0 || ((x-1) == taskAction.get(a)[0] && y == taskAction.get(a)[1])) && ThreadMapWay[x - 1][y] == 0) {
                                    ThreadMapWay[x - 1][y] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((y + 1) < Map.HeightMap && (Map.map[x][y + 1] == 0 || (x == taskAction.get(a)[0] && (y+1) == taskAction.get(a)[1])) && ThreadMapWay[x][y + 1] == 0) {
                                    ThreadMapWay[x][y + 1] = (inc + 1);
                                    AliveTide = true;
                                }
                                if ((y - 1) >= 0 && (Map.map[x][y - 1] == 0 || (x == taskAction.get(a)[0] && (y-1) == taskAction.get(a)[1])) && ThreadMapWay[x][y - 1] == 0) {
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
                        if(taskAction.get(a)[3] == 0) {
                            Units.setHeroesTask(taskAction.get(a)[0], taskAction.get(a)[1], taskAction.get(a)[2], ID);
                            taskAction.get(a)[3] = 1;
                        }
                        ThreadTask = true;

                        return;
                    }

            }

            ThreadTask = true;


        });
        FindWays.start();

    }


    public static void render(){
        for(int i = 0; i < taskAction.size(); i++){
            if(taskAction.get(i)[3] == 0){
                Engine.g.setColor(new Color(0xAC5800));
            }else{
                Engine.g.setColor(new Color(0x00A8BB));
                Engine.g.fillRect((int)(taskAction.get(i)[0] * Map.scale + Map.IndentX +2),(int)(taskAction.get(i)[1] * Map.scale + Map.IndentY + 2),
                        (int)(Map.scale-5),(int)(Map.scale-5));
                Engine.g.setColor(new Color(0xAF0063));
            }
            Engine.g.drawRect((int)(taskAction.get(i)[0] * Map.scale + Map.IndentX +2  ),(int)(taskAction.get(i)[1] * Map.scale + Map.IndentY+2),
                    (int)(Map.scale-5),(int)(Map.scale-5));

        }
    }

}