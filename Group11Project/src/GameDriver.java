/**
 * Driver for Endless Sea
 */

import org.jsfml.graphics.*;
import org.jsfml.system.Clock;
import org.jsfml.window.*;
import org.jsfml.window.event.*;

import java.util.Random;
import java.util.ArrayList;

public class GameDriver {
    public static int getWinWidth() {
        return WIN_WIDTH;
    }

    public static int getWinHeight() {
        return WIN_HEIGHT;
    }
    private GameDriver driver = this;
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

        Textures textures = new Textures();
        machine = new FSM();
        menu = new Menu(machine, driver, window, textures);
        game = new Game(machine, driver, window, textures);

        // Add all states the FSM controls to its ArrayList for access later
        machine.getStates().add(menu);
        machine.getStates().add(game);

        // Set menu state for game launch
        machine.setState(menu);


        // jack: testing frames
       // int frame = new Random().nextInt(40);
       // int frame2 = new Random().nextInt(40);
       // int frame3 = new Random().nextInt(40);
       // int frame4 = new Random().nextInt(40);

       // Clock animClock = new Clock();
        // jack: frame test end


        // Game loop
        while(window.isOpen()){
            window.clear(Color.WHITE);

            // Add to window relevant objects depending on state
            machine.run();

            // jack: frame test
//            if(animClock.getElapsedTime().asMicroseconds() >= 50) {
//                animClock.restart();
//                frame++;
//                frame2++;
//                frame3++;
//                frame4++;
//
//                if(frame > 39) frame = 0;
//                if(frame2 > 39) frame2 = 0;
//                if(frame3 > 39) frame3 = 0;
//                if(frame4 > 39) frame4 = 0;
//
//                int frameRow = frame / 20;
//                int frameCol = frame % 20;
//                textures.britishMarineFire.setTextureRect(new IntRect(frameCol * 65, frameRow * 185, 65, 185));
//
//                int frameRow2 = frame2 / 20;
//                int frameCol2 = frame2 % 20;
//                textures.frenchMarineFire.setTextureRect(new IntRect(frameCol2 * 65, frameRow2 * 185, 65, 185));
//
//
//                int frameRow3 = frame3 / 20;
//                int frameCol3 = frame3 % 20;
//                textures.spanishMarineFire.setTextureRect(new IntRect(frameCol3 * 65, frameRow3 * 185, 65, 185));
//
//                int frameRow4 = frame4 / 20;
//                int frameCol4 = frame4 % 20;
//                textures.neutralMarineFire.setTextureRect(new IntRect(frameCol4 * 65, frameRow4 * 185, 65, 185));
//            }
            // jack: frame test end


            // NOTE: States must call window.display() and poll for relevant events themselves
        }
    }

    public static void main(String[] args) {
        GameDriver driver = new GameDriver();
        driver.run();
    }
}
