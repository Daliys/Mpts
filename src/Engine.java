import java.awt.*;

public class Engine implements Runnable {
    private boolean startGame;
    private float delta;
    private static long nanosec = 1000000000;
    private static float UpdateInterval = nanosec /60.0f; // кол во обновления game
    private int fps;
    private int upd;
    private int updSkip;            // считает количество пропусков update
    public static Graphics2D g;
    private int witx = 0;       // для круга
    private int wity = 0;
    private int addx = 1;
    private int addy = 1;

    Map map;
    Units units;

    public Engine(){
        Display.CreateBuffer(0xff000000,2);
        g = Display.getGraphics();

        map = new Map();
        units = new Units();

        Thread thread = new Thread(this,"Engine");
        thread.start();
    }

    public void StartGame(){startGame = true;}
    public void PauseGame(){startGame = false;}

    Timer timer = new Timer(30);

    public void render(){
        Display.clear();



        map.render();
        units.render();

        TaskPlayers.render();


        g.fillOval(witx,wity,50,50);


        Display.swapBuffer();
        if(timer.getTime())
            KeyActionListener.render();
    }

    public void update(){
        if(wity > 550 || wity < 0 ){
            addy *=-1;
        }
        if(witx > 550 || witx < 0 ){
            addx *=-1;
        }
        witx += addx;
        wity += addy;


        map.update();
        units.update();

    }

    @Override
    public void run(){

        long lastTime = System.nanoTime();
        long coutSec = 0;

        while (true) {      //Поток работает до конца завершения программы

            while (startGame) {     //  служит что бы остановить(Пауза), возобновить игру (главный цикл игры)
                //видео 6  (46.30)
                long now = System.nanoTime();
                long elapsedTime = now - lastTime;
                lastTime = now;
                coutSec += elapsedTime;
                boolean render = false;
                delta += (elapsedTime / UpdateInterval);
                while (delta >1){

                    update();
                    upd++;
                    delta--;
                    if(render){
                        updSkip++;
                    }else {
                        render = true;
                    }
                }
                if(render){
                    render();
                    fps++;
                }else {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if(coutSec >= nanosec){
                    coutSec = 0;
                    Display.setTitle("FPS:" + fps + "  UPD:" + upd + "  SKIP:"+updSkip);
                    fps = 0;
                    upd = 0;
                    updSkip = 0;
                }


            }


            try{    //проверка снята ли игра с паузы
                Thread.sleep(300);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
