import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;
import statemachine.FSM;
import statemachine.FSMState;

public class GameOver extends FSMState {
    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;

    public GameOver(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures) {
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;
    }

    @Override
    public void execute() {
        System.out.println("GAME OVER!");

        System.exit(0);

        window.display();

        for(Event event : window.pollEvents()){
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
            }
        }
    }
}
