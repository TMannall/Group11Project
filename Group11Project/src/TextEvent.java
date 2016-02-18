import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;

import java.util.Random;

public class TextEvent extends Events {

    public TextEvent(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures, Random randGenerator, EventGenerator eventGenerator){
        super(stateMachine, driver, window, textures, randGenerator, eventGenerator);
    }

    public void execute(){
        window.clear(Color.WHITE);
        window.display();
        System.out.println("Executing text event!");

        for(Event event : window.pollEvents()){
            switch (event.type) {
                case CLOSED:
                    window.close();
            }
        }
    }
}