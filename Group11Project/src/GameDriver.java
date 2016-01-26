/**
 * Driver for Endless Sea
 */

import org.jsfml.graphics.*;
import org.jsfml.window.*;
import org.jsfml.window.event.*;

public class GameDriver {
    private static final int WIN_WIDTH = 1280;
    private static final int WIN_HEIGHT = 720;
    private static final String TITLE = "Endless Sea";

    private FSM machine;
    private FSMState menu;
    private FSMState game;

    public void run(){
        // Initial setup
        RenderWindow window = new RenderWindow();
        window.create(new VideoMode(WIN_WIDTH, WIN_HEIGHT), TITLE, WindowStyle.DEFAULT);
        window.setFramerateLimit(30);

        machine = new FSM();
        menu = new Menu(window);
        game = new Game(window);

        // Set menu state for game launch
        machine.setState(menu);

        // Game loop
        while(window.isOpen()){
            window.clear(Color.WHITE);

            // Add to window relevant objects depending on state
            machine.run();

            // Update window with changes made
            window.display();

            // Handle events
            for(Event event : window.pollEvents()) {
                if(event.type == Event.Type.CLOSED)
                    window.close();
            }

        }
    }



    public static void main(String[] args) {
        GameDriver driver = new GameDriver();
        driver.run();
    }
}
