import java.util.concurrent.TimeUnit;

/**
 * Timer class used for the game logic, methods in this class are called in order to base timings in the game.
 */
public class Timer {
    private long start;

    public Timer(){
        start = System.nanoTime();
    }

    public void restart(){
        start = System.nanoTime();
    }

    public long time(){
        return System.nanoTime() - start;
    }

    public long time(TimeUnit unit){
        return unit.convert(time(), TimeUnit.NANOSECONDS);
    }
}
