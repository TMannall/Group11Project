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

    private Ship playerShip;
    private Ship enemyShip;

    public Game(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures){
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;
        randGenerator = new Random();
        setup();
    }

    public void setup(){
        playerShip = new Ship(textures, driver, window, Ship.ShipType.PLAYER, (float)0.5, 500, 300);
        enemyShip = new Ship(textures, driver, window, Ship.ShipType.STANDARD, (float)0.5, 500, 800);
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

        for(Event event : window.pollEvents()){
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case MOUSE_BUTTON_PRESSED:
                    int xPos = event.asMouseEvent().position.x;
                    int yPos = event.asMouseEvent().position.y;
                    ShipSection clicked = enemyShip.validateClick(xPos, yPos);
                    if(clicked != null)
                        attack(clicked);
                    // Not sure why this doesn't
//                case KEY_PRESSED:
//                    KeyEvent keyEvent = event.asKeyEvent();
//                    if (keyEvent.key == Keyboard.Key.ESCAPE) {
//                        stateMachine.setState(stateMachine.getStates().get(0));
//                        break;
//                    } else if (keyEvent.key == Keyboard.Key.M) {
//                        stateMachine.setState(stateMachine.getStates().get(3));
//                    }

            }
        }
    }

    public void attack(ShipSection clicked) {
        if (!playerShip.isGunLoaded()){
            System.out.println("CANNONS STILL RELOADING!");
            return;
        }
        if(clicked.isTargetable()){
            System.out.println("---------------------------------");
            System.out.println("ENEMY " + clicked.getType() + " CLICKED!");
            System.out.println(clicked.getType() + "HP: " + clicked.getHP());

            System.out.println("FIRING GUNS!");
            int dmg = (randGenerator.nextInt(15 - 10 + 1) + 10) * playerShip.getGunStr(); // Random damage between 10 and 15, multiplied by gunStr modifier
            System.out.println("HIT FOR " + dmg + " DMG");
            clicked.damage(dmg);
            playerShip.setGunLoaded(false);
            playerShip.getReloadTimer().restart();

            System.out.println(clicked.getType() + "HP: " + clicked.getHP());
            System.out.println("---------------------------------");
        }
        else{
            System.out.println("---------------------------------");
            System.out.println(clicked.getType() + " HAS BEEN DESTROYED! CANNOT TARGET!");
            System.out.println("---------------------------------");
        }
    }
}