/**
 * Driver and Main function for The Endless Sea Game
 */

import org.jsfml.graphics.*;
import org.jsfml.graphics.Color;
import org.jsfml.window.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

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

    public Color BROWN = new Color(56, 35, 5);

    private FSM machine;
    protected EventGenerator eventGenerator;

    private FSMState menu;
    private FSMState settings;
    private FSMState leaderboard;
    private FSMState cptSelection;
    protected FSMState map;
    private FSMState encounter;         // Current encounter event being played
    private FSMState afterEvent;
    private FSMState gameover;
    private FSMState instructions;

    private RenderWindow window = new RenderWindow();
    private Textures textures = new Textures();
    private Random randGenerator = new Random();
    private SoundFX sound = new SoundFX();

    //private PlayerShip playerShip;
    private PlayerShip playerShip = new PlayerShip(textures, driver, window, randGenerator, sound, Ship.ShipType.PLAYER, (float)0.5, 800, 1020);

    protected Leaderboard leaderboardObj = new Leaderboard();
    // jack: sprite testing
    //public List<Sprite> marineList = new ArrayList<>();
    // jack: end sprite testing

    public void run(){
        // Initial setup
        window.create(new VideoMode(WIN_WIDTH, WIN_HEIGHT), TITLE, WindowStyle.DEFAULT);
        window.setFramerateLimit(30);

        // some quick icon loading thing, it's kind of ugly
        try {
            Image icon = new Image();
            icon.loadFromFile(Paths.get("textures/icon.png"));
            window.setIcon(icon);
        } catch(IOException e) {
            System.err.println("ERROR: problem loading application icon");
        }

        machine = new FSM();
        eventGenerator = new EventGenerator(machine, driver, window, randGenerator, sound, textures);

        // States
        menu = new Menu(machine, driver, window, textures, sound);
        settings = new Settings(machine, driver, window, textures, sound);
        leaderboard = new LeaderboardDisplay(machine, driver, window, textures, leaderboardObj);
        map = new Map(machine, driver, window, textures, eventGenerator);
        gameover = new GameOver(machine, driver, window, textures, leaderboardObj, GameOver.Reason.DEFAULT);
        cptSelection = new CptSelection(machine, driver, window, textures, sound);
        instructions = new Instructions(machine, driver, window, textures);

        // Add all states the FSM controls to its ArrayList for access later
        machine.getStates().add(menu);
        machine.getStates().add(settings);
        machine.getStates().add(leaderboard);
        machine.getStates().add(cptSelection);
        machine.getStates().add(map);
        machine.getStates().add(encounter);
        machine.getStates().add(afterEvent);
        machine.getStates().add(gameover);
        machine.getStates().add(instructions);

        // Set menu state for game launch
        machine.setState(menu);

        // jack: frame test
//        SoundFX sound = new SoundFX();
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

    public FSMState getEncounter(){
        return encounter;
    }

    public void setEncounter(FSMState encounter){
        this.encounter = encounter;
    }

    public PlayerShip getPlayerShip(){
        return playerShip;
    }

    public void genNewPlayer(){
        playerShip = new PlayerShip(textures, driver, window, randGenerator, sound, Ship.ShipType.PLAYER, (float)0.5, 800, 1020);
    }
}
