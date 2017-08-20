public class Timer {
    public  long lastTimeHeroes;
    private int interval;

    public Timer(int interval){
        lastTimeHeroes = System.currentTimeMillis();
        this.interval = interval;
    }
    public Timer(){
        lastTimeHeroes = System.currentTimeMillis();

    }

    public  boolean getTimeHeroes(){
        if(System.currentTimeMillis() - lastTimeHeroes >= 500){
            lastTimeHeroes = System.currentTimeMillis();
            return true;
        }
        return false;
    }
    public boolean getTime(){
        if(System.currentTimeMillis() - lastTimeHeroes >= interval){
            lastTimeHeroes = System.currentTimeMillis();
            return true;
        }
        return false;
    }

}
