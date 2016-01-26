import org.jsfml.graphics.RenderWindow;

/**
 * Game state class for Endless Sea
 */
public class Game implements FSMState {
    RenderWindow window;

    public Game(RenderWindow window){
        this.window = window;
    }

    @Override
    // Update this method with what should be added to the window (using window variable)
    public void execute() {
        System.out.println("Game executing!");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
