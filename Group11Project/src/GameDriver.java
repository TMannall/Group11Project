/**
 * Driver for Endless Sea
 */

import org.jsfml.graphics.*;
import org.jsfml.window.*;

import java.util.List;
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
    private FSMState settings;
    private FSMState map;
    private FSMState gameover;
    private FSMState blank;
    private FSMState combatEvents;
    private FSMState leaderboard;
    private FSMState assExpEvents;
    private FSMState textEvents;
    private FSMState tradeEvents;
    private EventExampleDriver eventDriver = new EventExampleDriver();

    // jack: sprite testing
    public List<Sprite> marineList = new ArrayList<>();
    // jack: end sprite testing

    public void run(){
        // Initial setup
        RenderWindow window = new RenderWindow();
        window.create(new VideoMode(WIN_WIDTH, WIN_HEIGHT), TITLE, WindowStyle.DEFAULT);
        window.setFramerateLimit(30);

        Textures textures = new Textures();
        machine = new FSM();
        menu = new Menu(machine, driver, window, textures, eventDriver);
        settings = new Settings(machine, driver, window, textures);
        game = new Game(machine, driver, window, textures, eventDriver);
        map = new Map(machine, driver, window, textures, eventDriver);
        gameover = new GameOver(machine, driver, window, textures);
        blank = new BlankState(machine, driver, window, textures, eventDriver);
        combatEvents = new CombatEventState(machine, driver, window, textures, eventDriver);
        leaderboard = new LeaderboardDisplay(machine, driver, window, textures);
        assExpEvents = new AssExpEventState(machine, driver, window, textures, eventDriver);
        textEvents = new AssExpEventState(machine, driver, window, textures, eventDriver);
        tradeEvents = new AssExpEventState(machine, driver, window, textures, eventDriver);

        // Add all states the FSM controls to its ArrayList for access later
        machine.getStates().add(menu);
        machine.getStates().add(settings);
        machine.getStates().add(game);
        machine.getStates().add(map);
        machine.getStates().add(gameover);
        machine.getStates().add(blank);
        machine.getStates().add(combatEvents);
        machine.getStates().add(leaderboard);
        machine.getStates().add(assExpEvents);
        machine.getStates().add(textEvents);
        machine.getStates().add(tradeEvents);

        // Set menu state for game launch
        machine.setState(menu);

        // jack: frame test
//        SoundClass sound = new SoundClass();
//        sound.setSoundVolume(60);
//        Clock animClock = new Clock();
//        int[] frameList = new int[5];
//        for(int i = 0; i < 5; i++) {
//            marineList.add(i, textures.createSprite(textures.britishMarine, 0, 0, 65, 185));
//            frameList[i] = new Random().nextInt(39);
//        }
        // jack: frame test end


        // Game loop
        while(window.isOpen()){
            window.clear(Color.WHITE);

            // jack: frame test
//            if (animClock.getElapsedTime().asMicroseconds() >= 50) {
//                for(int i = 0; i < 5; i++) {
//                    animClock.restart();
//                    frameList[i] = frameList[i] + 1;
//
//                    if (frameList[i] == 5)
//                        sound.playSoundOnce("gun_01");
//
//                    if (frameList[i] > 39)
//                        frameList[i] = 0;
//
//                    int frameRow = frameList[i] / 20;
//                    int frameCol = frameList[i] % 20;
//                    marineList.get(i).setTextureRect(new IntRect(frameCol * 65, frameRow * 185, 65, 185));
//                }
//            }
            // jack: frame test end

            // Add to window relevant objects depending on state
            machine.run();


            // NOTE: States must call window.display() and poll for relevant combatEvents themselves
        }
    }

    public static void main(String[] args) {
        GameDriver driver = new GameDriver();
        driver.run();
    }
}
