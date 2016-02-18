import org.jsfml.graphics.RenderWindow;
import java.util.Random;

public class Combat extends Events {

    public Combat(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, Random randGenerator, EventGenerator eventGenerator){
        super(stateMachine, driver, window, textures, randGenerator, eventGenerator);
    }

    public void execute(){

    }
}
