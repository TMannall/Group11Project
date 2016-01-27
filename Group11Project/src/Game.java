import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;

/**
 * Game state class for Endless Sea
 */
public class Game extends FSMState {
    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;

    public Game(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures){
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;
    }

    @Override
    // Update this method with what should be added to the window (using window variable)
    public void execute() {

        window.display();

        for(Event event : window.pollEvents()) {
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
            }
        }
    }
}
