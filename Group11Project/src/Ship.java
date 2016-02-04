import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;

public class Ship{
    private Textures textures;
    private GameDriver driver;
    private RenderWindow window;

    private int hullHP;  // Overall ship integrity; 0 = game over, ship sinks
    private int gunStr; // Cannon strength (modifies damage dealt)

    public ShipSection guns;
    private ShipSection masts;
    private ShipSection bridge;
    private ShipSection hold;
    private ShipSection quarters;

    private enum Sections{GUNS, MASTS, BRIDGE, HOLD, QUARTERS};

    public Ship(Textures textures, GameDriver driver, RenderWindow window){
        this.textures = textures;
        this.driver = driver;
        this.window = window;
        guns = new ShipSection(textures, driver, window, textures.shipGunDeck);
        masts = new ShipSection(textures, driver, window, textures.shipMasts);
        bridge = new ShipSection(textures, driver, window, textures.shipBridge);
        hold = new ShipSection(textures, driver, window, textures.shipSupplies);
        quarters = new ShipSection(textures, driver, window, textures.shipMedical);
    }


    // CHANGE THIS SO YOU CAN JUST SET POSITION OF THE SHIP AND ALL SECTIONS ARE POSITIONED AUTOMATICALLY TO THAT SECTION
    public void setPos(Sections section, float xPos, float yPos){
        switch(section){
            case GUNS:
                guns.sprite.setPosition(xPos,yPos);
                break;
            case MASTS:
                masts.sprite.setPosition(xPos, yPos);
                break;
            case BRIDGE:
                bridge.sprite.setPosition(xPos, yPos);
                break;
            case HOLD:
                hold.sprite.setPosition(xPos, yPos);
                break;
            case QUARTERS:
                quarters.sprite.setPosition(xPos, yPos);
        }
    }

    public void draw(){
       // guns.sprite.setOrigin(0,0);
        guns.sprite.setPosition(driver.getWinWidth()/2, driver.getWinHeight()/2);
        window.draw(guns.sprite);
        window.draw(masts.sprite);
        window.draw(bridge.sprite);
        window.draw(hold.sprite);
        window.draw(quarters.sprite);
    }
}
