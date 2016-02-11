import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;

import java.util.Random;

/**
 * Game state class for Endless Sea
 */
public class Game extends FSMState {
    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;
    private Random randGenerator;

    private PlayerShip playerShip;
    private EnemyShip enemyShip;

    public Game(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures){
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;
        randGenerator = new Random();
        setup();
    }

    public void setup(){
        playerShip = new PlayerShip(textures, driver, window, (float)0.5, 500, 300);
        enemyShip = new EnemyShip(textures, driver, window, EnemyShip.ShipType.STANDARD, (float)0.5, 500, 800);
    }

    @Override
    // Update this method with what should be added to the window (using window variable)
    public void execute() {
        textures.ocean.setPosition(driver.getWinWidth() / 2, driver.getWinHeight() / 2);
        window.draw(textures.ocean);
        playerShip.draw();
        enemyShip.draw();

        window.display();

        if(!playerShip.isGunLoaded())
            playerShip.checkReload();

        if(!enemyShip.isGunLoaded())
            enemyShip.checkReload();
        else
            actionAI();     //NOTE AI WILL ATTACK AS SOON AS GUNS RELOAD - CHANGE THIS LATER TO WAIT RANDOM TIME BASED ON DIFFICULTY

        for(Event event : window.pollEvents()){
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case MOUSE_BUTTON_PRESSED:
                    int xPos = event.asMouseEvent().position.x;
                    int yPos = event.asMouseEvent().position.y;
                    ShipSection clicked = enemyShip.validateClicked(playerShip, xPos, yPos);
                    if(clicked != null){
                        playerShip.attack(clicked);
                        checkWin();
                    }
                    break;
                    // Not sure why this doesn't
                case KEY_PRESSED:
                    KeyEvent keyEvent = event.asKeyEvent();
                    if (keyEvent.key == Keyboard.Key.ESCAPE) {
                        stateMachine.setState(stateMachine.getStates().get(0));
                        break;
                    } else if (keyEvent.key == Keyboard.Key.M) {
                        stateMachine.setState(stateMachine.getStates().get(3));
                    }
                    break;
            }
        }
    }

    public void actionAI(){
        // choose to attack or run away
        if(enemyShip.getHullHP() < 20)
            System.out.println("RUN AWAY!");
        else{
            enemyShip.attack(playerShip);
            if(playerShip.getHullHP() <= 0){
                stateMachine.setState(stateMachine.getStates().get(4));
            }
        }
    }

    public void checkWin(){
        if(enemyShip.getHullHP() <= 0){
            // Create new temporary success state & move to it
            FSMState success = new SuccessState(stateMachine, driver, window, textures);
            stateMachine.setState(success);
        }
    }
}
