import org.jsfml.graphics.RenderWindow;

import java.util.Random;

public abstract class Events extends FSMState {
    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;
    private Random randGenerator;
    private EventGenerator eventGenerator;

    public Events(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, Random randGenerator, EventGenerator eventGenerator){
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;
        this.randGenerator = randGenerator;
        this.eventGenerator = eventGenerator;
    }

    public abstract void execute();
}
