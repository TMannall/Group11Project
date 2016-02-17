package endlesssea.states;

import endlesssea.GameDriver;
import endlesssea.Textures;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;
import endlesssea.statemachine.FSM;
import endlesssea.statemachine.FSMState;

// State after an event has been successful. Map state should be invoked after this to move to next event.
public class SuccessState extends FSMState {
    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;

    public SuccessState(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures) {
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;

        System.out.println("SUCCESS!");
    }

    @Override
    public void execute() {
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
