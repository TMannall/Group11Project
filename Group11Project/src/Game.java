import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;

/**
 * Game state class for Endless Sea
 */
public class Game extends FSMState {
    private FSM stateMachine;
    private GameDriver driver;
    private RenderWindow window;
    private Textures textures;

    private Ship playerShip;
    private Ship enemyShip;

    public Game(FSM stateMachine, GameDriver driver, RenderWindow window, Textures textures){
        this.stateMachine = stateMachine;
        this.driver = driver;
        this.window = window;
        this.textures = textures;

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

        for(Event event : window.pollEvents()){
            switch (event.type) {
                case CLOSED:
                    window.close();
                    break;
                case MOUSE_BUTTON_PRESSED:
                    int xPos = event.asMouseEvent().position.x;
                    int yPos = event.asMouseEvent().position.y;
                    enemyShip.validateClick(xPos, yPos);
            }
        }
    }
}
